package web.config;

import cn.kiway.webapp.model.ParamEncodeProperty;
import cn.kiway.webapp.util.SnowflakeIdWorker;
import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.io.DefaultVFS;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * spring 上下文，不扫描Controller层
 *
 * @author minte
 * @date 2019/8/21 09:08
 */
@Configuration
@EnableAsync
@EnableTransactionManagement
@EnableAspectJAutoProxy(exposeProxy = true)
@PropertySource(value = {"classpath:config/config.properties",
        "classpath:config/db.properties",
        "classpath:config/mybatisConfig.properties"
})
@Import({SpringBatchConfig.class})
@MapperScan({"cn.kiway.webapp.**.mapper"})
@ComponentScan(basePackages = {"cn.kiway.webapp.**"},
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION, value = {Controller.class}),
                @ComponentScan.Filter(type = FilterType.ANNOTATION, value = {ControllerAdvice.class})
        }
)
public class SpringRootConfig {

    private Logger logger = LoggerFactory.getLogger(SpringRootConfig.class);
    /**
     * 导入的属性文件中的属性会 注入到 Environment 中
     */

    @Resource
    private Environment env;

    @Resource
    private Properties projectInfo;

    @Autowired
    private ApplicationContext applicationContext;


    /**
     * 创建连接池
     *
     * @return 返回创建好的连接池交给spring 管理
     */

    @Scope
    @Lazy(false)
    @Bean("taskExecutor")
    protected ThreadPoolTaskExecutor getTaskExecutor(RejectedExecutionHandler rejectedExecutionHandler) {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(env.getProperty("threadPool.corePoolSize", Integer.class));
        threadPoolTaskExecutor.setQueueCapacity(env.getProperty("threadPool.queueCapacity", Integer.class));
        threadPoolTaskExecutor.setMaxPoolSize(env.getProperty("threadPool.maxPoolSize", Integer.class));
        threadPoolTaskExecutor.setKeepAliveSeconds(env.getProperty("threadPool.keepAliveSeconds", Integer.class));
        threadPoolTaskExecutor.setDaemon(env.getProperty("threadPool.daemon", Boolean.class));
        threadPoolTaskExecutor.setThreadNamePrefix(env.getProperty("threadPool.threadNamePrefix", String.class));
        threadPoolTaskExecutor.setRejectedExecutionHandler(rejectedExecutionHandler);
        return threadPoolTaskExecutor;
    }


