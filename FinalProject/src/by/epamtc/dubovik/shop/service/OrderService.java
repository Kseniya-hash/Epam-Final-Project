package by.epamtc.dubovik.shop.service;

import by.epamtc.dubovik.shop.entity.Cart;
import by.epamtc.dubovik.shop.entity.Order;
import by.epamtc.dubovik.shop.service.exception.InvalidException;
import by.epamtc.dubovik.shop.service.exception.ServiceException;

public interface OrderService {
	
	public boolean makeOrder(int userId, Cart cart) throws ServiceException, InvalidException;

}
