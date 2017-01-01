package iglabs.assistant.jsp.parsing;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;


public class Parser<T> {
	
	private static final HashMap<Class<?>, TypeParser> typeParsers;

	static {
		typeParsers = new HashMap<>();		
	}
	
	public static void registerDefaultTypeParsers() {
		typeParsers.put(String.class, new StringParser());
		typeParsers.put(Integer.class, new IntegerParser());
		typeParsers.put(int.class, new IntegerParser());
		typeParsers.put(Double.class, new DoubleParser());
		typeParsers.put(double.class, new DoubleParser());
	}
	
	public static void registerTypeParser(Class<?> cls, TypeParser typeParser) {
		typeParsers.put(cls, typeParser);
	}
	
	public static void unregisterTypeParser(Class<?> cls) {
		typeParsers.remove(cls);
	}
	
	
	
	private final Class<T> cls;
	private final Map<String, String> values;
	
	public Parser(Class<T> cls, Map<String, String> values) {
		this.cls = cls;
		this.values = values;
	}
	
	public T parse() {
		try {
			PropertyDescriptor[] descriptors =
				Introspector.getBeanInfo(cls).getPropertyDescriptors();
			
			T result = cls.newInstance();
			
			for (PropertyDescriptor descriptor: descriptors) {
				String name = descriptor.getName();
				String value = values.get(name);
				
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
