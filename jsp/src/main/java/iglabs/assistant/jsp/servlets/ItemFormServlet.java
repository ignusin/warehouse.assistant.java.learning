package iglabs.assistant.jsp.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import iglabs.assistant.jsp.models.Item;
import iglabs.assistant.jsp.models.ItemRequestValidator;
import iglabs.assistant.jsp.parsing.RequestParser;
import iglabs.assistant.jsp.persistence.ItemsDao;
import iglabs.assistant.jsp.persistence.JdbcItemsDao;
import iglabs.assistant.jsp.persistence.JndiDataSourceFactory;
import iglabs.assistant.jsp.validation.ModelValidation;

public class ItemFormServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final String TEMPLATE_URL = "/WEB-INF/templates/item-form.jsp";
	
	private ItemsDao itemsDao;
	
	
	@Override
	public void init() {
		JdbcItemsDao itemsDao = new JdbcItemsDao();
		itemsDao.setDataSourceFactory(new JndiDataSourceFactory());
		
		this.itemsDao = itemsDao;
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		if (!StringUtils.isBlank(request.getParameter("id"))) {
			int id = Integer.parseInt(request.getParameter("id"));
			
			Item item = itemsDao.get(id);
			request.setAttribute("item", item);
		}
		
		ModelValidation validation = new ModelValidation();
		request.setAttribute("validation", validation);
		
		request.getRequestDispatcher(TEMPLATE_URL).forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		
		ModelValidation validation = new ItemRequestValidator().validateRequest(request);
		request.setAttribute("validation", validation);
		
		if (validation.hasErrors()) {
			request
				.getRequestDispatcher(TEMPLATE_URL)
				.forward(request, response);
		}
		else {
			Item item = RequestParser.parse(Item.class, request);
			
			if (StringUtils.isEmpty(request.getParameter("id"))) {
				itemsDao.add(item);
			}
			else {
				itemsDao.update(item);
			}
			
			response.sendRedirect(getServletContext().getContextPath() + "/pages/items.jsp");
		}
	}
}
