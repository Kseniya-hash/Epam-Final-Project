package by.epamtc.dubovik.shop.service.factory;

import by.epamtc.dubovik.shop.service.*;
import by.epamtc.dubovik.shop.service.impl.*;
import by.epamtc.dubovik.shop.service.util.EncryptUtil;
import by.epamtc.dubovik.shop.service.util.impl.EncryptUtilImpl;

public class ServiceFactory {
	
	private final LoginService loginService;
	private final ProductForMenuService productForMenuService;
	private final RegisterService registerService;
	private final ProductService productService;
	private final PriceService priceService;
	private final CartService cartService;
	private final OrderService orderService;
	private final OrderForViewService orderForViewService;
	
	private final EncryptUtil encryptUtil;
	
	private ServiceFactory() {
		loginService = new LoginServiceImpl();
		productForMenuService = new ProductForMenuServiceImpl();
		registerService = new RegisterServiceImpl();
		productService = new ProductServiceImpl();
		priceService = new PriceServiceImpl();
		cartService = new CartServiceImpl();
		orderService = new OrderServiceImpl();
		orderForViewService = new OrderForViewServiceImpl();
		
		encryptUtil = new EncryptUtilImpl();
	}
	
	private static class SigletonHolder {
		private final static ServiceFactory INSTANCE = new ServiceFactory();
	}
	
	public static ServiceFactory getInstance() {
		return SigletonHolder.INSTANCE;
	}
	
	public LoginService getLoginService() {
		return loginService;
	}

	public ProductForMenuService getProductForMenuService() {
		return productForMenuService;
	}
	
	public RegisterService getRegisterService() {
		return registerService;
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
	
	public EncryptUtil getEncryptUtil() {
		return encryptUtil;
	}
}
