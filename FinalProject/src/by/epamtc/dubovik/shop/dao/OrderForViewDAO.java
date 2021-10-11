package by.epamtc.dubovik.shop.dao;

import java.sql.Timestamp;
import java.util.List;

import by.epamtc.dubovik.shop.entity.OrderForView;

public interface OrderForViewDAO {

	public List<OrderForView> findAll() throws DAOException;
	public List<OrderForView> findByUser(int userId) throws DAOException;
	//public List<OrderForView> findByDate(Timestamp date) throws DAOException;
	public List<OrderForView> findByOrderStatus(int orderStatusId) throws DAOException;
	
}
