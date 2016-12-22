package iglabs.assistant.jsp.models;

import java.util.ArrayList;

import iglabs.assistant.jsp.validation.AbstractRequestValidator;
import iglabs.assistant.jsp.validation.DecimalValidator;
import iglabs.assistant.jsp.validation.RequiredValidator;
import iglabs.assistant.jsp.validation.ValidationRule;

public class ItemRequestValidator extends AbstractRequestValidator {
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
