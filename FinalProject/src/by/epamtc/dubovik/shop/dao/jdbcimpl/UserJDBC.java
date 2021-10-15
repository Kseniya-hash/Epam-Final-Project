package by.epamtc.dubovik.shop.dao.jdbcimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.epamtc.dubovik.shop.connectionpool.ConnectionPool;
import by.epamtc.dubovik.shop.dao.DAOException;
import by.epamtc.dubovik.shop.dao.UserDAO;
import by.epamtc.dubovik.shop.dao.jdbcimpl.mapping.RoleMapping;
import by.epamtc.dubovik.shop.dao.jdbcimpl.mapping.UserMapping;
import by.epamtc.dubovik.shop.entity.User;
import by.epamtc.dubovik.shop.entity.UserForLogin;
import by.epamtc.dubovik.shop.entity.UserLogged;

public class UserJDBC implements UserDAO {
	
	private static final String SQL_SELECT_ALL = 
			"SELECT * FROM `dubovik_Shop`.`users` LIMIT ?,?";
	private static final String SQL_SELECT_BY_ID = 
			"SELECT * FROM `dubovik_Shop`.`users` WHERE `u_id`=?";
	private static final String SQL_CREATE = 
			"INSERT INTO `dubovik_Shop`.`users` "
			+ "(`u_phone`, `unq_u_login`, `u_password`, `u_name`, `u_e-mail`, `u_is_blacklisted`, `u_r_id`) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
	private static final String SQL_DELETE_BY_ID = 
			"DELETE FROM `dubovik_Shop`.`users` WHERE `u_id`=?";
	private static final String SQL_DELETE_BY_ENTITY = 
			"DELETE FROM `dubovik_Shop`.`users` "
			+ "WHERE `u_id` = ? AND `u_phone` = ? AND `unq_u_login` = ? AND `u_password` = ? "
			+ "AND `u_name` = ? AND `u_e-mail` = ? AND `u_is_blacklisted` = ? AND `u_r_id` = ?";
	private static final String SQL_UPDATE = 
			"UPDATE `dubovik_Shop`.`users` SET `u_phone` = ?, `unq_u_login` = ?, `u_password` = ?"
			+ ", `u_name` = ?, `u_e-mail` = ?, `u_is_blacklisted` = ?, `u_r_id` = ? "
			+ "WHERE `u_id` = ?";
	
	private static final String SQL_BLACKLIST = 
			"UPDATE `dubovik_Shop`.`users` SET `u_is_blacklisted` = 1 WHERE `u_id` = ?";
	private static final String SQL_SELECT_BY_LOGIN = 
			"SELECT * FROM `dubovik_Shop`.`users` WHERE `unq_u_login`=?";
	private static final String SQL_SELECT_LOGGED_BY_LOGIN = 
			"SELECT `u_id`, `unq_u_login`, `u_is_blacklisted`, `unq_r_name`"
			+ "FROM `dubovik_Shop`.`users` u INNER JOIN `dubovik_shop`.`roles` r "
			+ "ON u.u_r_id = r_id "
			+ "WHERE `unq_u_login`=? AND `u_password` = ?";
	private static final String SQL_SELECT_PASSWORD = 
			"SELECT `u_password` FROM `dubovik_Shop`.`users` WHERE `unq_u_login`=?";
	
	private User takeFromResultSet(ResultSet resultSet) throws SQLException {
		User user = null;
		if (!resultSet.isAfterLast()) {
			user = new User();
			user.setId(resultSet.getLong(UserMapping.ID));
			user.setPhone(resultSet.getString(UserMapping.PHONE));
			user.setLogin(resultSet.getString(UserMapping.LOGIN));
			user.setPassword(resultSet.getString(UserMapping.PASSWORD).getBytes());
			user.setName(resultSet.getString(UserMapping.NAME));
			user.setEMail(resultSet.getString(UserMapping.E_MAIL));
			user.setIsBlacklisted(resultSet.getBoolean(UserMapping.BLACKLIST));
			user.setRoleId(resultSet.getLong(UserMapping.ROLE_ID));
		}
		return user;
	}
	
	private UserLogged takeLoggedFromResultSet(ResultSet resultSet) throws SQLException {
		UserLogged user = null;
		if (!resultSet.isAfterLast()) {
			user = new UserLogged();
			user.setId(resultSet.getLong(UserMapping.ID));
			user.setLogin(resultSet.getString(UserMapping.LOGIN));
			user.setIsBlacklisted(resultSet.getBoolean(UserMapping.BLACKLIST));
			user.setRole(resultSet.getString(RoleMapping.NAME));
		}
		return user;
	}

