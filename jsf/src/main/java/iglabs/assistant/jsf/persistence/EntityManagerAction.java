package iglabs.assistant.jsf.persistence;

import javax.persistence.EntityManager;

public interface EntityManagerAction {
	void run(EntityManager session);
}
