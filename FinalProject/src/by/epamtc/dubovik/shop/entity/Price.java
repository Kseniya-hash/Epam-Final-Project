package by.epamtc.dubovik.shop.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Price implements Serializable {
	private static final long serialVersionUID = -732980308948139035L;
	private long id;
	private long productId;
	private int purchasePrice;
	private int sellingPrice;
	private LocalDateTime date;

	public Price() {};

	public Price(long id, long productId, int purchasePrice, int sellingPrice, LocalDateTime date) {
		this.id = id;
		this.productId = productId;
		this.purchasePrice = purchasePrice;
		this.sellingPrice = sellingPrice;
		this.date = date;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public long getProductId() {
		return productId;
	}
	
	public void setProductId(long productId) {
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
	
	public LocalDateTime getDate() {
		return date;
	}
	
	public void setDate(LocalDateTime date) {
		this.date = date;
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
			this.sellingPrice == other.getSellingPrice() &&
			Objects.equals(this.date, other.getDate())) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		int result = 1;
		int prime = 31;
		result = result * prime + (int)id;
		result = result * prime + (int)productId;
		result = result * prime + purchasePrice;
		result = result * prime + sellingPrice;
		result = result * prime + Objects.hashCode(date);
		return result;
	}

	@Override
	public String toString() {
		StringBuffer result = new StringBuffer(this.getClass().getName() + "@");
		result.append(id + ",");
		result.append(productId + ",");
		result.append(purchasePrice + ",");
		result.append(sellingPrice + ",");
		result.append(date);
		return result.toString();
	}
}

