package by.epamtc.dubovik.shop.dao;

import java.time.LocalDateTime;

import by.epamtc.dubovik.shop.entity.Price;

public interface PriceDAO {

	/**
	 * add entity to the database. If unable to add entity return false
	 * @param entity
	 * @return
	 * @throws DAOException
	 */
	public boolean create(Price entity) throws DAOException;
	
	/**
	 * Find a price of product during specific time by product id and date. 
	 * If product with such id does not exist return null
	 * @param productId - product's id
	 * @param date - date
	 * @return price
	 * @throws DAOException
	 */
	public Price findByProduct(long productId, LocalDateTime date) throws DAOException;
	
	/**
	 * Find a current price of product by product id. 
	 * If product with such id does not exist return null
	 * @param productId - product's id
	 * @return price
	 * @throws DAOException
	 */
	public Price findByProduct(long productId) throws DAOException;
	
	/**
	 * Find a price by its id. If price with such id does not exist return null
	 * @param id - price's id
	 * @return price
	 * @throws DAOException
	 */
	public Price findById(long id) throws DAOException;
	
}
