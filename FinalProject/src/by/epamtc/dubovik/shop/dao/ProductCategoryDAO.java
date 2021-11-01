package by.epamtc.dubovik.shop.dao;

import java.util.List;

import by.epamtc.dubovik.shop.dao.generic.GenericDAO;
import by.epamtc.dubovik.shop.entity.ProductCategory;

public interface ProductCategoryDAO extends GenericDAO <ProductCategory>{
	
	/**
	 * Find a product category by its name.
	 * If category with such name does not exist return null
	 * @param name
	 * @return
	 * @throws DAOException
	 */
	public ProductCategory findByName(String name) throws DAOException;
	
	/**
	 * Find all product categories. If there is none returns an empty list
	 * @return list categories
	 * @throws DAOException
	 */
	public List<ProductCategory> findAll() throws DAOException;
	
}
