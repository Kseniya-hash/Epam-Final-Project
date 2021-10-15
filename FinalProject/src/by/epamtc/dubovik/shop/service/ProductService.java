package by.epamtc.dubovik.shop.service;

import by.epamtc.dubovik.shop.entity.Product;
import by.epamtc.dubovik.shop.service.exception.ServiceException;

public interface ProductService {
	
	public Product takeProductInfo(long productId) throws ServiceException;
	public Product takeProductInfo(String productName) throws ServiceException;
	public boolean redactProductInfo(Product product) throws ServiceException;
	public boolean createProduct(Product product) throws ServiceException;

}
