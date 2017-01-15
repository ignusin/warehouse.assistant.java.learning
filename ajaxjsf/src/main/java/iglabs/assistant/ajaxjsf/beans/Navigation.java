package iglabs.assistant.ajaxjsf.beans;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

@ManagedBean
@ApplicationScoped
public class Navigation {
	private String getRelativePath() {
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance()
			.getExternalContext()
			.getRequest();
		
		ServletContext servletContext = (ServletContext)FacesContext.getCurrentInstance()
			.getExternalContext()
			.getContext();
		
		String requestUri = request.getRequestURI();
		String contextPath = servletContext.getContextPath();
		
		String uri = requestUri.substring(contextPath.length());
		return uri;
	}
	
	private boolean relativePathEquals(String value) {
		String uri = getRelativePath();
		return uri.equals(value);
	}
	
	private boolean relativePathContains(String value) {
		String uri = getRelativePath();
		return uri.contains(value);
	}
	
	public boolean isItemsSection() {
		return relativePathEquals("/") || relativePathContains("item");
	}
	
	public boolean isOrdersSection() {
		return relativePathContains("order");
	}
}
