package by.epamtc.dubovik.shop.entity;

import java.sql.Timestamp;
import java.util.List;

public class OrderForView extends Entity {

	private static final long serialVersionUID = -6559233986868939722L;
	
	private int id;
	private int userId;
	private String userLogin;
	private String userPhone;
	private boolean isBlacklisted;
	private Timestamp date;
	private String orderStatus;
	private List<OrderToProductForView> sales;
	
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
	
	public Timestamp getDate() {
		return date;
	}
	
	public void setDate(Timestamp date) {
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
			equalsWithNull(this.userLogin, other.getUserLogin()) &&
			equalsWithNull(this.userPhone, other.getUserPhone()) &&
			isBlacklisted == other.getIsBlacklisted() &&
			equalsWithNull(this.date, other.getDate()) &&
			equalsWithNull(this.orderStatus, other.getOrderStatus()) &&
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
		result = result * prime + hashCodeWithNull(userLogin);
		result = result * prime + hashCodeWithNull(userPhone);
		result = result * prime + (isBlacklisted? 1 : 0);
		result = result * prime + hashCodeWithNull(date);
		result = result * prime + hashCodeWithNull(orderStatus);
		result = result * prime + hashCodeWithNull(sales);
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
