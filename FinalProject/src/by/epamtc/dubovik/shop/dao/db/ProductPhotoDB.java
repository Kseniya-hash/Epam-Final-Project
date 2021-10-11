package by.epamtc.dubovik.shop.dao.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import by.epamtc.dubovik.shop.dao.DAOException;
import by.epamtc.dubovik.shop.dao.ProductPhotoDAO;
import by.epamtc.dubovik.shop.dao.db.mapping.ProductPhotoMapping;
import by.epamtc.dubovik.shop.entity.ProductPhoto;
import by.epamtc.dubovik.shop.entity.User;

public class ProductPhotoDB implements ProductPhotoDAO {
	
	private static final String SQL_SELECT_ALL = 
			"SELECT * FROM `dubovik_Shop`.`product_photos` LIMIT ?,?";
	private static final String SQL_SELECT_BY_ID = 
			"SELECT * FROM `dubovik_Shop`.`product_photos` WHERE `u_id`=?";
	private static final String SQL_CREATE = 
			"INSERT INTO `dubovik_Shop`.`product_photos` "
			+ "(`unq_u_phone`, `u_password`, `u_name`, `u_e-mail`, `u_is_blacklisted`, `u_r_id`) "
			+ "VALUES (?, ?, ?, ?, ?, ?)";
	private static final String SQL_DELETE_BY_ID = 
			"DELETE FROM `dubovik_Shop`.`product_photos` WHERE `u_id`=?";
	private static final String SQL_DELETE_BY_ENTITY = 
			"DELETE FROM `dubovik_Shop`.`product_photos` "
			+ "WHERE `u_id` = ? AND `unq_u_phone` = ? AND `u_password` = ? AND `u_name` = ? "
			+ "AND `u_e-mail` = ? AND `u_is_blacklisted` = ? AND `u_r_id` = ?";
	private static final String SQL_UPDATE = 
			"UPDATE `dubovik_Shop`.`product_photos` SET `unq_u_phone` = ?, `u_password` = ?"
			+ ", `u_name` = ?, `u_e-mail` = ?, `u_is_blacklisted` = ?, `u_r_id` = ? "
			+ "WHERE `u_id` = ?";
	
	private static final String SQL_SELECT_BY_PRODUCTID = 
			"SELECT * FROM `dubovik_Shop`.`product_photos` WHERE  LIMIT ?,?";
	
	private ProductPhoto takeFromResultSet(ResultSet resultSet) throws SQLException {
		ProductPhoto photo = null;
		if (!resultSet.isAfterLast()) {
			photo = new ProductPhoto();
			photo.setId(resultSet.getInt(ProductPhotoMapping.ID_COLUMN));
			photo.setProductId(resultSet.getInt(ProductPhotoMapping.PRODUCT_ID_COLUMN));
			photo.setPhotoPath(resultSet.getString(ProductPhotoMapping.PATH_COLUMN));
		}
		return photo;
	}

	@Override
	public ProductPhoto findById(int id) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(int id) throws DAOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<ProductPhoto> findAll(int offset, int count) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(ProductPhoto entity) throws DAOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean create(ProductPhoto entity) throws DAOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(ProductPhoto entity) throws DAOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<ProductPhoto> findByProductId(int productId, int offset, int count) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

}
