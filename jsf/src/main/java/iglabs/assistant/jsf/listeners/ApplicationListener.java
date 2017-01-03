package iglabs.assistant.jsf.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import iglabs.assistant.jsf.persistence.SessionFactoryHolder;

public class ApplicationListener implements ServletContextListener {
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		SessionFactoryHolder.getInstance().init();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}
}
