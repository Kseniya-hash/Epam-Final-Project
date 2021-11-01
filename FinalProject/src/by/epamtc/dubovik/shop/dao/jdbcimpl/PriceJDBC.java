package by.epamtc.dubovik.shop.dao.jdbcimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import by.epamtc.dubovik.shop.dao.DAOException;
import by.epamtc.dubovik.shop.dao.PriceDAO;
import by.epamtc.dubovik.shop.dao.connectionpool.ConnectionPool;
import by.epamtc.dubovik.shop.dao.jdbcimpl.mapping.PriceLogMapping;
import by.epamtc.dubovik.shop.entity.Price;

public class PriceJDBC implements PriceDAO {
	
	private static final String SQL_CREATE = 
			"INSERT INTO price_logs "
			+ "(pl_p_id, pl_purchase_price, pl_selling_price, pl_date) "
			+ "VALUES (?,?,?, ?)";
	
	private static final String SQL_SELECT_BY_ID = 
			"SELECT * FROM price_logs WHERE pl_id = ?";
	
	private static final String SQL_SELECT_BY_PRODUCT_ID = 
			"SELECT * FROM price_logs pl " + 
					"INNER JOIN ( " + 
					"SELECT pl_p_id, MAX(pl_date) AS pl_date " + 
					"FROM price_logs GROUP BY pl_p_id) " + 
					"AS max USING (pl_p_id, pl_date) where pl_p_id = ?";
	
	private static final String SQL_SELECT_BY_PRODUCT_ID_AND_DATE = 
			"SELECT * FROM price_logs pl " + 
					"INNER JOIN ( " + 
					"SELECT pl_p_id, MAX(pl_date) AS pl_date " + 
					"FROM price_logs WHERE pl_date <= ? GROUP BY pl_p_id) " + 
					"AS max USING (pl_p_id, pl_date) where pl_p_id = ?";
	
	private static final ConnectionPool POOL = ConnectionPool.getInstance();
	
	@Override
	public Price findById(long id) throws DAOException {
		
		Price price = null;
		try (Connection cn = POOL.takeConnection(); 
				PreparedStatement st = cn
						.prepareStatement(SQL_SELECT_BY_ID)){
			st.setLong(1, id);
			try(ResultSet rs = st.executeQuery()){
				if(rs.next()) {
					price = takeFromResultSet(rs);
				}
			}
		} catch(SQLException e) {
			throw new DAOException(e); 
		}
		return price;
	}
	
	@Override
	public boolean create(Price entity) throws DAOException {
		
		boolean flag = false;
		
		try (Connection cn = POOL.takeConnection(); 
				PreparedStatement st = cn.prepareStatement(SQL_CREATE)){
			st.setLong(1, entity.getProductId());
			st.setInt(2, entity.getPurchasePrice());
			st.setInt(3, entity.getSellingPrice());
			st.setTimestamp(4, Timestamp.valueOf(entity.getDate()));
			int result = st.executeUpdate();
			flag = result > 0;
		} catch(SQLException e) {
			throw new DAOException(e);
		}
		return flag;
	}

	@Override
	public Price findByProduct(long productId) 
			throws DAOException {
		
		Price price = null;
		try (Connection cn = POOL.takeConnection(); 
				PreparedStatement st = cn
						.prepareStatement(SQL_SELECT_BY_PRODUCT_ID)) {
			
			st.setLong(1, productId);
			
			try(ResultSet rs = st.executeQuery()){
				if(rs.next()) {
					price = takeFromResultSet(rs);
				}
			}
		} catch(SQLException e) {
			throw new DAOException(e); 
		}
		return price;
	}

	@Override
	public Price findByProduct(long productId, LocalDateTime date)
			throws DAOException {
		
		Price price = null;
		
		try (Connection cn = POOL.takeConnection(); 
				PreparedStatement st = cn
						.prepareStatement(SQL_SELECT_BY_PRODUCT_ID_AND_DATE)){
			st.setTimestamp(1, Timestamp.valueOf(date));
			st.setLong(2, productId);
			try(ResultSet rs = st.executeQuery()){
				if(rs.next()) {
					price = takeFromResultSet(rs);
				}
			}
		} catch(SQLException e) {
			throw new DAOException(e); 
		}
		return price;
	}
	
	private Price takeFromResultSet(ResultSet resultSet) 
			throws SQLException {
		Price price = null;
		if (!resultSet.isAfterLast()) {
			price = new Price();
			price.setId(resultSet
					.getLong(PriceLogMapping.ID));
			price.setProductId(resultSet
					.getLong(PriceLogMapping.PRODUCT_ID));
			price.setPurchasePrice(resultSet
					.getInt(PriceLogMapping.PURCHASE_PRICE));
			price.setSellingPrice(resultSet
					.getInt(PriceLogMapping.SELLING_PRICE));
			price.setDate(resultSet
					.getTimestamp(PriceLogMapping.DATE).toLocalDateTime());
		}
		
		return price;
	}
}
