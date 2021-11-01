package by.epamtc.dubovik.shop.service.impl;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import by.epamtc.dubovik.shop.dao.DAOException;
import by.epamtc.dubovik.shop.dao.DAOFactory;
import by.epamtc.dubovik.shop.dao.OrderDAO;
import by.epamtc.dubovik.shop.entity.Cart;
import by.epamtc.dubovik.shop.entity.Order;
import by.epamtc.dubovik.shop.entity.Sale;
import by.epamtc.dubovik.shop.entity.Price;
import by.epamtc.dubovik.shop.service.OrderService;
import by.epamtc.dubovik.shop.service.PriceService;
import by.epamtc.dubovik.shop.service.ServiceFactory;
import by.epamtc.dubovik.shop.service.exception.InvalidException;
import by.epamtc.dubovik.shop.service.exception.ServiceException;
import by.epamtc.dubovik.shop.service.validation.CartValidation;
import by.epamtc.dubovik.shop.service.validation.ValidationFactory;

public class OrderServiceImpl implements OrderService {
	
	private final static int PROCESSED_ORDER = 1;
	private final static int PAID_ORDER = 2;
	private final static int DELIVERED_ORDER = 3;

	@Override
	public boolean makeOrder(long userId, Cart cart) 
			throws InvalidException, ServiceException {
		Order order = takeOrderFromCart(userId, cart);
		order.setDate(LocalDateTime.now());
		OrderDAO orderDAO = DAOFactory.getInstance().getOrderDAO();
		boolean isMade = false;
		try {
			isMade = orderDAO.create(order);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		
		return isMade;
	}
	
	private Order takeOrderFromCart(long userId, Cart cart) 
			throws InvalidException {
		
		CartValidation validator = 
				ValidationFactory.getInstance().getCartValidation();
		if(!validator.isValid(cart)) {
			throw new InvalidException("Invalid cart");
		}
		Order order = new Order();
		order.setUserId(userId);
		order.setOrderStatusId(PROCESSED_ORDER);
		List<Sale> sales = new LinkedList<Sale>();
		for (Map.Entry<Long, Integer> entry : cart.entrySet()) {
			Sale sale = new Sale();
			sale.setProductId(entry.getKey());
			sale.setQuantity(entry.getValue());
			sales.add(sale);
		}
		order.setSales(sales);
		return order;
	}
	
	@Override
	public Order findOrderById(long orderId) 
			throws ServiceException {
		OrderDAO orderDAO = DAOFactory.getInstance().getOrderDAO();
		Order order = null;
		try {
			order = orderDAO.findById(orderId);
		} catch (DAOException e) {
			throw new ServiceException();
		}
		return order;
	}
	
	@Override
	public int calculatePrice(long orderId) 
			throws ServiceException, InvalidException {
		
		Order order = findOrderById(orderId);
		int price = calculatePrice(order);
		return price;
	}
	
	private int calculatePrice(Order order)
			throws ServiceException, InvalidException {
		
		if(order == null) {
			throw new InvalidException("Order does not exist");
		}
		PriceService priceService = ServiceFactory
				.getInstance().getPriceService();
		
		int priceOfOrder = 0;
		LocalDateTime date = order.getDate();
		for(Sale sale : order.getSales()) {
			Price priceOfProduct = priceService
					.findPriceByProduct(sale.getProductId(), date);
			priceOfOrder += sale.getQuantity() * priceOfProduct.getSellingPrice();
		}
		return priceOfOrder;
	} 

	@Override
	public boolean payForOrder(long orderId, long card) 
			throws ServiceException, InvalidException {
		
		boolean isPaid = false;
		Order order = findOrderById(orderId);
		int price = calculatePrice(orderId);
		isPaid = pay(card, price);
		if(isPaid) {
			order.setOrderStatusId(PAID_ORDER);
			update(order);
		}
		return isPaid;
	}
	
	private boolean pay(long card, int price) {
		//TODO доделать заглушку
		return true;
	}
	
	@Override
	public boolean deliverOrder(long orderId) 
			throws ServiceException {
		
		boolean isDelivered = false;
		Order order = findOrderById(orderId);
		
		if(order != null && order.getOrderStatusId() == PAID_ORDER) {
			order.setOrderStatusId(DELIVERED_ORDER);
			isDelivered = update(order);
		}
		
		return isDelivered;
	}
	
	private boolean update(Order order) 
			throws ServiceException {
		
		OrderDAO orderDAO = 
				DAOFactory.getInstance().getOrderDAO();
		boolean isUpdated = false;
		
		try {
			isUpdated = orderDAO.update(order);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		
		return isUpdated;
	}
}
