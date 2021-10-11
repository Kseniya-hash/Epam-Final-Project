package by.epamtc.dubovik.shop.dao.generic;

import by.epamtc.dubovik.shop.dao.DAOException;

public interface GenericIntIdDAO<T> extends GenericDAO<T> {
	
	public T findById(int id) throws DAOException;
	public boolean delete(int id) throws DAOException;

}
