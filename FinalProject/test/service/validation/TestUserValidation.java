package service.validation;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import by.epamtc.dubovik.shop.entity.User;
import by.epamtc.dubovik.shop.service.validation.UserValidation;
import by.epamtc.dubovik.shop.service.validation.ValidationFactory;

public class TestUserValidation {

	private UserValidation validator = 
			ValidationFactory.getInstance().getUserValidation();
	
	private User user = new User();
	
	@Before
	public void setUp() {
		user.setId(1);
		user.setLogin("mari");
		user.setName("Иванова Мария");
		user.setPassword(new String("Pass_123").getBytes());
		user.setPhone("375291110088");
		user.setEMail("mari@gmail.com");
		user.setRoleId(2);
	}
	
	@Test
	public void isValidTest() {
		boolean expected = true;
		boolean actual = validator.isValid(user);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void isValidTestEmptyEMail() {
		user.setEMail("");
		
		boolean expected = true;
		boolean actual = validator.isValid(user);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void isValidTestNullEMail() {
		user.setEMail(null);
		
		boolean expected = true;
		boolean actual = validator.isValid(user);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void isValidTestInvalidEMail() {
		user.setEMail("marigmail.com");
		
		boolean expected = false;
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
	public void isValidTestEmptyName() {
		user.setName("");
		
		boolean expected = false;
		boolean actual = validator.isValid(user);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void isValidTestNullName() {
		user.setName(null);
		
		boolean expected = false;
		boolean actual = validator.isValid(user);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void isValidTestPasswordTooShort() {
		user.setPassword(new String("Pass_12").getBytes());
		
		boolean expected = false;
		boolean actual = validator.isValid(user);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void isValidTestPasswordTooLong() {
		user.setPassword(new String("Pass_1234567890123456").getBytes());
		
		boolean expected = false;
		boolean actual = validator.isValid(user);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void isValidTestPasswordNoNumbers() {
		user.setPassword(new String("Password__").getBytes());
		
		boolean expected = false;
		boolean actual = validator.isValid(user);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void isValidTestPasswordNoUpperCase() {
		user.setPassword(new String("pass_123").getBytes());
		
		boolean expected = false;
		boolean actual = validator.isValid(user);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void isValidTestPasswordNoLowerCase() {
		user.setPassword(new String("PASS_123").getBytes());
		
		boolean expected = false;
		boolean actual = validator.isValid(user);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void isValidTestPasswordNoSumbols() {
		user.setPassword(new String("Password123").getBytes());
		
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
	public void isValidTestPhoneTooShort() {
		user.setPhone("11122233344");
		
		boolean expected = false;
		boolean actual = validator.isValid(user);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void isValidTestPhoneTooLong() {
		user.setPhone("1112223334445");
		
		boolean expected = false;
		boolean actual = validator.isValid(user);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void isValidTestPhoneHasLetters() {
		user.setPhone("11122233344a");
		
		boolean expected = false;
		boolean actual = validator.isValid(user);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void isValidTestEmptyPhone() {
		user.setPhone("");
		
		boolean expected = false;
		boolean actual = validator.isValid(user);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void isValidTestNullPhone() {
		user.setPhone(null);
		
		boolean expected = false;
		boolean actual = validator.isValid(user);
		
		Assert.assertEquals(expected, actual);
	}
}
