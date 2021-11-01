package by.epamtc.dubovik.shop.service.impl;

import by.epamtc.dubovik.shop.dao.DAOException;
import by.epamtc.dubovik.shop.dao.DAOFactory;
import by.epamtc.dubovik.shop.dao.ProductDAO;
import by.epamtc.dubovik.shop.entity.Product;
import by.epamtc.dubovik.shop.service.ProductService;
import by.epamtc.dubovik.shop.service.exception.InvalidException;
import by.epamtc.dubovik.shop.service.exception.ServiceException;
import by.epamtc.dubovik.shop.service.validation.ProductValidation;
import by.epamtc.dubovik.shop.service.validation.ValidationFactory;

public class ProductServiceImpl implements ProductService {

	@Override
	public Product findProductInfo(long productId) 
			throws ServiceException {
		
		ProductDAO productDAO = DAOFactory.getInstance().getProductDAO();
		Product product = null;
		try {
			product = productDAO.findById(productId);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return product;
	}
	
	@Override
	public Product findProductInfo(String productName) 
			throws ServiceException {
		
		ProductDAO productDAO = DAOFactory.getInstance().getProductDAO();
		Product product = null;
		try {
			product = productDAO.findByName(productName);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return product;
	}

	@Override
	public boolean updateProductInfo(Product product) 
			throws InvalidException, ServiceException {
		
		ProductDAO productDAO = 
				DAOFactory.getInstance().getProductDAO();
		ProductValidation validator = ValidationFactory.getInstance()
				.getProductValidation();
		boolean isRedacted = false;
		
		if(!validator.isValid(product)) {
			throw new InvalidException("Invalid product");
		}
		
		try {
			isRedacted = productDAO.update(product);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		
		return isRedacted;
	}

	@Override
	public boolean createProduct(Product product) 
			throws InvalidException, ServiceException {
		ProductDAO productDAO = DAOFactory.getInstance().getProductDAO();
		ProductValidation validator = ValidationFactory.getInstance()
				.getProductValidation();
		boolean isCreated = false;
		
		if(!validator.isValid(product)) {
			throw new InvalidException("Invalid product");
		}
		
		try {
			isCreated = productDAO.create(product);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		
		return isCreated;
	}
}
