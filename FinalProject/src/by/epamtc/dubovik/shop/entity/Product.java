package by.epamtc.dubovik.shop.entity;

public class Product extends Entity {
	private static final long serialVersionUID = -1848202786878946658L;
	private int id;
	private String name;
	private int categoryId;
	private int quantity;
	private String description;
	private Integer weight;
	private Integer length;
	private Integer high;
	private Integer width;
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

	public int getCategoryId() {
		return categoryId;
	}
	
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public Integer getWeight() {
		return weight;
	}
	
	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Integer getLength() {
		return length;
	}
	
	public void setLength(Integer length) {
		this.length = length;
	}

	public Integer getHigh() {
		return high;
	}
	
	public void setHigh(Integer high) {
		this.high = high;
	}

	public Integer getWidth() {
		return width;
	}
	
	public void setWidth(Integer width) {
		this.width = width;
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
		Product other = (Product)o;
		if(	this.id == other.getId() &&
			equalsWithNull(this.name, other.getName()) &&
			this.categoryId == other.getCategoryId() &&
			equalsWithNull(this.description, other.getDescription()) &&
			this.quantity == other.getQuantity() &&
			equalsWithNull(this.weight, other.getWeight())&&
			equalsWithNull(this.length, other.getLength())&&
			equalsWithNull(this.high, other.getHigh())&&
			equalsWithNull(this.width, other.getWidth()) &&
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
		result = result * prime + categoryId;
		result = result * prime + hashCodeWithNull(description);
		result = result * prime + quantity;
		result = result * prime + hashCodeWithNull(weight);
		result = result * prime + hashCodeWithNull(length);
		result = result * prime + hashCodeWithNull(high);
		result = result * prime + hashCodeWithNull(width);
		result = result * prime + hashCodeWithNull(photoPath);
		return result;
	}

	@Override
	public String toString() {
		StringBuffer result = new StringBuffer(this.getClass().getName() + "@");
		result.append(id + ",");
		result.append(name + ",");
		result.append(categoryId + ",");
		result.append(description + ",");
		result.append(quantity + ",");
		result.append(weight + ",");
		result.append(length + ",");
		result.append(high + ",");
		result.append(width + ",");
		result.append(photoPath);
		return result.toString();
	}
}

