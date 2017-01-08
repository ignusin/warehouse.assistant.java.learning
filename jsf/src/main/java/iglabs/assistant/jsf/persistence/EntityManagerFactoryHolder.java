package iglabs.assistant.jsf.persistence;

import javax.persistence.EntityManagerFactory;

import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AvailableSettings;

import iglabs.assistant.jsf.model.Item;
import iglabs.assistant.jsf.model.Order;
import iglabs.assistant.jsf.model.OrderItem;

public class EntityManagerFactoryHolder {
	private static final EntityManagerFactoryHolder instance = new EntityManagerFactoryHolder();
	
	public static EntityManagerFactoryHolder getInstance() {
		return instance;
	}
	
	
	private EntityManagerFactory emFactory = null;
	
	private EntityManagerFactoryHolder() {
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
			.addAnnotatedClass(Order.class)
			.addAnnotatedClass(OrderItem.class)
			.buildMetadata();
		
		emFactory = metadata.buildSessionFactory();
	}
	
	public EntityManagerFactory getEntityManagerFactory() {
		return emFactory;
	}
}
