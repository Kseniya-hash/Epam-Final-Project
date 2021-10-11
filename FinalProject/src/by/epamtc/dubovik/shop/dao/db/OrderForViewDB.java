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
import by.epamtc.dubovik.shop.dao.OrderForViewDAO;
import by.epamtc.dubovik.shop.dao.db.mapping.OrderMapping;
import by.epamtc.dubovik.shop.dao.db.mapping.OrderStatusMapping;
import by.epamtc.dubovik.shop.dao.db.mapping.OrderToProductMapping;
import by.epamtc.dubovik.shop.dao.db.mapping.PriceLogMapping;
import by.epamtc.dubovik.shop.dao.db.mapping.ProductMapping;
import by.epamtc.dubovik.shop.dao.db.mapping.UserMapping;
import by.epamtc.dubovik.shop.entity.OrderForView;
import by.epamtc.dubovik.shop.entity.OrderToProductForView;

public class OrderForViewDB implements OrderForViewDAO {
	
	private static final String SQL_SELECT_ALL = "SELECT o_id, u_id, unq_u_login, u_phone, o_date, unq_os_name " + 
			"FROM `dubovik_shop`.`orders` o " + 
			"LEFT JOIN `dubovik_shop`.`users` u ON o.o_u_id = u.u_id " + 
			"LEFT JOIN `dubovik_shop`.`order_statuses` os ON o.o_os_id = os.os_id;";
	
	private static final String SQL_SELECT_BY_USER = "SELECT o_id, u_id, unq_u_login, u_phone, o_date, unq_os_name " + 
			"FROM `dubovik_shop`.`orders` o " + 
			"LEFT JOIN `dubovik_shop`.`users` u ON o.o_u_id = u.u_id " + 
			"LEFT JOIN `dubovik_shop`.`order_statuses` os ON o.o_os_id = os.os_id WHERE u_id = ?;";
	private static final String SQL_SELECT_BY_ORDER_STATUS = "SELECT o_id, u_id, unq_u_login, u_phone, o_date, unq_os_name " + 
			"FROM `dubovik_shop`.`orders` o " + 
			"LEFT JOIN `dubovik_shop`.`users` u ON o.o_u_id = u.u_id " + 
			"LEFT JOIN `dubovik_shop`.`order_statuses` os ON o.o_os_id = os.os_id WHERE os.os_id = ?;";
	
	private static final String SQL_SELECT_SALE = 
			"SELECT s_o_id, s_quantity, p_id, unq_p_name, pl_selling_price " + 
			"FROM `dubovik_shop`.`sales` s " + 
			"INNER JOIN `dubovik_shop`.`products` p ON s.s_p_id = p.p_id " + 
			"INNER JOIN `dubovik_shop`.`price_logs` pl ON s.s_p_id = pl.pl_p_id " + 
			"INNER JOIN (SELECT pl_p_id, MAX(pl_date) AS pl_date " + 
			"			FROM `dubovik_shop`.`price_logs` WHERE pl_date <= ? GROUP BY pl_p_id) " + 
			"			AS max USING (`pl_p_id`, `pl_date`) WHERE s_o_id = ?;";

	private OrderForView takeFromResultSet(ResultSet resultSet) throws SQLException {
		OrderForView order = null;
		
		if (!resultSet.isAfterLast()) {
			order = new OrderForView();
			order.setId(resultSet.getInt(OrderMapping.ID));
			order.setUserId(resultSet.getInt(UserMapping.ID_COLUMN));
			order.setUserLogin(resultSet.getString(UserMapping.LOGIN_COLUMN));
			order.setUserPhone(resultSet.getString(UserMapping.PHONE_COLUMN));
			order.setOrderStatus(resultSet.getString(OrderStatusMapping.NAME));
			order.setDate(resultSet.getTimestamp(OrderMapping.DATE));
		}
		
		return order;
	}
	
	private OrderToProductForView takeSaleFromResultSet(ResultSet resultSet) throws SQLException {
		OrderToProductForView sale = null;
		
		if (!resultSet.isAfterLast()) {
			sale = new OrderToProductForView();
			sale.setOrderId(resultSet.getInt(OrderToProductMapping.ORDER_ID));
			sale.setProductId(resultSet.getInt(ProductMapping.ID));
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
			st.setTimestamp(1, order.getDate());
			st.setInt(2, order.getId());
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
	
	@Override
	public List<OrderForView> findByUser(int userId) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		List<OrderForView> orders= new ArrayList<>();
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			cn = pool.takeConnection();
			st = cn.prepareStatement(SQL_SELECT_BY_USER);
			st.setInt(1, userId);
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
/*
	@Override
	public List<OrderForView> findByDate(Timestamp date) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}
*/
	@Override
	public List<OrderForView> findByOrderStatus(int orderStatusId) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		List<OrderForView> orders= new ArrayList<>();
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			cn = pool.takeConnection();
			st = cn.prepareStatement(SQL_SELECT_BY_ORDER_STATUS);
			st.setInt(1, orderStatusId);
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
	public List<OrderForView> findAll() throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		List<OrderForView> orders= new ArrayList<>();
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			cn = pool.takeConnection();
			st = cn.prepareStatement(SQL_SELECT_ALL);
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

}
