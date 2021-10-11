package by.epamtc.dubovik.shop.service;

import java.util.List;

import by.epamtc.dubovik.shop.entity.OrderForView;
import by.epamtc.dubovik.shop.service.exception.ServiceException;

public interface OrderForViewService {
	
	List<OrderForView> takeUserOrders(int userId) throws ServiceException;

}
