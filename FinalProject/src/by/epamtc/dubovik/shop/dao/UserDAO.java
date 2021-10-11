package by.epamtc.dubovik.shop.dao;

import by.epamtc.dubovik.shop.dao.generic.GenericIntIdDAO;
import by.epamtc.dubovik.shop.entity.User;
import by.epamtc.dubovik.shop.entity.UserForLogin;
import by.epamtc.dubovik.shop.entity.UserLogged;

public interface UserDAO extends GenericIntIdDAO<User> {
	
	public boolean blacklistUser(int userId) throws DAOException;
	public User findUserInfoByLogin(String login) throws DAOException;
	public UserLogged authorize(UserForLogin user) throws DAOException;
	public byte[] takePassword(String login) throws DAOException;
}
