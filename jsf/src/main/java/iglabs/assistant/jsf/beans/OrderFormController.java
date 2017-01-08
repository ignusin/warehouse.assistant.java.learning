package iglabs.assistant.jsf.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import iglabs.assistant.jsf.model.Item;
import iglabs.assistant.jsf.model.Order;
import iglabs.assistant.jsf.model.OrderItem;
import iglabs.assistant.jsf.persistence.DefaultTransactionScope;
import iglabs.assistant.jsf.persistence.TransactionScope;

@ManagedBean
@ViewScoped
public class OrderFormController {
	public static class OrderFormItem implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private String itemId;
		private String quantity;
		
		public String getItemId() {
			return itemId;
		}
		
		public void setItemId(String itemId) {
			this.itemId = itemId;
		}
		
		public String getQuantity() {
			return quantity;
		}
		
		public void setQuantity(String quantity) {
			this.quantity = quantity;
		}
	}
	
	private final TransactionScope txScope;
	private String name;
	private List<OrderFormItem> orderItems;
	
	public OrderFormController() {
		txScope = new DefaultTransactionScope();
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<OrderFormItem> getOrderItems() {
		if (orderItems == null) {
			ArrayList<OrderFormItem> emptyItems = new ArrayList<>();
			emptyItems.add(new OrderFormItem());
			
			orderItems = emptyItems;
		}
		
		return orderItems;
	}
	
	public Map<String, String> getItems() {
		return txScope.run(em -> {
			List<Item> items = em
				.createQuery("SELECT i FROM Item i ORDER BY i.name", Item.class)
				.getResultList();
			
			TreeMap<String, String> result = new TreeMap<>();
			for (Item item: items) {	
				result.put(String.format("%s (%s)", item.getName(), item.getItemNo()), item.getId().toString());
			}
			
			return result;
		});
	}
	
	public void setOrderItems(List<OrderFormItem> orderItems) {
		this.orderItems = orderItems;
	}
	
	public void addItem() {
		getOrderItems().add(new OrderFormItem());
	}
	
	public void removeItem(int index) {
		getOrderItems().remove(index);
	}
	
	public String submit() {
		txScope.run(em -> {
			Order order = new Order();
			order.setName(name);
			em.persist(order);
			
			for (OrderFormItem orderFormItem: orderItems) {
				int itemId = Integer.parseInt(orderFormItem.getItemId());
				double quantity = Double.parseDouble(orderFormItem.getQuantity());
				
				Item item = em.find(Item.class, itemId);
				if (item == null) {
					continue;
				}
				
				OrderItem orderItem = new OrderItem();
				orderItem.setItemId(itemId);
				orderItem.setItemNo(item.getItemNo());
				orderItem.setName(item.getName());
				orderItem.setUnit(item.getUnit());
				orderItem.setPrice(item.getPrice());
				orderItem.setQuantity(quantity);
				orderItem.setOrder(order);
				em.persist(orderItem);
			}
		});
		
		return "/orders?faces-redirect=true";
	}
}
