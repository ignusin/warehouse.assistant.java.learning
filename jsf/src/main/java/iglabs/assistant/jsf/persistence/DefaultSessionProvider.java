package iglabs.assistant.jsf.persistence;

import org.hibernate.Session;

public class DefaultSessionProvider implements SessionProvider {
	@Override
	public void run(SessionAction action) {
		Session session = SessionFactoryHolder
			.getInstance()
			.getSessionFactory()
			.getCurrentSession();
		
		try {
			session.beginTransaction();
			action.run(session);
			session.getTransaction().commit();
		}
		catch (Exception ex) {
			if (session.getTransaction().isActive()) {
				session.getTransaction().rollback();
			}
			
			throw new RuntimeException(ex);
		}
	}
	
	@Override
	public <T> T run(SessionFunc<T> func) {
		Session session = SessionFactoryHolder
				.getInstance()
				.getSessionFactory()
				.getCurrentSession();
			
		try {
			session.beginTransaction();
			T result = func.run(session);
			session.getTransaction().commit();
			
			return result;
		}
		catch (Exception ex) {
			if (session.getTransaction().isActive()) {
				session.getTransaction().rollback();
			}
			
			throw new RuntimeException(ex);
		}
	}
}
