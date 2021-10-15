package by.epamtc.dubovik.shop.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class OrderForView implements Serializable {

	private static final long serialVersionUID = -6559233986868939722L;
	
	private long id;
	private long userId;
	private String userLogin;
	private String userPhone;
	private boolean isBlacklisted;
	private LocalDateTime date;
	private String orderStatus;
	private List<OrderToProductForView> sales;
	
	public OrderForView() {}
	
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
	
	public String getUserLogin() {
		return userLogin;
	}
	
	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}
	
	public String getUserPhone() {
		return userPhone;
	}
	
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	
	public boolean getIsBlacklisted() {
		return isBlacklisted;
	}
	
	public void setIsBlacklisted(boolean isBlacklisted) {
		this.isBlacklisted = isBlacklisted;
	}
	
	public LocalDateTime getDate() {
		return date;
	}
	
	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getOrderStatus() {
		return orderStatus;
	}
	
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public List<OrderToProductForView> getSales() {
		return sales;
	}
	
	public void setSales(List<OrderToProductForView> sales) {
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
		OrderForView other = (OrderForView)o;
		if(this.id == other.getId() &&
			this.userId == other.getUserId() &&
			Objects.equals(this.userLogin, other.getUserLogin()) &&
			Objects.equals(this.userPhone, other.getUserPhone()) &&
			isBlacklisted == other.getIsBlacklisted() &&
			Objects.equals(this.date, other.getDate()) &&
			Objects.equals(this.orderStatus, other.getOrderStatus()) &&
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
		result = result * prime + Objects.hashCode(userLogin);
		result = result * prime + Objects.hashCode(userPhone);
		result = result * prime + (isBlacklisted? 1 : 0);
		result = result * prime + Objects.hashCode(date);
		result = result * prime + Objects.hashCode(orderStatus);
		result = result * prime + Objects.hashCode(sales);
	return result;	
	}
	
	@Override
	public String toString() {
		StringBuffer result = new StringBuffer(this.getClass().getName() + "@");	
		result.append(id + ",");
		result.append(userId + ",");
		result.append(userLogin + ",");
		result.append(userPhone + ",");
		result.append(isBlacklisted + ",");
		result.append(date + ",");
		result.append(orderStatus + ",");
		result.append(sales);
		return result.toString();
	}

}
