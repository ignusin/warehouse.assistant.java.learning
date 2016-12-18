package iglabs.assistant.jsp.persistence;

import java.util.List;

import iglabs.assistant.jsp.models.Item;


public interface ItemsDao {
	List<Item> getItems();
	Item get(int id);
	
	void add(Item item);
	void update(Item item);
}
