package iglabs.assistant.jsp.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import iglabs.assistant.jsp.models.Item;
import iglabs.assistant.jsp.models.Order;
import iglabs.assistant.jsp.models.OrderItem;
import iglabs.assistant.jsp.parsing.Parser;
import iglabs.assistant.jsp.persistence.ItemsDao;
import iglabs.assistant.jsp.persistence.JdbcItemsDao;
import iglabs.assistant.jsp.persistence.JdbcOrderItemsDao;
import iglabs.assistant.jsp.persistence.JdbcOrdersDao;
import iglabs.assistant.jsp.persistence.JndiDataSourceFactory;
import iglabs.assistant.jsp.persistence.OrderItemsDao;
import iglabs.assistant.jsp.persistence.OrdersDao;
import iglabs.assistant.jsp.validation.ModelValidation;


public class OrderFormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String TEMPLATE_URL = "/WEB-INF/templates/order-form.jsp";

	private ItemsDao itemsDao;
	private OrdersDao ordersDao;
	private OrderItemsDao orderItemsDao;
	
	
	private HashMap<String, String> parseOrderData(HttpServletRequest request) {
		HashMap<String, String> orderData = new HashMap<>();
		
		String name = request.getParameter("name");
		if (name == null) {
			name = "";
		}
		
		orderData.put("name", name);
		
		return orderData;
	}
	
	private ArrayList<HashMap<String, String>> parseOrderItems(HttpServletRequest request) {
		ArrayList<String> paramNames = Collections.list(request.getParameterNames());
		
		int count = 0;
		for (String paramName: paramNames) {
			if (!paramName.startsWith("item_")) {
				continue;
			}
			
			int c = Integer.parseInt(paramName.substring("item_".length())) + 1;
			if (c > count) {
				count = c;
			}
		}
		
		ArrayList<HashMap<String, String>> result = new ArrayList<>();
		
		if (count == 0) {
			result.add(new HashMap<String, String>());
			return result;
		}

		for (int i = 0; i < count; ++i) {
			result.add(new HashMap<String, String>());
		}
		
		for (String paramName: paramNames) {
			if (paramName.startsWith("item_")) {
				int i = Integer.parseInt(paramName.substring("item_".length()));
				result.get(i).put("itemId", request.getParameter(paramName));
			}
			else if (paramName.startsWith("quantity_")) {
				int i = Integer.parseInt(paramName.substring("quantity_".length()));
				result.get(i).put("quantity", request.getParameter(paramName));
			}
		}
		
		return result;
	}
	
	private void saveOrder(
		HashMap<String, String> orderData,
		ArrayList<HashMap<String, String>> orderItems) {
		
		Order order = new Parser<Order>(Order.class, orderData).parse();
		ordersDao.add(order);
		
		for (HashMap<String, String> orderItemValues: orderItems) {
			int itemId = Integer.parseInt(orderItemValues.get("itemId"));
			double quantity = Double.parseDouble(orderItemValues.get("quantity"));
			
			Item item = itemsDao.get(itemId);
			item.setInStock(item.getInStock() - quantity);
			itemsDao.update(item);
			
			OrderItem orderItem = new OrderItem();
			orderItem.setOrderId(order.getId());
			orderItem.setItemId(itemId);
			orderItem.setItemNo(item.getItemNo());
			orderItem.setName(item.getName());
			orderItem.setUnit(item.getUnit());
			orderItem.setPrice(item.getPrice());
			orderItem.setQuantity(quantity);
			
			orderItemsDao.add(orderItem);
		}
	}
	
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
		
		HashMap<String, Object> orderData = new HashMap<>();
		request.setAttribute("orderData", orderData);
		
		ArrayList<HashMap<String, Object>> orderItems = new ArrayList<>(); 
		orderItems.add(new HashMap<String, Object>());
		request.setAttribute("orderItems", orderItems);
		
		request.getRequestDispatcher(TEMPLATE_URL).forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		
		HashMap<String, String> orderData = parseOrderData(request);
		ArrayList<HashMap<String, String>> orderItems = parseOrderItems(request);
		
		boolean displayForm = true;
		
		if (request.getParameterMap().containsKey("addItem")) {			
			orderItems.add(new HashMap<String, String>());
		}
		
		else if (request.getParameterMap().containsKey("removeItem")) {
			int index = Integer.parseInt(request.getParameter("removeItem"));
			orderItems.remove(index);
		}
		
		else {
			ModelValidation orderValidation = new OrderValidator(orderData).validate();
			
			boolean hasOrderItemError = false;
			ArrayList<ModelValidation> orderItemValidations = new ArrayList<>();
			
			for (HashMap<String, String> orderItemValues: orderItems) {
				ModelValidation orderItemValidation = new OrderItemValidator(orderItemValues).validate();
				if (orderItemValidation.hasErrors()) {
					hasOrderItemError = true;
				}
				
				orderItemValidations.add(orderItemValidation);
			}
			
			if (!orderValidation.hasErrors() && !hasOrderItemError) {
				displayForm = false;
				
				saveOrder(orderData, orderItems);
				response.sendRedirect(request.getServletContext().getContextPath() + "/pages/orders.jsp");
			}
			else {
				request.setAttribute("orderValidation", orderValidation);
				request.setAttribute("orderItemValidations", orderItemValidations);
			}
		}
		
		if (displayForm) {
			request.setAttribute("orderData", orderData);
			request.setAttribute("orderItems", orderItems);
			request.getRequestDispatcher(TEMPLATE_URL).forward(request, response);
		}
	}
}
