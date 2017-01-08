package iglabs.assistant.jsf.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import iglabs.assistant.jsf.model.Item;
import iglabs.assistant.jsf.persistence.DefaultTransactionScope;
import iglabs.assistant.jsf.persistence.TransactionScope;


@ManagedBean
@RequestScoped
public class ItemFormController {
	private final TransactionScope txScope;
	
	@ManagedProperty(value="#{param.id}")
	private int id;
	
	private Item item;
	
	public ItemFormController() {
		txScope = new DefaultTransactionScope();
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Item getItem() {
		if (item == null) {
			if (id > 0) {
				item = txScope.run(em -> {
					return em.find(Item.class, id);
				});
			}
			
			if (item == null) {
				item = new Item();
			}
		}
		
		return item;
	}
	
	public String submit() {
		txScope.run(em -> {
			if (item.getId() == null) {
				em.persist(item);
			}
			else {
				em.merge(item);
			}
		});
		
		return "items?faces-redirect=true";
	}
}
