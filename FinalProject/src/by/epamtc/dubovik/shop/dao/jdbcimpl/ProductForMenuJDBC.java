package by.epamtc.dubovik.shop.dao.jdbcimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import by.epamtc.dubovik.shop.connectionpool.ConnectionPool;
import by.epamtc.dubovik.shop.dao.DAOException;
import by.epamtc.dubovik.shop.dao.ProductForMenuDAO;
import by.epamtc.dubovik.shop.dao.jdbcimpl.mapping.PriceLogMapping;
import by.epamtc.dubovik.shop.dao.jdbcimpl.mapping.ProductCategoryMapping;
import by.epamtc.dubovik.shop.dao.jdbcimpl.mapping.ProductForMenuMapping;
import by.epamtc.dubovik.shop.dao.jdbcimpl.mapping.ProductMapping;
import by.epamtc.dubovik.shop.entity.ProductForMenu;

public class ProductForMenuJDBC implements ProductForMenuDAO {
	
	private static final String SQL_SELECT_SORT_BY_RATING =
			"SELECT * FROM products_for_menu ORDER BY pm_average_rating DESC LIMIT ?, ?;";
	private static final String SQL_SELECT_SORT_BY_COMMENT_COUNT =
			"SELECT * FROM products_for_menu ORDER BY pm_comment_count DESC LIMIT ?, ?;";
	private static final String SQL_SELECT_SORT_BY_PRICE_INC =
			"SELECT * FROM products_for_menu ORDER BY pl_selling_price LIMIT ?, ?;";
	private static final String SQL_SELECT_SORT_BY_PRICE_DESC =
			"SELECT * FROM products_for_menu ORDER BY pl_selling_price DESC LIMIT ?, ?;";
	private static final String SQL_COUNT_ALL = "SELECT count(p_id) FROM dubovik_shop.products_for_menu";
	
	@Override
	public List<ProductForMenu> findSortedByRating(int offset, int count) throws DAOException {
		return takeList(SQL_SELECT_SORT_BY_RATING, offset, count);
	}
	

	@Override
	public List<ProductForMenu> findSortedByCommentCount(int offset, int count) throws DAOException {
		return takeList(SQL_SELECT_SORT_BY_COMMENT_COUNT, offset, count);
	}

	@Override
	public List<ProductForMenu> findSortedByPriceInc(int offset, int count) throws DAOException {
		return takeList(SQL_SELECT_SORT_BY_PRICE_INC, offset, count);
	}

	@Override
	public List<ProductForMenu> findSortedByPriceDesc(int offset, int count) throws DAOException {
		return takeList(SQL_SELECT_SORT_BY_PRICE_DESC, offset, count);
	}

	private List<ProductForMenu> takeList(String statement, int offset, int count) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		List<ProductForMenu> products = new ArrayList<>();
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			cn = pool.takeConnection();
			st = cn.prepareStatement(statement);
			st.setInt(1, offset);
			st.setInt(2, count);
			rs = st.executeQuery();
			while(rs.next()) {
				ProductForMenu current = takeFromResultSet(rs);
				products.add(current);
			}
		} catch(SQLException e) {
			throw new DAOException(e); 
		} finally {
			pool.closeConnection(cn, st, rs);
		}
		return products;
	}
	
	private ProductForMenu takeFromResultSet(ResultSet resultSet) throws SQLException {
		ProductForMenu product = null;
		if (!resultSet.isAfterLast()) {
			product = new ProductForMenu();
			product.setId(resultSet.getLong(ProductMapping.ID));
			product.setName(resultSet.getString(ProductMapping.NAME));
			product.setProductCategory(resultSet.getString(ProductCategoryMapping.NAME));
			product.setDescription(resultSet.getString(ProductMapping.DESCRIPTION));
			product.setCommentCount(resultSet.getInt(ProductForMenuMapping.COMMENT));
			product.setRating(resultSet.getInt(ProductForMenuMapping.RATING));
			product.setSellingPrice(resultSet.getInt(PriceLogMapping.SELLING_PRICE));
			product.setPhotoPath(resultSet.getString(ProductMapping.PHOTO_PATH));
		}
		return product;
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
			System.out.println(commentCount);
		} catch(SQLException e) {
			throw new DAOException(e); 
		} finally {
			pool.closeConnection(cn, st, rs);
		}
		return commentCount;
	}

}