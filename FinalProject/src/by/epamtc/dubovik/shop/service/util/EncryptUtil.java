package by.epamtc.dubovik.shop.service.util;

import by.epamtc.dubovik.shop.service.exception.ServiceException;

public interface EncryptUtil {

	public byte[] incryptPassword(byte[] password) throws ServiceException;
	public boolean cheakPassword(byte[] plain, byte[] encrypted) throws ServiceException;
	public void clearArray(byte[] array);
	public long takeSecureRandom();
}
