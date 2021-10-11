package by.epamtc.dubovik.shop.entity;

public class OrderStatus extends Entity {
	private static final long serialVersionUID = -7304616662620199753L;
	private int id;
	private String name;

	public OrderStatus() {};

	public OrderStatus(int id, String name) {
		this.id = id;
		this.name = name;
	}

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

	@Override
	public boolean equals(Object o) {
		if(this == o)
			return true;
		if(o == null)
			return false;
		if(this.getClass() != o.getClass())
			return false;
		OrderStatus other = (OrderStatus)o;
		if(this.id == other.getId() &&
				equalsWithNull(this.name, other.getName())) {
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

