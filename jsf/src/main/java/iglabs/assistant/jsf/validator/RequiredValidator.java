package iglabs.assistant.jsf.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.ValidatorException;

@FacesValidator("iglabs.RequiredValidator")
public class RequiredValidator extends AbstractCommandValidator {
	@Override
	protected void doValidate(FacesContext context,
			UIComponent component, Object value) throws ValidatorException {
		
		boolean valid = value != null;
		if (valid && value instanceof String && value == "") {
			valid = false;
		}
		
		if (!valid) {
			FacesMessage msg = new FacesMessage("Required field");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			
			throw new ValidatorException(msg);
		}
	}
}
