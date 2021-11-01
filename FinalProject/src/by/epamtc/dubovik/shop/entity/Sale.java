package by.epamtc.dubovik.shop.entity;

import java.io.Serializable;

public class Sale implements Serializable {
	private static final long serialVersionUID = -395221761385286625L;
	private long orderId;
	private long productId;
	private int quantity;
	
	public Sale() {}
	
	public Sale(long orderId, long productId, int quantity) {
		this.orderId = orderId;
		this.productId = productId;
		this.quantity = quantity;
	}
	
	public long getOrderId() {
		return orderId;
	}
	
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public long getProductId() {
		return productId;
	}
	
	public void setProductId(long productId) {
		this.productId = productId;
	}

	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public boolean equals(Object o) {
		if(this == o)
			return true;
		if(o == null)
			return false;
		if(this.getClass() != o.getClass())
			return false;
		Sale other = (Sale)o;
		if(this.orderId == other.getOrderId() &&
				this.productId == other.getProductId() &&
				this.quantity == other.getQuantity()) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		int result = 1;
		int prime = 31;
		result = result * prime + (int)orderId;
		result = result * prime + (int)productId;
		result = result * prime + quantity;
		return result;
	}

	@Override
	public String toString() {
		StringBuffer result = new StringBuffer(this.getClass().getName() + "@");
		result.append(orderId + ",");
		result.append(productId + ",");
		result.append(quantity);
		return result.toString();
	}
}

