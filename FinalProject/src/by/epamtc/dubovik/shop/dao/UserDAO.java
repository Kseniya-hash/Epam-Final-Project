package by.epamtc.dubovik.shop.dao;

import by.epamtc.dubovik.shop.dao.generic.GenericDAO;
import by.epamtc.dubovik.shop.entity.User;
import by.epamtc.dubovik.shop.entity.UserLogged;

public interface UserDAO extends GenericDAO<User> {
	
	/**
	 * Find user info by their login.
	 * If user with such login does not exist return null
	 * @param login
	 * @return
	 * @throws DAOException
	 */
	public User findUserInfoByLogin(String login) throws DAOException;
	
	/**
	 * Find logged user info by their login.
	 * If user with such login does not exist return null
	 * @param login
	 * @return
	 * @throws DAOException
	 */
	public UserLogged findLoggedUser(String login) throws DAOException;
	
	/**
	 * Find user's password by their login.
	 * If user with such login does not exist return null
	 * @param login
	 * @return
	 * @throws DAOException
	 */
	public byte[] findPassword(String login) throws DAOException;
}
