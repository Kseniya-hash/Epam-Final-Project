package by.epamtc.dubovik.shop.dao;

import java.util.List;

import by.epamtc.dubovik.shop.entity.OrderForView;

public interface OrderForViewDAO {

	public List<OrderForView> findAll() throws DAOException;
	public List<OrderForView> findByUser(int userId) throws DAOException;
	public OrderForView findById(int orderId) throws DAOException;
	public List<OrderForView> findByOrderStatus(int orderStatusId) throws DAOException;
	
}
