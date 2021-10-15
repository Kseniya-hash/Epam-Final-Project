package by.epamtc.dubovik.shop.controller;

public final class Page {
	
	public static final String INDEX = "/index.jsp";
	public static final String CONTROLLER = "/Controller";
	public static final String WELLCOME_CONTROLLER = "Controller?command=wellcome";
	public static final String LOGIN = "/WEB-INF/jsp/login.jsp";
	public static final String LOGOUT = "/WEB-INF/jsp/logout.jsp";
	public static final String REGISTRATION = "/WEB-INF/jsp/registration.jsp";
	
	public static final String LIST = "/WEB-INF/jsp/list.jsp";
	public static final String CART = "/WEB-INF/jsp/cart.jsp";
	public static final String PRODUCT = "/WEB-INF/jsp/product.jsp";
	public static final String REDACT_PRODUCT = "/WEB-INF/jsp/redact_product.jsp";
	public static final String USER_ORDERS = "/WEB-INF/jsp/user_orders.jsp";
	public static final String CUSTOMER_ORDERS ="/WEB-INF/jsp/customer_orders.jsp";
	public static final String PAY_ORDER = "/WEB-INF/jsp/pay_for_order.jsp";
	
	public static final String ERROR404 = "/error/error404.jsp";
	public static final String ERROR500 = "/error/error500.jsp";
	
	private Page() {};
}
