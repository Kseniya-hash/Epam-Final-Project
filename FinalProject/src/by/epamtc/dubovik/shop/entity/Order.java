package by.epamtc.dubovik.shop.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Order implements Serializable {
	private static final long serialVersionUID = -3747621069091964608L;
	
	private long id;
	private long userId;
	private long orderStatusId;
	private LocalDateTime date;
	private List<Sale> sales;
	
	public Order() {}
	
	public Order(long id, 
			long userId,
			long orderStatusId,
			LocalDateTime date,
			List<Sale> sales) {
		this.id = id;
		this.userId = userId;
		this.orderStatusId = orderStatusId;
		this.date = date;
		this.sales = sales;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getOrderStatusId() {
		return orderStatusId;
	}
	
	public void setOrderStatusId(long orderStatusId) {
		this.orderStatusId = orderStatusId;
	}

	public LocalDateTime getDate() {
		return date;
	}
	
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	
	public List<Sale> getSales() {
		return sales;
	}
	
	public void setSales(List<Sale> sales) {
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
			Objects.equals(this.date, other.getDate()) &&
			Objects.equals(this.sales, other.getSales())) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
	int result = 1;
	int prime = 31;
		result = result * prime + (int)id;
		result = result * prime + (int)userId;
		result = result * prime + (int)orderStatusId;
		result = result * prime + Objects.hashCode(date);
		result = result * prime + Objects.hashCode(sales);
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