    /**
     * 构造线程池处理例外策略 ，默认为CallerRunsPolicy策略
     *
     * @return RejectedExecutionHandler
     */
    @Bean
    protected RejectedExecutionHandler newInstanceExecutionHandler() {
        String rejectedExecutionHandlerClassName = env.getProperty("threadPool.rejectedExecutionHandler", String.class);

        //默认处理策略
        RejectedExecutionHandler rejectedExecutionHandler = new ThreadPoolExecutor.CallerRunsPolicy();
        try {
            if (!StringUtils.isEmpty(rejectedExecutionHandlerClassName)) {
                Class<?> handler = Class.forName(rejectedExecutionHandlerClassName);
                Object object = handler.newInstance();
                if (object instanceof RejectedExecutionHandler) {
                    rejectedExecutionHandler = (RejectedExecutionHandler) object;
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return rejectedExecutionHandler;
    }

    @Bean
    @Lazy(false)
    protected DataSource initDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(env.getProperty("db.oracle.url", String.class));
        dataSource.setUsername(env.getProperty("db.oracle.username", String.class));
        dataSource.setPassword(env.getProperty("db.oracle.password", String.class));
        dataSource.setMaxActive(env.getProperty("db.maxActive", Integer.class));
        dataSource.setInitialSize(env.getProperty("db.initialSize", Integer.class));
        dataSource.setMaxWait(env.getProperty("db.maxWait", Integer.class));
        dataSource.setMinIdle(env.getProperty("db.minIdle", Integer.class));
        dataSource.setTimeBetweenEvictionRunsMillis(env.getProperty("db.timeBetweenEvictionRunsMillis", Long.class));
        dataSource.setMinEvictableIdleTimeMillis(env.getProperty("db.minEvictableIdleTimeMillis", Long.class));
        dataSource.setTestWhileIdle(env.getProperty("db.testWhileIdle", Boolean.class));
        dataSource.setTestOnBorrow(env.getProperty("db.testOnBorrow", Boolean.class));
        dataSource.setTestOnReturn(env.getProperty("db.testOnReturn", Boolean.class));
        dataSource.setPoolPreparedStatements(env.getProperty("db.poolPreparedStatements", Boolean.class));
        dataSource.setMaxOpenPreparedStatements(env.getProperty("db.maxOpenPreparedStatements", Integer.class));
        dataSource.setProxyFilters(getDruidFilter());
        return dataSource;
    }

    /**
     * 获取spring bean 中所有的数据源拦截器
     *
     * @return
     */
    private List<Filter> getDruidFilter() {
        Map<String, Filter> filters = applicationContext.getBeansOfType(Filter.class);
        List<Filter> filterList = new ArrayList<>(filters.values());

        return filterList;
    }


    /**
     * druid监控统计bean 对应上文dataSource中的proxyFilters
     *
     * @return
     */
    @Bean("statFilter")
    protected StatFilter initStaFilter() {
        StatFilter statFilter = new StatFilter();
        statFilter.setLogSlowSql(true);
        statFilter.setSlowSqlMillis(1000);
        return statFilter;
    }


    @Bean("wallConfig")
    protected WallConfig initWallConfig() {
        WallConfig wallConfig = new WallConfig();
        wallConfig.setDir("META-INF/druid/wall/oracle");
        wallConfig.setFunctionCheck(true);
        wallConfig.setMultiStatementAllow(true);
        return wallConfig;
    }

    @Bean("wallFilter")
    protected WallFilter initWallFilter(WallConfig wallConfig) {
        WallFilter wallFilter = new WallFilter();
        wallFilter.setConfig(wallConfig);
        wallFilter.setDbType("oracle");

        return wallFilter;
    }


    @Bean("transactionManager")
    protected PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * 注册 SqlSessionFactory
     *
     * @param dataSource 数据源
     * @return
     * @throws Exception
     */
    @Bean
    protected SqlSessionFactory sessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setVfs(DefaultVFS.class);
        sessionFactoryBean.setConfigurationProperties(projectInfo);
        //加载mybatis配置文件
        sessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:config/mybatisConfiguration.xml"));
        //加载XML文件,用@MapperScan注解，该配置可以忽略
        //  sessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath*:cn/kiway/webapp/**/mapper/*.xml"));
        sessionFactoryBean.setPlugins(getMybatisInterceptor());
        return sessionFactoryBean.getObject();
    }

    /**
     * 注册批量操作 SqlSessionTemplate
     *
     * @param sqlSessionFactory
     * @return
     */
    @Bean("batchSqlSession")
    protected SqlSessionTemplate getSqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory, ExecutorType.BATCH);
        return sqlSessionTemplate;
    }


    /**
     * 获取所有的mybatis插件
     *
     * @return
     */
    private Interceptor[] getMybatisInterceptor() {
        Map<String, Interceptor> p = applicationContext.getBeansOfType(Interceptor.class);
        List<Interceptor> ps = new ArrayList<>(p.values());

        return ps.stream().toArray(Interceptor[]::new);

    }

    /**
     * 注册分页插件
     *
     * @return
     */
    @Bean
    protected PageInterceptor pagePlugins() {
        PageInterceptor pageInterceptor = new PageInterceptor();
        pageInterceptor.setProperties(projectInfo);
        return pageInterceptor;

    }


    /**
     * <br>注册事件监听注册中心
     * <br>该注册中心bean 的名字必须叫"applicationEventMulticaster",
     * 否则spring 容器会注册一个名字叫"applicationEventMulticaster"的bean，
     * 导致此异步调用失败
     *
     * @return SimpleApplicationEventMulticaster 注册中心给spring管理
     */
    @Scope
    @Lazy(false)
    @Bean("applicationEventMulticaster")
    public SimpleApplicationEventMulticaster getSimpleApplicationEventMulticaster(Executor threadPoolTaskExecutor) {
        SimpleApplicationEventMulticaster eventMulticaster = new SimpleApplicationEventMulticaster();
        eventMulticaster.setTaskExecutor(threadPoolTaskExecutor);
        return eventMulticaster;
    }

    /**
     * 注册默认主键生成类，
     *
     * @return {@link cn.kiway.webapp.util.SnowflakeIdWorker} 主键生成中心
     */
    @Bean("snowflakeIdWorker")
    protected SnowflakeIdWorker getSnowflakeIdWorker() {
        SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(1, 1);

        return snowflakeIdWorker;


    }

    /**
     * 初始化参数加密属性
     * @return  ParamEncodeProperty
     */
    @Bean
    protected ParamEncodeProperty initParamEncodeProperty() {

        String rsaPrivateKey = env.getProperty("web.filter.rsaPrivateKey");
        boolean isEncode = env.getProperty("web.filter.isEncode", Boolean.TYPE);
        String privilegeURI = env.getProperty("web.filter.privilegeURIs");

        ParamEncodeProperty property = new ParamEncodeProperty();
        property.setEncodeType(isEncode);
        property.setPrivilegeURI(privilegeURI);
        property.setRsaPrivateKey(rsaPrivateKey);
        return property;
    }
}
