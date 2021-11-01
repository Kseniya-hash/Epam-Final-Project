package by.epamtc.dubovik.shop.service;

import by.epamtc.dubovik.shop.entity.Cart;
import by.epamtc.dubovik.shop.entity.Order;
import by.epamtc.dubovik.shop.service.exception.InvalidException;
import by.epamtc.dubovik.shop.service.exception.ServiceException;

public interface OrderService {
	
	/**
	 * Create new order for user using products from cart.
	 * @param userId
	 * @param cart
	 * @return True - if order is made
	 * 			False - if quantity of some products from cart is too big to make an order
	 * @throws InvalidException
	 * @throws ServiceException
	 */
	public boolean makeOrder(long userId, Cart cart) 
			throws InvalidException, ServiceException;
	
	/**
	 * Find specific order by it's id
	 * @param orderId
	 * @return
	 * @throws ServiceException
	 */
	public Order findOrderById(long orderId) throws ServiceException;
	
	/**
	 * Calculate price of order with given id. 
	 * If order does not exist throw InvalidException
	 * @param orderId
	 * @return
	 * @throws ServiceException
	 * @throws InvalidException
	 */
	public int calculatePrice(long orderId) 
			throws ServiceException, InvalidException;
	
	/**
	 * Pay for order with given id. If order does not exist throw InvalidException
	 * @param orderId
	 * @param card
	 * @return True - if payment is successful. False - if not
	 * @throws ServiceException
	 * @throws InvalidException
	 */
	public boolean payForOrder(long orderId, long card) 
			throws ServiceException, InvalidException;

	/**
	 * Mark order as delivered. If order does not exist return false
	 * @param orderId
	 * @return
	 * @throws ServiceException
	 */
	boolean deliverOrder(long orderId) throws ServiceException;

}
