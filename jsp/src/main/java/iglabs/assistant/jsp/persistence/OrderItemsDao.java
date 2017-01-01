package iglabs.assistant.jsp.persistence;

import java.util.List;

import iglabs.assistant.jsp.models.OrderItem;

public interface OrderItemsDao extends GenericDao<OrderItem> {
	List<OrderItem> listByOrderId(int orderId);
}
