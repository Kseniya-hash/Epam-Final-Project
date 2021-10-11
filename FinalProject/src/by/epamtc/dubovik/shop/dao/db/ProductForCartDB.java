package by.epamtc.dubovik.shop.dao.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import by.epamtc.dubovik.shop.connectionpool.ConnectionPool;
import by.epamtc.dubovik.shop.dao.DAOException;
import by.epamtc.dubovik.shop.dao.ProductForCartDAO;
import by.epamtc.dubovik.shop.dao.db.mapping.PriceLogMapping;
import by.epamtc.dubovik.shop.dao.db.mapping.ProductCategoryMapping;
import by.epamtc.dubovik.shop.dao.db.mapping.ProductForMenuMapping;
import by.epamtc.dubovik.shop.dao.db.mapping.ProductMapping;
import by.epamtc.dubovik.shop.entity.ProductForCart;

public class ProductForCartDB implements ProductForCartDAO {
	
	private static final String SQL_SELECT_BY_ID =
			"SELECT `p_id`, `unq_p_name`, `unq_pc_name`, `pl_selling_price`, `p_quantity`, `pm_average_rating` "
			+ "FROM `dubovik_shop`.`products_for_menu` WHERE `p_id` = ?;";

	private ProductForCart takeFromResultSet(ResultSet resultSet) throws SQLException {
		ProductForCart product = null;
		if (!resultSet.isAfterLast()) {
			product = new ProductForCart();
			product.setId(resultSet.getInt(ProductMapping.ID));
			product.setName(resultSet.getString(ProductMapping.NAME));
			product.setProductCategory(resultSet.getString(ProductCategoryMapping.NAME));
			product.setRating(resultSet.getInt(ProductForMenuMapping.RATING));
			product.setSellingPrice(resultSet.getInt(PriceLogMapping.SELLING_PRICE));
			product.setQuantity(resultSet.getInt(ProductMapping.QUANTIY));
		}
		return product;
	}

	@Override
	public ProductForCart findById(int id) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		ProductForCart product = null;
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			cn = pool.takeConnection();
			st = cn.prepareStatement(SQL_SELECT_BY_ID);
			st.setInt(1, id);
			rs = st.executeQuery();
			if(rs.next()) {
				product = takeFromResultSet(rs);
			}
		} catch(SQLException e) {
			throw new DAOException(e); 
		} finally {
			pool.closeConnection(cn, st, rs);
		}
		return product;
	}
	
}
