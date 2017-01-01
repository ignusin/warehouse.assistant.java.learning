package iglabs.assistant.jsp.validation;

import java.util.Map;

public abstract class AbstractDataValidator {
	private final Map<String, String> values;
	
	
	protected AbstractDataValidator(Map<String, String> values) {
		this.values = values;
	}
	
	protected Map<String, String> getValues() {
		return values;
	}
	
	public abstract Iterable<ValidationRule> getValidationRules();
	
	public ModelValidation validate() {
		return new Validator(values, getValidationRules()).validate();
	}
}
