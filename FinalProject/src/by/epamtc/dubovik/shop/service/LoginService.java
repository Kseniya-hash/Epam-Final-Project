package by.epamtc.dubovik.shop.service;

import by.epamtc.dubovik.shop.entity.UserForLogin;
import by.epamtc.dubovik.shop.entity.UserLogged;
import by.epamtc.dubovik.shop.service.exception.InvalidException;
import by.epamtc.dubovik.shop.service.exception.ServiceException;

public interface LoginService {
	
	public UserLogged authorize(UserForLogin user) throws ServiceException, InvalidException;

}
