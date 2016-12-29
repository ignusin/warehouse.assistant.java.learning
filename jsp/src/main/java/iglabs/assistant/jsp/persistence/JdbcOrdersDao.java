package iglabs.assistant.jsp.persistence;

import java.util.ArrayList;
import java.util.List;

import iglabs.assistant.jsp.models.Order;

public class JdbcOrdersDao
	extends AbstractJdbcGenericDao<Order>
	implements OrdersDao {

	@Override
	protected Class<Order> getEntityClass() {
		return Order.class;
	}

	@Override
	protected String getTableName() {
		return "orders";
	}

	@Override
	protected List<ColumnMapping> getColumnMap() {
		ArrayList<ColumnMapping> map = new ArrayList<ColumnMapping>();
		map.add(new ColumnMapping("id", "id"));
		map.add(new ColumnMapping("name", "name"));
		
		return map;
	}
}
