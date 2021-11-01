package by.epamtc.dubovik.shop.dao;

import java.util.List;

import by.epamtc.dubovik.shop.entity.OrderForView;

public interface OrderForViewDAO {

	/**
	 * Find a list of orders. If offset is too big returns an empty list.
	 * @param offset - count of orders to skip at the beginning
	 * @param count - count of orders to return
	 * @return list of orders
	 * @throws DAOException
	 */
	public List<OrderForView> findAll(int offset, int count) throws DAOException;
	
	/**
	 * Count all orders.
	 * @return count of orders
	 * @throws DAOException
	 */
	public int countAll() throws DAOException;
	
	/**
	 * Find a list of orders by user's id. If user with such id  does not exist return null
	 * @param userId - user's id
	 * @return list of orders
	 * @throws DAOException
	 */
	public List<OrderForView> findByUser(long userId) throws DAOException;
	
	/**
	 * Find an order by its id. If order with such id does not exist return null
	 * @param orderId - order's id
	 * @return order
	 * @throws DAOException
	 */
	public OrderForView findById(long orderId) throws DAOException;
	
}
