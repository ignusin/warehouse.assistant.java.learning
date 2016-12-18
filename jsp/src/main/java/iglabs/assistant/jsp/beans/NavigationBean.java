package iglabs.assistant.jsp.beans;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

public class NavigationBean {
	
	private PageContext pageContext;
	
	
	public void setPageContext(PageContext pageContext) {
		this.pageContext = pageContext;
	}
	
	public PageContext getPageContext() {
		return pageContext;
	}
	
	private String getRelativePath() {
		String requestUri = ((HttpServletRequest)pageContext.getRequest()).getRequestURI();
		String contextPath = pageContext.getServletContext().getContextPath();
		
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
