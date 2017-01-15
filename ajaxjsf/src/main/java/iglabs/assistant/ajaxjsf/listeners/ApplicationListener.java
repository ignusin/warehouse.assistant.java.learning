package iglabs.assistant.ajaxjsf.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import iglabs.assistant.ajaxjsf.persistence.EntityManagerFactoryHolder;

public class ApplicationListener implements ServletContextListener {
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		EntityManagerFactoryHolder.getInstance().init();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}
}
