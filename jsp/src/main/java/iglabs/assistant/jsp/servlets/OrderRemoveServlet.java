package iglabs.assistant.jsp.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import iglabs.assistant.jsp.models.Item;
import iglabs.assistant.jsp.models.OrderItem;
import iglabs.assistant.jsp.persistence.ItemsDao;
import iglabs.assistant.jsp.persistence.JdbcItemsDao;
import iglabs.assistant.jsp.persistence.JdbcOrderItemsDao;
import iglabs.assistant.jsp.persistence.JdbcOrdersDao;
import iglabs.assistant.jsp.persistence.JndiDataSourceFactory;
import iglabs.assistant.jsp.persistence.OrderItemsDao;
import iglabs.assistant.jsp.persistence.OrdersDao;


public class OrderRemoveServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String TEMPLATE_URL = "/WEB-INF/templates/order-remove.jsp";
	
	private ItemsDao itemsDao;
	private OrdersDao ordersDao;
	private OrderItemsDao orderItemsDao;
		
	@Override
	public void init() {
		JdbcItemsDao itemsDao = new JdbcItemsDao();
		itemsDao.setDataSourceFactory(new JndiDataSourceFactory());
		
		JdbcOrdersDao ordersDao = new JdbcOrdersDao();
		ordersDao.setDataSourceFactory(new JndiDataSourceFactory());
		
		JdbcOrderItemsDao orderItemsDao = new JdbcOrderItemsDao();
		orderItemsDao.setDataSourceFactory(new JndiDataSourceFactory());
		
		this.itemsDao = itemsDao;
		this.ordersDao = ordersDao;
		this.orderItemsDao = orderItemsDao;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		
		request.getRequestDispatcher(TEMPLATE_URL).forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		
		int orderId = Integer.parseInt(request.getParameter("id"));
		
		List<OrderItem> orderItems = orderItemsDao.listByOrderId(orderId);
		for (OrderItem orderItem: orderItems) {
			Item item = itemsDao.get(orderItem.getItemId());
			if (item != null) {
				item.setInStock(item.getInStock() + orderItem.getQuantity());
				itemsDao.update(item);
			}
			
			orderItemsDao.remove(orderItem.getId());
		}
		
		ordersDao.remove(orderId);
		
		response.sendRedirect(getServletContext().getContextPath() + "/pages/orders.jsp");
	}
}
