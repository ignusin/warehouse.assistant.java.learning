package iglabs.assistant.jsf.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.hibernate.Session;

import iglabs.assistant.jsf.model.Item;
import iglabs.assistant.jsf.persistence.DefaultSessionProvider;
import iglabs.assistant.jsf.persistence.SessionAction;
import iglabs.assistant.jsf.persistence.SessionProvider;

@ManagedBean
@RequestScoped
public class ItemRemoveController {
	private final SessionProvider sessionProvider;
		
	public ItemRemoveController() {
		sessionProvider = new DefaultSessionProvider();
	}
	
	@ManagedProperty(value="#{param.id}")
	private int id;

	public void setId(int id) {
		this.id = id;
	}
	
	public String delete() {
		sessionProvider.run(new SessionAction() {
			@Override
			public void run(Session session) {
				Item item = session.get(Item.class, id);
				if (item != null) {
					session.delete(item);
				}
			}
		});
		
		return "items?faces-redirect=true";
	}
	
	public String back() {
		return "items?faces-redirect=true";
	}
}
