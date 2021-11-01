package by.epamtc.dubovik.shop.service.validation.impl;

import by.epamtc.dubovik.shop.entity.User;
import by.epamtc.dubovik.shop.service.validation.UserValidation;

public class UserValidationImpl implements UserValidation {
	
	private static final String PHONE_REGEX ="^([0-9]){12}$";
	private static final String PASSWORD_REGEX =
			"^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[*.!@$%^&(){}\\[\\]:;<>,.?\\/\\\\~_+\\-=|]).{8,20}$";
	private static final String E_MAIL_REGEX =
			"^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$";
	
	@Override
	public boolean isValid(User user) {
		boolean isValid = false;
		if(user != null &&
			user.getLogin() != null && user.getLogin().length() != 0 &&
			checkPassword(user.getPassword()) &&
			user.getPhone() != null && user.getPhone().matches(PHONE_REGEX) &&
			user.getName() != null && user.getName().length() != 0 &&
			isEmailValid(user.getEMail())) {
			isValid = true;
		}
		return isValid;
	}

	private boolean isEmailValid(String email) {
		boolean isValid = false;
		if(email == null || email.length() == 0 || email.matches(E_MAIL_REGEX)) {
			isValid = true;
		}
		return isValid;
	}
	
	private boolean checkPassword(byte[] password) {
		return password != null && (new String(password)).matches(PASSWORD_REGEX);
	}
}
