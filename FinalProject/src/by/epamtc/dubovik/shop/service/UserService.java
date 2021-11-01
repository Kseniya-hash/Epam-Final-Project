package by.epamtc.dubovik.shop.service;

import by.epamtc.dubovik.shop.entity.User;
import by.epamtc.dubovik.shop.entity.UserForLogin;
import by.epamtc.dubovik.shop.entity.UserLogged;
import by.epamtc.dubovik.shop.service.exception.InvalidException;
import by.epamtc.dubovik.shop.service.exception.ServiceException;
import by.epamtc.dubovik.shop.service.exception.UserAlreadyExistException;

public interface UserService {

	/**
	 * Authorize user and return logged user. 
	 * If user with such login and password does not exist return null.
	 * If either login or password is empty throw InvalidException
	 * @param user
	 * @return
	 * @throws ServiceException
	 * @throws InvalidException
	 */
	public UserLogged authorize(UserForLogin user) 
			throws ServiceException, InvalidException;
	
	/**
	 * Register user. If unable to register return false
	 * If user contains invalid fields throws InvalidException
	 * If user already exist throws UserAlreadyExistException
	 * @param user
	 * @param passwordRepeat
	 * @return
	 * @throws ServiceException
	 * @throws InvalidException
	 * @throws UserAlreadyExistException
	 */
	public boolean register(User user, byte[] passwordRepeat) 
			throws ServiceException, InvalidException, UserAlreadyExistException;
	/**
	 * Blacklist user. If unable to blacklist return false
	 * @param userId
	 * @return
	 * @throws ServiceException
	 */
	public boolean blacklistUser(long userId) throws ServiceException;
	
}
