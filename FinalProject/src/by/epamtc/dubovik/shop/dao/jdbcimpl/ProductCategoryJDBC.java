package by.epamtc.dubovik.shop.dao.jdbcimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import by.epamtc.dubovik.shop.dao.DAOException;
import by.epamtc.dubovik.shop.dao.ProductCategoryDAO;
import by.epamtc.dubovik.shop.dao.connectionpool.ConnectionPool;
import by.epamtc.dubovik.shop.dao.jdbcimpl.mapping.ProductCategoryMapping;
import by.epamtc.dubovik.shop.entity.ProductCategory;

public class ProductCategoryJDBC implements ProductCategoryDAO {
	private static final String SQL_SELECT_ALL_BY_PAGES = 
			"SELECT * FROM product_categories LIMIT ?,?";
	private static final String SQL_SELECT_BY_ID = 
			"SELECT * FROM product_categories WHERE pc_id=?";
	private static final String SQL_CREATE = 
			"INSERT INTO product_categories (unq_pc_name) VALUES (?)";
	private static final String SQL_UPDATE = 
			"UPDATE product_categories "
			+ "SET unq_pc_name = ? WHERE pc_id = ?";
	
	private static final String SQL_SELECT_BY_NAME = 
			"SELECT * FROM product_categories WHERE unq_pc_name=?";
	private static final String SQL_SELECT_ALL = 
			"SELECT * FROM product_categories";
	
	private static final ConnectionPool POOL = ConnectionPool.getInstance();

	@Override
	public ProductCategory findById(long id) throws DAOException {
		
		ProductCategory productCategory = null;
		
		try (Connection cn = POOL.takeConnection(); 
				PreparedStatement st = cn
						.prepareStatement(SQL_SELECT_BY_ID)){
			st.setLong(1, id);
			try(ResultSet rs = st.executeQuery()){
				if(rs.next()) {
					productCategory = takeFromResultSet(rs);
				}
			}
		} catch(SQLException e) {
			throw new DAOException(e); 
		}
		return productCategory;
	}

	@Override
	public List<ProductCategory> findAll(int offset, int count) 
			throws DAOException {
		
		List<ProductCategory> productCategories = new ArrayList<>();
		
		try (Connection cn = POOL.takeConnection(); 
				PreparedStatement st = cn
						.prepareStatement(SQL_SELECT_ALL_BY_PAGES)){
			st.setInt(1, offset);
			st.setInt(2, count);
			try(ResultSet rs = st.executeQuery()){
				while(rs.next()) {
					ProductCategory current = takeFromResultSet(rs);
					productCategories.add(current);
				}
			}
		} catch(SQLException e) {
			throw new DAOException(e); 
		}
		return productCategories;
	}

	@Override
	public boolean create(ProductCategory entity) throws DAOException {
		
		boolean flag = false;
		
		try (Connection cn = POOL.takeConnection(); 
				PreparedStatement st = cn
						.prepareStatement(SQL_CREATE)){
			st.setString(1, entity.getName());
			int result = st.executeUpdate();
			flag = result > 0;
		} catch(SQLException e) {
			throw new DAOException(e);
		}
		return flag;
	}

	@Override
	public boolean update(ProductCategory entity) 
			throws DAOException {
		
		boolean flag = false;
		
		try (Connection cn = POOL.takeConnection(); 
				PreparedStatement st = cn
						.prepareStatement(SQL_UPDATE)){
			st.setString(1, entity.getName());
			st.setLong(9, entity.getId());
			int result = st.executeUpdate();
			flag = result > 0;
		} catch(SQLException e) {
			throw new DAOException(e);
		}
		return flag;
	}

	@Override
	public ProductCategory findByName(String name) 
			throws DAOException {
		
		ProductCategory productCategory = null;
		
		try (Connection cn = POOL.takeConnection(); 
				PreparedStatement st = cn
						.prepareStatement(SQL_SELECT_BY_NAME)){
			
			st.setString(1, name);
			try(ResultSet rs = st.executeQuery()){
				if(rs.next()) {
					productCategory = takeFromResultSet(rs);
				}
			}
		} catch(SQLException e) {
			throw new DAOException(e); 
		}
		return productCategory;
	}
	
	@Override
	public List<ProductCategory> findAll() throws DAOException {
		
		List<ProductCategory> productCategories = new ArrayList<>();
		
		try (Connection cn = POOL.takeConnection(); 
				Statement st = cn.createStatement();
				ResultSet rs = st.executeQuery(SQL_SELECT_ALL)){
			while(rs.next()) {
				ProductCategory current = takeFromResultSet(rs);
				productCategories.add(current);
			}
		} catch(SQLException e) {
			throw new DAOException(e); 
		}
		return productCategories;
	}
	
	private ProductCategory takeFromResultSet(ResultSet resultSet) 
			throws SQLException {
		ProductCategory category = null;
		
		if (!resultSet.isAfterLast()) {
			category = new ProductCategory();
			category.setId(resultSet
					.getLong(ProductCategoryMapping.ID));
			category.setName(resultSet
					.getString(ProductCategoryMapping.NAME));
		}
		
		return category;
	}
}
