 package by.epamtc.dubovik.shop.dao.jdbcimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import by.epamtc.dubovik.shop.dao.DAOException;
import by.epamtc.dubovik.shop.dao.OrderDAO;
import by.epamtc.dubovik.shop.dao.connectionpool.ConnectionPoolException;
import by.epamtc.dubovik.shop.dao.connectionpool.ConnectionPool;
import by.epamtc.dubovik.shop.dao.jdbcimpl.mapping.OrderMapping;
import by.epamtc.dubovik.shop.dao.jdbcimpl.mapping.SaleMapping;
import by.epamtc.dubovik.shop.entity.Order;
import by.epamtc.dubovik.shop.entity.Sale;
public class OrderJDBC implements OrderDAO {
	
	private final static String CREATE_CREATE_ORDER = "INSERT INTO orders " +
			"(o_u_id,o_os_id,o_date) " +
			"VALUES ( ?, ?, ?);";
	private final static String CREATE_TAKE_PRODUCTS = "UPDATE products "
			+ "SET p_quantity = p_quantity - ? WHERE p_id = ?;";
	private final static String CREATE_ADD_SALE = " INSERT INTO sales (s_o_id,s_p_id,s_quantity)"
			+ " VALUES ((SELECT max(LAST_INSERT_ID()) FROM orders), ?,?);";

	private final static String SQL_SELECT_BY_ID = "SELECT * FROM orders WHERE o_id = ?";
	
	private final static String SQL_SELECT_ALL = "SELECT * FROM orders ORDER BY o_date DESC LIMIT ?, ?";
	
	private static final String SQL_SELECT_SALE = 
			"SELECT * FROM sales WHERE s_o_id = ?;";
	
	private static final String SQL_UPDATE =
			"UPDATE orders SET o_u_id = ?, o_os_id = ?, o_date = ?"
					+ " WHERE o_id = ?";
	
	private static final ConnectionPool POOL = ConnectionPool.getInstance();
	
	@Override
	public Order findById(long id) throws DAOException {
		
		Order order = null;
		
		try (Connection cn = POOL.takeConnection(); 
				PreparedStatement st = cn.prepareStatement(SQL_SELECT_BY_ID)){
			st.setLong(1, id);
			try(ResultSet rs = st.executeQuery()){
				if(rs.next()) {
					order = takeFromResultSet(rs);
					List<Sale> sales = takeSales(order);
					order.setSales(sales);
				}
			}
		} catch(SQLException e) {
			throw new DAOException(e); 
		}
		return order;
	}
	
	private List<Sale> takeSales(Order order)
			throws DAOException {
		
		List<Sale> sales = new ArrayList<>();
		try (Connection cn = POOL.takeConnection(); 
				PreparedStatement st = cn.prepareStatement(SQL_SELECT_SALE)){
			st.setLong(1, order.getId());
			try(ResultSet rs = st.executeQuery()){
				while(rs.next()) {
					Sale currentSale = takeSaleFromResultSet(rs);
					sales.add(currentSale);
				}
			}
		} catch(SQLException e) {
			throw new DAOException(e); 
		}
		return sales;
	}

	@Override
	public List<Order> findAll(int offset, int count) 
			throws DAOException {
		
		List<Order> orders= new ArrayList<>();
		
		try (Connection cn = POOL.takeConnection(); 
				PreparedStatement st = cn.prepareStatement(SQL_SELECT_ALL)){
			st.setInt(1, offset);
			st.setInt(2, count);
			try(ResultSet rs = st.executeQuery()){
				while(rs.next()) {
					Order currentOrder = takeFromResultSet(rs);
					List<Sale> sales = takeSales(currentOrder);
					currentOrder.setSales(sales);
					orders.add(currentOrder);
				}
			}
		} catch(SQLException e) {
			throw new DAOException(e); 
		}
		return orders;
	}
	
	private Order takeFromResultSet(ResultSet resultSet) 
			throws SQLException {
		Order order = null;
		
		if (!resultSet.isAfterLast()) {
			order = new Order();
			order.setId(resultSet
					.getLong(OrderMapping.ID));
			order.setUserId(resultSet
					.getLong(OrderMapping.USER_ID));
			order.setOrderStatusId(resultSet
					.getLong(OrderMapping.ORDER_STATUS));
			order.setDate(resultSet
					.getTimestamp(OrderMapping.DATE).toLocalDateTime());
		}
		
		return order;
	}
	
	private Sale takeSaleFromResultSet(ResultSet resultSet) 
			throws SQLException {
		Sale sale = null;
		
		if (!resultSet.isAfterLast()) {
			sale = new Sale();
			sale.setOrderId(resultSet
					.getLong(SaleMapping.ORDER_ID));
			sale.setProductId(resultSet
					.getLong(SaleMapping.PRODUCT_ID));
			sale.setQuantity(resultSet
					.getInt(SaleMapping.QUANTITY));
		}
		
		return sale;
	}
	
	@Override
	public boolean create(Order entity) throws DAOException {
		
		boolean flag = false;
		try (Connection cn = POOL.takeConnection()) {
			try(PreparedStatement createStatement = cn.prepareStatement(CREATE_CREATE_ORDER);
				PreparedStatement takeProductsStatement = cn.prepareStatement(CREATE_TAKE_PRODUCTS);
				PreparedStatement addSaleStatement = cn.prepareStatement(CREATE_ADD_SALE)){
			cn.setAutoCommit(false);
			
			createStatement.setLong(1, entity.getUserId());
			createStatement.setLong(2, entity.getOrderStatusId());
			createStatement.setTimestamp(3, Timestamp.valueOf(entity.getDate()));
			createStatement.executeUpdate();
			for(Sale sale : entity.getSales()) {
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
				cn.rollback();
			}
		} catch (ConnectionPoolException | SQLException e) {
			throw new DAOException(e);
		}
		return flag;
	}

	@Override
	public boolean update(Order entity) throws DAOException {
		
		boolean flag = false;
		
		try (Connection cn = POOL.takeConnection(); 
				PreparedStatement st = cn.prepareStatement(SQL_UPDATE)){
			st.setLong(1, entity.getUserId());
			st.setLong(2, entity.getOrderStatusId());
			st.setTimestamp(3, Timestamp.valueOf(entity.getDate()));
			st.setLong(4, entity.getId());
			int result = st.executeUpdate();
			flag = result > 0;
		} catch(SQLException e) {
			throw new DAOException(e);
		}
		return flag;
	}

}
