package by.epamtc.dubovik.shop.service;

import by.epamtc.dubovik.shop.service.impl.*;
import by.epamtc.dubovik.shop.service.util.*;
import by.epamtc.dubovik.shop.service.util.impl.*;

public class ServiceFactory {
	
	private final UserService userService;
	private final ProductForMenuService productForMenuService;
	private final ProductService productService;
	private final PriceService priceService;
	private final CartService cartService;
	private final OrderService orderService;
	private final OrderForViewService orderForViewService;
	private final ProductCategoryService productCategoryService;
	private final CommentService commentService;
	
	private final EncryptUtil encryptUtil;
	
	private ServiceFactory() {
		userService = new UserServiceImpl();
		productForMenuService = new ProductForMenuServiceImpl();
		productService = new ProductServiceImpl();
		priceService = new PriceServiceImpl();
		cartService = new CartServiceImpl();
		orderService = new OrderServiceImpl();
		orderForViewService = new OrderForViewServiceImpl();
		productCategoryService = new ProductCategoryServiceImpl();
		commentService = new CommentServiceImpl();
		
		encryptUtil = new EncryptUtilImpl();
	}
	
	private static class SigletonHolder {
		private final static ServiceFactory INSTANCE = new ServiceFactory();
	}
	
	public static ServiceFactory getInstance() {
		return SigletonHolder.INSTANCE;
	}
	
	public UserService getUserService() {
		return userService;
	}
	
	public ProductForMenuService getProductForMenuService() {
		return productForMenuService;
	}
	
	public ProductService getProductService() {
		return productService;
	}
	
	public PriceService getPriceService() {
		return priceService;
	}
	
	public CartService getCartService() {
		return cartService;
	}
	
	public OrderService getOrderService() {
		return orderService;
	}
	
	public OrderForViewService getOrderForViewService() {
		return orderForViewService;
	}
	
	public ProductCategoryService getProductCategoryService() {
		return productCategoryService;
	}
	
	public CommentService getCommentService() {
		return commentService;
	}
	
	public EncryptUtil getEncryptUtil() {
		return encryptUtil;
	}
}
