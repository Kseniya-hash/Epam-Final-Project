package by.epamtc.dubovik.shop.service.impl;

import java.util.List;

import by.epamtc.dubovik.shop.dao.DAOException;
import by.epamtc.dubovik.shop.dao.OrderForViewDAO;
import by.epamtc.dubovik.shop.dao.factory.DAOFactory;
import by.epamtc.dubovik.shop.entity.OrderForView;
import by.epamtc.dubovik.shop.service.OrderForViewService;
import by.epamtc.dubovik.shop.service.exception.ServiceException;

public class OrderForViewServiceImpl implements OrderForViewService {

	@Override
	public List<OrderForView> takeUserOrders(int userId) throws ServiceException {
		List<OrderForView> orders = null;
		DAOFactory factory = DAOFactory.getInstance();
		OrderForViewDAO orderForViewDAO = factory.getOrderForViewDAO();
		try {
			orders = orderForViewDAO.findByUser(userId);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return orders;
	}

}