	@Override
	public User findById(long id) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		User user = null;
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			cn = pool.takeConnection();
			st = cn.prepareStatement(SQL_SELECT_BY_ID);
			st.setLong(1, id);
			rs = st.executeQuery();
			if(rs.next()) {
				user = takeFromResultSet(rs);
			}
		} catch(SQLException e) {
			throw new DAOException(e); 
		} finally {
			pool.closeConnection(cn, st, rs);
		}
		return user;
	}

	@Override
	public boolean delete(long id) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		boolean flag = false;
		Connection cn = null;
		PreparedStatement st = null;
		
		try {
			cn = pool.takeConnection();
			st = cn.prepareStatement(SQL_DELETE_BY_ID);
			st.setLong(1, id);
			int result = st.executeUpdate();
			flag = result > 0;
		} catch(SQLException e) {
			flag = false;
		} finally {
			pool.closeConnection(cn, st);
		}
		return flag;
	}

	@Override
	public List<User> findAll(int offset, int count) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		List<User> users= new ArrayList<>();
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			cn = pool.takeConnection();
			st = cn.prepareStatement(SQL_SELECT_ALL);
			st.setInt(1, offset);
			st.setInt(2, count);
			rs = st.executeQuery();
			while(rs.next()) {
				User current = takeFromResultSet(rs);
				users.add(current);
			}
		} catch(SQLException e) {
			throw new DAOException(e); 
		} finally {
			pool.closeConnection(cn, st, rs);
		}
		return users;
	}
	
	@Override
	public boolean delete(User entity) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		boolean flag = false;
		Connection cn = null;
		PreparedStatement st = null;
		
		try {
			cn = pool.takeConnection();
			st = cn.prepareStatement(SQL_DELETE_BY_ENTITY);
			st.setLong(1, entity.getId());
			st.setString(2, entity.getPhone());
			st.setString(3, entity.getLogin());
			st.setString(4, new String(entity.getPassword()));
			st.setString(5, entity.getName());
			st.setString(6, entity.getEMail());
			st.setBoolean(7, entity.getIsBlacklisted());
			st.setLong(8, entity.getRoleId());
			int result = st.executeUpdate();
			flag = result > 0;
		} catch(SQLException e) {
			flag = false;
		} finally {
			pool.closeConnection(cn, st);
		}
		return flag;
	}
	
	@Override
	public boolean create(User entity) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		boolean flag = false;
		Connection cn = null;
		PreparedStatement st = null;
		
		try {
			cn = pool.takeConnection();
			st = cn.prepareStatement(SQL_CREATE);
			st.setString(1, entity.getPhone());
			st.setString(2, entity.getLogin());
			st.setString(3, new String(entity.getPassword()));
			st.setString(4, entity.getName());
			st.setString(5, entity.getEMail());
			st.setBoolean(6, entity.getIsBlacklisted());
			st.setLong(7, entity.getRoleId());
			int result = st.executeUpdate();
			flag = result > 0;
		} catch(SQLException e) {
			flag = false;
		} finally {
			pool.closeConnection(cn, st);
		}
		return flag;
	}
	
	@Override
	public boolean update(User entity) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection cn = null;
		PreparedStatement st = null;
		boolean flag = false;
		
		try {
			cn = pool.takeConnection();
			st = cn.prepareStatement(SQL_UPDATE);
			st.setString(1, entity.getPhone());
			st.setString(2, entity.getLogin());
			st.setString(3, new String(entity.getPassword()));
			st.setString(4, entity.getName());
			st.setString(5, entity.getEMail());
			st.setBoolean(6, entity.getIsBlacklisted());
			st.setLong(7, entity.getRoleId());
			st.setLong(8, entity.getId());
			int result = st.executeUpdate();
			flag = result > 0;
		} catch(SQLException e) {
			flag = false;
		} finally {
			pool.closeConnection(cn, st);
		}
		return flag;
	}

	@Override
	public boolean blacklistUser(long userId) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection cn = null;
		PreparedStatement st = null;
		boolean flag = false;
		
		try {
			cn = pool.takeConnection();
			st = cn.prepareStatement(SQL_BLACKLIST);
			st.setLong(1, userId);
			int result = st.executeUpdate();
			flag = result > 0;
		} catch(SQLException e) {
			throw new DAOException(e); 
		} finally {
			pool.closeConnection(cn, st);
		}
		return flag;
	}

	@Override
	public User findUserInfoByLogin(String login) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		User user = null;
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			cn = pool.takeConnection();
			st = cn.prepareStatement(SQL_SELECT_BY_LOGIN);
			st.setString(1, login);
			rs = st.executeQuery();
			if(rs.next()) {
				user = takeFromResultSet(rs);
			}
		} catch(SQLException e) {
			throw new DAOException(e); 
		} finally {
			pool.closeConnection(cn, st, rs);
		}
		return user;
	}
	
	@Override
	public UserLogged authorize(UserForLogin user) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		UserLogged userLogged = null;
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			cn = pool.takeConnection();
			st = cn.prepareStatement(SQL_SELECT_LOGGED_BY_LOGIN);
			st.setString(1, user.getLogin());
			st.setString(2, new String(user.getPassword()));
			rs = st.executeQuery();
			if(rs.next()) {
				userLogged = takeLoggedFromResultSet(rs);
			}
		} catch(SQLException e) {
			throw new DAOException(e); 
		} finally {
			pool.closeConnection(cn, st, rs);
		}
		return userLogged;
	}

	@Override
	public byte[] takePassword(String login) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		byte[] password = null;
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			cn = pool.takeConnection();
			st = cn.prepareStatement(SQL_SELECT_PASSWORD);
			st.setString(1, login);
			rs = st.executeQuery();
			if(rs.next()) {
				password = rs.getString(UserMapping.PASSWORD).getBytes();
			}
		} catch(SQLException e) {
			throw new DAOException(e); 
		} finally {
			pool.closeConnection(cn, st, rs);
		}
		return password;
	}

}
