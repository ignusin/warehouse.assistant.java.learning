package iglabs.assistant.jsp.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class RequestParamExtractor {
	private final HttpServletRequest request;
	
	public RequestParamExtractor(HttpServletRequest request) {
		this.request = request;
	}
	
	public Map<String, String> extract() {
		HashMap<String, String> result = new HashMap<String, String>(); 
		
		List<String> names = Collections.list(request.getParameterNames());
		for (String name: names) {
			result.put(name, request.getParameter(name));
		}
		
		return result;
	}
}
