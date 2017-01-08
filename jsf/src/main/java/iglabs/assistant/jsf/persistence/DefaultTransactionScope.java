package iglabs.assistant.jsf.persistence;

import javax.persistence.EntityManager;

public class DefaultTransactionScope implements TransactionScope {
	@Override
	public void run(EntityManagerAction action) {
		EntityManager em = EntityManagerFactoryHolder
			.getInstance()
			.getEntityManagerFactory()
			.createEntityManager();
		
		try {
			em.getTransaction().begin();
			action.run(em);
			em.getTransaction().commit();
		}
		catch (Exception ex) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			
			throw new RuntimeException(ex);
		}
	}
	
	@Override
	public <T> T run(EntityManagerFunc<T> func) {
		EntityManager em = EntityManagerFactoryHolder
				.getInstance()
				.getEntityManagerFactory()
				.createEntityManager();
			
		try {
			em.getTransaction().begin();
			T result = func.run(em);
			em.getTransaction().commit();
			
			return result;
		}
		catch (Exception ex) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			
			throw new RuntimeException(ex);
		}
	}
}
