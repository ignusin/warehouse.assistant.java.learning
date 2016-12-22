package iglabs.assistant.jsp.persistence;

import java.util.List;

import iglabs.assistant.jsp.models.Entity;

public interface GenericDao<T extends Entity> {
	List<T> list();
	T get(int id);
	
	void add(T item);
	void update(T item);
	void remove(int id);
}
