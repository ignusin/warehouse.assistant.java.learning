package iglabs.assistant.jsp.servlets;

import java.util.ArrayList;
import java.util.Map;

import iglabs.assistant.jsp.validation.AbstractDataValidator;
import iglabs.assistant.jsp.validation.DecimalValidator;
import iglabs.assistant.jsp.validation.RequiredValidator;
import iglabs.assistant.jsp.validation.ValidationRule;

public class ItemValidator extends AbstractDataValidator {
	public ItemValidator(Map<String, String> values) {
		super(values);
	}
	
	@Override
	public Iterable<ValidationRule> getValidationRules() {
		ArrayList<ValidationRule> itemValidationRules = new ArrayList<>();
		itemValidationRules.add(new ValidationRule("itemNo", new RequiredValidator()));
		itemValidationRules.add(new ValidationRule("name", new RequiredValidator()));
		itemValidationRules.add(new ValidationRule("description", new RequiredValidator()));
		itemValidationRules.add(new ValidationRule("unit", new RequiredValidator()));
		itemValidationRules.add(new ValidationRule("price", new RequiredValidator(), new DecimalValidator()));
		itemValidationRules.add(new ValidationRule("inStock", new RequiredValidator(), new DecimalValidator()));
		
		return itemValidationRules;
	}
}
