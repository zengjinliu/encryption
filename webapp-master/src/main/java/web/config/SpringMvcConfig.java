package web.config;


import cn.kiway.webapp.util.DateFormatFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;


import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link EnableWebMvc} 相当于mvc:annotation-driven
 *
 * @author minte
 * @date 2019/8/21 09:16
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"cn.kiway.webapp.**.controller","cn.kiway.webapp.**.advice"},
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION, value = {Controller.class}),
                @ComponentScan.Filter(type = FilterType.ANNOTATION, value = {ControllerAdvice.class})
        },
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = {Service.class})},
        useDefaultFilters = false)
public class SpringMvcConfig extends WebMvcConfigurerAdapter implements ServletContextAware {

    private ServletContext servletContext;

    @Autowired
    RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    @Autowired
    MappingJackson2HttpMessageConverter jsonConverter;


    @PostConstruct
    private void init() {
        //删除原有 Jackson2HttpMessageConverter
        List<HttpMessageConverter<?>> converters = requestMappingHandlerAdapter.getMessageConverters();
        converters.removeIf(msg -> msg instanceof MappingJackson2HttpMessageConverter);
        //新增Jackson2HttpMessageConverter
        converters.add(jsonConverter);
    }

    /**
     * 注册springMVC拦截器
     *
     * @param registry 拦截器注册类
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则, 这里假设拦截 /** 后面的全部链接
        // excludePathPatterns 用户排除拦截
        // registry.addInterceptor(encryptionInterceptorAdapter).addPathPatterns("/*");
        super.addInterceptors(registry);
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }


    @Bean
    @Profile("jsp")
    public ViewResolver jspViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        viewResolver.setContentType("ext/html;charset=UTF-8");
        viewResolver.setExposePathVariables(true);
        return viewResolver;
    }


    /**
     * 配置静态资源
     *
     * @param configurer
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();

    }

    /**
     * 处理静态资源
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/js/**").addResourceLocations("/js/");
        registry.addResourceHandler("/img/**").addResourceLocations("/img/");
        super.addResourceHandlers(registry);
    }


    /**
     * jackson 解析模板 ，支持JDK8 时间处理
     *
     * @return ObjectMapper
     */
    @Bean
    protected ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new ParameterNamesModule())
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule());
        objectMapper.setDateFormat(DateFormatFactory.instance().getSdf("yyyy-MM-dd HH:mm:ss"));
        return objectMapper;

    }


    /**
     * 注册自定义MappingJackson2HttpMessageConverter 消息解析器。
     *
     * @param objectMapper
     * @return
     */
    @Bean
    protected MappingJackson2HttpMessageConverter registerMappingJackson2HttpMessageConverter(ObjectMapper objectMapper) {
        MappingJackson2HttpMessageConverter jsonConverter
                = new MappingJackson2HttpMessageConverter();
        //MappingJackson2HttpMessageConverter接收JSON类型消息的转换
        MediaType mediaType = MediaType.APPLICATION_JSON_UTF8;
        List<MediaType> mediaTypes = new ArrayList<MediaType>();
        mediaTypes.add(mediaType);
        //加入转换器的支持类型
        jsonConverter.setSupportedMediaTypes(mediaTypes);
        //解析映射模板
        jsonConverter.setObjectMapper(objectMapper);

        return jsonConverter;

    }


}
