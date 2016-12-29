package iglabs.assistant.jsp.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class OrderFormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String TEMPLATE_URL = "/WEB-INF/templates/order-form.jsp";
	
	
	private HashMap<String, Object> parseOrderData(HttpServletRequest request) {
		HashMap<String, Object> orderData = new HashMap<>();
		
		String name = request.getParameter("name");
		if (name == null) {
			name = "";
		}
		
		orderData.put("name", name);
		orderData.put("orderItems", parseOrderItems(request));
		
		return orderData;
	}
	
	private ArrayList<HashMap<String, Object>> parseOrderItems(HttpServletRequest request) {
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
		
		ArrayList<HashMap<String, Object>> result = new ArrayList<>();
		
		if (count == 0) {
			result.add(new HashMap<String, Object>());
			return result;
		}

		for (int i = 0; i < count; ++i) {
			result.add(new HashMap<String, Object>());
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
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		
		HashMap<String, Object> orderData = new HashMap<>();
		
		ArrayList<HashMap<String, Object>> orderItems = new ArrayList<>(); 
		orderItems.add(new HashMap<String, Object>());
		orderData.put("orderItems", orderItems);
		
		request.setAttribute("orderData", orderData);
		
		request.getRequestDispatcher(TEMPLATE_URL).forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		
		HashMap<String, Object> orderData = parseOrderData(request);
		
		@SuppressWarnings("unchecked")
		ArrayList<HashMap<String, Object>> orderItems =
			(ArrayList<HashMap<String, Object>>)orderData.get("orderItems");

		
		if (request.getParameterMap().containsKey("addItem")) {			
			orderItems.add(new HashMap<String, Object>());
			
			request.setAttribute("orderData", orderData);
			request.getRequestDispatcher(TEMPLATE_URL).forward(request, response);
		}
		
		else if (request.getParameterMap().containsKey("removeItem")) {
			int index = Integer.parseInt(request.getParameter("removeItem"));
			orderItems.remove(index);
			
			request.setAttribute("orderData", orderData);
			request.getRequestDispatcher(TEMPLATE_URL).forward(request, response);
		}		
	}
}
