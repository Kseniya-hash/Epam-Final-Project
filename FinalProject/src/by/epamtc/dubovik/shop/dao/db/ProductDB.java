package by.epamtc.dubovik.shop.dao.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.epamtc.dubovik.shop.connectionpool.ConnectionPool;
import by.epamtc.dubovik.shop.dao.DAOException;
import by.epamtc.dubovik.shop.dao.ProductDAO;
import by.epamtc.dubovik.shop.dao.db.mapping.ProductMapping;
import by.epamtc.dubovik.shop.entity.Product;

public class ProductDB implements ProductDAO {
	
	private static final String SQL_SELECT_ALL = 
			"SELECT * FROM `dubovik_Shop`.`products` LIMIT ?,?";
	private static final String SQL_CREATE = 
			"INSERT INTO `dubovik_Shop`.`products` "
			+ "(`unq_p_name`, `p_pc_id`, `p_description`, `p_quantity`, "
			+ "`p_weight`, `p_length`, `p_high`, `p_width`) "
			+ "VALUES (?,?,?,?,?,?,?,?)";
	private static final String SQL_DELETE_BY_ID = 
			"DELETE FROM `dubovik_Shop`.`products` WHERE `p_id`=?";
	private static final String SQL_DELETE_BY_ENTITY = 
			"DELETE FROM `dubovik_Shop`.`products` WHERE `p_id` = ? AND "
			+ "`unq_p_name` = ? AND `p_pc_id` = ? AND `p_description` = ? AND `p_quantity` = ? "
			+ "AND `p_weight` = ? AND `p_length` = ? AND `p_high` = ? AND `p_width` = ?";
	private static final String SQL_UPDATE = 
			"UPDATE `dubovik_Shop`.`products` "
			+ "SET `unq_p_name` = ?, `p_pc_id` = ?, `p_description` = ?, `p_quantity` = ?, "
			+ "`p_weight` = ?, `p_length` = ?, `p_high` = ?, `p_width` = ?"
			+ " WHERE `p_id` = ?";
	private static final String SQL_SELECT_BY_ID = 
			"SELECT * FROM `dubovik_Shop`.`products` WHERE `p_id`=?";

	private Product takeFromResultSet(ResultSet resultSet) throws SQLException {
		Product product = null;
		if (!resultSet.isAfterLast()) {
			product = new Product();
			product.setId(resultSet.getInt(ProductMapping.ID));
			product.setName(resultSet.getString(ProductMapping.NAME));
			product.setCategoryId(resultSet.getInt(ProductMapping.CATEGORY_ID));
			product.setDescription(resultSet.getString(ProductMapping.DESCRIPTION));
			product.setQuantity(resultSet.getInt(ProductMapping.QUANTIY));
			product.setWeight(takeInteger(resultSet, ProductMapping.WEIGHT));
			product.setLength(takeInteger(resultSet, ProductMapping.LENGTH));
			product.setHigh(takeInteger(resultSet, ProductMapping.HIGH));
			product.setWidth(takeInteger(resultSet, ProductMapping.WIDTH));
		}
		
		return product;
	}
	
	private Integer takeInteger(ResultSet resultSet, String columnName) throws SQLException {
		Integer integer = resultSet.getInt(columnName);
		if(resultSet.wasNull()) {
			integer = null;
		}
		return integer;
	}
	
	@Override
	public Product findById(int id) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		Product product = null;
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
	public List<Product> findAll(int offset, int count) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		List<Product> products = new ArrayList<>();
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			cn = pool.takeConnection();
			st = cn.prepareStatement(SQL_SELECT_ALL);
			st.setInt(1, offset);
			st.setInt(2, count);
			rs = st.executeQuery();
			while(rs.next()) {
				Product current = takeFromResultSet(rs);
				products.add(current);
			}
		} catch(SQLException e) {
			throw new DAOException(e); 
		} finally {
			pool.closeConnection(cn, st, rs);
		}
		return products;
	}
	
	@Override
	public boolean delete(Product entity) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		boolean flag = false;
		Connection cn = null;
		PreparedStatement st = null;
		
		try {
			cn = pool.takeConnection();
			st = cn.prepareStatement(SQL_DELETE_BY_ENTITY);
			st.setInt(1, entity.getId());
			st.setString(2, entity.getName());
			st.setInt(3, entity.getCategoryId());
			st.setString(4, entity.getDescription());
			st.setInt(5, entity.getQuantity());
			st.setInt(6, entity.getWeight());
			st.setInt(7, entity.getLength());
			st.setInt(8, entity.getHigh());
			st.setInt(9, entity.getWidth());
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
	public boolean create(Product entity) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		boolean flag = false;
		Connection cn = null;
		PreparedStatement st = null;
		
		try {
			cn = pool.takeConnection();
			st = cn.prepareStatement(SQL_CREATE);
			st.setString(1, entity.getName());
			st.setInt(2, entity.getCategoryId());
			st.setString(3, entity.getDescription());
			st.setInt(4, entity.getQuantity());
			st.setInt(5, entity.getWeight());
			st.setInt(6, entity.getLength());
			st.setInt(7, entity.getHigh());
			st.setInt(8, entity.getWidth());
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
	public boolean update(Product entity) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection cn = null;
		PreparedStatement st = null;
		boolean flag = false;
		
		try {
			cn = pool.takeConnection();
			st = cn.prepareStatement(SQL_UPDATE);
			st.setString(1, entity.getName());
			st.setInt(2, entity.getCategoryId());
			st.setString(3, entity.getDescription());
			st.setInt(4, entity.getQuantity());
			st.setObject(5, entity.getWeight(), java.sql.Types.INTEGER);
			st.setObject(6, entity.getLength(), java.sql.Types.INTEGER);
			st.setObject(7, entity.getHigh(), java.sql.Types.INTEGER);
			st.setObject(8, entity.getWidth(), java.sql.Types.INTEGER);
			st.setInt(9, entity.getId());
			int result = st.executeUpdate();
			flag = result > 0;
		} catch(SQLException e) {
			System.out.println(e);
			flag = false;
		} finally {
			pool.closeConnection(cn, st);
		}
		return flag;
	}

}
