package by.epamtc.dubovik.shop.service;

import by.epamtc.dubovik.shop.entity.Cart;
import by.epamtc.dubovik.shop.entity.Order;
import by.epamtc.dubovik.shop.service.exception.InvalidException;
import by.epamtc.dubovik.shop.service.exception.ServiceException;

public interface OrderService {
	
	public boolean makeOrder(int userId, Cart cart) throws ServiceException, InvalidException;
	
	public Order takeOrderById(int orderId) throws ServiceException;
	
	public int calculatePrice(int orderId) throws ServiceException, InvalidException;
	
	public int calculatePrice(Order order) throws ServiceException, InvalidException;
	
	public boolean payForOrder(int orderId, long card) throws ServiceException, InvalidException;

	boolean deliverOrder(int orderId) throws ServiceException, InvalidException;

}
