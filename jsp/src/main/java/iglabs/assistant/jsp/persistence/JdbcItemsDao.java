package iglabs.assistant.jsp.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import iglabs.assistant.jsp.model.Item;

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
		item.setInStock(resultSet.getInt("in_stock"));
		
		return item;
	}
	
	public List<Item> getItems() {
		try (Connection conn = getDataSourceFactory().getDataSource().getConnection()) {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM \"items\" ORDER BY \"item_no\"");
			ResultSet resultSet = stmt.executeQuery();
			
			return extractItems(resultSet);
		}
		catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}
