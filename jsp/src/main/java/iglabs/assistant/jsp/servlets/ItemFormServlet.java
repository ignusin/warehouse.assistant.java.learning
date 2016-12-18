package iglabs.assistant.jsp.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import iglabs.assistant.jsp.models.Item;
import iglabs.assistant.jsp.persistence.ItemsDao;
import iglabs.assistant.jsp.persistence.JdbcItemsDao;
import iglabs.assistant.jsp.persistence.JndiDataSourceFactory;
import iglabs.assistant.jsp.validation.DecimalValidator;
import iglabs.assistant.jsp.validation.ModelValidation;
import iglabs.assistant.jsp.validation.RequestValidator;
import iglabs.assistant.jsp.validation.RequiredValidator;
import iglabs.assistant.jsp.validation.ValidationRule;


public class ItemFormServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	private static final ArrayList<ValidationRule> itemValidationRules;
	
	static {
		itemValidationRules = new ArrayList<ValidationRule>();
		itemValidationRules.add(new ValidationRule("itemNo", new RequiredValidator()));
		itemValidationRules.add(new ValidationRule("name", new RequiredValidator()));
		itemValidationRules.add(new ValidationRule("description", new RequiredValidator()));
		itemValidationRules.add(new ValidationRule("unit", new RequiredValidator()));
		itemValidationRules.add(new ValidationRule("inStock", new RequiredValidator(), new DecimalValidator()));
	}
	
	private ItemsDao itemsDao;
	
	
	@Override
	public void init() {
		JdbcItemsDao itemsDao = new JdbcItemsDao();
		itemsDao.setDataSourceFactory(new JndiDataSourceFactory());
		
		this.itemsDao = itemsDao;
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		
		ModelValidation validation = RequestValidator.validate(request, itemValidationRules);
		request.setAttribute("validation", validation);
		
		if (validation.hasErrors()) {
			request
				.getRequestDispatcher("/pages/items-form.jsp")
				.forward(request, response);
		}
		else {
			Item item = ModelRequestParser.parse(Item.class, request);
			
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
