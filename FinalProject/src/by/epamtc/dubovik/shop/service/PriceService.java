package by.epamtc.dubovik.shop.service;

import java.sql.Timestamp;

import by.epamtc.dubovik.shop.entity.Price;
import by.epamtc.dubovik.shop.service.exception.ServiceException;

public interface PriceService {
	
	public Price takePriceByProduct(int productId) throws ServiceException;
	public Price takePriceByProduct(int productId, Timestamp date) throws ServiceException;
	public boolean changePrice(Price price) throws ServiceException;

}
