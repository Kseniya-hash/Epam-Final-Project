package by.epamtc.dubovik.shop.dao;

import java.time.LocalDateTime;

import by.epamtc.dubovik.shop.entity.Price;

public interface PriceDAO {

	public boolean create(Price entity) throws DAOException;
	public Price findByProduct(long productId, LocalDateTime date) throws DAOException;
	public Price findByProduct(long productId) throws DAOException;
	public Price findById(long id) throws DAOException;
	
}
