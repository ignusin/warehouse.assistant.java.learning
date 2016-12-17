package iglabs.assistant.jsp.beans;

import java.util.List;
import iglabs.assistant.jsp.model.Item;
import iglabs.assistant.jsp.persistence.ItemsDao;
import iglabs.assistant.jsp.persistence.JdbcItemsDao;
import iglabs.assistant.jsp.persistence.JndiDataSourceFactory;

public class ItemsBean {
	private ItemsDao itemsDao;
	private List<Item> items;
	
	
	public ItemsBean() {
		JdbcItemsDao itemsDao = new JdbcItemsDao();
		itemsDao.setDataSourceFactory(new JndiDataSourceFactory());
		
		setItemsDao(itemsDao);
	}
	
	public ItemsDao getItemsDao() {
		return itemsDao;
	}
	
	public void setItemsDao(ItemsDao itemsDao) {
		this.itemsDao = itemsDao;
	}
	
	public List<Item> getItems() {
		if (items == null) {
			items = getItemsDao().getItems();
		}
		
		return items;
	}
}
