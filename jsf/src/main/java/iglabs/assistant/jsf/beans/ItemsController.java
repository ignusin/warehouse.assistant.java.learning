package iglabs.assistant.jsf.beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import iglabs.assistant.jsf.model.Item;
import iglabs.assistant.jsf.persistence.DefaultTransactionScope;
import iglabs.assistant.jsf.persistence.TransactionScope;

@ManagedBean
@RequestScoped
public class ItemsController {
	private final TransactionScope txScope;
	private List<Item> items = null;
	
	public ItemsController() {
		txScope = new DefaultTransactionScope();
	}
	
	public List<Item> getItems() {
		if (items == null) {		
			items = txScope.run(em -> {
				return em.createQuery("SELECT it FROM Item it ORDER BY it.itemNo", Item.class)
					.getResultList();
			});
		}
		
		return items;
	}
	
	public String edit(Item item) {
		return String.format("item-form?faces-redirect=true&id=%d", item.getId());
	}
	
	public String delete(Item item) {
		return String.format("item-remove?faces-redirect=true&id=%d", item.getId());
	}
}
