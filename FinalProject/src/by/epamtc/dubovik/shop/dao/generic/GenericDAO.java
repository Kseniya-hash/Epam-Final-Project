package by.epamtc.dubovik.shop.dao.generic;

import java.util.List;
import by.epamtc.dubovik.shop.dao.DAOException;

public interface GenericDAO <T> {
	
	public List<T> findAll(int offset, int count) throws DAOException;
	public T findById(long id) throws DAOException;
	public boolean create(T entity) throws DAOException;
	public boolean update(T entity) throws DAOException;
	
}
