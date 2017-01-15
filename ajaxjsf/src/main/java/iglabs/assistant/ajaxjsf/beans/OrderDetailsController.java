package iglabs.assistant.ajaxjsf.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import iglabs.assistant.ajaxjsf.model.Order;
import iglabs.assistant.ajaxjsf.model.OrderItem;
import iglabs.assistant.ajaxjsf.persistence.DefaultTransactionScope;
import iglabs.assistant.ajaxjsf.persistence.TransactionScope;

@ManagedBean
@RequestScoped
public class OrderDetailsController {
	private final TransactionScope txScope;
	
	public OrderDetailsController() {
		txScope = new DefaultTransactionScope();
	}
	
	@ManagedProperty(value="#{param.id}")
	private int id;

	public void setId(int id) {
		this.id = id;
	}
	
	private Order order;
	
	public Order getOrder() {
		if (order == null) {
			order = txScope.run(em -> {
				Order order = em.find(Order.class, id);
				if (order != null) {
					order.getOrderItems();
				}
				
				return order;
			});
		}
		
		return order;
	}
	
	public double getOrderItemsSum() {
		Order order = getOrder();
		if (order == null) {
			return 0.;
		}
		
		double result = 0.;
		for (OrderItem orderItem: order.getOrderItems()) {
			result += orderItem.getQuantity() * orderItem.getPrice();
		}
		
		return result;
	}
}
