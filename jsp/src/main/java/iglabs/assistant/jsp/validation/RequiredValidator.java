package iglabs.assistant.jsp.validation;

import org.apache.commons.lang3.StringUtils;


public class RequiredValidator implements ValueValidator {
	@Override
	public boolean isValid(String value) {
		return StringUtils.isNotBlank(value);
	}

	@Override
	public String getMessage() {
		return "Required field";
	}
}
