package by.epamtc.dubovik.shop.service;

import java.util.List;

import by.epamtc.dubovik.shop.entity.ProductCategory;
import by.epamtc.dubovik.shop.service.exception.ServiceException;

public interface ProductCategoryService {

	/**
	 * Find all product categories. If there is none returns an empty list
	 * @return list categories
	 * @throws ServiceException
	 */
	public List<ProductCategory> findAllCategories() throws ServiceException;
	
	/**
	 * Find a product category by its name.
	 * If category with such name does not exist return null
	 * @param name
	 * @return
	 * @throws ServiceException
	 */
	public ProductCategory findByName(String name) throws ServiceException;
	
	/**
	 * Find a product category by its id.
	 * If category with such id does not exist return null
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public ProductCategory findById(long id) throws ServiceException;
	
	/**
	 * Create product category. If unable to create category return false.
	 * @param category
	 * @return
	 * @throws ServiceException
	 */
	public boolean createCategory(ProductCategory category) throws ServiceException;
}
