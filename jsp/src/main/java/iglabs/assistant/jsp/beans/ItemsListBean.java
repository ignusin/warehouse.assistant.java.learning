package iglabs.assistant.jsp.beans;

import java.util.List;

import iglabs.assistant.jsp.models.Item;
import iglabs.assistant.jsp.persistence.ItemsDao;
import iglabs.assistant.jsp.persistence.JdbcItemsDao;
import iglabs.assistant.jsp.persistence.JndiDataSourceFactory;

public class ItemsListBean {
	private ItemsDao itemsDao;
	
	
	public ItemsListBean() {
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
		return getItemsDao().list();
	}
}
