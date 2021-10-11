package by.epamtc.dubovik.shop.dao;

import java.sql.Timestamp;

import by.epamtc.dubovik.shop.entity.Price;

public interface PriceDAO {

	public boolean create(Price entity) throws DAOException;
	public Price findByProduct(int productId, Timestamp date) throws DAOException;
	public Price findByProduct(int productId) throws DAOException;
	public Price findById(int id) throws DAOException;
	
}
