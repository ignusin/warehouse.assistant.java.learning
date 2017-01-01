package iglabs.assistant.jsp.beans;

import java.util.List;

import iglabs.assistant.jsp.models.Item;
import iglabs.assistant.jsp.persistence.ItemsDao;
import iglabs.assistant.jsp.persistence.JdbcItemsDao;
import iglabs.assistant.jsp.persistence.JndiDataSourceFactory;

public class ItemsListBean {
	private final ItemsDao itemsDao;
	
	
	public ItemsListBean() {
		JdbcItemsDao itemsDao = new JdbcItemsDao();
		itemsDao.setDataSourceFactory(new JndiDataSourceFactory());
		
		this.itemsDao = itemsDao;
	}
	
	public List<Item> getItems() {
		return itemsDao.list();
	}
}
