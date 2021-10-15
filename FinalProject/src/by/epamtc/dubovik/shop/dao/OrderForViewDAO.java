package by.epamtc.dubovik.shop.dao;

import java.util.List;

import by.epamtc.dubovik.shop.entity.OrderForView;

public interface OrderForViewDAO {

	public List<OrderForView> findAll(int offset, int count) throws DAOException;
	public int countAll() throws DAOException;
	public List<OrderForView> findByUser(long userId) throws DAOException;
	public OrderForView findById(long orderId) throws DAOException;
	public List<OrderForView> findByOrderStatus(long orderStatusId) throws DAOException;
	
}
