package iglabs.assistant.jsp.validation;

import java.util.List;
import javax.servlet.http.HttpServletRequest;


public class RequestValidator {
	public static ModelValidation validate(
		HttpServletRequest request,
		List<ValidationRule> rules) {
		
		ModelValidation result = new ModelValidation();
		
		for (ValidationRule rule: rules) {
			String value = request.getParameter(rule.getProperty());
			
			for (ValueValidator validator: rule.getValidators()) {
				if (!validator.isValid(value)) {
					result.addError(rule.getProperty(), validator.getMessage());
				}
			}
		}
		
		return result;
	}
}
