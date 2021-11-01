package service.validation;

import org.junit.Test;
import org.junit.Assert;
import org.junit.Before;

import by.epamtc.dubovik.shop.entity.Price;
import by.epamtc.dubovik.shop.service.validation.PriceValidation;
import by.epamtc.dubovik.shop.service.validation.ValidationFactory;

public class TestPriceValidation {
	
	private PriceValidation validator =
			ValidationFactory.getInstance().getPriceValidation();
	
	private Price price = new Price();
	
	@Before
	public void setUp() {
		price.setId(1);
		price.setProductId(1);
		price.setPurchasePrice(1000);
		price.setSellingPrice(1500);
	}
	
	@Test
	public void isValidTest() {
		boolean expected = true;
		boolean actual = validator.isValid(price);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void isValidTestZeroPurchasePrice() {
		price.setPurchasePrice(0);
		
		boolean expected = false;
		boolean actual = validator.isValid(price);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void isValidTestZeroSellingPrice() {
		price.setSellingPrice(0);
		
		boolean expected = false;
		boolean actual = validator.isValid(price);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void isValidTestNegativePurchasePrice() {
		price.setPurchasePrice(-1000);
		
		boolean expected = false;
		boolean actual = validator.isValid(price);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void isValidTestNegativeSellingPrice() {
		price.setSellingPrice(-1500);
		
		boolean expected = false;
		boolean actual = validator.isValid(price);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void isValidTestNullPrice() {
		Price price = null;
		
		boolean expected = false;
		boolean actual = validator.isValid(price);
		
		Assert.assertEquals(expected, actual);
	}

}
