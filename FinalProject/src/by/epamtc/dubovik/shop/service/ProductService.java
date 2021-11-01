package by.epamtc.dubovik.shop.service;

import by.epamtc.dubovik.shop.entity.Product;
import by.epamtc.dubovik.shop.service.exception.InvalidException;
import by.epamtc.dubovik.shop.service.exception.ServiceException;

public interface ProductService {
	
	/**
	 * Find product by its id.
	 * If product with such id does not exist return null
	 * @param productId
	 * @return
	 * @throws ServiceException
	 */
	public Product findProductInfo(long productId) 
			throws ServiceException;
	
	/**
	 * Find product by its name.
	 * If product with such name does not exist return null
	 * @param productName
	 * @return
	 * @throws ServiceException
	 */
	public Product findProductInfo(String productName) 
			throws ServiceException;
	
	/**
	 * Redact product. 
	 * If unable to redact product return false.
	 * If product contains invalid fields throws InvalidException
	 * @param product
	 * @return
	 * @throws InvalidException
	 * @throws ServiceException
	 */
	public boolean updateProductInfo(Product product) 
			throws InvalidException, ServiceException;
	
	/**
	 * Create product. 
	 * If unable to create product return false.
	 * If product contains invalid fields throws InvalidException
	 * @param product
	 * @return
	 * @throws InvalidException
	 * @throws ServiceException
	 */
	public boolean createProduct(Product product) 
			throws InvalidException, ServiceException;
	
}
