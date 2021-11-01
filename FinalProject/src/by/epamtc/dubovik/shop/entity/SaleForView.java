package by.epamtc.dubovik.shop.entity;

import java.io.Serializable;
import java.util.Objects;

public class SaleForView implements Serializable {

	private static final long serialVersionUID = 2820851810081335537L;
	
	private long orderId;
	private long productId;
	private String productName;
	private int quantity;
	private int price;
	
	public SaleForView() {}
	
	public SaleForView(long orderId, 
			long productId, 
			String productName, 
			int quantity, 
			int price) {
		this.orderId = orderId;
		this.productId = productId;
		this.productName = productName;
		this.quantity = quantity;
		this.price = price;
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
	
	public String getProductName() {
		return productName;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public int getPrice() {
		return price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public boolean equals(Object o) {
		if(this == o)
			return true;
		if(o == null)
			return false;
		if(this.getClass() != o.getClass())
			return false;
		SaleForView other = (SaleForView)o;
		if(this.orderId == other.getOrderId() &&
				this.productId == other.getProductId() &&
				Objects.equals(this.productName, other.getProductName()) &&
				this.quantity == other.getQuantity() &&
				this.price == other.getPrice()) {
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
		result = result * prime + Objects.hashCode(productName);
		result = result * prime + quantity;
		result = result * prime + price;
		return result;
	}

	@Override
	public String toString() {
		StringBuffer result = new StringBuffer(this.getClass().getName() + "@");
		result.append(orderId + ",");
		result.append(productId + ",");
		result.append(productName + ",");
		result.append(quantity + ",");
		result.append(price);
		return result.toString();
	}

}
