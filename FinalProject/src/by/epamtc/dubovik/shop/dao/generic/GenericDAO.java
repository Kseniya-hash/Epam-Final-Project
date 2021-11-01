package by.epamtc.dubovik.shop.dao.generic;

import java.util.List;
import by.epamtc.dubovik.shop.dao.DAOException;

public interface GenericDAO <T> {
	
	/**
	 * Find a list of entities. If offset is too big returns an empty list.
	 * @param offset - count of entities to skip at the beginning
	 * @param count - count of entities to return
	 * @return list of entities
	 * @throws DAOException
	 */
	public List<T> findAll(int offset, int count) throws DAOException;
	
	/**
	 * Find entity by its id.
	 * If entity with such id does not exist return null
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	public T findById(long id) throws DAOException;
	
	/**
	 * Create entity. If unable to create entity return false
	 * @param entity
	 * @return
	 * @throws DAOException
	 */
	public boolean create(T entity) throws DAOException;
	
	/**
	 * Update entity. If unable to update entity return false
	 * @param entity
	 * @return
	 * @throws DAOException
	 */
	public boolean update(T entity) throws DAOException;
	
}
