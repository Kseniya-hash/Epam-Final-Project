package by.epamtc.dubovik.shop.entity;

public class OrderToProductForView extends Entity {

	private static final long serialVersionUID = 2820851810081335537L;
	
	private int orderId;
	private int productId;
	private String productName;
	private int quantity;
	private int price;
	
	public int getOrderId() {
		return orderId;
	}
	
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getProductId() {
		return productId;
	}
	
	public void setProductId(int productId) {
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
		OrderToProductForView other = (OrderToProductForView)o;
		if(this.orderId == other.getOrderId() &&
				this.productId == other.getProductId() &&
				equalsWithNull(this.productName, other.getProductName()) &&
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
		result = result * prime + orderId;
		result = result * prime + productId;
		result = result * prime + hashCodeWithNull(productName);
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
