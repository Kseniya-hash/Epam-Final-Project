package by.epamtc.dubovik.shop.entity;

import java.sql.Timestamp;

public class Price extends Entity {
	private static final long serialVersionUID = -732980308948139035L;
	private int id;
	private int productId;
	private int purchasePrice;
	private int sellingPrice;

	public Price() {};

	public Price(int id, int productId, int purchasePrice, int sellingPrice, Timestamp date) {
		this.id = id;
		this.productId = productId;
		this.purchasePrice = purchasePrice;
		this.sellingPrice = sellingPrice;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public int getProductId() {
		return productId;
	}
	
	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getPurchasePrice() {
		return purchasePrice;
	}
	
	public void setPurchasePrice(int purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public int getSellingPrice() {
		return sellingPrice;
	}
	
	public void setSellingPrice(int sellingPrice) {
		this.sellingPrice = sellingPrice;
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o)
			return true;
		if(o == null)
			return false;
		if(this.getClass() != o.getClass())
			return false;
		Price other = (Price)o;
		if(	this.id == other.getId() &&
			this.productId == other.getProductId() &&
			this.purchasePrice == other.getPurchasePrice() &&
			this.sellingPrice == other.getSellingPrice()) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		int result = 1;
		int prime = 31;
		result = result * prime + id;
		result = result * prime + productId;
		result = result * prime + purchasePrice;
		result = result * prime + sellingPrice;
		return result;
	}

	@Override
	public String toString() {
		StringBuffer result = new StringBuffer(this.getClass().getName() + "@");
		result.append(id + ",");
		result.append(productId + ",");
		result.append(purchasePrice + ",");
		result.append(sellingPrice);
		return result.toString();
	}
}

