package cn.kiway.webapp.listener.servlet;



import org.apache.logging.log4j.LogManager;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author minte
 * @date 2019/9/17 08:31
 */
public class Log4jDestroyedListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        //停止日志
        LogManager.shutdown();
    }
}
