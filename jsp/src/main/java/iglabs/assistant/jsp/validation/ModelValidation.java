package iglabs.assistant.jsp.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class ModelValidation {
	
	public static class ValidationEntry {
		private String property;
		private List<String> errors;
		
		public ValidationEntry(String property, List<String> errors) {
			this.property = property;
			this.errors = errors;
		}
		
		public String getProperty() {
			return property;
		}
		
		public List<String> getErrors() {
			return errors;
		}
	}
	
	private TreeMap<String, List<String>> propertyErrors = new TreeMap<>();
	
	public List<ValidationEntry> getErrors() {
		ArrayList<ValidationEntry> result = new ArrayList<ValidationEntry>();
		
		for (String property: propertyErrors.keySet()) {
			ValidationEntry entry = new ValidationEntry(property, propertyErrors.get(property));
			result.add(entry);
		}
		
		return result;
	}

	private List<String> getOrCreateErrorsEntry(String property) {
		List<String> errors = propertyErrors.get(property);
		if (errors == null) {
			errors = new ArrayList<String>();
			propertyErrors.put(property, errors);
		}

		return errors;
	}
	
	public void addError(String property, String error) {
		getOrCreateErrorsEntry(property).add(error);
	}
	
	public void addErrors(String property, Iterable<String> errors) {
		List<String> errorsEntry = getOrCreateErrorsEntry(property);
		for (String error: errors) {
			errorsEntry.add(error);
		}
	}
	
	public List<String> getErrors(String property) {
		return propertyErrors.get(property);
	}
	
	public boolean hasErrors(String property) {
		return propertyErrors.containsKey(property);
	}
	
	public boolean hasErrors() {
		return !propertyErrors.isEmpty();
	}
	
}
