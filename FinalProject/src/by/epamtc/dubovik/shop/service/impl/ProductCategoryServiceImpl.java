package by.epamtc.dubovik.shop.service.impl;

import java.util.List;

import by.epamtc.dubovik.shop.dao.DAOException;
import by.epamtc.dubovik.shop.dao.DAOFactory;
import by.epamtc.dubovik.shop.dao.ProductCategoryDAO;
import by.epamtc.dubovik.shop.entity.ProductCategory;
import by.epamtc.dubovik.shop.service.ProductCategoryService;
import by.epamtc.dubovik.shop.service.exception.ServiceException;

public class ProductCategoryServiceImpl implements ProductCategoryService {

	@Override
	public List<ProductCategory> findAllCategories()
			throws ServiceException {
		
		ProductCategoryDAO productCategoryDAO = 
				DAOFactory.getInstance().getProductCategoryDAO();
		
		List<ProductCategory> categories = null;
		try {
			categories = productCategoryDAO.findAll();
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return categories;
	}

	@Override
	public ProductCategory findByName(String name) 
			throws ServiceException {
		
		ProductCategoryDAO productCategoryDAO = 
				DAOFactory.getInstance().getProductCategoryDAO();
		
		ProductCategory category = null;
		try {
			category = productCategoryDAO.findByName(name);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return category;
	}

	@Override
	public ProductCategory findById(long id) 
			throws ServiceException {
		
		ProductCategoryDAO productCategoryDAO = 
				DAOFactory.getInstance().getProductCategoryDAO();
		ProductCategory category = null;
		try {
			category = productCategoryDAO.findById(id);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return category;
	}

	@Override
	public boolean createCategory(ProductCategory category) 
			throws ServiceException {
		
		ProductCategoryDAO productCategoryDAO = 
				DAOFactory.getInstance().getProductCategoryDAO();
		boolean isCreated = false;
		
		try {
			isCreated = productCategoryDAO.create(category);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		
		return isCreated;
	}

}
