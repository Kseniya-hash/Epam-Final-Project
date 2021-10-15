package by.epamtc.dubovik.shop.entity;

import java.io.Serializable;
import java.util.Objects;

public class Comment implements Serializable {
	
	private static final long serialVersionUID = 1373368554795132640L;
	
	private long id;
	private long userId;
	private String userLogin;
	private long productId;
	private String text;
	private Integer rating;
	
	public Comment() {}
	
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

	public long getProductId() {
		return productId;
	}
	
	public void setProductId(long productId) {
		this.productId = productId;
	}

	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}

	public Integer getRating() {
		return rating;
	}
	
	public void setRating(Integer rating) {
		this.rating = rating;
	}

	@Override
	public boolean equals(Object o) {
		if(this == o)
			return true;
		if(o == null)
			return false;
		if(this.getClass() != o.getClass())
			return false;
		Comment other = (Comment)o;
		if(	this.id == other.getId() &&
			this.userId == other.getUserId() &&
			Objects.equals(this.userLogin, other.getUserLogin()) &&
			this.productId == other.getProductId() &&
			Objects.equals(this.text, other.getText()) &&
			Objects.equals(this.rating, other.getRating())) {
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
		result = result * prime + (int)productId;
		result = result * prime + Objects.hashCode(text);
		result = result * prime + Objects.hashCode(rating);
		return result;
	}

	@Override
	public String toString() {
		StringBuffer result = new StringBuffer(this.getClass().getName() + "@");
		result.append(id + ",");
		result.append(userId + ",");
		result.append(userLogin + ",");
		result.append(productId + ",");
		result.append(text + ",");
		result.append(rating);
		return result.toString();
	}
}

