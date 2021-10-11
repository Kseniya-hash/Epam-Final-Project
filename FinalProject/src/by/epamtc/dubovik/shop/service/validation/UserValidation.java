package by.epamtc.dubovik.shop.service.validation;

import by.epamtc.dubovik.shop.entity.User;
import by.epamtc.dubovik.shop.service.exception.ServiceException;

public interface UserValidation {
	
	public boolean isValid(User user);
	public boolean cheakPasswordToRepeat(byte[] password, byte[] passwordRepeat);
	public boolean isExist(User user) throws ServiceException;

}
