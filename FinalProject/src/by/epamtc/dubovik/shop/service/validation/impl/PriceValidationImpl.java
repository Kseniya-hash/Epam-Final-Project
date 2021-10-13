package by.epamtc.dubovik.shop.service.validation.impl;

import by.epamtc.dubovik.shop.entity.Price;
import by.epamtc.dubovik.shop.service.validation.PriceValidation;

public class PriceValidationImpl implements PriceValidation {
	
	@Override
	public boolean isValid(Price price) {
		boolean isValid = price != null &&
				price.getProductId() > 0 &&
				price.getPurchasePrice() > 0 &&
				price.getSellingPrice() > 0;
		
		return isValid;
	}
}
