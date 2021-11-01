package by.epamtc.dubovik.shop.service.impl;

import java.time.LocalDateTime;

import by.epamtc.dubovik.shop.dao.DAOException;
import by.epamtc.dubovik.shop.dao.DAOFactory;
import by.epamtc.dubovik.shop.dao.PriceDAO;
import by.epamtc.dubovik.shop.entity.Price;
import by.epamtc.dubovik.shop.service.PriceService;
import by.epamtc.dubovik.shop.service.exception.ServiceException;
import by.epamtc.dubovik.shop.service.validation.PriceValidation;
import by.epamtc.dubovik.shop.service.validation.ValidationFactory;

public class PriceServiceImpl implements PriceService {

	@Override
	public Price findPriceByProduct(long productId) 
			throws ServiceException {
		
		PriceDAO priceDAO = DAOFactory.getInstance().getPriceDAO();
		Price price = null;
		try {
			price = priceDAO.findByProduct(productId);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return price;
	}

	@Override
	public Price findPriceByProduct(long productId, LocalDateTime date) 
			throws ServiceException {
		
		PriceDAO priceDAO = DAOFactory.getInstance().getPriceDAO();
		Price price = null;
		try {
			price = priceDAO.findByProduct(productId, date);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return price;
	}

	@Override
	public boolean changePrice(Price price) 
			throws ServiceException {
		
		PriceDAO priceDAO = DAOFactory
				.getInstance().getPriceDAO();
		PriceValidation validator = 
				ValidationFactory.getInstance().getPriceValidation();
		boolean isChanged = false;
		if(validator.isValid(price)) {
			try {
				Price currentPrice = priceDAO
						.findByProduct(price.getProductId());
				if(currentPrice == null || 
						price.getSellingPrice() != currentPrice.getSellingPrice() ||
						price.getPurchasePrice() != currentPrice.getPurchasePrice()) {
					price.setDate(LocalDateTime.now());
					isChanged = priceDAO.create(price);
				}
			} catch (DAOException e) {
				throw new ServiceException(e);
			}
		}
		return isChanged;
	}

}
