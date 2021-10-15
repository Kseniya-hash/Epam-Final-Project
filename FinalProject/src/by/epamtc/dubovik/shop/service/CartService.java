package by.epamtc.dubovik.shop.service;

import java.util.List;

import by.epamtc.dubovik.shop.entity.Cart;
import by.epamtc.dubovik.shop.entity.ProductForCart;
import by.epamtc.dubovik.shop.service.exception.InvalidException;
import by.epamtc.dubovik.shop.service.exception.ServiceException;

public interface CartService {
	
	public boolean add(Cart cart, long productId, int count) throws InvalidException, ServiceException;
	public boolean remove(Cart cart, long productId, int count) throws InvalidException;
	
	public boolean increment(Cart cart, long productId) throws InvalidException, ServiceException;
	public boolean decrement(Cart cart, long productId) throws InvalidException;
	
	public Cart createIfDontExist(Cart cart);
	
	public void remove(Cart cart, long productId);
	
	public List<ProductForCart> takeProducts(Cart cart) throws InvalidException, ServiceException;
}
