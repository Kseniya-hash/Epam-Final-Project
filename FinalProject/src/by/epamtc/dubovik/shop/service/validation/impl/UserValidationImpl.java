package by.epamtc.dubovik.shop.service.validation.impl;

import java.util.Arrays;

import by.epamtc.dubovik.shop.dao.DAOException;
import by.epamtc.dubovik.shop.dao.UserDAO;
import by.epamtc.dubovik.shop.dao.factory.DAOFactory;
import by.epamtc.dubovik.shop.entity.User;
import by.epamtc.dubovik.shop.service.exception.ServiceException;
import by.epamtc.dubovik.shop.service.validation.UserValidation;

public class UserValidationImpl implements UserValidation {
	
	private static final String PHONE_REGEX ="^([0-9]){12}$";
	private static final String PASSWORD_REGEX ="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[*.!@$%^&(){}\\[\\]:;<>,.?\\/\\\\~_+-=|]).{8,20}$";
	private static final String E_MAIL_REGEX ="^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$";
	
	@Override
	public boolean isValid(User user) {
		boolean isValid = true;
		if(user.getLogin() == null && user.getLogin().length() == 0) {
			isValid = false;
		}
		if(!checkPassword(user.getPassword())) {
			isValid = false;
		}
		if(!user.getPhone().matches(PHONE_REGEX)) {
			isValid = false;
		}
		if(user.getName() == null && user.getName().length() == 0) {
			isValid = false;
		}
		if(user.getEMail() != null && user.getEMail().length() != 0 && !user.getEMail().matches(E_MAIL_REGEX)) {
			isValid = false;
		}
		return isValid;
	}

	private boolean checkPassword(byte[] password) {
		return (new String(password)).matches(PASSWORD_REGEX);
	}
	
	@Override
	public boolean cheakPasswordToRepeat(byte[] password, byte[] passwordRepeat) {
		return Arrays.equals(password, passwordRepeat);
	}
	
	@Override
	public boolean isExist(User user) throws ServiceException {
		DAOFactory factory = DAOFactory.getInstance();
		UserDAO userDAO = factory.getUserDAO();
		boolean isExist = false;
		try {
			isExist = userDAO.findUserInfoByLogin(user.getLogin()) != null;
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return isExist;
	}
}
