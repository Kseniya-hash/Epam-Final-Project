package by.epamtc.dubovik.shop.service;

import java.util.List;

import by.epamtc.dubovik.shop.entity.Cart;
import by.epamtc.dubovik.shop.entity.ProductForCart;
import by.epamtc.dubovik.shop.service.exception.InvalidException;
import by.epamtc.dubovik.shop.service.exception.ServiceException;

public interface CartService {
	
	/**
	 * Increment quantity of product with given id by one.
	 * If product is not in the cart add it and set it's quantity to one.
	 * @param cart
	 * @param productId
	 * @return  True - if quantity is increased. 
	 * 			False - If there is not enough product to increase it's quantity
	 * @throws InvalidException
	 * @throws ServiceException
	 */
	public boolean increment(Cart cart, long productId) 
			throws InvalidException, ServiceException;
	
	/**
	 * Decrement quantity of product with given id by one.
	 * @param cart
	 * @param productId
	 * @return  True - if quantity is decreased. 
	 * 			False - if product is not in the cart
	 * @throws InvalidException
	 * @throws ServiceException
	 */
	public boolean decrement(Cart cart, long productId) 
			throws InvalidException;
	
	/**
	 * Remove product with given id from the cart.
	 * @param cart
	 * @param productId
	 * @throws InvalidException
	 * @throws ServiceException
	 */
	public void remove(Cart cart, long productId) 
			throws InvalidException;
	/**
	 * If cart is null create new cart and return it's reference
	 * @param cart
	 * @return
	 */
	public Cart createIfDontExist(Cart cart);
	
	/**
	 * Take product by it's id from cart
	 * @param cart
	 * @return
	 * @throws InvalidException
	 * @throws ServiceException
	 */
	public List<ProductForCart> takeProducts(Cart cart) 
			throws InvalidException, ServiceException;
}
