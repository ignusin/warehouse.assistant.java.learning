package iglabs.assistant.jsp.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import iglabs.assistant.jsp.persistence.ItemsDao;
import iglabs.assistant.jsp.persistence.JdbcItemsDao;
import iglabs.assistant.jsp.persistence.JndiDataSourceFactory;

public class ItemRemoveServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String TEMPLATE_URL = "/WEB-INF/templates/item-remove.jsp";
	
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
		
		request.getRequestDispatcher(TEMPLATE_URL).forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		
		int id = Integer.parseInt(request.getParameter("id"));
		itemsDao.remove(id);
		
		response.sendRedirect(getServletContext().getContextPath() + "/pages/items.jsp");
	}
}
