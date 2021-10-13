package by.epamtc.dubovik.shop.service;

import by.epamtc.dubovik.shop.entity.User;
import by.epamtc.dubovik.shop.entity.UserForLogin;
import by.epamtc.dubovik.shop.entity.UserLogged;
import by.epamtc.dubovik.shop.service.exception.InvalidException;
import by.epamtc.dubovik.shop.service.exception.ServiceException;
import by.epamtc.dubovik.shop.service.exception.UserAlreadyExistException;

public interface UserService {

	public UserLogged authorize(UserForLogin user) 
			throws ServiceException, InvalidException;
	
	public boolean register(User user, byte[] passwordRepeat) 
			throws ServiceException, InvalidException, UserAlreadyExistException;
	
	public boolean blacklistUser(int userId) throws ServiceException;
	
}
