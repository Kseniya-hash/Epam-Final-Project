package by.epamtc.dubovik.shop.entity;

import java.sql.Timestamp;
import java.util.List;

public class Order extends Entity {
	private static final long serialVersionUID = -3747621069091964608L;
	
	private int id;
	private int userId;
	private int orderStatusId;
	private List<OrderToProduct> sales;
	private Timestamp date;

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getOrderStatusId() {
		return orderStatusId;
	}
	
	public void setOrderStatusId(int orderStatusId) {
		this.orderStatusId = orderStatusId;
	}

	public Timestamp getDate() {
		return date;
	}
	
	public void setDate(Timestamp date) {
		this.date = date;
	}
	
	public List<OrderToProduct> getSales() {
		return sales;
	}
	
	public void setSales(List<OrderToProduct> sales) {
		this.sales = sales;
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o)
			return true;
		if(o == null)
			return false;
		if(this.getClass() != o.getClass())
			return false;
		Order other = (Order)o;
		if(this.id == other.getId() &&
			this.userId == other.getUserId() &&
			this.orderStatusId == other.getOrderStatusId() &&
			equalsWithNull(this.date, other.getDate()) &&
			equalsWithNull(this.sales, other.getSales())) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
	int result = 1;
	int prime = 31;
		result = result * prime + id;
		result = result * prime + userId;
		result = result * prime + orderStatusId;
		result = result * prime + hashCodeWithNull(date);
		result = result * prime + hashCodeWithNull(sales);
	return result;	
	}
	
	@Override
	public String toString() {
	StringBuffer result = new StringBuffer(this.getClass().getName() + "@");	
	result.append(id + ",");
	result.append(userId + ",");
	result.append(orderStatusId + ",");
	result.append(date + ",");
	result.append(sales);
		return result.toString();
	}

}
