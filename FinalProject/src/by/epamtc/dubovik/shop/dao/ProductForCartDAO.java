package by.epamtc.dubovik.shop.dao;

import by.epamtc.dubovik.shop.entity.ProductForCart;

public interface ProductForCartDAO {
	
	/**
	 * Find product by its id.
	 * If product with such id does not exist return null
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	public ProductForCart findById(long id) throws DAOException;

}
