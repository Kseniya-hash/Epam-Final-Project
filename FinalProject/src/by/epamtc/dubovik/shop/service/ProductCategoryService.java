package by.epamtc.dubovik.shop.service;

import java.util.List;

import by.epamtc.dubovik.shop.entity.ProductCategory;
import by.epamtc.dubovik.shop.service.exception.ServiceException;

public interface ProductCategoryService {

	public List<ProductCategory> takeAllCategories() throws ServiceException;
	public ProductCategory takeByName(String name) throws ServiceException;
	public ProductCategory takeById(long id) throws ServiceException;
	public boolean createCategory(ProductCategory category) throws ServiceException;
}
