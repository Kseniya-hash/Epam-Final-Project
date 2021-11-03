package by.epamtc.dubovik.shop.dao.jdbcimpl;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import by.epamtc.dubovik.shop.dao.DAOException;
import by.epamtc.dubovik.shop.dao.ProductDAO;
import by.epamtc.dubovik.shop.dao.connectionpool.ConnectionPool;
import by.epamtc.dubovik.shop.dao.jdbcimpl.mapping.ProductMapping;
import by.epamtc.dubovik.shop.entity.Product;

public class ProductJDBC implements ProductDAO {
	
	private static final String SQL_SELECT_ALL = 
			"SELECT * FROM products LIMIT ?,?";
	private static final String SQL_CREATE = 
			"INSERT INTO products "
			+ "(unq_p_name, p_pc_id, p_description, p_quantity, "
			+ "p_weight, p_length, p_high, p_width, p_photopath) "
			+ "VALUES (?,?,?,?,?,?,?,?,?)";
	private static final String SQL_UPDATE = 
			"UPDATE products "
			+ "SET unq_p_name = ?, p_pc_id = ?, p_description = ?, p_quantity = ?, "
			+ "p_weight = ?, p_length = ?, p_high = ?, p_width = ?, p_photopath = ?"
			+ " WHERE p_id = ?";
	private static final String SQL_SELECT_BY_ID = 
			"SELECT * FROM products WHERE p_id=?";
	private static final String SQL_SELECT_BY_NAME = 
			"SELECT * FROM products WHERE unq_p_name=?";
	
	private static final ConnectionPool POOL = ConnectionPool.getInstance();
	
	@Override
	public Product findById(long id) throws DAOException {
		
		Product product = null;
		
		try (Connection cn = POOL.takeConnection(); 
				PreparedStatement st = cn.prepareStatement(SQL_SELECT_BY_ID)){
			st.setLong(1, id);
			try(ResultSet rs = st.executeQuery()){
				if(rs.next()) {
					product = takeFromResultSet(rs);
				}
			}
		} catch(SQLException e) {
			throw new DAOException(e); 
		}
		return product;
	}

	@Override
	public List<Product> findAll(int offset, int count) throws DAOException {
		
		List<Product> products = new ArrayList<>();
		
		try (Connection cn = POOL.takeConnection(); 
				PreparedStatement st = cn.prepareStatement(SQL_SELECT_ALL)){
			st.setInt(1, offset);
			st.setInt(2, count);
			try(ResultSet rs = st.executeQuery()){
				while(rs.next()) {
					Product current = takeFromResultSet(rs);
					products.add(current);
				}
			}
		} catch(SQLException e) {
			throw new DAOException(e); 
		}
		return products;
	}

	@Override
	public boolean create(Product entity) throws DAOException {
		
		boolean flag = false;
		
		try (Connection cn = POOL.takeConnection(); 
				PreparedStatement st = cn.prepareStatement(SQL_CREATE)){
			
			uploadPhoto(entity);
			
			st.setString(1, entity.getName());
			st.setLong(2, entity.getCategoryId());
			st.setString(3, entity.getDescription());
			st.setInt(4, entity.getQuantity());
			st.setObject(5, entity.getWeight(), java.sql.Types.INTEGER);
			st.setObject(6, entity.getLength(), java.sql.Types.INTEGER);
			st.setObject(7, entity.getHigh(), java.sql.Types.INTEGER);
			st.setObject(8, entity.getWidth(), java.sql.Types.INTEGER);
			st.setString(9, entity.getPhotoPath());
			int result = st.executeUpdate();
			flag = result > 0;
		} catch(SQLException e) {
			throw new DAOException(e);
		}
		return flag;
	}
	
	@Override
	public boolean update(Product entity) throws DAOException {
		
		boolean flag = false;
		
		try (Connection cn = POOL.takeConnection(); 
				PreparedStatement st = cn.prepareStatement(SQL_UPDATE)){
			
			uploadPhoto(entity);
			
			st.setString(1, entity.getName());
			st.setLong(2, entity.getCategoryId());
			st.setString(3, entity.getDescription());
			st.setInt(4, entity.getQuantity());
			st.setObject(5, entity.getWeight(), java.sql.Types.INTEGER);
			st.setObject(6, entity.getLength(), java.sql.Types.INTEGER);
			st.setObject(7, entity.getHigh(), java.sql.Types.INTEGER);
			st.setObject(8, entity.getWidth(), java.sql.Types.INTEGER);
			st.setString(9, entity.getPhotoPath());
			st.setLong(10, entity.getId());
			int result = st.executeUpdate();
			flag = result > 0;
		} catch(SQLException e) {
			throw new DAOException(e);
		}
		return flag;
	}

	@Override
	public Product findByName(String name) throws DAOException {
		
		Product product = null;
		
		try (Connection cn = POOL.takeConnection(); 
				PreparedStatement st = cn
						.prepareStatement(SQL_SELECT_BY_NAME)){
			st.setString(1, name);
			try(ResultSet rs = st.executeQuery()){
				if(rs.next()) {
					product = takeFromResultSet(rs);
				}
			}
		} catch(SQLException e) {
			throw new DAOException(e);
		}
		return product;
	}
	
	private boolean uploadPhoto(Product product) throws DAOException {
		boolean isUploaded = false;
    	
		String photoPath = product.getPhotoPath();
		
    	if(photoPath != null && photoPath.length() != 0 && 
    			product.getPhotoContent() != null) {
    		File targetFile = new File(photoPath);
    		try {
				FileUtils.copyInputStreamToFile(product.getPhotoContent(), targetFile);
			} catch (IOException e) {
				throw new DAOException(e);
			} finally {
				try {
					product.getPhotoContent().close();
				} catch (IOException e) {
					throw new DAOException(e);
				}
			}
    		String photoName = photoPath.substring(photoPath.lastIndexOf("\\"));
    		product.setPhotoPath(photoName);
    		isUploaded = true;
    	}
		return isUploaded;
	}
	
	private Product takeFromResultSet(ResultSet resultSet) 
			throws SQLException {
		Product product = null;
		if (!resultSet.isAfterLast()) {
			product = new Product();
			product.setId(
					resultSet.getLong(ProductMapping.ID));
			product.setName(
					resultSet.getString(ProductMapping.NAME));
			product.setCategoryId(
					resultSet.getLong(ProductMapping.CATEGORY_ID));
			product.setDescription(
					resultSet.getString(ProductMapping.DESCRIPTION));
			product.setQuantity(
					resultSet.getInt(ProductMapping.QUANTIY));
			product.setWeight(
					takeInteger(resultSet, ProductMapping.WEIGHT));
			product.setLength(
					takeInteger(resultSet, ProductMapping.LENGTH));
			product.setHigh(
					takeInteger(resultSet, ProductMapping.HIGH));
			product.setWidth(
					takeInteger(resultSet, ProductMapping.WIDTH));
			product.setPhotoPath(
					resultSet.getString(ProductMapping.PHOTO_PATH));
		}
		
		return product;
	}
	
	private Integer takeInteger(ResultSet resultSet, String columnName)
			throws SQLException {
		Integer integer = resultSet.getInt(columnName);
		if(resultSet.wasNull()) {
			integer = null;
		}
		return integer;
	}
}
