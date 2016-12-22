package iglabs.assistant.jsp.persistence;

public class ColumnMapping {
	private String propertyName;
	private String columnName;
	
	public ColumnMapping(String propertyName, String columnName) {
		this.propertyName = propertyName;
		this.columnName = columnName;
	}
	
	public String getPropertyName() {
		return propertyName;
	}
	
	public String getColumnName() {
		return columnName;
	}
}
