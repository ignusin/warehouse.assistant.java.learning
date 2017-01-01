package iglabs.assistant.jsp.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import iglabs.assistant.jsp.parsing.Parser;

public class ApplicationListener implements ServletContextListener {
	
	@Override
	public void contextInitialized(ServletContextEvent args) {
		Parser.registerDefaultTypeParsers();
	}

	@Override
	public void contextDestroyed(ServletContextEvent args) {
	}

}
