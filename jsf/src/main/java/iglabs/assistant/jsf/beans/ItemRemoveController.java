package iglabs.assistant.jsf.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import iglabs.assistant.jsf.model.Item;
import iglabs.assistant.jsf.persistence.DefaultTransactionScope;
import iglabs.assistant.jsf.persistence.TransactionScope;

@ManagedBean
@RequestScoped
public class ItemRemoveController {
	private final TransactionScope txScope;
		
	public ItemRemoveController() {
		txScope = new DefaultTransactionScope();
	}
	
	@ManagedProperty(value="#{param.id}")
	private int id;

	public void setId(int id) {
		this.id = id;
	}
	
	public String delete() {
		txScope.run(em -> {
			Item item = em.find(Item.class, id);
			if (item != null) {
				em.remove(item);
			}
		});
		
		return "/items?faces-redirect=true";
	}
}
