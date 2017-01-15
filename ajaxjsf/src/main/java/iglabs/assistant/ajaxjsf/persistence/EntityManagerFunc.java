package iglabs.assistant.ajaxjsf.persistence;

import javax.persistence.EntityManager;

public interface EntityManagerFunc<T> {
	T run(EntityManager em);
}
