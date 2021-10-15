package by.epamtc.dubovik.shop.entity;

import java.io.Serializable;
import java.util.Objects;

public class ProductCategory implements Serializable {
	private static final long serialVersionUID = 6023233189012694387L;
	
	private long id;
	private String name;

	public ProductCategory() {};

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

	@Override
	public boolean equals(Object o) {
		if(this == o)
			return true;
		if(o == null)
			return false;
		if(this.getClass() != o.getClass())
			return false;
		ProductCategory other = (ProductCategory)o;
		if(	this.id == other.getId() &&
			Objects.equals(this.name, other.getName())) {
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
		return result;
	}

	@Override
	public String toString() {
		StringBuffer result = new StringBuffer(this.getClass().getName() + "@");
		result.append(id + ",");
		result.append(name);
		return result.toString();
	}
}

