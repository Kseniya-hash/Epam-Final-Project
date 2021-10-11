package by.epamtc.dubovik.shop.service.impl;

import by.epamtc.dubovik.shop.dao.DAOException;
import by.epamtc.dubovik.shop.dao.UserDAO;
import by.epamtc.dubovik.shop.dao.factory.DAOFactory;
import by.epamtc.dubovik.shop.entity.User;
import by.epamtc.dubovik.shop.service.RegisterService;
import by.epamtc.dubovik.shop.service.exception.InvalidException;
import by.epamtc.dubovik.shop.service.exception.ServiceException;
import by.epamtc.dubovik.shop.service.exception.UserAlreadyExistException;
import by.epamtc.dubovik.shop.service.factory.ServiceFactory;
import by.epamtc.dubovik.shop.service.util.EncryptUtil;
import by.epamtc.dubovik.shop.service.validation.UserValidation;
import by.epamtc.dubovik.shop.service.validation.factory.ValidationFactory;

public class RegisterServiceImpl implements RegisterService {
	
	private final static int USER_ROLE_ID = 2;
	
	@Override
	public boolean register(User user, byte[] passwordRepeat) 
					throws ServiceException, InvalidException, UserAlreadyExistException {
		boolean isRegistrated = false;
		EncryptUtil encrypt = ServiceFactory.getInstance().getEncryptUtil();
		UserValidation validator = ValidationFactory.getInstance().getUserValidation();
		if(!validator.cheakPasswordToRepeat(user.getPassword(), passwordRepeat) || !validator.isValid(user)) {
			throw new InvalidException("Invalid user");
		}
		if(validator.isExist(user)) {
			throw new UserAlreadyExistException("User with this login already exist");
		}
		user.setRoleId(USER_ROLE_ID);
		user.setPassword(encrypt.incryptPassword(user.getPassword()));
		DAOFactory factory = DAOFactory.getInstance();
		UserDAO userDAO = factory.getUserDAO();
		try {
			isRegistrated = userDAO.create(user);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return isRegistrated;
	}
}
