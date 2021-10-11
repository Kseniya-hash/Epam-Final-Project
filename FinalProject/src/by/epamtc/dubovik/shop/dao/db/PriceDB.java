package by.epamtc.dubovik.shop.dao.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import by.epamtc.dubovik.shop.connectionpool.ConnectionPool;
import by.epamtc.dubovik.shop.dao.DAOException;
import by.epamtc.dubovik.shop.dao.PriceDAO;
import by.epamtc.dubovik.shop.dao.db.mapping.PriceLogMapping;
import by.epamtc.dubovik.shop.entity.Price;

public class PriceDB implements PriceDAO {
	
	private static final String SQL_CREATE = 
			"INSERT INTO `dubovik_Shop`.`price_logs` "
			+ "(`pl_p_id`, `pl_purchase_price`, `pl_selling_price`, `pl_date`) "
			+ "VALUES (?,?,?, NOW())";
	
	private static final String SQL_SELECT_BY_ID = 
			"SELECT * FROM `dubovik_shop`.`price_logs` pl " + 
					"INNER JOIN ( " + 
					"SELECT `pl_p_id`, MAX(`pl_date`) AS `pl_date` " + 
					"FROM `dubovik_shop`.`price_logs` GROUP BY `pl_p_id`) " + 
					"AS max USING (`pl_p_id`, `pl_date`) where `pl_id` = ?";
	
	private static final String SQL_SELECT_BY_PRODUCT_ID = 
			"SELECT * FROM `dubovik_shop`.`price_logs` pl " + 
					"INNER JOIN ( " + 
					"SELECT `pl_p_id`, MAX(`pl_date`) AS `pl_date` " + 
					"FROM `dubovik_shop`.`price_logs` GROUP BY `pl_p_id`) " + 
					"AS max USING (`pl_p_id`, `pl_date`) where `pl_p_id` = ?";
	
	private static final String SQL_SELECT_BY_PRODUCT_ID_AND_DATE = 
			"SELECT * FROM `dubovik_shop`.`price_logs` pl " + 
					"INNER JOIN ( " + 
					"SELECT `pl_p_id`, MAX(`pl_date`) AS `pl_date` " + 
					"FROM `dubovik_shop`.`price_logs` WHERE `pl_date` <= ? GROUP BY `pl_p_id`) " + 
					"AS max USING (`pl_p_id`, `pl_date`) where `pl_p_id` = ?";
	
	private Price takeFromResultSet(ResultSet resultSet) throws SQLException {
		Price price = null;
		if (!resultSet.isAfterLast()) {
			price = new Price();
			price.setId(resultSet.getInt(PriceLogMapping.ID));
			price.setProductId(resultSet.getInt(PriceLogMapping.PRODUCT_ID));
			price.setPurchasePrice(resultSet.getInt(PriceLogMapping.PURCHASE_PRICE));
			price.setSellingPrice(resultSet.getInt(PriceLogMapping.SELLING_PRICE));
		}
		
		return price;
	}
	
	@Override
	public Price findById(int id) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		Price price = null;
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			cn = pool.takeConnection();
			st = cn.prepareStatement(SQL_SELECT_BY_ID);
			st.setInt(1, id);
			rs = st.executeQuery();
			if(rs.next()) {
				price = takeFromResultSet(rs);
			}
		} catch(SQLException e) {
			throw new DAOException(e); 
		} finally {
			pool.closeConnection(cn, st, rs);
		}
		return price;
	}
	
	@Override
	public boolean create(Price entity) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		boolean flag = false;
		Connection cn = null;
		PreparedStatement st = null;
		
		try {
			cn = pool.takeConnection();
			st = cn.prepareStatement(SQL_CREATE);
			st.setInt(1, entity.getProductId());
			st.setInt(2, entity.getPurchasePrice());
			st.setInt(3, entity.getSellingPrice());
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
	public Price findByProduct(int productId) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		Price price = null;
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			cn = pool.takeConnection();
			st = cn.prepareStatement(SQL_SELECT_BY_PRODUCT_ID);
			st.setInt(1, productId);
			rs = st.executeQuery();
			if(rs.next()) {
				price = takeFromResultSet(rs);
			}
		} catch(SQLException e) {
			throw new DAOException(e); 
		} finally {
			pool.closeConnection(cn, st, rs);
		}
		return price;
	}

	@Override
	public Price findByProduct(int productId, Timestamp date) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		Price price = null;
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			cn = pool.takeConnection();
			st = cn.prepareStatement(SQL_SELECT_BY_PRODUCT_ID_AND_DATE);
			st.setInt(1, productId);
			st.setTimestamp(2, date);
			rs = st.executeQuery();
			if(rs.next()) {
				price = takeFromResultSet(rs);
			}
		} catch(SQLException e) {
			throw new DAOException(e); 
		} finally {
			pool.closeConnection(cn, st, rs);
		}
		return price;
	}
}
