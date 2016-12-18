package iglabs.assistant.jsp.validation;

import org.apache.commons.lang3.StringUtils;

public class DecimalValidator implements ValueValidator {
	@Override
	public boolean isValid(String value) {
		if (StringUtils.isBlank(value)) {
			return true;
		}
		
		try {
			Double.parseDouble(value);
			return true;
		}
		catch (NumberFormatException ex) {
			return false;
		}
	}

	@Override
	public String getMessage() {
		return "Expected to be decimal value";
	}
}
