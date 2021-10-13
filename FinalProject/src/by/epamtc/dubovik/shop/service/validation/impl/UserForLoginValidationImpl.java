package by.epamtc.dubovik.shop.service.validation.impl;

import by.epamtc.dubovik.shop.entity.UserForLogin;
import by.epamtc.dubovik.shop.service.validation.UserForLoginValidation;

public class UserForLoginValidationImpl implements UserForLoginValidation {
	
	public boolean isValid(UserForLogin user) {
		boolean isValid = user != null &&
				user.getLogin() != null && user.getLogin().length() != 0 &&
				user.getPassword() != null && user.getPassword().length != 0;
		return isValid;
	}

}
