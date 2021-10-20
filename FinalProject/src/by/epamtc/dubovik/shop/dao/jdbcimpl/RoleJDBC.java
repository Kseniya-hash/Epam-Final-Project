package by.epamtc.dubovik.shop.dao.jdbcimpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import by.epamtc.dubovik.shop.connectionpool.ConnectionPool;
import by.epamtc.dubovik.shop.dao.DAOException;
import by.epamtc.dubovik.shop.dao.RoleDAO;
import by.epamtc.dubovik.shop.dao.jdbcimpl.mapping.RoleMapping;
import by.epamtc.dubovik.shop.entity.Role;

public class RoleJDBC implements RoleDAO {
	private static final String SQL_SELECT_ALL = 
			"SELECT * FROM roles LIMIT ?,?";
	private static final String SQL_SELECT_BY_ID = 
			"SELECT * FROM roles WHERE r_id=?";
	private static final String SQL_CREATE = 
			"INSERT INTO roles (unq_r_name) VALUES (?)";
	private static final String SQL_UPDATE = 
			"UPDATE roles SET unq_r_name = ? WHERE r_id = ?";

	@Override
	public List<Role> findAll(int offset, int count) throws DAOException{
		ConnectionPool pool = ConnectionPool.getInstance();
		List<Role> roles = new ArrayList<>();
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
				Role role = takeFromResultSet(rs);
				roles.add(role);
			}
		} catch(SQLException e) {
			throw new DAOException(e); 
		} finally {
			pool.closeConnection(cn, st, rs);
		}
		return roles;
	}
	
	@Override
	public Role findById(long id) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		Role role = null;
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			cn = pool.takeConnection();
			st = cn.prepareStatement(SQL_SELECT_BY_ID);
			st.setLong(1, id);
			rs = st.executeQuery();
			if(rs.next()) {
				role = takeFromResultSet(rs);
			}
		} catch(SQLException e) {
			throw new DAOException(e); 
		} finally {
			pool.closeConnection(cn, st, rs);
		}
		return role;
	}
	
	@Override
	public boolean create(Role entity) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		boolean flag = false;
		Connection cn = null;
		PreparedStatement st = null;
		
		try {
			cn = pool.takeConnection();
			st = cn.prepareStatement(SQL_CREATE);
			st.setString(1, entity.getName());
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
	public boolean update(Role entity) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection cn = null;
		PreparedStatement st = null;
		boolean flag = false;
		
		try {
			cn = pool.takeConnection();
			st = cn.prepareStatement(SQL_UPDATE);
			st.setString(1, entity.getName());
			st.setLong(2, entity.getId());
			int result = st.executeUpdate();
			flag = result > 0;
		} catch(SQLException e) {
			flag = false;
		} finally {
			pool.closeConnection(cn, st);
		}
		return flag;
	}
	
	private Role takeFromResultSet(ResultSet resultSet) throws SQLException {
		Role role = null;
		if (!resultSet.isAfterLast()) {
			role = new Role();
			role.setId(resultSet.getLong(RoleMapping.ID));
			role.setName(resultSet.getString(RoleMapping.NAME));
		}
		return role;
	}
}
