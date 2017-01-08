package iglabs.assistant.jsf.beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import iglabs.assistant.jsf.model.Order;
import iglabs.assistant.jsf.persistence.DefaultTransactionScope;
import iglabs.assistant.jsf.persistence.TransactionScope;

@ManagedBean
@RequestScoped
public class OrdersController {
	private final TransactionScope txScope;
	private List<Order> orders = null;
	
	public OrdersController() {
		txScope = new DefaultTransactionScope();
	}
	
	public List<Order> getOrders() {
		if (orders == null) {
			orders = txScope.run(em -> {
				return em.createQuery("SELECT o FROM Order o ORDER BY o.id", Order.class)
					.getResultList();
			});
		}
		
		return orders;
	}
	
	public String details(Order order) {
		return String.format("/order-details?faces-redirect=true&id=%d", order.getId());
	}
	
	public String delete(Order order) {
		return String.format("/order-remove?faces-redirect=true&id=%d", order.getId());
	}
}
