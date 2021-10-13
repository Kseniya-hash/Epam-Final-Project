package by.epamtc.dubovik.shop.entity;

public class ProductForMenu extends Entity {
	private static final long serialVersionUID = -7848542087857798329L;
	private int id;
	private String name;
	private String productCategory;
	private String description;
	private int commentCount;
	private int rating;
	private int sellingPrice;
	private String photoPath;
	
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

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getCommentCount() {
		return commentCount;
	}
	
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
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
	
	public String getPhotoPath() {
		return photoPath;
	}
	
	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	@Override
	public boolean equals(Object o) {
		if(this == o)
			return true;
		if(o == null)
			return false;
		if(this.getClass() != o.getClass())
			return false;
		ProductForMenu other = (ProductForMenu)o;
		if(	this.id == other.getId() &&
			equalsWithNull(this.name, other.getName()) &&
			equalsWithNull(this.productCategory, other.getProductCategory()) &&
			equalsWithNull(this.description, other.getDescription()) &&
			this.commentCount == other.getCommentCount() &&
			this.rating == other.getRating() &&
			this.sellingPrice == other.getSellingPrice() &&
			equalsWithNull(this.photoPath, other.getPhotoPath())) {
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
		result = result * prime + hashCodeWithNull(description);
		result = result * prime + commentCount;
		result = result * prime + rating;
		result = result * prime + sellingPrice;
		result = result * prime + hashCodeWithNull(photoPath);
		return result;
	}

	@Override
	public String toString() {
		StringBuffer result = new StringBuffer(this.getClass().getName() + "@");
		result.append(id + ",");
		result.append(name + ",");
		result.append(productCategory + ",");
		result.append(description + ",");
		result.append(commentCount + ",");
		result.append(rating + ",");
		result.append(sellingPrice + ",");
		result.append(photoPath);
		return result.toString();
	}
}
