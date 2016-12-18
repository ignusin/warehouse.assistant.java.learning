package iglabs.assistant.jsp.validation;

import java.util.Arrays;
import java.util.List;


public class ValidationRule {
	private String property;
	private List<ValueValidator> validators;
	
	
	public ValidationRule(String property, ValueValidator... validators) {
		this.property = property;
		this.validators = Arrays.asList(validators);
	}
	
	public ValidationRule(String property, List<ValueValidator> validators) {
		this.property = property;
		this.validators = validators;
	}
	
	public String getProperty() {
		return property;
	}
	
	public List<ValueValidator> getValidators() {
		return validators;
	}
}
