package iglabs.assistant.jsf.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import iglabs.assistant.jsf.model.Order;
import iglabs.assistant.jsf.model.OrderItem;
import iglabs.assistant.jsf.persistence.DefaultTransactionScope;
import iglabs.assistant.jsf.persistence.TransactionScope;

@ManagedBean
@RequestScoped
public class OrderRemoveController {
	private final TransactionScope txScope;
		
	public OrderRemoveController() {
		txScope = new DefaultTransactionScope();
	}
	
	@ManagedProperty(value="#{param.id}")
	private int id;

	public void setId(int id) {
		this.id = id;
	}

	public String delete() {
		txScope.run(em -> {
			Order order = em.find(Order.class, id);
			if (order == null) {
				return;
			}
			
			for (OrderItem orderItem: order.getOrderItems()) {
				em.remove(orderItem);
			}
			
			em.remove(order);
		});
		
		return "/orders?faces-redirect=true";
	}
}
