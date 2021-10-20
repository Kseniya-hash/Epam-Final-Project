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
import by.epamtc.dubovik.shop.dao.OrderDAO;
import by.epamtc.dubovik.shop.dao.jdbcimpl.mapping.OrderMapping;
import by.epamtc.dubovik.shop.dao.jdbcimpl.mapping.OrderToProductMapping;
import by.epamtc.dubovik.shop.entity.Order;
import by.epamtc.dubovik.shop.entity.OrderToProduct;
public class OrderJDBC implements OrderDAO {
	
	private final static String CREATE_CREATE_ORDER = "INSERT INTO orders " +
			"(o_u_id,o_os_id,o_date) " +
			"VALUES ( ?, ?,NOW());";
	private final static String CREATE_TAKE_PRODUCTS = "UPDATE products "
			+ "SET p_quantity = p_quantity - ? WHERE p_id = ?;";
	private final static String CREATE_ADD_SALE = " INSERT INTO sales (s_o_id,s_p_id,s_quantity)"
			+ " VALUES ((SELECT max(LAST_INSERT_ID()) FROM orders), ?,?);";

	private final static String SQL_SELECT_BY_ID = "SELECT * FROM orders WHERE o_id = ?";
	
	private final static String SQL_SELECT_ALL = "SELECT * FROM orders LIMIT ?, ?";
	
	private static final String SQL_SELECT_SALE = 
			"SELECT * FROM sales WHERE s_o_id = ?;";
	
	private static final String SQL_UPDATE =
			"UPDATE orders SET o_u_id = ?, o_os_id = ?, o_date = ?"
					+ " WHERE o_id = ?";
	
	@Override
	public Order findById(long id) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		Order order = null;
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			cn = pool.takeConnection();
			st = cn.prepareStatement(SQL_SELECT_BY_ID);
			st.setLong(1, id);
			rs = st.executeQuery();
			if(rs.next()) {
				order = takeFromResultSet(rs);
				List<OrderToProduct> sales = takeSales(order);
				order.setSales(sales);
			}
		} catch(SQLException e) {
			throw new DAOException(e); 
		} finally {
			pool.closeConnection(cn, st, rs);
		}
		return order;
	}
	
	private List<OrderToProduct> takeSales(Order order) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		List<OrderToProduct> sales = new ArrayList<>();
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			cn = pool.takeConnection();
			st = cn.prepareStatement(SQL_SELECT_SALE);
			st.setLong(1, order.getId());
			rs = st.executeQuery();
			while(rs.next()) {
				OrderToProduct currentSale = takeSaleFromResultSet(rs);
				sales.add(currentSale);
			}
		} catch(SQLException e) {
			throw new DAOException(e); 
		} finally {
			pool.closeConnection(cn, st, rs);
		}
		return sales;
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
				List<OrderToProduct> sales = takeSales(currentOrder);
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
	
	private Order takeFromResultSet(ResultSet resultSet) throws SQLException {
		Order order = null;
		
		if (!resultSet.isAfterLast()) {
			order = new Order();
			order.setId(resultSet.getLong(OrderMapping.ID));
			order.setUserId(resultSet.getLong(OrderMapping.USER_ID));
			order.setOrderStatusId(resultSet.getLong(OrderMapping.ORDER_STATUS));
			order.setDate(resultSet.getTimestamp(OrderMapping.DATE).toLocalDateTime());
		}
		
		return order;
	}
	
	private OrderToProduct takeSaleFromResultSet(ResultSet resultSet) throws SQLException {
		OrderToProduct sale = null;
		
		if (!resultSet.isAfterLast()) {
			sale = new OrderToProduct();
			sale.setOrderId(resultSet.getLong(OrderToProductMapping.ORDER_ID));
			sale.setProductId(resultSet.getLong(OrderToProductMapping.PRODUCT_ID));
			sale.setQuantity(resultSet.getInt(OrderToProductMapping.QUANTITY));
		}
		
		return sale;
	}
	
	@Override
	public boolean create(Order entity) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		boolean flag = false;
		Connection cn = null;
		PreparedStatement createStatement = null;
		PreparedStatement takeProductsStatement = null;
		PreparedStatement addSaleStatement = null;
		try {
			cn = pool.takeConnection();
			cn.setAutoCommit(false);
			
			createStatement = cn.prepareStatement(CREATE_CREATE_ORDER);
			takeProductsStatement = cn.prepareStatement(CREATE_TAKE_PRODUCTS);
			addSaleStatement = cn.prepareStatement(CREATE_ADD_SALE);
			
			createStatement.setLong(1, entity.getUserId());
			createStatement.setLong(2, entity.getOrderStatusId());
			createStatement.executeUpdate();
			for(OrderToProduct sale : entity.getSales()) {
				takeProductsStatement.setInt(1, sale.getQuantity());
				takeProductsStatement.setLong(2, sale.getProductId());
				takeProductsStatement.executeUpdate();
				
				addSaleStatement.setLong(1, sale.getProductId());
				addSaleStatement.setInt(2, sale.getQuantity());
				addSaleStatement.executeUpdate();
			}
			cn.commit();
			flag = true;
		} catch(SQLException e) {
			try {
				cn.rollback();
			} catch (SQLException e1) {
				throw new DAOException(e1);
			}
			flag = false;
		} finally {
			try {
				takeProductsStatement.close();
			} catch(SQLException e) {
				throw new DAOException(e);
			}
			try {
				addSaleStatement.close();
			} catch(SQLException e) {
				throw new DAOException(e);
			}
			pool.closeConnection(cn, createStatement);
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
			st.setLong(1, entity.getUserId());
			st.setLong(2, entity.getOrderStatusId());
			st.setTimestamp(3, Timestamp.valueOf(entity.getDate()));
			st.setLong(4, entity.getId());
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