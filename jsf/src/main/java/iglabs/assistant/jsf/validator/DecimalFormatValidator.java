package iglabs.assistant.jsf.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.ValidatorException;

@FacesValidator("iglabs.DecimalFormatValidator")
public class DecimalFormatValidator extends AbstractCommandValidator {
	@Override
	protected void doValidate(FacesContext context,
			UIComponent component, Object value) throws ValidatorException {
		
		if (value == null || !(value instanceof String) || (String)value == "") {
			return;
		}
		
		try {
			Double.parseDouble((String)value);
		}
		catch (NumberFormatException ex) {
			FacesMessage msg = new FacesMessage("Not a decimal number");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			
			throw new ValidatorException(msg);
		}
	}	
}
