package by.epamtc.dubovik.shop.service;

import java.time.LocalDateTime;

import by.epamtc.dubovik.shop.entity.Price;
import by.epamtc.dubovik.shop.service.exception.ServiceException;

public interface PriceService {
	
	public Price takePriceByProduct(long productId) throws ServiceException;
	public Price takePriceByProduct(long productId, LocalDateTime date) throws ServiceException;
	public boolean changePrice(Price price) throws ServiceException;

}
