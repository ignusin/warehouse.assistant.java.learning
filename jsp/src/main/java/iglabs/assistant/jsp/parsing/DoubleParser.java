package iglabs.assistant.jsp.parsing;

public class DoubleParser implements TypeParser {
	@Override
	public Object parse(String value) {
		return Double.parseDouble(value);
	}
}
