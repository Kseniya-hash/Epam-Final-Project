package by.epamtc.dubovik.shop.service.impl;

import java.util.List;

import by.epamtc.dubovik.shop.dao.DAOException;
import by.epamtc.dubovik.shop.dao.DAOFactory;
import by.epamtc.dubovik.shop.dao.OrderForViewDAO;
import by.epamtc.dubovik.shop.entity.OrderForView;
import by.epamtc.dubovik.shop.service.OrderForViewService;
import by.epamtc.dubovik.shop.service.exception.ServiceException;

public class OrderForViewServiceImpl implements OrderForViewService {

	@Override
	public List<OrderForView> findOrders(long userId) 
			throws ServiceException {
		
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
	
	@Override
	public List<OrderForView> findOrders(int page, int count) 
			throws ServiceException {
		
		List<OrderForView> orders = null;
		DAOFactory factory = DAOFactory.getInstance();
		OrderForViewDAO orderForViewDAO = factory.getOrderForViewDAO();
		try {
			int offset = (page - 1) * count;
			orders = orderForViewDAO.findAll(offset, count);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return orders;
	}

	@Override
	public OrderForView findById(long orderId) 
			throws ServiceException {
		
		OrderForView order = null;
		DAOFactory factory = DAOFactory.getInstance();
		OrderForViewDAO orderForViewDAO = factory.getOrderForViewDAO();
		try {
			order = orderForViewDAO.findById(orderId);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return order;
	}

	@Override
	public int countAll() throws ServiceException {
		int count = 0;
		DAOFactory factory = DAOFactory.getInstance();
		OrderForViewDAO orderForViewDAO = factory.getOrderForViewDAO();
		try {
			count = orderForViewDAO.countAll();
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return count;
	}

}
