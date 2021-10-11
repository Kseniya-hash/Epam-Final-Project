package by.epamtc.dubovik.shop.entity;

public class Comment extends Entity {
	
	private static final long serialVersionUID = 1373368554795132640L;
	
	private int id;
	private int userId;
	private int productId;
	private String text;
	private Integer rating;
	
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

	public int getProductId() {
		return productId;
	}
	
	public void setProductId(int productId) {
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
			this.productId == other.getProductId() &&
			equalsWithNull(this.text, other.getText()) &&
			equalsWithNull(this.rating, other.getRating())) {
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
		result = result * prime + productId;
		result = result * prime + hashCodeWithNull(text);
		result = result * prime + hashCodeWithNull(rating);
		return result;
	}

	@Override
	public String toString() {
		StringBuffer result = new StringBuffer(this.getClass().getName() + "@");
		result.append(id + ",");
		result.append(userId + ",");
		result.append(productId + ",");
		result.append(text + ",");
		result.append(rating);
		return result.toString();
	}
}

