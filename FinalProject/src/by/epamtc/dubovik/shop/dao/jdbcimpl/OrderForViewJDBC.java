package by.epamtc.dubovik.shop.dao.jdbcimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import by.epamtc.dubovik.shop.connectionpool.ConnectionPool;
import by.epamtc.dubovik.shop.dao.DAOException;
import by.epamtc.dubovik.shop.dao.OrderForViewDAO;
import by.epamtc.dubovik.shop.dao.jdbcimpl.mapping.OrderMapping;
import by.epamtc.dubovik.shop.dao.jdbcimpl.mapping.OrderStatusMapping;
import by.epamtc.dubovik.shop.dao.jdbcimpl.mapping.OrderToProductMapping;
import by.epamtc.dubovik.shop.dao.jdbcimpl.mapping.PriceLogMapping;
import by.epamtc.dubovik.shop.dao.jdbcimpl.mapping.ProductMapping;
import by.epamtc.dubovik.shop.dao.jdbcimpl.mapping.UserMapping;
import by.epamtc.dubovik.shop.entity.OrderForView;
import by.epamtc.dubovik.shop.entity.OrderToProductForView;

public class OrderForViewJDBC implements OrderForViewDAO {
	
	private static final String SQL_SELECT_ALL = "SELECT o_id, u_id, unq_u_login, u_phone, u_is_blacklisted, o_date, unq_os_name " + 
			"FROM orders o " + 
			"LEFT JOIN users u ON o.o_u_id = u.u_id " + 
			"LEFT JOIN order_statuses os ON o.o_os_id = os.os_id ORDER BY o_date DESC LIMIT ?,?;";
	
	private static final String SQL_COUNT_ALL = "SELECT count(o_id) FROM orders";
	
	private static final String SQL_SELECT_BY_USER = "SELECT o_id, u_id, unq_u_login, u_phone, u_is_blacklisted, o_date, unq_os_name " + 
			"FROM orders o " + 
			"LEFT JOIN users u ON o.o_u_id = u.u_id " + 
			"LEFT JOIN order_statuses os ON o.o_os_id = os.os_id WHERE u_id = ? ORDER BY o_date DESC;";
	
	private static final String SQL_SELECT_BY_ORDER_STATUS = "SELECT o_id, u_id, unq_u_login, u_phone, u_is_blacklisted, o_date, unq_os_name " + 
			"FROM orders o " + 
			"LEFT JOIN users u ON o.o_u_id = u.u_id " + 
			"LEFT JOIN order_statuses os ON o.o_os_id = os.os_id WHERE os.os_id = ? ORDER BY o_date DESC;";
	
	private static final String SQL_SELECT_BY_ID = "SELECT o_id, u_id, unq_u_login, u_phone, u_is_blacklisted, o_date, unq_os_name " + 
			"FROM orders o " + 
			"LEFT JOIN users u ON o.o_u_id = u.u_id " + 
			"LEFT JOIN order_statuses os ON o.o_os_id = os.os_id WHERE o_id = ?;";
	
	private static final String SQL_SELECT_SALE = 
			"SELECT s_o_id, s_quantity, p_id, unq_p_name, pl_selling_price " + 
			"FROM sales s " + 
			"INNER JOIN products p ON s.s_p_id = p.p_id " + 
			"INNER JOIN price_logs pl ON s.s_p_id = pl.pl_p_id " + 
			"INNER JOIN (SELECT pl_p_id, MAX(pl_date) AS pl_date " + 
			"			FROM price_logs WHERE pl_date <= ? GROUP BY pl_p_id) " + 
			"			AS max USING (pl_p_id, pl_date) WHERE s_o_id = ?;";
	
