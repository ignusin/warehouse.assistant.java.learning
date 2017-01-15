package iglabs.assistant.ajaxjsf.persistence;

import javax.persistence.EntityManager;

public interface EntityManagerAction {
	void run(EntityManager session);
}
