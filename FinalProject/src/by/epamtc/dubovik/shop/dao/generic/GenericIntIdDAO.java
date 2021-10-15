package by.epamtc.dubovik.shop.dao.generic;

import by.epamtc.dubovik.shop.dao.DAOException;

public interface GenericIntIdDAO<T> extends GenericDAO<T> {
	
	public T findById(long id) throws DAOException;
	public boolean delete(long id) throws DAOException;

}
