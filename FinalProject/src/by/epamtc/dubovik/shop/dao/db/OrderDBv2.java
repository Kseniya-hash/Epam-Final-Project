package by.epamtc.dubovik.shop.dao.db;

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
import by.epamtc.dubovik.shop.dao.db.mapping.OrderMapping;
import by.epamtc.dubovik.shop.dao.db.mapping.OrderToProductMapping;
import by.epamtc.dubovik.shop.dao.db.mapping.PriceLogMapping;
import by.epamtc.dubovik.shop.dao.db.mapping.ProductMapping;
import by.epamtc.dubovik.shop.entity.Order;
import by.epamtc.dubovik.shop.entity.OrderForView;
import by.epamtc.dubovik.shop.entity.OrderToProduct;
import by.epamtc.dubovik.shop.entity.OrderToProductForView;

public class OrderDBv2 implements OrderDAO {
	
	private final static String CREATE_CREATE_ORDER = "INSERT INTO `dubovik_shop`.`orders` " +
			"(`o_u_id`,`o_os_id`,`o_date`) " +
			"VALUES ( ?, ?,NOW());";
	private final static String CREATE_TAKE_PRODUCTS = "UPDATE `dubovik_shop`.`products` "
			+ "SET p_quantity = p_quantity - ? WHERE p_id = ?;";
	private final static String CREATE_ADD_SALE = " INSERT INTO `dubovik_shop`.`sales` (`s_o_id`,`s_p_id`,`s_quantity`)"
			+ " VALUES ((SELECT max(LAST_INSERT_ID()) FROM `dubovik_shop`.`orders`), ?,?);";

	private final static String SQL_SELECT_BY_ID = "SELECT * FROM `dubovik_shop`.`orders` WHERE `o_id` = ?";
	
	private static final String SQL_SELECT_SALE = 
			"SELECT * FROM `dubovik_shop`.`sales` WHERE s_o_id = ?;";
	
	private static final String SQL_UPDATE =
			"UPDATE `dubovik_Shop`.`orders` SET `o_u_id` = ?, `o_os_id` = ?, `o_date` = ?"
					+ " WHERE `o_id` = ?";
	
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
	
	private OrderToProduct takeSaleFromResultSet(ResultSet resultSet) throws SQLException {
		OrderToProduct sale = null;
		
		if (!resultSet.isAfterLast()) {
			sale = new OrderToProduct();
			sale.setOrderId(resultSet.getInt(OrderToProductMapping.ORDER_ID));
			sale.setProductId(resultSet.getInt(OrderToProductMapping.PRODUCT_ID));
			sale.setQuantity(resultSet.getInt(OrderToProductMapping.QUANTITY));
		}
		
		return sale;
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
			st.setInt(1, order.getId());
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
	public boolean delete(int id) throws DAOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Order> findAll(int offset, int count) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Order entity) throws DAOException {
		// TODO Auto-generated method stub
		return false;
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
			
			createStatement.setInt(1, entity.getUserId());
			createStatement.setInt(2, entity.getOrderStatusId());
			createStatement.executeUpdate();
			for(OrderToProduct sale : entity.getSales()) {
				takeProductsStatement.setInt(1, sale.getQuantity());
				takeProductsStatement.setInt(2, sale.getProductId());
				takeProductsStatement.executeUpdate();
				
				addSaleStatement.setInt(1, sale.getProductId());
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

}
