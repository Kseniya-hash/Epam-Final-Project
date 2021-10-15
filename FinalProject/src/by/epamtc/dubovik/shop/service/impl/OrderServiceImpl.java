package by.epamtc.dubovik.shop.service.impl;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import by.epamtc.dubovik.shop.dao.DAOException;
import by.epamtc.dubovik.shop.dao.OrderDAO;
import by.epamtc.dubovik.shop.dao.factory.DAOFactory;
import by.epamtc.dubovik.shop.entity.Cart;
import by.epamtc.dubovik.shop.entity.Order;
import by.epamtc.dubovik.shop.entity.OrderToProduct;
import by.epamtc.dubovik.shop.entity.Price;
import by.epamtc.dubovik.shop.service.OrderService;
import by.epamtc.dubovik.shop.service.PayService;
import by.epamtc.dubovik.shop.service.PriceService;
import by.epamtc.dubovik.shop.service.exception.InvalidException;
import by.epamtc.dubovik.shop.service.exception.ServiceException;
import by.epamtc.dubovik.shop.service.factory.ServiceFactory;
import by.epamtc.dubovik.shop.service.validation.CartValidation;
import by.epamtc.dubovik.shop.service.validation.factory.ValidationFactory;

public class OrderServiceImpl implements OrderService {
	
	private final static int PROCESSED_ORDER = 1;
	private final static int PAID_ORDER = 2;
	private final static int DELIVERED_ORDER = 3;

	@Override
	public boolean makeOrder(long userId, Cart cart) throws ServiceException, InvalidException {
		Order order = takeOrderFromCart(userId, cart);
		OrderDAO orderDAO = DAOFactory.getInstance().getOrderDAO();
		boolean isMade = false;
		try {
			isMade = orderDAO.create(order);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return isMade;
	}
	
	private Order takeOrderFromCart(long userId, Cart cart) throws InvalidException {
		CartValidation validator = ValidationFactory.getInstance().getCartValidation();
		if(!validator.isValid(cart)) {
			throw new InvalidException("Invalid cart");
		}
		Order order = new Order();
		order.setUserId(userId);
		order.setOrderStatusId(PROCESSED_ORDER);
		List<OrderToProduct> sales = new LinkedList<OrderToProduct>();
		for (Map.Entry<Long, Integer> entry : cart.entrySet()) {
			OrderToProduct sale = new OrderToProduct();
			sale.setProductId(entry.getKey());
			sale.setQuantity(entry.getValue());
			sales.add(sale);
		}
		order.setSales(sales);
		return order;
	}
	
	public Order takeOrderById(long orderId) throws ServiceException {
		OrderDAO orderDAO = DAOFactory.getInstance().getOrderDAO();
		Order order = null;
		try {
			order = orderDAO.findById(orderId);
		} catch (DAOException e) {
			throw new ServiceException();
		}
		return order;
	}
	
	public int calculatePrice(long orderId) throws ServiceException, InvalidException {
		Order order = takeOrderById(orderId);
		int price = calculatePrice(order);
		return price;
	}
	
	public int calculatePrice(Order order) throws ServiceException, InvalidException {
		if(order == null) {
			throw new InvalidException("Order does not exist");
		}
		PriceService priceService = ServiceFactory.getInstance().getPriceService();
		int priceOfOrder = 0;
		LocalDateTime date = order.getDate();
		for(OrderToProduct sale : order.getSales()) {
			Price priceOfProduct = priceService.takePriceByProduct(sale.getProductId(), date);
			priceOfOrder += sale.getQuantity() * priceOfProduct.getSellingPrice();
		}
		return priceOfOrder;
	}

	@Override
	public boolean payForOrder(long orderId, long card) throws ServiceException, InvalidException {
		PayService payService = ServiceFactory.getInstance().getPayService();
		
		boolean isPaid = false;
		Order order = takeOrderById(orderId);
		int price = calculatePrice(orderId);
		isPaid = payService.pay(card, price);
		if(isPaid) {
			order.setOrderStatusId(PAID_ORDER);
			update(order);
		}
		return isPaid;
	}
	
	private boolean update(Order order) throws ServiceException {
		OrderDAO orderDAO = DAOFactory.getInstance().getOrderDAO();
		boolean isUpdated = false;
		try {
			isUpdated = orderDAO.update(order);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return isUpdated;
	}
	
	@Override
	public boolean deliverOrder(long orderId) throws ServiceException, InvalidException {
		boolean isDelivered = false;
		Order order = takeOrderById(orderId);
		
		if(order.getOrderStatusId() == PAID_ORDER) {
			order.setOrderStatusId(DELIVERED_ORDER);
			isDelivered = update(order);
		}
		
		return isDelivered;
	}
}
