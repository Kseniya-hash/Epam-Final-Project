package by.epamtc.dubovik.shop.dao.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

import by.epamtc.dubovik.shop.connectionpool.ConnectionPool;
import by.epamtc.dubovik.shop.dao.DAOException;
import by.epamtc.dubovik.shop.dao.OrderDAO;
import by.epamtc.dubovik.shop.entity.Order;
import by.epamtc.dubovik.shop.entity.OrderToProduct;

public class OrderDBv2 implements OrderDAO {
	
	private final static String CREATE_CREATE_ORDER = "INSERT INTO `dubovik_shop`.`orders` " +
			"(`o_u_id`,`o_os_id`,`o_date`) " +
			"VALUES ( ?, ?,NOW());";
	private final static String CREATE_TAKE_PRODUCTS = "UPDATE `dubovik_shop`.`products` "
			+ "SET p_quantity = p_quantity - ? WHERE p_id = ?;";
	private final static String CREATE_ADD_SALE = " INSERT INTO `dubovik_shop`.`sales` (`s_o_id`,`s_p_id`,`s_quantity`)"
			+ " VALUES ((SELECT max(LAST_INSERT_ID()) FROM `dubovik_shop`.`orders`), ?,?);";

	@Override
	public Order findById(int id) throws DAOException {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return false;
	}

}
