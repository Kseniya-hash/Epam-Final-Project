package by.epamtc.dubovik.shop.entity;

public class ProductForCart extends Entity {
	
	private static final long serialVersionUID = -6862433718211217214L;
	private int id;
	private String name;
	private String productCategory;
	private int rating;
	private int sellingPrice;
	private int quantity;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getProductCategory() {
		return productCategory;
	}
	
	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}
	
	public int getRating() {
		return rating;
	}
	
	public void setRating(int rating) {
		this.rating = rating;
	}
	
	public int getSellingPrice() {
		return sellingPrice;
	}
	
	public void setSellingPrice(int sellingPrice) {
		this.sellingPrice = sellingPrice;
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
		ProductForCart other = (ProductForCart)o;
		if(	this.id == other.getId() &&
			equalsWithNull(this.name, other.getName()) &&
			equalsWithNull(this.productCategory, other.getProductCategory()) &&
			this.rating == other.getRating() &&
			this.sellingPrice == other.getSellingPrice() &&
					this.quantity == other.getQuantity()) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		int result = 1;
		int prime = 31;
		result = result * prime + id;
		result = result * prime + hashCodeWithNull(name);
		result = result * prime + hashCodeWithNull(productCategory);
		result = result * prime + rating;
		result = result * prime + sellingPrice;
		result = result * prime + quantity;
		return result;
	}

	@Override
	public String toString() {
		StringBuffer result = new StringBuffer(this.getClass().getName() + "@");
		result.append(id + ",");
		result.append(name + ",");
		result.append(productCategory + ",");
		result.append(rating + ",");
		result.append(sellingPrice + ",");
		result.append(quantity);
		return result.toString();
	}

}