package by.epamtc.dubovik.shop.service;

import java.util.List;

import by.epamtc.dubovik.shop.entity.OrderForView;
import by.epamtc.dubovik.shop.service.exception.ServiceException;

public interface OrderForViewService {
	
	List<OrderForView> takeOrders(long userId) throws ServiceException;
	List<OrderForView> takeOrders(int page, int count) throws ServiceException;
	public int countAll() throws ServiceException;
	OrderForView takeById(long orderId) throws ServiceException;

}
