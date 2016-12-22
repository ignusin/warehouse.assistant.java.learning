package iglabs.assistant.jsp.persistence;

import java.util.ArrayList;
import java.util.List;

import iglabs.assistant.jsp.models.Item;


public class JdbcItemsDao
	extends AbstractJdbcGenericDao<Item>
	implements ItemsDao {

	@Override
	protected Class<Item> getEntityClass() {
		return Item.class;
	}

	@Override
	protected String getTableName() {
		return "items";
	}

	@Override
	protected List<ColumnMapping> getColumnMap() {
		ArrayList<ColumnMapping> map = new ArrayList<>();
		
		map.add(new ColumnMapping("id", "id"));
		map.add(new ColumnMapping("itemNo", "item_no"));
		map.add(new ColumnMapping("name", "name"));
		map.add(new ColumnMapping("description", "description"));
		map.add(new ColumnMapping("unit", "unit"));
		map.add(new ColumnMapping("price", "price"));
		map.add(new ColumnMapping("inStock", "in_stock"));
		
		return map;
	}
}
