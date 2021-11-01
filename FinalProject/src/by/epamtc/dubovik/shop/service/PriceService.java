package by.epamtc.dubovik.shop.service;

import java.time.LocalDateTime;

import by.epamtc.dubovik.shop.entity.Price;
import by.epamtc.dubovik.shop.service.exception.ServiceException;

public interface PriceService {
	
	
	/**
	 * Find a current price of product by product id. 
	 * If product with such id does not exist return null
	 * @param productId
	 * @return
	 * @throws ServiceException
	 */
	public Price findPriceByProduct(long productId) throws ServiceException;
	
	/**
	 * Find a price of product during specific time by product id and date. 
	 * If product with such id does not exist return null
	 * @param productId
	 * @param date
	 * @return
	 * @throws ServiceException
	 */
	public Price findPriceByProduct(long productId, LocalDateTime date) 
			throws ServiceException;
	
	/**
	 * Change price for a product. Set price date to the moment of change.
	 * If new price is the same as current or new price is invalid 
	 * does now set new price.
	 * @param price
	 * @return
	 * @throws ServiceException
	 */
	public boolean changePrice(Price price) throws ServiceException;

}
