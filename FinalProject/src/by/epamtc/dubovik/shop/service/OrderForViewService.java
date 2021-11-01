package by.epamtc.dubovik.shop.service;

import java.util.List;

import by.epamtc.dubovik.shop.entity.OrderForView;
import by.epamtc.dubovik.shop.service.exception.ServiceException;

public interface OrderForViewService {
	
	/**
	 * Find a list of orders by user's id. 
	 * If user with such id  does not exist return null
	 * @param userId - user's id
	 * @return list of orders
	 * @throws ServiceException
	 */
	List<OrderForView> findOrders(long userId) 
			throws ServiceException;
	
	/**
	 * Find a list of orders for specific page given the amount of orders on one page.
	 * If no orders for this page is found return an empty list
	 * @param offset - count of orders to skip at the beginning
	 * @param count - count of orders to return
	 * @return list of orders
	 * @throws ServiceException
	 */
	List<OrderForView> findOrders(int page, int count) throws ServiceException;
	
	/**
	 * Count all orders.
	 * @return count of orders
	 * @throws ServiceException
	 */
	public int countAll() throws ServiceException;
	
	/**
	 * Find an order by its id. If order with such id does not exist return null
	 * @param orderId - order's id
	 * @return order
	 * @throws ServiceException
	 */
	OrderForView findById(long orderId) throws ServiceException;

}
