package iglabs.assistant.jsp.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import iglabs.assistant.jsp.models.OrderItem;

public class JdbcOrderItemsDao
	extends AbstractJdbcGenericDao<OrderItem> 
	implements OrderItemsDao {
	
	@Override
	protected Class<OrderItem> getEntityClass() {
		return OrderItem.class;
	}

	@Override
	protected String getTableName() {
		return "order_items";
	}

	@Override
	protected List<ColumnMapping> getColumnMap() {
		ArrayList<ColumnMapping> result = new ArrayList<>();
		
		result.add(new ColumnMapping("id", "id"));
		result.add(new ColumnMapping("orderId", "order_id"));
		result.add(new ColumnMapping("itemId", "item_id"));
		result.add(new ColumnMapping("itemNo", "item_no"));
		result.add(new ColumnMapping("name", "name"));
		result.add(new ColumnMapping("unit", "unit"));
		result.add(new ColumnMapping("price", "price"));
		result.add(new ColumnMapping("quantity", "quantity"));

		return result;
	}
	
	private String createListByOrderIdQuery() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM \"");
		sb.append(getTableName());
		sb.append("\" WHERE \"order_id\"=? ORDER BY ");
		sb.append(getDefaultSortCondition());
		
		return sb.toString();
	}
	
	@Override
	public List<OrderItem> listByOrderId(int orderId) {
		try (Connection conn = getDataSourceFactory().getDataSource().getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(createListByOrderIdQuery());
			stmt.setInt(1, orderId);
			
			ResultSet resultSet = stmt.executeQuery();
			
			return extractItems(resultSet);
		}
		catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}
