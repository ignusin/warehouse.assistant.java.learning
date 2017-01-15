package iglabs.assistant.ajaxjsf.beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import iglabs.assistant.ajaxjsf.model.Item;
import iglabs.assistant.ajaxjsf.persistence.DefaultTransactionScope;
import iglabs.assistant.ajaxjsf.persistence.TransactionScope;


@ManagedBean
@ViewScoped
public class ItemFormController implements Serializable {
	private static final long serialVersionUID = 1L;

	private final TransactionScope txScope;
	
	private Integer id;
	
	private Item item;
	
	public ItemFormController() {
		txScope = new DefaultTransactionScope();
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Integer getId() {
		if (id == null) {
			String idStr = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap().get("id");
			
			if (idStr != null) {
				id = Integer.parseInt(idStr);
			}
			else {
				id = 0;
			}
		}
		
		return id;
	}
	
	public Item getItem() {
		if (item == null) {
			if (getId() > 0) {
				item = txScope.run(em -> {
					return em.find(Item.class, getId());
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
