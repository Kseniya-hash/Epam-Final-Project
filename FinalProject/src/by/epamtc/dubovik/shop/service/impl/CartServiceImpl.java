package by.epamtc.dubovik.shop.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import by.epamtc.dubovik.shop.dao.DAOException;
import by.epamtc.dubovik.shop.dao.DAOFactory;
import by.epamtc.dubovik.shop.dao.ProductDAO;
import by.epamtc.dubovik.shop.dao.ProductForCartDAO;
import by.epamtc.dubovik.shop.entity.Cart;
import by.epamtc.dubovik.shop.entity.Product;
import by.epamtc.dubovik.shop.entity.ProductForCart;
import by.epamtc.dubovik.shop.service.CartService;
import by.epamtc.dubovik.shop.service.exception.InvalidException;
import by.epamtc.dubovik.shop.service.exception.ServiceException;
import by.epamtc.dubovik.shop.service.validation.CartValidation;
import by.epamtc.dubovik.shop.service.validation.ValidationFactory;

public class CartServiceImpl implements CartService {
	
	private static final int DEFAULT_COUNT = 1;
	
	@Override
	public boolean increment(Cart cart, long productId) throws InvalidException, ServiceException {
		return add(cart, productId, DEFAULT_COUNT);
	}
	
	private boolean add(Cart cart, long productId, int count) throws InvalidException, ServiceException {
		if(count < 0) {
			throw new InvalidException("Can not add negative number to the cart");
		}
		CartValidation validator = ValidationFactory.getInstance().getCartValidation();
		if(!validator.isValid(cart)) {
			throw new InvalidException("Invalid cart");
		}
		
		boolean isAdded = false;
		
		int currentCount = (cart.get(productId) != null? cart.get(productId) : 0);
		int newCount = count + currentCount;
		
		ProductDAO productDAO = DAOFactory.getInstance().getProductDAO();
		try {
			Product product = productDAO.findById(productId);
			if(product != null && product.getQuantity() >= newCount) {
				cart.put(productId, newCount);
				isAdded = true;
			}
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return isAdded;
	}
	
	@Override
	public boolean decrement(Cart cart, long productId) throws InvalidException {
		return remove(cart, productId, DEFAULT_COUNT);
	}

	private boolean remove(Cart cart, long productId, int count) throws InvalidException {
		if(count < 0) {
			throw new InvalidException("Can not take negative number from the cart");
		}
		CartValidation validator = ValidationFactory.getInstance().getCartValidation();
		if(!validator.isValid(cart)) {
			throw new InvalidException("Invalid cart");
		}
		
		boolean isRemoved = false;
		Integer currentCount = cart.get(productId);
		if(currentCount != null && currentCount >= count) {
			cart.put(productId, currentCount - count);
			isRemoved = true;
		}
		return isRemoved;
	}

	@Override
	public void remove(Cart cart, long productId) throws InvalidException {
		CartValidation validator = ValidationFactory.getInstance().getCartValidation();
		if(!validator.isValid(cart)) {
			throw new InvalidException("Invalid cart");
		}
		if(cart.containsKey(productId)) {
			cart.remove(productId);
		}
	}

	@Override
	public Cart createIfDontExist(Cart cart) {
		if(cart == null) {
			cart = new Cart();
		}
		return cart;
	}

	@Override
	public List<ProductForCart> takeProducts(Cart cart) throws InvalidException, ServiceException {
		CartValidation validator = ValidationFactory.getInstance().getCartValidation();
		if(!validator.isValid(cart)) {
			throw new InvalidException("Invalid cart");
		}
		
		List<ProductForCart> products = new LinkedList<>();
		
		ProductForCartDAO productForCartDAO = DAOFactory.getInstance().getProductForCartDAO();
		for (Map.Entry<Long, Integer> entry : cart.entrySet()) {
			ProductForCart product = null;
			
			try {
				product = productForCartDAO.findById(entry.getKey());
			} catch (DAOException e) {
				throw new ServiceException(e);
			}
			
			if(product != null) {
				if (product.getQuantity() > entry.getValue()) {
					product.setQuantity(entry.getValue());
				} else {
					cart.put(entry.getKey(), product.getQuantity());
				}
				
				products.add(product);
			} else {
				cart.remove(entry.getKey());
			}
		}
		
		return products;
	}
}
