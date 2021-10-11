package by.epamtc.dubovik.shop.service.validation.impl;

import by.epamtc.dubovik.shop.entity.Product;
import by.epamtc.dubovik.shop.service.validation.ProductValidation;

public class ProductValidationImpl implements ProductValidation {
	
	@Override
	public boolean isValid(Product product) {
		boolean isValid = product.getName() != null &&
				product.getCategoryId() > 0 &&
				isValidValue(product.getLength()) &&
				isValidValue(product.getWidth()) &&
				isValidValue(product.getHigh()) &&
				isValidValue(product.getWeight()) &&
				product.getQuantity() >= 0;
				
		return isValid;
	}
	
	private boolean isValidValue(Integer integer) {
		return integer == null || integer > 0;
	}

}