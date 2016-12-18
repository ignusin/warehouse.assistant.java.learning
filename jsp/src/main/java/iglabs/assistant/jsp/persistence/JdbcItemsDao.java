package iglabs.assistant.jsp.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import iglabs.assistant.jsp.models.Item;

public class JdbcItemsDao implements ItemsDao {

	private DataSourceFactory dataSourceFactory;
	
	public DataSourceFactory getDataSourceFactory() {
		return dataSourceFactory;
	}
	
	public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
		this.dataSourceFactory = dataSourceFactory;
	}
	
	private List<Item> extractItems(ResultSet resultSet) throws SQLException {
		ArrayList<Item> result = new ArrayList<Item>();
		
		while (resultSet.next()) {
			Item item = extractItem(resultSet);
			result.add(item);
		}
		
		return result;
	}
	
	private Item extractItem(ResultSet resultSet) throws SQLException {
		Item item = new Item();
		item.setId(resultSet.getInt("id"));
		item.setItemNo(resultSet.getString("item_no"));
		item.setName(resultSet.getString("name"));
		item.setDescription(resultSet.getString("description"));
		item.setUnit(resultSet.getString("unit"));
		item.setInStock(resultSet.getDouble("in_stock"));
		
		return item;
	}
	
	public List<Item> list() {
		try (Connection conn = getDataSourceFactory().getDataSource().getConnection()) {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM \"items\" ORDER BY \"item_no\"");
			ResultSet resultSet = stmt.executeQuery();
			
			return extractItems(resultSet);
		}
		catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public Item get(int id) {
		try (Connection conn = getDataSourceFactory().getDataSource().getConnection()) {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM \"items\" WHERE \"id\"=?");
			stmt.setInt(1, id);
			
			ResultSet resultSet = stmt.executeQuery();
			
			return extractItem(resultSet);
		}
		catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public void add(Item item) {
		String query = "INSERT INTO \"items\" (\"item_no\", \"name\", "
			+ "\"description\", \"unit\", \"in_stock\")  VALUES(?, ?, ?, ?, ?)";
		
		try (Connection conn = getDataSourceFactory().getDataSource().getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(query);
			
			stmt.setString(1, item.getItemNo());
			stmt.setString(2, item.getName());
			stmt.setString(3, item.getDescription());
			stmt.setString(4, item.getUnit());
			stmt.setDouble(5, item.getInStock());
			
			stmt.execute();
		}
		catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public void update(Item item) {
		String query = "UPDATE \"items\" set \"item_no\"=?, \"name\"=?, "
				+ "\"description\"=?, \"unit\"=?, \"in_stock\"=? WHERE \"id\"=?";
			
		try (Connection conn = getDataSourceFactory().getDataSource().getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(query);
			
			stmt.setString(1, item.getItemNo());
			stmt.setString(2, item.getName());
			stmt.setString(3, item.getDescription());
			stmt.setString(4, item.getUnit());
			stmt.setDouble(5, item.getInStock());
			stmt.setInt(6, item.getId());
			
			stmt.execute();
		}
		catch (Exception ex) {
			throw new RuntimeException(ex);
		}		
	}
}
