package iglabs.assistant.jsp.beans;

import java.util.List;

import iglabs.assistant.jsp.models.Order;
import iglabs.assistant.jsp.persistence.JdbcOrdersDao;
import iglabs.assistant.jsp.persistence.JndiDataSourceFactory;
import iglabs.assistant.jsp.persistence.OrdersDao;

public class OrdersListBean {
	private OrdersDao ordersDao;
	
	
	public OrdersListBean() {
		JdbcOrdersDao ordersDao = new JdbcOrdersDao();
		ordersDao.setDataSourceFactory(new JndiDataSourceFactory());
		
		setOrdersDao(ordersDao);
	}
	
	public OrdersDao getOrdersDao() {
		return ordersDao;
	}
	
	public void setOrdersDao(OrdersDao ordersDao) {
		this.ordersDao = ordersDao;
	}
	
	public List<Order> getOrders() {
		return getOrdersDao().list();
	}
}
