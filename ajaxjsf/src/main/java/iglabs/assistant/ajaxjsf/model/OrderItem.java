package iglabs.assistant.ajaxjsf.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="order_items")
public class OrderItem extends IdentityEntity {
	@Column(name="item_id", nullable=false)
	private Integer itemId;
	
	@Column(name="item_no", nullable=false)
	private String itemNo;
	
	@Column(name="name", nullable=false)
	private String name;
	
	@Column(name="unit", nullable=false)
	private String unit;
	
	@Column(name="price", nullable=false)
	private Double price;
	
	@Column(name="quantity", nullable=false)
	private Double quantity;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="order_id")
	private Order order;
	
	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
}
