package iglabs.assistant.jsp.servlets;

import java.util.ArrayList;
import java.util.Map;

import iglabs.assistant.jsp.validation.AbstractDataValidator;
import iglabs.assistant.jsp.validation.RequiredValidator;
import iglabs.assistant.jsp.validation.ValidationRule;

public class OrderValidator extends AbstractDataValidator {
	public OrderValidator(Map<String, String> values) {
		super(values);
	}
	
	@Override
	public Iterable<ValidationRule> getValidationRules() {
		ArrayList<ValidationRule> rules = new ArrayList<>();
		rules.add(new ValidationRule("name", new RequiredValidator()));
		
		return rules;
	}
}
