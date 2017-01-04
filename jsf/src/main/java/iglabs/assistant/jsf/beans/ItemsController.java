package iglabs.assistant.jsf.beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.hibernate.Session;

import iglabs.assistant.jsf.model.Item;
import iglabs.assistant.jsf.persistence.DefaultSessionProvider;
import iglabs.assistant.jsf.persistence.SessionProvider;

@ManagedBean
@RequestScoped
public class ItemsController {
	private final SessionProvider sessionProvider;
	private List<Item> items = null;
	
	public ItemsController() {
		sessionProvider = new DefaultSessionProvider();
	}
	
	public List<Item> getItems() {
		if (items == null) {		
			items = sessionProvider.run((Session session) -> {
				return session
					.createQuery("SELECT it FROM Item it ORDER BY it.itemNo", Item.class)
					.getResultList();
			});
		}
		
		return items;
	}
	
	public String delete(Item item) {
		return String.format("item-remove?faces-redirect=true&id=%d", item.getId());
	}
}
