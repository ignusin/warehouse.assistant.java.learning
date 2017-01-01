package iglabs.assistant.jsp.servlets;

import java.util.ArrayList;
import java.util.Map;

import iglabs.assistant.jsp.validation.AbstractDataValidator;
import iglabs.assistant.jsp.validation.DecimalValidator;
import iglabs.assistant.jsp.validation.RequiredValidator;
import iglabs.assistant.jsp.validation.ValidationRule;


public class OrderItemValidator extends AbstractDataValidator {
	public OrderItemValidator(Map<String, String> values) {
		super(values);
	}
	
	@Override
	public Iterable<ValidationRule> getValidationRules() {
		ArrayList<ValidationRule> rules = new ArrayList<>();
		rules.add(new ValidationRule("itemId", new RequiredValidator()));
		rules.add(new ValidationRule("quantity", new RequiredValidator(), new DecimalValidator()));
		
		return rules;
	}
}
