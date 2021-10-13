package by.epamtc.dubovik.shop.servlet.command.factory;

import java.util.HashMap;
import java.util.Map;

import by.epamtc.dubovik.shop.servlet.command.*;

public class CommandMapping {
	
	public static final String LOGIN = "login";
	public static final String TO_LOGIN_PAGE = "to_login_page";
	public static final String LOGOUT = "logout";
	public static final String REGISTRATION = "registration";
	public static final String TO_REGISTRATION_PAGE = "to_registration_page";
	public static final String WELLCOME = "wellcome";
	public static final String PRODUCT_SORT = "product_sort";
	public static final String SHOW_PRODUCT = "show_product";
	public static final String REDACT_PRODUCT = "redact_product";
	public static final String CART_INCRIMENT = "cart_incriment";
	public static final String CART_DECRIMENT = "cart_decriment";
	public static final String CART_REMOVE = "cart_remove";
	public static final String TO_REDACT_PRODUCT_PAGE = "to_redact_product";
	public static final String SHOW_CART = "show_cart";
	public static final String CHANGE_LOCALE = "change_locale";
	public static final String MAKE_ORDER = "make_order";
	public static final String SHOW_USER_ORDERS = "user_orders";
	public static final String SHOW_BUYER_ORDERS = "buyer_orders";
	public static final String ADD_CATEGORY = "add_category";
	public static final String TO_CREATE_PRODUCT_PAGE = "to_create_product";
	public static final String CREATE_PRODUCT = "create_product";
	public static final String TO_PAY_ORDER = "to_pay_fo_order";
	public static final String PAY_FOR_ORDER = "pay_fo_order";
	public static final String DELIVER_ORDER = "deliver_order";
	public static final String BLACKLIST_USER = "blacklist_user";
	public static final String CREATE_COMMENT = "create_comment";
	
	private Map <String, ActionCommand> map = new HashMap<>();
	
	private CommandMapping() {
		map.put(LOGIN, new LoginCommand());
		map.put(TO_LOGIN_PAGE, new ToLoginPageCommand());
		map.put(LOGOUT, new LogoutCommand());
		map.put(REGISTRATION, new RegistrationCommand());
		map.put(TO_REGISTRATION_PAGE, new ToRegistrationPageCommand());
		map.put(WELLCOME, new WellcomeCommand());
		map.put(PRODUCT_SORT, new ProductSortCommand());
		map.put(SHOW_PRODUCT, new ShowProductCommand());
		map.put(REDACT_PRODUCT, new RedactProductCommand());
		map.put(CART_INCRIMENT, new CartIncrimentCommand());
		map.put(CART_DECRIMENT, new CartDecrimentCommand());
		map.put(CART_REMOVE, new CartRemoveCommand());
		map.put(TO_REDACT_PRODUCT_PAGE, new ToRedactProductCommand());
		map.put(SHOW_CART, new ShowCartCommand());
		map.put(CHANGE_LOCALE, new ChangeLocaleCommand());
		map.put(MAKE_ORDER, new MakeOrderCommand());
		map.put(SHOW_USER_ORDERS, new ShowUserOrdersCommand());
		map.put(SHOW_BUYER_ORDERS, new ShowBuyerOrdersCommand());
		map.put(ADD_CATEGORY, new CreateProductCategoryCommand());
		map.put(TO_CREATE_PRODUCT_PAGE, new ToCreateProductCommand());
		map.put(CREATE_PRODUCT, new CreateProductCommand());
		map.put(TO_PAY_ORDER, new ToPayOrderCommand());
		map.put(PAY_FOR_ORDER, new PayForOrderCommand());
		map.put(DELIVER_ORDER, new DeliverOrderCommand());
		map.put(BLACKLIST_USER, new BlacklistUserCommand());
		map.put(CREATE_COMMENT, new CreateCommentCommand());
	}
	
	private static class SigletonHolder {
		private final static CommandMapping INSTANCE = new CommandMapping();
	}
	
	public static CommandMapping getInstance() {
		return SigletonHolder.INSTANCE;
	}
	
	public boolean containsKey(String key) {
		return map.containsKey(key);
	}
	
	public  ActionCommand get(String key) {
		return map.get(key);
	}
}
