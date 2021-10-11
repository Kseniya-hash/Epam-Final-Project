package by.epamtc.dubovik.shop.service.impl;

import by.epamtc.dubovik.shop.dao.DAOException;
import by.epamtc.dubovik.shop.dao.UserDAO;
import by.epamtc.dubovik.shop.dao.factory.DAOFactory;
import by.epamtc.dubovik.shop.entity.UserForLogin;
import by.epamtc.dubovik.shop.entity.UserLogged;
import by.epamtc.dubovik.shop.service.LoginService;
import by.epamtc.dubovik.shop.service.exception.InvalidException;
import by.epamtc.dubovik.shop.service.exception.ServiceException;
import by.epamtc.dubovik.shop.service.factory.ServiceFactory;
import by.epamtc.dubovik.shop.service.util.EncryptUtil;
import by.epamtc.dubovik.shop.service.validation.UserForLoginValidation;
import by.epamtc.dubovik.shop.service.validation.factory.ValidationFactory;

public class LoginServiceImpl implements LoginService {
	@Override
	public UserLogged authorize(UserForLogin user) throws ServiceException, InvalidException {
		UserForLoginValidation validator = ValidationFactory.getInstance().getUserForLoginValidation();
		if(!validator.isValid(user)) {
			throw new InvalidException("Login or password is empthy");
		}
		UserLogged logged = null;
		DAOFactory factory = DAOFactory.getInstance();
		UserDAO userDAO = factory.getUserDAO();
		try {
			EncryptUtil encrypt = ServiceFactory.getInstance().getEncryptUtil();
			byte[] encryptedPassword = userDAO.takePassword(user.getLogin());
			if(encrypt.cheakPassword(user.getPassword(), encryptedPassword)) {
				user.setPassword(encryptedPassword);
				logged = userDAO.authorize(user);
			};
			logged = userDAO.authorize(user);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return logged;
	}
}
