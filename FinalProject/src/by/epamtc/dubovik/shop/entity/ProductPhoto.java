package by.epamtc.dubovik.shop.entity;

public class ProductPhoto extends Entity {
	private static final long serialVersionUID = 488022789408263468L;
	private int id;
	private int productId;
	private String photoPath;

	public ProductPhoto() {};

	public ProductPhoto(int id, int productId, String photoPath) {
		this.id = id;
		this.productId = productId;
		this.photoPath = photoPath;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public int getProductId() {
		return productId;
	}
	
	public void setProductId(int productId) {
		this.productId = productId;
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
		ProductPhoto other = (ProductPhoto)o;
		if(	this.id == other.getId() &&
			this.productId == other.getProductId() &&
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
		result = result * prime + productId;
		result = result * prime + hashCodeWithNull(photoPath);
		return result;
	}

	@Override
	public String toString() {
		StringBuffer result = new StringBuffer(this.getClass().getName() + "@");
		result.append(id + ",");
		result.append(productId + ",");
		result.append(photoPath);
		return result.toString();
	}
}

