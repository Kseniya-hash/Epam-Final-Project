package by.epamtc.dubovik.shop.dao;

import by.epamtc.dubovik.shop.entity.ProductForCart;

public interface ProductForCartDAO {
	
	public ProductForCart findById(long id) throws DAOException;

}
