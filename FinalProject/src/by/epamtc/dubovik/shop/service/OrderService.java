package by.epamtc.dubovik.shop.service;

import by.epamtc.dubovik.shop.entity.Cart;
import by.epamtc.dubovik.shop.entity.Order;
import by.epamtc.dubovik.shop.service.exception.InvalidException;
import by.epamtc.dubovik.shop.service.exception.ServiceException;

public interface OrderService {
	
	public boolean makeOrder(long userId, Cart cart) throws ServiceException, InvalidException;
	
	public Order takeOrderById(long orderId) throws ServiceException;
	
	public int calculatePrice(long orderId) throws ServiceException, InvalidException;
	
	public int calculatePrice(Order order) throws ServiceException, InvalidException;
	
	public boolean payForOrder(long orderId, long card) throws ServiceException, InvalidException;

	boolean deliverOrder(long orderId) throws ServiceException, InvalidException;

}
