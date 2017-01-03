package iglabs.assistant.jsf.persistence;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AvailableSettings;

import iglabs.assistant.jsf.model.Item;

public class SessionFactoryHolder {
	private static final SessionFactoryHolder instance = new SessionFactoryHolder();
	
	public static SessionFactoryHolder getInstance() {
		return instance;
	}
	
	
	private SessionFactory sessionFactory = null;
	
	private SessionFactoryHolder() {
	}
	
	public void init() {
		StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
			.applySetting(AvailableSettings.DIALECT, "org.hibernate.dialect.PostgreSQL9Dialect")
			.applySetting(AvailableSettings.DRIVER, "org.postgresql.Driver")
			.applySetting(AvailableSettings.URL, "jdbc:postgresql://localhost:5432/assistant")
			.applySetting(AvailableSettings.USER, "postgres")
			.applySetting(AvailableSettings.PASS, "123qwe")
			.applySetting(AvailableSettings.CURRENT_SESSION_CONTEXT_CLASS, "thread")
			.build();
		
		Metadata metadata = new MetadataSources(registry)
			.addAnnotatedClass(Item.class)
			.buildMetadata();
		
		sessionFactory = metadata.buildSessionFactory();
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
