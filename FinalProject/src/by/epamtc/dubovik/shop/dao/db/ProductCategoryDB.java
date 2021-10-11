package by.epamtc.dubovik.shop.dao.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.epamtc.dubovik.shop.connectionpool.ConnectionPool;
import by.epamtc.dubovik.shop.dao.DAOException;
import by.epamtc.dubovik.shop.dao.ProductCategoryDAO;
import by.epamtc.dubovik.shop.dao.db.mapping.ProductCategoryMapping;
import by.epamtc.dubovik.shop.entity.ProductCategory;

public class ProductCategoryDB implements ProductCategoryDAO {
	private static final String SQL_SELECT_ALL_BY_PAGES = 
			"SELECT * FROM `dubovik_Shop`.`product_categories` LIMIT ?,?";
	private static final String SQL_SELECT_BY_ID = 
			"SELECT * FROM `dubovik_Shop`.`product_categories` WHERE `pc_id`=?";
	private static final String SQL_CREATE = 
			"INSERT INTO `dubovik_Shop`.`product_categories` (`unq_pc_name`, `pc_description`) VALUES (?, ?)";
	private static final String SQL_DELETE_BY_ID = 
			"DELETE FROM `dubovik_Shop`.`product_categories` WHERE `pc_id`=?";
	private static final String SQL_DELETE_BY_ENTITY = 
			"DELETE FROM `dubovik_Shop`.`product_categories` "
			+ "WHERE `pc_id` = ? AND `unq_pc_name` = ? AND `pc_description` = ?";
	private static final String SQL_UPDATE = 
			"UPDATE `dubovik_Shop`.`product_categories` "
			+ "SET `unq_pc_name` = ?, `pc_description` = ? WHERE `pc_id` = ?";
	
	private static final String SQL_SELECT_BY_NAME = 
			"SELECT * FROM `dubovik_Shop`.`product_categories` WHERE `unq_pc_name`=?";
	private static final String SQL_SELECT_ALL = 
			"SELECT * FROM `dubovik_Shop`.`product_categories`";
	
	private ProductCategory takeFromResultSet(ResultSet resultSet) throws SQLException {
		ProductCategory category = null;
		
		if (!resultSet.isAfterLast()) {
			category = new ProductCategory();
			category.setId(resultSet.getInt(ProductCategoryMapping.ID));
			category.setName(resultSet.getString(ProductCategoryMapping.NAME));
			category.setDescription(resultSet.getString(ProductCategoryMapping.DESCRIPTION));
		}
		
		return category;
	}

	@Override
	public ProductCategory findById(int id) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		ProductCategory productCategory = null;
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			cn = pool.takeConnection();
			st = cn.prepareStatement(SQL_SELECT_BY_ID);
			st.setInt(1, id);
			rs = st.executeQuery();
			if(rs.next()) {
				productCategory = takeFromResultSet(rs);
			}
		} catch(SQLException e) {
			throw new DAOException(e); 
		} finally {
			pool.closeConnection(cn, st, rs);
		}
		return productCategory;
	}

	@Override
	public boolean delete(int id) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		boolean flag = false;
		Connection cn = null;
		PreparedStatement st = null;
		
		try {
			cn = pool.takeConnection();
			st = cn.prepareStatement(SQL_DELETE_BY_ID);
			st.setInt(1, id);
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
	public List<ProductCategory> findAll(int offset, int count) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		List<ProductCategory> productCategories = new ArrayList<>();
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			cn = pool.takeConnection();
			st = cn.prepareStatement(SQL_SELECT_ALL_BY_PAGES);
			st.setInt(1, offset);
			st.setInt(2, count);
			rs = st.executeQuery();
			while(rs.next()) {
				ProductCategory current = takeFromResultSet(rs);
				productCategories.add(current);
			}
		} catch(SQLException e) {
			throw new DAOException(e); 
		} finally {
			pool.closeConnection(cn, st, rs);
		}
		return productCategories;
	}

	@Override
	public boolean delete(ProductCategory entity) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		boolean flag = false;
		Connection cn = null;
		PreparedStatement st = null;
		
		try {
			cn = pool.takeConnection();
			st = cn.prepareStatement(SQL_DELETE_BY_ENTITY);
			st.setInt(1, entity.getId());
			st.setString(2, entity.getName());
			st.setString(3, entity.getDescription());
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
	public boolean create(ProductCategory entity) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		boolean flag = false;
		Connection cn = null;
		PreparedStatement st = null;
		
		try {
			cn = pool.takeConnection();
			st = cn.prepareStatement(SQL_CREATE);
			st.setString(1, entity.getName());
			st.setString(2, entity.getDescription());
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
	public boolean update(ProductCategory entity) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection cn = null;
		PreparedStatement st = null;
		boolean flag = false;
		
		try {
			cn = pool.takeConnection();
			st = cn.prepareStatement(SQL_UPDATE);
			st.setString(1, entity.getName());
			st.setString(2, entity.getDescription());
			st.setInt(9, entity.getId());
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
	public ProductCategory findByName(String name) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		ProductCategory productCategory = null;
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			cn = pool.takeConnection();
			st = cn.prepareStatement(SQL_SELECT_BY_NAME);
			st.setString(1, name);
			rs = st.executeQuery();
			if(rs.next()) {
				productCategory = takeFromResultSet(rs);
			}
		} catch(SQLException e) {
			throw new DAOException(e); 
		} finally {
			pool.closeConnection(cn, st, rs);
		}
		return productCategory;
	}
	
	@Override
	public List<ProductCategory> findAll() throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		List<ProductCategory> productCategories = new ArrayList<>();
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			cn = pool.takeConnection();
			st = cn.prepareStatement(SQL_SELECT_ALL);
			rs = st.executeQuery();
			while(rs.next()) {
				ProductCategory current = takeFromResultSet(rs);
				productCategories.add(current);
			}
		} catch(SQLException e) {
			throw new DAOException(e); 
		} finally {
			pool.closeConnection(cn, st, rs);
		}
		return productCategories;
	}
}
