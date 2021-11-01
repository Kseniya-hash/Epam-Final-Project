package service.validation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import by.epamtc.dubovik.shop.entity.UserForLogin;
import by.epamtc.dubovik.shop.service.validation.UserForLoginValidation;
import by.epamtc.dubovik.shop.service.validation.ValidationFactory;

public class TestUserForLoginValidation {
	
	private UserForLoginValidation validator =
			ValidationFactory.getInstance().getUserForLoginValidation();
	
	private UserForLogin user = new UserForLogin();
	
	@Before
	public void setUp() {
		user.setLogin("login");
		user.setPassword(new String("Pass_123").getBytes());
	}
	
	@Test
	public void isValidTest() {
		boolean expected = true;
		boolean actual = validator.isValid(user);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void isValidTestEmptyLogin() {
		user.setLogin("");
		
		boolean expected = false;
		boolean actual = validator.isValid(user);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void isValidTestNullLogin() {
		user.setLogin(null);
		
		boolean expected = false;
		boolean actual = validator.isValid(user);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void isValidTestEmptyPassword() {
		user.setPassword(new byte[0]);
		
		boolean expected = false;
		boolean actual = validator.isValid(user);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void isValidTestNullPassword() {
		user.setPassword(null);
		
		boolean expected = false;
		boolean actual = validator.isValid(user);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void isValidNullUser() {
		user = null;
		
		boolean expected = false;
		boolean actual = validator.isValid(user);
		
		Assert.assertEquals(expected, actual);
	}

}
