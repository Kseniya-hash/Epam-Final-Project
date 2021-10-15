package by.epamtc.dubovik.shop.entity;

import java.io.Serializable;
import java.util.Objects;

public class Product implements Serializable {
	private static final long serialVersionUID = -1848202786878946658L;
	private long id;
	private String name;
	private long categoryId;
	private int quantity;
	private String description;
	private Integer weight;
	private Integer length;
	private Integer high;
	private Integer width;
	private String photoPath;
	
	public Product() {}

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public long getCategoryId() {
		return categoryId;
	}
	
	public void setCategoryId(long categoryId) {
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
			Objects.equals(this.name, other.getName()) &&
			this.categoryId == other.getCategoryId() &&
			Objects.equals(this.description, other.getDescription()) &&
			this.quantity == other.getQuantity() &&
			Objects.equals(this.weight, other.getWeight())&&
			Objects.equals(this.length, other.getLength())&&
			Objects.equals(this.high, other.getHigh())&&
			Objects.equals(this.width, other.getWidth()) &&
			Objects.equals(this.photoPath, other.getPhotoPath())) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		int result = 1;
		int prime = 31;
		result = result * prime + (int)id;
		result = result * prime + Objects.hashCode(name);
		result = result * prime + (int)categoryId;
		result = result * prime + Objects.hashCode(description);
		result = result * prime + quantity;
		result = result * prime + Objects.hashCode(weight);
		result = result * prime + Objects.hashCode(length);
		result = result * prime + Objects.hashCode(high);
		result = result * prime + Objects.hashCode(width);
		result = result * prime + Objects.hashCode(photoPath);
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

