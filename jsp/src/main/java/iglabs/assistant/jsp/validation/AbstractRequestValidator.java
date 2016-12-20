package iglabs.assistant.jsp.validation;

import javax.servlet.http.HttpServletRequest;

public abstract class AbstractRequestValidator {
	public abstract Iterable<ValidationRule> getValidationRules();
	
	public ModelValidation validateRequest(HttpServletRequest request) {
		return RequestValidator.validate(request, getValidationRules());
	}
}
