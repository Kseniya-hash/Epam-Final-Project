package by.epamtc.dubovik.shop.service.factory;

import by.epamtc.dubovik.shop.service.*;
import by.epamtc.dubovik.shop.service.impl.*;
import by.epamtc.dubovik.shop.service.util.*;
import by.epamtc.dubovik.shop.service.util.impl.*;

public class ServiceFactory {
	
	private final UserService userService;
	//private final LoginService loginService;
	private final ProductForMenuService productForMenuService;
	//private final RegisterService registerService;
	private final ProductService productService;
	private final PriceService priceService;
	private final CartService cartService;
	private final OrderService orderService;
	private final OrderForViewService orderForViewService;
	private final ProductCategoryService productCategoryService;
	private final CommentService commentService;
	
	private final PayService payService;
	
	private final EncryptUtil encryptUtil;
	private final RequestUtil requestUtil;
	
	
	
	private final SecurityConfig securityConfig;
	private final SecurityUtil securityUtil;
	
	private ServiceFactory() {
		userService = new UserServiceImpl();
		//loginService = new LoginServiceImpl();
		productForMenuService = new ProductForMenuServiceImpl();
		//registerService = new RegisterServiceImpl();
		productService = new ProductServiceImpl();
		priceService = new PriceServiceImpl();
		cartService = new CartServiceImpl();
		orderService = new OrderServiceImpl();
		orderForViewService = new OrderForViewServiceImpl();
		productCategoryService = new ProductCategoryServiceImpl();
		commentService = new CommentServiceImpl();
		
		payService = new PayServiceImpl();
		
		encryptUtil = new EncryptUtilImpl();
		requestUtil = new RequestUtilImpl();
		
		securityConfig = new SecurityConfigImpl();
		securityUtil = new SecurityUtilImpl();
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
	/*
	public LoginService getLoginService() {
		return loginService;
	}
*/
	public ProductForMenuService getProductForMenuService() {
		return productForMenuService;
	}
	/*
	public RegisterService getRegisterService() {
		return registerService;
	}
	*/
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
	
	public PayService getPayService() {
		return payService;
	}
	
	public EncryptUtil getEncryptUtil() {
		return encryptUtil;
	}
	
	public RequestUtil getRequestUtil() {
		return requestUtil;
	}
	
	public SecurityConfig getSecurityConfig() {
		return securityConfig;
	}
	
	public SecurityUtil getSecurityUtil() {
		return securityUtil;
	}
}
