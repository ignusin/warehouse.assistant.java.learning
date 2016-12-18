package iglabs.assistant.jsp.validation;

public interface ValueValidator {
	boolean isValid(String value);
	String getMessage();
}
