package iglabs.assistant.jsp.persistence;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import iglabs.assistant.jsp.models.Entity;

public abstract class AbstractJdbcGenericDao<T extends Entity> implements GenericDao<T> {
	
	private ArrayList<ColumnMapping> columnMap;
	
	private DataSourceFactory dataSourceFactory;
	
	
	protected AbstractJdbcGenericDao() {
		this.columnMap = null;
		this.dataSourceFactory = null;
	}
	
	protected abstract Class<T> getEntityClass();
	
	protected abstract String getTableName();
	
	protected abstract List<ColumnMapping> getColumnMap();
	
	protected String getDefaultSortCondition() {
		return "\"id\" ASC";
	}
	
	private ArrayList<ColumnMapping> ensureColumnMap() {
		if (this.columnMap == null) {
			ArrayList<ColumnMapping> extendedColumnMap = new ArrayList<>(getColumnMap());
			
			boolean hasId = false;
			for (ColumnMapping m: extendedColumnMap) {
				if ("id".equals(m.getPropertyName())) {
					hasId = true;
					break;
				}
			}
			
			if (!hasId) {
				extendedColumnMap.add(new ColumnMapping("id", "id"));			
			}
			
			this.columnMap = extendedColumnMap;
		}
		
		return this.columnMap;
	}
	
	private Map<String, PropertyDescriptor> getPropertyDescriptorMap() throws IntrospectionException {
		PropertyDescriptor[] descriptors =
				Introspector.getBeanInfo(getEntityClass()).getPropertyDescriptors();
		HashMap<String, PropertyDescriptor> descriptorMap = new HashMap<>();
		
		for (PropertyDescriptor pd: descriptors) {
			descriptorMap.put(pd.getName(), pd);
		}
		
		return descriptorMap;
	}
	
	public DataSourceFactory getDataSourceFactory() {
		return dataSourceFactory;
	}
	
	public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
		this.dataSourceFactory = dataSourceFactory;
	}
	
	protected List<T> extractItems(ResultSet resultSet) throws Exception {
		ArrayList<T> result = new ArrayList<T>();
		
		while (resultSet.next()) {
			T item = extractItem(resultSet);
			result.add(item);
		}
		
		return result;
	}
	
	protected T extractItem(ResultSet resultSet) throws Exception {
		
		T entity = getEntityClass().newInstance();
		
		Map<String, PropertyDescriptor> dm = getPropertyDescriptorMap();
		
		for (ColumnMapping m: ensureColumnMap()) {
			dm.get(m.getPropertyName()).getWriteMethod().invoke(entity, resultSet.getObject(m.getColumnName()));
		}
		
		return entity;
	}
	
	protected String createListQuery() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM \"");
		sb.append(getTableName());
		sb.append("\" ORDER BY ");
		sb.append(getDefaultSortCondition());
		
		return sb.toString();
	}
	
	protected String createGetQuery() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM \"");
		sb.append(getTableName());
		sb.append("\" WHERE \"id\"=?");
		
		return sb.toString();
	}
	
	protected String createAddQuery() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("INSERT INTO \"");
		sb.append(getTableName());
		sb.append("\" (");
		
		boolean first = true;
		for (ColumnMapping m: ensureColumnMap()) {
			if ("id".equals(m.getPropertyName())) {
				continue;
			}
			
			if (!first) {
				sb.append(", ");
			}
			
			sb.append("\"");
			sb.append(m.getColumnName());
			sb.append("\"");
			
			first = false;
		}
		
		sb.append(") VALUES (");
		
		first = true;
		for (ColumnMapping m: ensureColumnMap()) {
			if ("id".equals(m.getPropertyName())) {
				continue;
			}
			
			if (!first) {
				sb.append(", ");
			}
			
			sb.append("?");
			first = false;
		}
		
		sb.append(")");
		
		return sb.toString();
	}
	
	protected String createUpdateQuery() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("UPDATE \"");
		sb.append(getTableName());
		sb.append("\" SET ");
		
		boolean first = true;
		for (ColumnMapping m: ensureColumnMap()) {
			if ("id".equals(m.getPropertyName())) {
				continue;
			}
			
			if (!first) {
				sb.append(", ");
			}
			
			sb.append("\"");
			sb.append(m.getColumnName());
			sb.append("\"=?");
			
			first = false;
		}
		
		sb.append(" WHERE \"id\"=?");
		
		return sb.toString();
	}
	
	protected String createDeleteQuery() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("DELETE FROM \"");
		sb.append(getTableName());
		sb.append("\" WHERE \"id\"=?");
		
		return sb.toString();
	}
	
	public List<T> list() {
		try (Connection conn = getDataSourceFactory().getDataSource().getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(createListQuery());
			ResultSet resultSet = stmt.executeQuery();
			
			return extractItems(resultSet);
		}
		catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public T get(int id) {
		try (Connection conn = getDataSourceFactory().getDataSource().getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(createGetQuery());
			stmt.setInt(1, id);
			
			ResultSet resultSet = stmt.executeQuery();
			
			return extractItem(resultSet);
		}
		catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public void add(T entity) {
		try (Connection conn = getDataSourceFactory().getDataSource().getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(createAddQuery());
			
			Map<String, PropertyDescriptor> dm = getPropertyDescriptorMap();
			
			int i = 1;
			for (ColumnMapping m: ensureColumnMap()) {
				if ("id".equals(m.getPropertyName())) {
					continue;
				}
				
				PropertyDescriptor pd = dm.get(m.getPropertyName());
				Object value = pd.getReadMethod().invoke(entity);
				
				stmt.setObject(i, value);
				
				++i;
			}
			
			stmt.execute();
		}
		catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public void update(T entity) {
		try (Connection conn = getDataSourceFactory().getDataSource().getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(createUpdateQuery());
			
			Map<String, PropertyDescriptor> dm = getPropertyDescriptorMap();
			
			int i = 1;
			for (ColumnMapping m: ensureColumnMap()) {
				if ("id".equals(m.getPropertyName())) {
					continue;
				}
				
				PropertyDescriptor pd = dm.get(m.getPropertyName());
				Object value = pd.getReadMethod().invoke(entity);
				
				stmt.setObject(i, value);
				
				++i;
			}

			PropertyDescriptor pd = dm.get("id");
			Object value = pd.getReadMethod().invoke(entity);
			stmt.setObject(i, value);
			
			stmt.execute();
		}
		catch (Exception ex) {
			throw new RuntimeException(ex);
		}		
	}
	
	public void remove(int id) {
		try (Connection conn = getDataSourceFactory().getDataSource().getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(createDeleteQuery());
			stmt.setInt(1, id);
			
			stmt.execute();
		}
		catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}
