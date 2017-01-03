package iglabs.assistant.jsf.persistence;

import org.hibernate.Session;

public interface SessionFunc<T> {
	T run(Session session);
}
