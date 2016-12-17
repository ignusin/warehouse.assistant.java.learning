package iglabs.assistant.jsp.persistence;

import java.util.List;
import iglabs.assistant.jsp.model.Item;


public interface ItemsDao {
	List<Item> getItems();
}
