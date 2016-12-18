package iglabs.assistant.jsp.beans;

import iglabs.assistant.jsp.models.Item;
import iglabs.assistant.jsp.persistence.ItemsDao;
import iglabs.assistant.jsp.persistence.JdbcItemsDao;
import iglabs.assistant.jsp.persistence.JndiDataSourceFactory;

public class ItemFormBean {
	private ItemsDao itemsDao;
	private Item item;
	private int id;
	
	
	public ItemFormBean() {
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
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Item getItem() {
		if (item == null) {
			item = getItemsDao().get(id);
		}
		
		return item;
	}
}
