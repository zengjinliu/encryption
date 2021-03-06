package web.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.support.DatabaseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;


/**
 * <br>{@link EnableBatchProcessing}用于开启批处理作业的配置,并且自动补全缺漏配置。
 * <br>{@link EnableScheduling} 用于开启定时任务的配置
 *
 * @author 刘玉祥
 * @date 2019/9/11 15:11
 */
@Configuration
@EnableBatchProcessing
@EnableScheduling
public class SpringBatchConfig {

    @Bean("jobRepository")
    protected JobRepository jobRepository(DataSource dataSource,  @Qualifier("transactionManager") PlatformTransactionManager transactionManager) throws Exception {

        JobRepositoryFactoryBean jobRepositoryFactoryBean = new JobRepositoryFactoryBean();
        //注入事务管理
        jobRepositoryFactoryBean.setTransactionManager(transactionManager);
        jobRepositoryFactoryBean.setDataSource(dataSource);
        jobRepositoryFactoryBean.setDatabaseType(DatabaseType.ORACLE.getProductName());
        //设置为oracle 事务级别
        //spring batch 默认是 ISOLATION_SERIALIZABLE,设置为ISOLATION_READ_COMMITTED
        jobRepositoryFactoryBean.setIsolationLevelForCreate("ISOLATION_READ_COMMITTED");
        return jobRepositoryFactoryBean.getObject();
    }


    @Bean("jobBuilderFactory")
    @Autowired
    protected JobBuilderFactory jobBuilderFactory(@Qualifier("jobRepository") JobRepository jobRepository) {

        JobBuilderFactory jobBuilderFactory = new JobBuilderFactory(jobRepository);
        return jobBuilderFactory;

    }

    @Bean("stepBuilderFactory")
    protected StepBuilderFactory stepBuilderFactory(@Qualifier("jobRepository")JobRepository jobRepository, @Qualifier("transactionManager") PlatformTransactionManager transactionManager) {
        StepBuilderFactory stepBuilderFactory = new StepBuilderFactory(jobRepository, transactionManager);
        return stepBuilderFactory;
    }

    @Autowired
    @Bean("jobLauncher")
    protected SimpleJobLauncher jobLauncher(@Qualifier("taskExecutor") ThreadPoolTaskExecutor executor, @Qualifier("jobRepository") JobRepository jobRepository) {

        SimpleJobLauncher simpleJobLauncher = new SimpleJobLauncher();
        simpleJobLauncher.setJobRepository(jobRepository);
        simpleJobLauncher.setTaskExecutor(executor);

        return simpleJobLauncher;


    }

    /**
     * Mybatis ItemReader 其中queryId：对应Mapper xml文件中的id，此处为空，因要在代码中动态填充，所以该bean为多例，需从上下文中
     * 获取
     *
     * @param sqlSessionFactory
     * @return MyBatisPagingItemReader
     */
    @Bean("myBatisItemReader")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    protected MyBatisPagingItemReader getMyBatisPagingItemReader(SqlSessionFactory sqlSessionFactory) {
        MyBatisPagingItemReader myBatisPagingItemReader = new MyBatisPagingItemReader();
        myBatisPagingItemReader.setSqlSessionFactory(sqlSessionFactory);
        myBatisPagingItemReader.setPageSize(1000);
        return myBatisPagingItemReader;
    }
}
