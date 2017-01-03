package iglabs.assistant.jsf.persistence;

import org.hibernate.Session;

public interface SessionAction {
	void run(Session session);
}
