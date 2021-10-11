package by.epamtc.dubovik.shop.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import by.epamtc.dubovik.shop.dao.DAOException;
import by.epamtc.dubovik.shop.dao.OrderDAO;
import by.epamtc.dubovik.shop.dao.factory.DAOFactory;
import by.epamtc.dubovik.shop.entity.Cart;
import by.epamtc.dubovik.shop.entity.Order;
import by.epamtc.dubovik.shop.entity.OrderToProduct;
import by.epamtc.dubovik.shop.service.OrderService;
import by.epamtc.dubovik.shop.service.exception.InvalidException;
import by.epamtc.dubovik.shop.service.exception.ServiceException;
import by.epamtc.dubovik.shop.service.validation.CartValidation;
import by.epamtc.dubovik.shop.service.validation.factory.ValidationFactory;

public class OrderServiceImpl implements OrderService {
	
	private final static int PROCESSED_ORDER = 1;

	@Override
	public boolean makeOrder(int userId, Cart cart) throws ServiceException, InvalidException {
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
	
	private Order takeOrderFromCart(int userId, Cart cart) throws InvalidException {
		CartValidation validator = ValidationFactory.getInstance().getCartValidation();
		if(!validator.isValid(cart)) {
			throw new InvalidException("Invalid cart");
		}
		Order order = new Order();
		order.setUserId(userId);
		order.setOrderStatusId(PROCESSED_ORDER);
		List<OrderToProduct> sales = new LinkedList<OrderToProduct>();
		for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
			OrderToProduct sale = new OrderToProduct();
			sale.setProductId(entry.getKey());
			sale.setQuantity(entry.getValue());
			sales.add(sale);
		}
		order.setSales(sales);
		return order;
	}

}