	@Override
	public List<OrderForView> findByUser(long userId) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		List<OrderForView> orders= new ArrayList<>();
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			cn = pool.takeConnection();
			st = cn.prepareStatement(SQL_SELECT_BY_USER);
			st.setLong(1, userId);
			rs = st.executeQuery();
			while(rs.next()) {
				OrderForView currentOrder = takeFromResultSet(rs);
				List<OrderToProductForView> sales = takeSales(currentOrder);
				currentOrder.setSales(sales);
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
	public List<OrderForView> findByOrderStatus(long orderStatusId) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		List<OrderForView> orders= new ArrayList<>();
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			cn = pool.takeConnection();
			st = cn.prepareStatement(SQL_SELECT_BY_ORDER_STATUS);
			st.setLong(1, orderStatusId);
			rs = st.executeQuery();
			while(rs.next()) {
				OrderForView currentOrder = takeFromResultSet(rs);
				List<OrderToProductForView> sales = takeSales(currentOrder);
				currentOrder.setSales(sales);
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
	public List<OrderForView> findAll(int offset, int count) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		List<OrderForView> orders= new ArrayList<>();
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
				OrderForView currentOrder = takeFromResultSet(rs);
				List<OrderToProductForView> sales = takeSales(currentOrder);
				currentOrder.setSales(sales);
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
	public OrderForView findById(long orderId) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		OrderForView order = null;
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			cn = pool.takeConnection();
			st = cn.prepareStatement(SQL_SELECT_BY_ID);
			st.setLong(1, orderId);
			rs = st.executeQuery();
			if(rs.next()) {
				order = takeFromResultSet(rs);
				List<OrderToProductForView> sales = takeSales(order);
				order.setSales(sales);
			}
		} catch(SQLException e) {
			throw new DAOException(e); 
		} finally {
			pool.closeConnection(cn, st, rs);
		}
		return order;
	}

	@Override
	public int countAll() throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		int commentCount = 0;
		Connection cn = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			cn = pool.takeConnection();
			st = cn.createStatement();
			rs = st.executeQuery(SQL_COUNT_ALL);
			if(rs.next()) {
				commentCount = rs.getInt(1);
			}
		} catch(SQLException e) {
			throw new DAOException(e); 
		} finally {
			pool.closeConnection(cn, st, rs);
		}
		return commentCount;
	}
	
	private OrderForView takeFromResultSet(ResultSet resultSet) throws SQLException {
		OrderForView order = null;
		
		if (!resultSet.isAfterLast()) {
			order = new OrderForView();
			order.setId(resultSet.getLong(OrderMapping.ID));
			order.setUserId(resultSet.getLong(UserMapping.ID));
			order.setUserLogin(resultSet.getString(UserMapping.LOGIN));
			order.setUserPhone(resultSet.getString(UserMapping.PHONE));
			order.setIsBlacklisted(resultSet.getBoolean(UserMapping.BLACKLIST));
			order.setOrderStatus(resultSet.getString(OrderStatusMapping.NAME));
			order.setDate(resultSet.getTimestamp(OrderMapping.DATE).toLocalDateTime());
		}
		
		return order;
	}
	
	private OrderToProductForView takeSaleFromResultSet(ResultSet resultSet) throws SQLException {
		OrderToProductForView sale = null;
		
		if (!resultSet.isAfterLast()) {
			sale = new OrderToProductForView();
			sale.setOrderId(resultSet.getLong(OrderToProductMapping.ORDER_ID));
			sale.setProductId(resultSet.getLong(ProductMapping.ID));
			sale.setProductName(resultSet.getString(ProductMapping.NAME));
			sale.setPrice(resultSet.getInt(PriceLogMapping.SELLING_PRICE));
			sale.setQuantity(resultSet.getInt(OrderToProductMapping.QUANTITY));
		}
		
		return sale;
	}
	
	private List<OrderToProductForView> takeSales(OrderForView order) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		List<OrderToProductForView> sales = new ArrayList<>();
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			cn = pool.takeConnection();
			st = cn.prepareStatement(SQL_SELECT_SALE);
			st.setTimestamp(1, Timestamp.valueOf(order.getDate()));
			st.setLong(2, order.getId());
			rs = st.executeQuery();
			while(rs.next()) {
				OrderToProductForView currentSale = takeSaleFromResultSet(rs);
				sales.add(currentSale);
			}
		} catch(SQLException e) {
			throw new DAOException(e); 
		} finally {
			pool.closeConnection(cn, st, rs);
		}
		return sales;
	}

}
