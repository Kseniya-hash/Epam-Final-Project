package by.epamtc.dubovik.shop.dao;

import by.epamtc.dubovik.shop.dao.jdbcimpl.*;

public class DAOFactory {
	
	private final CommentDAO commentDAO;
	private final OrderDAO orderDAO;
	private final OrderStatusDAO orderStatusDAO;
	private final ProductCategoryDAO productCategoryDAO;
	private final ProductDAO productDAO;
	private final RoleDAO roleDAO;
	private final UserDAO userDAO;
	private final ProductForMenuDAO productForMenuDAO;
	private final ProductForCartDAO productForCartDAO;
	private final PriceDAO priceDAO;
	private final OrderForViewDAO orderForViewDAO;
	
	private DAOFactory() {
		commentDAO = new CommentJDBC();
		orderDAO = new OrderJDBC();
		orderStatusDAO = new OrderStatusJDBC();
		productCategoryDAO = new ProductCategoryJDBC();
		productDAO = new ProductJDBC();
		roleDAO = new RoleJDBC();
		userDAO = new UserJDBC();
		productForMenuDAO = new ProductForMenuJDBC();
		productForCartDAO = new ProductForCartJDBC();
		priceDAO = new PriceJDBC();
		orderForViewDAO = new OrderForViewJDBC();
	};
	
	private static class SigletonHolder {
		private final static DAOFactory INSTANCE = new DAOFactory();
	}
	
	public static DAOFactory getInstance() {
		return SigletonHolder.INSTANCE;
	}

	public CommentDAO getCommentDAO() {
		return commentDAO;
	}
	
	public OrderDAO getOrderDAO(){
		return orderDAO;
	}
	
	public OrderStatusDAO getOrderStatusDAO() {
		return orderStatusDAO;
	}
	
	public ProductCategoryDAO getProductCategoryDAO() {
		return productCategoryDAO;
	}
	
	public ProductDAO getProductDAO() {
		return productDAO;
	}
	
	public RoleDAO getRoleDAO() {
		return roleDAO;
	}
	
	public UserDAO getUserDAO() {
		return userDAO;
	}
	
	public ProductForMenuDAO getProductForMenuDAO() {
		return productForMenuDAO;
	}
	
	public ProductForCartDAO getProductForCartDAO() {
		return productForCartDAO;
	}
	
	public PriceDAO getPriceDAO() {
		return priceDAO;
	}
	
	public OrderForViewDAO getOrderForViewDAO() {
		return orderForViewDAO;
	}
}
