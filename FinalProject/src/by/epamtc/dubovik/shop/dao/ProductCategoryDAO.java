package by.epamtc.dubovik.shop.dao;

import java.util.List;

import by.epamtc.dubovik.shop.dao.generic.GenericDAO;
import by.epamtc.dubovik.shop.entity.ProductCategory;

public interface ProductCategoryDAO extends GenericDAO <ProductCategory>{
	
	public ProductCategory findByName(String name) throws DAOException;
	public List<ProductCategory> findAll() throws DAOException;
	
}
