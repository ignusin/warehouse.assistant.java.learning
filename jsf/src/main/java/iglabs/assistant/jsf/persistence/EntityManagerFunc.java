package iglabs.assistant.jsf.persistence;

import javax.persistence.EntityManager;

public interface EntityManagerFunc<T> {
	T run(EntityManager em);
}
