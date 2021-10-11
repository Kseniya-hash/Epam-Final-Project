package by.epamtc.dubovik.shop.service.impl;

import by.epamtc.dubovik.shop.dao.DAOException;
import by.epamtc.dubovik.shop.dao.ProductDAO;
import by.epamtc.dubovik.shop.dao.factory.DAOFactory;
import by.epamtc.dubovik.shop.entity.Product;
import by.epamtc.dubovik.shop.service.ProductService;
import by.epamtc.dubovik.shop.service.exception.ServiceException;
import by.epamtc.dubovik.shop.service.validation.ProductValidation;
import by.epamtc.dubovik.shop.service.validation.factory.ValidationFactory;

public class ProductServiceImpl implements ProductService {

	@Override
	public Product takeProductInfo(int productId) throws ServiceException {
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
	public boolean redactProductInfo(Product product) throws ServiceException {
		ProductDAO productDAO = DAOFactory.getInstance().getProductDAO();
		ProductValidation validator = ValidationFactory.getInstance().getProductValidation();
		boolean isRedacted = false;
		if(validator.isValid(product)) {
			try {
				isRedacted = productDAO.update(product);
			} catch (DAOException e) {
				throw new ServiceException(e);
			}
		}
		return isRedacted;
	}

}
