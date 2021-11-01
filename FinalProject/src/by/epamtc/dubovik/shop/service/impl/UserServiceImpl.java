package by.epamtc.dubovik.shop.service.impl;

import java.util.Arrays;

import by.epamtc.dubovik.shop.dao.DAOException;
import by.epamtc.dubovik.shop.dao.DAOFactory;
import by.epamtc.dubovik.shop.dao.UserDAO;
import by.epamtc.dubovik.shop.entity.User;
import by.epamtc.dubovik.shop.entity.UserForLogin;
import by.epamtc.dubovik.shop.entity.UserLogged;
import by.epamtc.dubovik.shop.service.ServiceFactory;
import by.epamtc.dubovik.shop.service.UserService;
import by.epamtc.dubovik.shop.service.exception.InvalidException;
import by.epamtc.dubovik.shop.service.exception.ServiceException;
import by.epamtc.dubovik.shop.service.exception.UserAlreadyExistException;
import by.epamtc.dubovik.shop.service.util.EncryptUtil;
import by.epamtc.dubovik.shop.service.validation.UserForLoginValidation;
import by.epamtc.dubovik.shop.service.validation.UserValidation;
import by.epamtc.dubovik.shop.service.validation.ValidationFactory;

public class UserServiceImpl implements UserService {

	private final static int USER_ROLE_ID = 2;
	
	@Override
	public UserLogged authorize(UserForLogin user) 
			throws ServiceException, InvalidException {
		UserForLoginValidation validator = 
				ValidationFactory.getInstance().getUserForLoginValidation();
		if(!validator.isValid(user)) {
			throw new InvalidException("Login or password is empthy");
		}
		UserLogged logged = null;
		UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
		try {
			EncryptUtil encrypt = ServiceFactory.getInstance()
					.getEncryptUtil();
			byte[] encryptedPassword = userDAO
					.findPassword(user.getLogin());
			if(encryptedPassword != null &&
					encrypt.cheakPassword(user.getPassword(), encryptedPassword)) {
				logged = userDAO.findLoggedUser(user.getLogin());
			};
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return logged;
	}

	@Override
	public boolean register(User user, byte[] passwordRepeat)
			throws InvalidException, 
			UserAlreadyExistException, ServiceException {
		
		boolean isRegistrated = false;
		
		EncryptUtil encrypt = ServiceFactory.getInstance()
				.getEncryptUtil();
		UserValidation validator = ValidationFactory.getInstance()
				.getUserValidation();
		
		if(!cheakPasswordToRepeat(user.getPassword(), passwordRepeat) || 
				!validator.isValid(user)) {
			throw new InvalidException("Invalid user");
		}
		if(isExist(user)) {
			throw new UserAlreadyExistException("User with this login already exist");
		}
		
		user.setRoleId(USER_ROLE_ID);
		user.setPassword(encrypt.incryptPassword(user.getPassword()));
		UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
		
		try {
			isRegistrated = userDAO.create(user);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		
		return isRegistrated;
	}
	
	private boolean cheakPasswordToRepeat(byte[] password, 
											byte[] passwordRepeat) {
		return Arrays.equals(password, passwordRepeat);
	}
	
	private boolean isExist(User user) throws ServiceException {
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

	@Override
	public boolean blacklistUser(long userId) 
			throws ServiceException {
		
		boolean isBlacklisted = false;
		
		DAOFactory factory = DAOFactory.getInstance();
		UserDAO userDAO = factory.getUserDAO();
		
		try {
			User user = userDAO.findById(userId);
			if( user!= null) {
				user.setIsBlacklisted(true);
				isBlacklisted = userDAO.update(user);
			}
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return isBlacklisted;
	}

}
