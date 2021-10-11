package by.epamtc.dubovik.shop.dao;

import by.epamtc.dubovik.shop.entity.ProductForCart;

public interface ProductForCartDAO {
	
	public ProductForCart findById(int id) throws DAOException;

}
