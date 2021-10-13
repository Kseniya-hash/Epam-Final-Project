package by.epamtc.dubovik.shop.service.validation.impl;

import java.util.Map;

import by.epamtc.dubovik.shop.entity.Cart;
import by.epamtc.dubovik.shop.service.validation.CartValidation;

public class CartValidationImpl implements CartValidation {

	@Override
	public boolean isValid(Cart cart) {
		boolean isValid = (cart != null);
		if(cart != null) {
			for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
				if(entry.getValue() < 0) {
					isValid = false;
					break;
				}
			}
		} else {
			isValid = false;
		}
		return isValid;
	}

}
