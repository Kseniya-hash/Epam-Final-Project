package by.epamtc.dubovik.shop.dao.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import by.epamtc.dubovik.shop.connectionpool.ConnectionPool;
import by.epamtc.dubovik.shop.dao.DAOException;
import by.epamtc.dubovik.shop.dao.OrderDAO;
import by.epamtc.dubovik.shop.dao.db.mapping.OrderMapping;
import by.epamtc.dubovik.shop.entity.Order;

public class OrderDB implements OrderDAO {
	private static final String SQL_SELECT_ALL = 
			"SELECT * FROM `dubovik_shop`.`orders` LIMIT ?, ?";
	private static final String SQL_SELECT_BY_ID = 
			"SELECT * FROM `dubovik_shop`.`orders` WHERE `o_id` = ?";
	private static final String SQL_DELETE_BY_ID = 
			"DELETE FROM `dubovik_Shop`.`orders` WHERE `o_id` = ?";
	private static final String SQL_DELETE_BY_ENTITY = 
			"DELETE FROM `dubovik_Shop`.`orders` WHERE `o_id` = ? AND `o_u_id` = ? "
			+ "AND `o_os_id` = ? AND `o_date` = ?";
	private static final String SQL_CREATE = 
			"INSERT INTO `dubovik_Shop`.`orders` (`o_u_id`, `o_os_id`, `o_date`) "
			+ "VALUES (?,?,?)";
	private static final String SQL_UPDATE =
			"UPDATE `dubovik_Shop`.`orders` SET `o_u_id` = ?, `o_os_id` = ?, `o_date` = ?"
					+ " WHERE `o_id` = ?";
	
	private static final String SQL_SELECT_BY_USERID = 
			"SELECT * FROM `dubovik_shop`.`orders` WHERE `o_u_id` = ? LIMIT ?, ?";
	private static final String SQL_SELECT_BY_DATE = 
			"SELECT * FROM `dubovik_shop`.`orders` "
			+ "WHERE DATE(`o_date`) = DATE(?) LIMIT ?, ?";
	private static final String SQL_SELECT_BY_ORDERSTATUS = 
			"SELECT * FROM `dubovik_shop`.`orders` WHERE `o_os_id` = ? LIMIT ?, ?";
	
	private Order takeFromResultSet(ResultSet resultSet) throws SQLException {
		Order order = null;
		
		if (!resultSet.isAfterLast()) {
			order = new Order();
			order.setId(resultSet.getInt(OrderMapping.ID));
			order.setUserId(resultSet.getInt(OrderMapping.USER_ID));
			order.setOrderStatusId(resultSet.getInt(OrderMapping.ORDER_STATUS));
			order.setDate(resultSet.getTimestamp(OrderMapping.DATE));
		}
		
		return order;
	}
	
	@Override
	public Order findById(int id) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		Order order = null;
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			cn = pool.takeConnection();
			st = cn.prepareStatement(SQL_SELECT_BY_ID);
			st.setInt(1, id);
			rs = st.executeQuery();
			if(rs.next()) {
				order = takeFromResultSet(rs);
			}
		} catch(SQLException e) {
			throw new DAOException(e); 
		} finally {
			pool.closeConnection(cn, st, rs);
		}
		return order;
	}
	
	@Override
	public boolean delete(int id) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		boolean flag = false;
		Connection cn = null;
		PreparedStatement st = null;
		
		try {
			cn = pool.takeConnection();
			st = cn.prepareStatement(SQL_DELETE_BY_ID);
			st.setInt(1, id);
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
	public List<Order> findAll(int offset, int count) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		List<Order> orders= new ArrayList<>();
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
				Order currentOrder = takeFromResultSet(rs);
				orders.add(currentOrder);
			}
		} catch(SQLException e) {
			throw new DAOException(e); 
		} finally {
			pool.closeConnection(cn, st, rs);
		}
		return orders;
	}
	@Override
	public boolean delete(Order entity) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		boolean flag = false;
		Connection cn = null;
		PreparedStatement st = null;
		
		try {
			cn = pool.takeConnection();
			st = cn.prepareStatement(SQL_DELETE_BY_ENTITY);
			st.setInt(1, entity.getId());
			st.setInt(2, entity.getUserId());
			st.setInt(3, entity.getOrderStatusId());
			st.setTimestamp(4, entity.getDate());
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
	public boolean create(Order entity) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		boolean flag = false;
		Connection cn = null;
		PreparedStatement st = null;
		
		try {
			cn = pool.takeConnection();
			st = cn.prepareStatement(SQL_CREATE);
			st.setInt(1, entity.getUserId());
			st.setInt(2, entity.getOrderStatusId());
			st.setTimestamp(3, entity.getDate());
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
	public boolean update(Order entity) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection cn = null;
		PreparedStatement st = null;
		boolean flag = false;
		
		try {
			cn = pool.takeConnection();
			st = cn.prepareStatement(SQL_UPDATE);
			st.setInt(1, entity.getUserId());
			st.setInt(2, entity.getOrderStatusId());
			st.setTimestamp(3, entity.getDate());
			st.setInt(4, entity.getId());
			int result = st.executeUpdate();
			flag = result > 0;
		} catch(SQLException e) {
			flag = false;
		} finally {
			pool.closeConnection(cn, st);
		}
		return flag;
	}
	/*
	@Override
	public List<Order> findByUser(int userId, int offset, int count) 
			throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		List<Order> orders= new ArrayList<>();
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			cn = pool.takeConnection();
			st = cn.prepareStatement(SQL_SELECT_BY_USERID);
			st.setInt(1, userId);
			st.setInt(2, offset);
			st.setInt(3, count);
			rs = st.executeQuery();
			while(rs.next()) {
				Order currentOrder = takeFromResultSet(rs);
				orders.add(currentOrder);
			}
		} catch(SQLException e) {
			throw new DAOException(e); 
		} finally {
			pool.closeConnection(cn, st, rs);
		}
		return orders;
	}
	
	@Override
	public List<Order> findByDate(Timestamp date, int offset, int count) 
			throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		List<Order> orders= new ArrayList<>();
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			cn = pool.takeConnection();
			st = cn.prepareStatement(SQL_SELECT_BY_DATE);
			st.setTimestamp(1, date);
			st.setInt(2, offset);
			st.setInt(3, count);
			rs = st.executeQuery();
			while(rs.next()) {
				Order currentOrder = takeFromResultSet(rs);
				orders.add(currentOrder);
			}
		} catch(SQLException e) {
			throw new DAOException(e); 
		} finally {
			pool.closeConnection(cn, st, rs);
		}
		return orders;
	}
	
	@Override
	public List<Order> findByOrderStatus(int orderStatusId, int offset, int count) 
			throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		List<Order> orders= new ArrayList<>();
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			cn = pool.takeConnection();
			st = cn.prepareStatement(SQL_SELECT_BY_ORDERSTATUS);
			st.setInt(1, orderStatusId);
			st.setInt(2, offset);
			st.setInt(3, count);
			rs = st.executeQuery();
			while(rs.next()) {
				Order currentOrder = takeFromResultSet(rs);
				orders.add(currentOrder);
			}
		} catch(SQLException e) {
			throw new DAOException(e); 
		} finally {
			pool.closeConnection(cn, st, rs);
		}
		return orders;
	}
*/
}
