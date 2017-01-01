package iglabs.assistant.jsp.beans;

import java.util.List;

import iglabs.assistant.jsp.models.Order;
import iglabs.assistant.jsp.models.OrderItem;
import iglabs.assistant.jsp.persistence.JdbcOrderItemsDao;
import iglabs.assistant.jsp.persistence.JdbcOrdersDao;
import iglabs.assistant.jsp.persistence.JndiDataSourceFactory;
import iglabs.assistant.jsp.persistence.OrderItemsDao;
import iglabs.assistant.jsp.persistence.OrdersDao;

public class OrderDetailsBean {
	private final OrdersDao ordersDao;
	private final OrderItemsDao orderItemsDao;
	
	private int orderId;
	
	public OrderDetailsBean() {
		JdbcOrdersDao ordersDao = new JdbcOrdersDao();
		ordersDao.setDataSourceFactory(new JndiDataSourceFactory());
		
		JdbcOrderItemsDao orderItemsDao = new JdbcOrderItemsDao();
		orderItemsDao.setDataSourceFactory(new JndiDataSourceFactory());
		
		this.ordersDao = ordersDao;
		this.orderItemsDao = orderItemsDao;
	}
	
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	
	public int getOrderId() {
		return orderId;
	}
	
	public Order getOrder() {
		return ordersDao.get(orderId);
	}
	
	public List<OrderItem> getOrderItems() {
		return orderItemsDao.listByOrderId(orderId);
	}
	
	public double getTotalPrice(List<OrderItem> orderItems) {
		double result = 0.;
		for (OrderItem orderItem: orderItems) {
			result += orderItem.getPrice() * orderItem.getQuantity();
		}
		
		return result;
	}
}
