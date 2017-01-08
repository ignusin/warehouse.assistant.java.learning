package iglabs.assistant.jsf.validator;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public abstract class AbstractCommandValidator implements Validator {
	private static final String VALIDTE_COMMANDS_ATTR = "validateCommands";
	
	protected abstract void doValidate(FacesContext context,
			UIComponent component, Object value) throws ValidatorException;
	
	@Override
    public void validate(FacesContext context,
            UIComponent component,
            Object value) throws ValidatorException {
    	
    	String commandsAttrValue = (String)component.getAttributes().get(VALIDTE_COMMANDS_ATTR);
    	if (commandsAttrValue != null) {
    		Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
    		
    		String[] commands = commandsAttrValue.split(" ");
    		boolean found = false;
    		for (String command: commands) {
    			if (paramMap.containsKey(command)) {
    				found = true;
    				break;
    			}
    		}
    		
    		if (!found) {
    			return;
    		}
    	}
    	
    	doValidate(context, component, value);
    }
}
