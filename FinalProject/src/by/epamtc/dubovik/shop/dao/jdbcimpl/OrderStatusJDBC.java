package by.epamtc.dubovik.shop.dao.jdbcimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.epamtc.dubovik.shop.connectionpool.ConnectionPool;
import by.epamtc.dubovik.shop.dao.DAOException;
import by.epamtc.dubovik.shop.dao.OrderStatusDAO;
import by.epamtc.dubovik.shop.dao.jdbcimpl.mapping.OrderStatusMapping;
import by.epamtc.dubovik.shop.entity.OrderStatus;

public class OrderStatusJDBC implements OrderStatusDAO {
	private static final String SQL_SELECT_ALL = 
			"SELECT * FROM `dubovik_Shop`.`order_statuses` LIMIT ?,?";
	private static final String SQL_SELECT_BY_ID = 
			"SELECT * FROM `dubovik_Shop`.`order_statuses` WHERE `os_id`=?";
	private static final String SQL_CREATE = 
			"INSERT INTO `dubovik_Shop`.`order_statuses` (`unq_os_name`) VALUES (?)";
	private static final String SQL_DELETE_BY_ID = 
			"DELETE FROM `dubovik_Shop`.`order_statuses` WHERE `os_id`=?";
	private static final String SQL_DELETE_BY_ENTITY = 
			"DELETE FROM `dubovik_Shop`.`order_statuses` WHERE `os_id` = ? AND `unq_os_name` = ?";
	private static final String SQL_UPDATE = 
			"UPDATE `dubovik_Shop`.`order_statuses` SET `unq_os_name` = ? WHERE `os_id` = ?";
	
	private OrderStatus takeFromResultSet(ResultSet resultSet) throws SQLException {
		OrderStatus status = null;
		if (!resultSet.isAfterLast()) {
			status = new OrderStatus();
			status.setId(resultSet.getLong(OrderStatusMapping.ID));
			status.setName(resultSet.getString(OrderStatusMapping.NAME));
		}
		return status;
	}

	@Override
	public OrderStatus findById(long id) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		OrderStatus status = null;
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			cn = pool.takeConnection();
			st = cn.prepareStatement(SQL_SELECT_BY_ID);
			st.setLong(1, id);
			rs = st.executeQuery();
			if(rs.next()) {
				status = takeFromResultSet(rs);
			}
		} catch(SQLException e) {
			throw new DAOException(e); 
		} finally {
			pool.closeConnection(cn, st, rs);
		}
		return status;
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
	public List<OrderStatus> findAll(int offset, int count) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		List<OrderStatus> statuses= new ArrayList<>();
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
				OrderStatus current = takeFromResultSet(rs);
				statuses.add(current);
			}
		} catch(SQLException e) {
			throw new DAOException(e); 
		} finally {
			pool.closeConnection(cn, st, rs);
		}
		return statuses;
	}

	@Override
	public boolean delete(OrderStatus entity) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		boolean flag = false;
		Connection cn = null;
		PreparedStatement st = null;
		
		try {
			cn = pool.takeConnection();
			st = cn.prepareStatement(SQL_DELETE_BY_ENTITY);
			st.setLong(1, entity.getId());
			st.setString(2, entity.getName());
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
	public boolean create(OrderStatus entity) throws DAOException {
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
	public boolean update(OrderStatus entity) throws DAOException {
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

}
