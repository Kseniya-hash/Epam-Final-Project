package service.validation;

import org.junit.Test;

import by.epamtc.dubovik.shop.entity.Cart;
import by.epamtc.dubovik.shop.service.validation.CartValidation;
import by.epamtc.dubovik.shop.service.validation.ValidationFactory;

import org.junit.Assert;

public class TestCartValidation {
	
	private CartValidation validator = 
			ValidationFactory.getInstance().getCartValidation();
	
	@Test
	public void isValidTest() {
		Cart cart = new Cart();
		cart.put(2, 1);
		cart.put(10, 2);
		
		boolean expected = true;
		boolean actual = validator.isValid(cart);
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void isValidTestZeroQuantity() {
		Cart cart = new Cart();
		cart.put(2, 1);
		cart.put(10, 0);
		
		boolean expected = true;
		boolean actual = validator.isValid(cart);
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void isValidTestNegativeQuantity() {
		Cart cart = new Cart();
		cart.put(2, 1);
		cart.put(10, -2);
		
		boolean expected = false;
		boolean actual = validator.isValid(cart);
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void isValidTestNullCart() {
		Cart cart = null;
		
		boolean expected = false;
		boolean actual = validator.isValid(cart);
		Assert.assertEquals(expected, actual);
	}

}
