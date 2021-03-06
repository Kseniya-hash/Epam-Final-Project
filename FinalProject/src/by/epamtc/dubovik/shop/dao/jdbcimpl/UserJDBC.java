package by.epamtc.dubovik.shop.dao.jdbcimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.epamtc.dubovik.shop.dao.DAOException;
import by.epamtc.dubovik.shop.dao.UserDAO;
import by.epamtc.dubovik.shop.dao.connectionpool.ConnectionPool;
import by.epamtc.dubovik.shop.dao.jdbcimpl.mapping.RoleMapping;
import by.epamtc.dubovik.shop.dao.jdbcimpl.mapping.UserMapping;
import by.epamtc.dubovik.shop.entity.User;
import by.epamtc.dubovik.shop.entity.UserLogged;

public class UserJDBC implements UserDAO {
	
	private static final String SQL_SELECT_ALL = 
			"SELECT * FROM users LIMIT ?,?";
	private static final String SQL_SELECT_BY_ID = 
			"SELECT * FROM users WHERE u_id=?";
	private static final String SQL_CREATE = 
			"INSERT INTO users "
			+ "(u_phone, unq_u_login, u_password, u_name, `u_e-mail`, u_is_blacklisted, u_r_id) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
	private static final String SQL_UPDATE = 
			"UPDATE users SET u_phone = ?, unq_u_login = ?, u_password = ?"
			+ ", u_name = ?, `u_e-mail` = ?, u_is_blacklisted = ?, u_r_id = ? "
			+ "WHERE u_id = ?";
	
	private static final String SQL_SELECT_BY_LOGIN = 
			"SELECT * FROM users WHERE unq_u_login=?";
	private static final String SQL_SELECT_LOGGED_BY_LOGIN = 
			"SELECT u_id, unq_u_login, u_is_blacklisted, unq_r_name "
			+ "FROM users u INNER JOIN roles r "
			+ "ON u.u_r_id = r_id "
			+ "WHERE unq_u_login=?";
	private static final String SQL_SELECT_PASSWORD = 
			"SELECT u_password FROM users WHERE unq_u_login=?";
	
	private static final ConnectionPool POOL = ConnectionPool.getInstance();

	@Override
	public User findById(long id) throws DAOException {
		
		User user = null;
		try (Connection cn = POOL.takeConnection(); 
				PreparedStatement st = cn.prepareStatement(SQL_SELECT_BY_ID)){
			st.setLong(1, id);
			try(ResultSet rs = st.executeQuery()){
				if(rs.next()) {
					user = takeFromResultSet(rs);
				}
			}
		} catch(SQLException e) {
			throw new DAOException(e); 
		}
		return user;
	}

	@Override
	public List<User> findAll(int offset, int count) 
			throws DAOException {
		
		
		List<User> users= new ArrayList<>();
		
		try (Connection cn = POOL.takeConnection(); 
				PreparedStatement st = cn.prepareStatement(SQL_SELECT_ALL)){
	
			st.setInt(1, offset);
			st.setInt(2, count);
			try(ResultSet rs = st.executeQuery()){
				while(rs.next()) {
					User current = takeFromResultSet(rs);
					users.add(current);
				}
			}
		} catch(SQLException e) {
			throw new DAOException(e); 
		}
		return users;
	}
	
	@Override
	public boolean create(User entity) throws DAOException {
		
		boolean flag = false;
		
		try (Connection cn = POOL.takeConnection(); 
				PreparedStatement st = cn.prepareStatement(SQL_CREATE)){
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
			throw new DAOException(e);
		}
		return flag;
	}
	
	@Override
	public boolean update(User entity) throws DAOException {
		
		boolean flag = false;
		
		try (Connection cn = POOL.takeConnection(); 
				PreparedStatement st = cn.prepareStatement(SQL_UPDATE)){
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
			throw new DAOException(e);
		}
		return flag;
	}
	
	@Override
	public User findUserInfoByLogin(String login) 
			throws DAOException {
		
		User user = null;
		
		try (Connection cn = POOL.takeConnection(); 
				PreparedStatement st = cn.prepareStatement(SQL_SELECT_BY_LOGIN)){
			st.setString(1, login);
			try(ResultSet rs = st.executeQuery()){
				if(rs.next()) {
					user = takeFromResultSet(rs);
				}
			}
		} catch(SQLException e) {
			throw new DAOException(e); 
		}
		return user;
	}
	
	@Override
	public UserLogged findLoggedUser(String login) throws DAOException {
		
		UserLogged userLogged = null;
		
		try (Connection cn = POOL.takeConnection(); 
				PreparedStatement st = cn
						.prepareStatement(SQL_SELECT_LOGGED_BY_LOGIN)){
			st.setString(1, login);
			try(ResultSet rs = st.executeQuery()){
				if(rs.next()) {
					userLogged = takeLoggedFromResultSet(rs);
				}
			}
		} catch(SQLException e) {
			throw new DAOException(e); 
		}
		return userLogged;
	}

	@Override
	public byte[] findPassword(String login) throws DAOException {
		
		byte[] password = null;
		
		try (Connection cn = POOL.takeConnection(); 
				PreparedStatement st = cn.prepareStatement(SQL_SELECT_PASSWORD)){
			st.setString(1, login);
			try(ResultSet rs = st.executeQuery()){
				if(rs.next()) {
					password = rs.getString(UserMapping.PASSWORD).getBytes();
				}
			}
		} catch(SQLException e) {
			throw new DAOException(e); 
		}
		return password;
	}
	
	private User takeFromResultSet(ResultSet resultSet)
			throws SQLException {
		
		User user = null;
		if (!resultSet.isAfterLast()) {
			user = new User();
			user.setId(resultSet
					.getLong(UserMapping.ID));
			user.setPhone(resultSet
					.getString(UserMapping.PHONE));
			user.setLogin(resultSet
					.getString(UserMapping.LOGIN));
			user.setPassword(resultSet
					.getString(UserMapping.PASSWORD).getBytes());
			user.setName(resultSet
					.getString(UserMapping.NAME));
			user.setEMail(resultSet
					.getString(UserMapping.E_MAIL));
			user.setIsBlacklisted(resultSet
					.getBoolean(UserMapping.BLACKLIST));
			user.setRoleId(resultSet
					.getLong(UserMapping.ROLE_ID));
		}
		return user;
	}
	
	private UserLogged takeLoggedFromResultSet(ResultSet resultSet)
			throws SQLException {
		
		UserLogged user = null;
		if (!resultSet.isAfterLast()) {
			user = new UserLogged();
			user.setId(resultSet
					.getLong(UserMapping.ID));
			user.setLogin(resultSet
					.getString(UserMapping.LOGIN));
			user.setIsBlacklisted(resultSet
					.getBoolean(UserMapping.BLACKLIST));
			user.setRole(resultSet
					.getString(RoleMapping.NAME));
		}
		return user;
	}

}
