package by.epamtc.dubovik.shop.dao;

import by.epamtc.dubovik.shop.dao.generic.GenericIntIdDAO;
import by.epamtc.dubovik.shop.entity.Product;

public interface ProductDAO extends GenericIntIdDAO<Product> {
	
	public Product findByName(String name) throws DAOException;

}
