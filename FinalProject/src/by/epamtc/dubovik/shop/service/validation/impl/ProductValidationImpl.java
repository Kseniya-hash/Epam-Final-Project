package by.epamtc.dubovik.shop.service.validation.impl;

import by.epamtc.dubovik.shop.entity.Product;
import by.epamtc.dubovik.shop.service.validation.ProductValidation;

public class ProductValidationImpl implements ProductValidation {
	
	@Override
	public boolean isValid(Product product) {
		boolean isValid = product != null &&
				product.getName() != null &&
				product.getName().length() != 0 &&
				isValidDescription(product.getDescription()) &&
				isValidValue(product.getLength()) &&
				isValidValue(product.getWidth()) &&
				isValidValue(product.getHigh()) &&
				isValidValue(product.getWeight()) &&
				product.getQuantity() >= 0 &&
				isValidImage(product.getPhotoPath());
		
		return isValid;
	}
	
	private boolean isValidValue(Integer integer) {
		return integer == null || integer > 0;
	}
	
	private boolean isValidImage(String path) {
		String imageFile ="^(.)+\\.(jpg|jpeg|png|gif)$";
		return path != null && path.matches(imageFile);
	}
	
	private boolean isValidDescription(String description) {
		return description == null || description.length() <= 1000;
	}

}
