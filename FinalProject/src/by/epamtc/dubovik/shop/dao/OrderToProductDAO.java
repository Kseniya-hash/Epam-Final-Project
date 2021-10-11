package by.epamtc.dubovik.shop.dao;

import java.util.List;

import by.epamtc.dubovik.shop.dao.generic.GenericDAO;
import by.epamtc.dubovik.shop.entity.OrderToProduct;

public interface OrderToProductDAO extends GenericDAO<OrderToProduct> {
	
	public List<OrderToProduct> findByOrder(int orderId, int offset, int count) 
			throws DAOException;
	public List<OrderToProduct> findByProduct(int productId, int offset, int count) 
			throws DAOException;
	public boolean delete(int orderId, int productId) throws DAOException;

}
