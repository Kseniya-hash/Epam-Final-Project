package by.epamtc.dubovik.shop.dao;

import by.epamtc.dubovik.shop.dao.generic.GenericDAO;
import by.epamtc.dubovik.shop.entity.Product;

public interface ProductDAO extends GenericDAO<Product> {
	
	/**
	 * Find product by its name.
	 * If product with such name does not exist return null
	 * @param name
	 * @return
	 * @throws DAOException
	 */
	public Product findByName(String name) throws DAOException;

}
