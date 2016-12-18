package iglabs.assistant.jsp.servlets;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;


public class ModelRequestParser {

	public static class StringParser implements TypeParser {
		@Override
		public Object parse(String value) {
			return value;
		}
	}
	
	public static class IntegerParser implements TypeParser {
		@Override
		public Object parse(String value) {
			return Integer.parseInt(value);
		}
	}
	
	public static class DoubleParser implements TypeParser {
		@Override
		public Object parse(String value) {
			return Double.parseDouble(value);
		}
	}
	
	private static final HashMap<Class<?>, TypeParser> typeParsers;

	static {
		typeParsers = new HashMap<>();
		
		typeParsers.put(String.class, new StringParser());
		typeParsers.put(Integer.class, new IntegerParser());
		typeParsers.put(int.class, new IntegerParser());
		typeParsers.put(Double.class, new DoubleParser());
		typeParsers.put(double.class, new DoubleParser());
	}
	
	public static <T> T parse(Class<T> cls, HttpServletRequest request) {
		try {
			PropertyDescriptor[] descriptors =
				Introspector.getBeanInfo(cls).getPropertyDescriptors();
			
			T result = cls.newInstance();
			
			for (PropertyDescriptor descriptor: descriptors) {
				String name = descriptor.getName();
				String value = request.getParameter(name);
				
				if (!StringUtils.isEmpty(value)) {
					TypeParser typeParser = typeParsers.get(descriptor.getPropertyType());
					Object parsedValue = typeParser.parse(value);
					
					descriptor.getWriteMethod().invoke(result, parsedValue);
				}
			}
			
			return result;
		}
		catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}
