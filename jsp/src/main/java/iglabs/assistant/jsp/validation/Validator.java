package iglabs.assistant.jsp.validation;

import java.util.Map;


public class Validator {
	private final Map<String, String> values;
	private final Iterable<ValidationRule> rules;
	
	
	public Validator(Map<String, String> values, Iterable<ValidationRule> rules) {
		this.values = values;
		this.rules = rules;
	}
	
	public ModelValidation validate() {
		ModelValidation result = new ModelValidation();
		
		for (ValidationRule rule: rules) {
			String value = values.get(rule.getProperty());
			
			for (ValueValidator validator: rule.getValidators()) {
				if (!validator.isValid(value)) {
					result.addError(rule.getProperty(), validator.getMessage());
				}
			}
		}
		
		return result;
	}
}
