package by.epamtc.dubovik.shop.dao;

import by.epamtc.dubovik.shop.dao.generic.GenericDAO;
import by.epamtc.dubovik.shop.entity.Product;

public interface ProductDAO extends GenericDAO<Product> {
	
	public Product findByName(String name) throws DAOException;

}
