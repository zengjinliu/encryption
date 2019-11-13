package web.config;


import cn.kiway.webapp.filter.RequestDecryptFilter;
import cn.kiway.webapp.listener.servlet.Log4jDestroyedListener;
import net.sf.ehcache.constructs.web.ShutdownListener;
import org.springframework.web.context.ContextCleanupListener;

import org.springframework.web.context.request.RequestContextListener;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import org.springframework.web.util.IntrospectorCleanupListener;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;


/**
 * web配置
 *
 * @author 刘玉祥
 * @date 2019/8/21 09:05
 */
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {


    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        addInitParameter(servletContext);
        addCharacterEncodingFilter(servletContext);
        addDecryptFilter(servletContext);
        addIntrospectorCleanupListener(servletContext);
        addEhcacheShutdownListener(servletContext);
        addContextCleanupListener(servletContext);
        addRequestContextListener(servletContext);
        addLog4jDestroyedListener(servletContext);
        super.onStartup(servletContext);
    }

    /**
     * 添加<context-param>
     *
     * @param servletContext 上下文
     */
    public void addInitParameter(ServletContext servletContext) {
        servletContext.setInitParameter("log4jConfiguration", "classpath:log4j2.xml");

    }

    /**
     * 注册字符集设定拦截器，字符集设定为UTF-8
     *
     * @param servletContext 上下文
     */
    public void addCharacterEncodingFilter(ServletContext servletContext) {

        FilterRegistration.Dynamic encodingFilter = servletContext.addFilter("CharacterEncodingFilter", CharacterEncodingFilter.class);
        encodingFilter.setInitParameter("encoding", "UTF-8");
        encodingFilter.setInitParameter("forceEncoding", "true");
        encodingFilter.addMappingForUrlPatterns(null, false, "/*");
    }

    /**
     * 解密拦截器
     *
     * @param servletContext 上下文
     */

    public void addDecryptFilter(ServletContext servletContext) {

        FilterRegistration.Dynamic decryptFilter = servletContext.addFilter("requestDecryptFilter", RequestDecryptFilter.class);
        decryptFilter.addMappingForUrlPatterns(null, false, "/*");
    }

    /**
     * 刷新缓存
     *
     * @param servletContext 上下文
     */
    public void addIntrospectorCleanupListener(ServletContext servletContext) {
        servletContext.addListener(IntrospectorCleanupListener.class);
    }

    /**
     * 加载日志配置文件
     *
     * @param servletContext 上下文
     */
    public void addLog4jDestroyedListener(ServletContext servletContext) {
        servletContext.addListener(Log4jDestroyedListener.class);

    }

    /**
     * 释放ehcache资源
     *
     * @param servletContext 上下文
     */
    public void addEhcacheShutdownListener(ServletContext servletContext) {
        servletContext.addListener(ShutdownListener.class);
    }

    /**
     * 容器销毁时任务
     *
     * @param servletContext 上下文
     */
    public void addContextCleanupListener(ServletContext servletContext) {
        servletContext.addListener(ContextCleanupListener.class);

    }

    /**
     * spring bean 作用域 request、session、global session 中有效声明
     *
     * @param servletContext 上下文
     */
    public void addRequestContextListener(ServletContext servletContext) {
        servletContext.addListener(RequestContextListener.class);
    }


    /**
     * 加载spring 上下文
     *
     * @return
     */

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{SpringRootConfig.class};
    }

    /**
     * 加载springMVC 配置
     *
     * @return
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{SpringMvcConfig.class};
    }

    /**
     * DispatcherServlet的URI映射关系配置
     *
     * @return
     */
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/*"};
    }
}
