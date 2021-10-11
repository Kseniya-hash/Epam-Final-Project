package by.epamtc.dubovik.shop.service;

import by.epamtc.dubovik.shop.entity.Product;
import by.epamtc.dubovik.shop.service.exception.ServiceException;

public interface ProductService {
	
	public Product takeProductInfo(int productId) throws ServiceException;
	public boolean redactProductInfo(Product product) throws ServiceException;

}