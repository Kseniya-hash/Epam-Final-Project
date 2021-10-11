package by.epamtc.dubovik.shop.service;

import by.epamtc.dubovik.shop.entity.User;
import by.epamtc.dubovik.shop.service.exception.InvalidException;
import by.epamtc.dubovik.shop.service.exception.ServiceException;
import by.epamtc.dubovik.shop.service.exception.UserAlreadyExistException;

public interface RegisterService {

	public boolean register(User user, byte[] passwordRepeat) 
					throws ServiceException, InvalidException, UserAlreadyExistException;
}
