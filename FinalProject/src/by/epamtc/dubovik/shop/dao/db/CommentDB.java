package by.epamtc.dubovik.shop.dao.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.epamtc.dubovik.shop.connectionpool.ConnectionPool;
import by.epamtc.dubovik.shop.dao.CommentDAO;
import by.epamtc.dubovik.shop.dao.DAOException;
import by.epamtc.dubovik.shop.dao.db.mapping.CommentMapping;
import by.epamtc.dubovik.shop.dao.db.mapping.UserMapping;
import by.epamtc.dubovik.shop.entity.Comment;

public class CommentDB implements CommentDAO {

	private static final String SQL_SELECT_ALL = 
			"SELECT `c_id`, `c_u_id`, `unq_u_login`, `c_p_id`, `c_text`, `c_rating` "
			+ "FROM `dubovik_shop`.`comments` c " + 
			"INNER JOIN `dubovik_shop`.`users` u ON c.c_u_id = u.u_id LIMIT ?,?";
	private static final String SQL_CREATE = 
			"INSERT INTO `dubovik_Shop`.`comments` (`c_u_id`, `c_p_id`, `c_text`, `c_rating`) "
			+ "VALUES (?,?,?,?)";
	private static final String SQL_DELETE_BY_ID = 
			"DELETE FROM `dubovik_Shop`.`comments` WHERE `c_id`=?";
	private static final String SQL_DELETE_BY_ENTITY = 
			"DELETE FROM `dubovik_Shop`.`comments` WHERE `c_id` = ? AND "
			+ "`c_u_id` = ? AND `c_p_id` = ? AND `c_text` = ? AND `c_rating` = ?";
	private static final String SQL_UPDATE = 
			"UPDATE `dubovik_Shop`.`comments` SET `c_u_id` = ?, `c_p_id` = ?, `c_text` = ?, `c_rating` = ?"
			+ " WHERE `c_id` = ?";
	
	private static final String SQL_SELECT_BY_ID = 
			"SELECT `c_id`, `c_u_id`, `unq_u_login`, `c_p_id`, `c_text`, `c_rating` "
					+ "FROM `dubovik_shop`.`comments` c " + 
					"INNER JOIN `dubovik_shop`.`users` u ON c.c_u_id = u.u_id WHERE `c_id`=?";
	private static final String SQL_SELECT_BY_PRODUCTID = 
			"SELECT `c_id`, `c_u_id`, `unq_u_login`, `c_p_id`, `c_text`, `c_rating` "
					+ "FROM `dubovik_shop`.`comments` c " + 
					"INNER JOIN `dubovik_shop`.`users` u ON c.c_u_id = u.u_id WHERE `c_p_id`=?";
	private static final String SQL_SELECT_BY_USERID = 
			"SELECT `c_id`, `c_u_id`, `unq_u_login`, `c_p_id`, `c_text`, `c_rating` "
					+ "FROM `dubovik_shop`.`comments` c " + 
					"INNER JOIN `dubovik_shop`.`users` u ON c.c_u_id = u.u_id WHERE `c_u_id`=?";
	private static final String SQL_SELECT_COUNT_BY_PRODUCTID = 
			"SELECT count(`c_id`) FROM `dubovik_Shop`.`comments` WHERE `c_p_id`=?";
	private static final String SQL_SELECT_AVG_RATING_BY_PRODUCTID = 
			"SELECT avg(`c_rating`) FROM `dubovik_Shop`.`comments` WHERE `c_p_id`=?";
	
	private Comment takeFromResultSet(ResultSet resultSet) throws SQLException {
		Comment comment = null;
		
		if (!resultSet.isAfterLast()) {
			comment = new Comment();
			comment.setId(resultSet.getInt(CommentMapping.ID));
			comment.setUserId(resultSet.getInt(CommentMapping.USER_ID));
			comment.setUserLogin(resultSet.getString(UserMapping.LOGIN));
			comment.setProductId(resultSet.getInt(CommentMapping.PRODUCT_ID));
			comment.setText(resultSet.getString(CommentMapping.TEXT));
			comment.setRating(resultSet.getInt(CommentMapping.RATING));
			if(resultSet.wasNull()) {
				comment.setRating(null);
			}
		}
		
		return comment;
	}
	
	@Override
	public List<Comment> findAll(int offset, int count) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		List<Comment> comments= new ArrayList<>();
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
				Comment currentComment = takeFromResultSet(rs);
				comments.add(currentComment);
			}
		} catch(SQLException e) {
			throw new DAOException(e); 
		} finally {
			pool.closeConnection(cn, st, rs);
		}
		return comments;
	}

	@Override
	public boolean delete(Comment entity) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		boolean flag = false;
		Connection cn = null;
		PreparedStatement st = null;
		
		try {
			cn = pool.takeConnection();
			st = cn.prepareStatement(SQL_DELETE_BY_ENTITY);
			st.setInt(1, entity.getId());
			st.setInt(2, entity.getUserId());
			st.setInt(3, entity.getProductId());
			st.setString(4, entity.getText());
			st.setObject(5, entity.getRating(), java.sql.Types.INTEGER);
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
	public boolean create(Comment entity) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		boolean flag = false;
		Connection cn = null;
		PreparedStatement st = null;
		
		try {
			cn = pool.takeConnection();
			st = cn.prepareStatement(SQL_CREATE);
			st.setInt(1, entity.getUserId());
			st.setInt(2, entity.getProductId());
			st.setString(3, entity.getText());
			st.setObject(4, entity.getRating(), java.sql.Types.INTEGER);
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
	public boolean update(Comment entity) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection cn = null;
		PreparedStatement st = null;
		boolean flag = false;
		
		try {
			cn = pool.takeConnection();
			st = cn.prepareStatement(SQL_UPDATE);
			st.setInt(1, entity.getUserId());
			st.setInt(2, entity.getProductId());
			st.setString(3, entity.getText());
			st.setObject(4, entity.getRating(), java.sql.Types.INTEGER);
			st.setInt(5, entity.getId());
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
	public Comment findById(int id) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		Comment comment = null;
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			cn = pool.takeConnection();
			st = cn.prepareStatement(SQL_SELECT_BY_ID);
			st.setInt(1, id);
			rs = st.executeQuery();
			if(rs.next()) {
				comment = takeFromResultSet(rs);
			}
		} catch(SQLException e) {
			throw new DAOException(e); 
		} finally {
			pool.closeConnection(cn, st, rs);
		}
		return comment;
	}

	@Override
	public List<Comment> findByProduct(int productId) 
			throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		List<Comment> comments= new ArrayList<>();
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			cn = pool.takeConnection();
			st = cn.prepareStatement(SQL_SELECT_BY_PRODUCTID);
			st.setInt(1, productId);
			rs = st.executeQuery();
			while(rs.next()) {
				Comment currentComment = takeFromResultSet(rs);
				comments.add(currentComment);
			}
		} catch(SQLException e) {
			throw new DAOException(e); 
		} finally {
			pool.closeConnection(cn, st, rs);
		}
		return comments;
	}

	@Override
	public List<Comment> findByUser(int userId) 
			throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		List<Comment> comments= new ArrayList<>();
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			cn = pool.takeConnection();
			st = cn.prepareStatement(SQL_SELECT_BY_USERID);
			st.setInt(1, userId);
			rs = st.executeQuery();
			while(rs.next()) {
				Comment currentComment = takeFromResultSet(rs);
				comments.add(currentComment);
			}
		} catch(SQLException e) {
			throw new DAOException(e); 
		} finally {
			pool.closeConnection(cn, st, rs);
		}
		return comments;
	}

	@Override
	public int calculateCommentCount(int productId) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		int commentCount = 0;
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			cn = pool.takeConnection();
			st = cn.prepareStatement(SQL_SELECT_COUNT_BY_PRODUCTID);
			st.setInt(1, productId);
			rs = st.executeQuery();
			if(rs.next()) {
				commentCount = rs.getInt(1);
			}
		} catch(SQLException e) {
			throw new DAOException(e); 
		} finally {
			pool.closeConnection(cn, st, rs);
		}
		return commentCount;
	}

	@Override
	public int calculateAverageRating(int productId) throws DAOException {
		ConnectionPool pool = ConnectionPool.getInstance();
		int avgRating = 0;
		Connection cn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			cn = pool.takeConnection();
			st = cn.prepareStatement(SQL_SELECT_AVG_RATING_BY_PRODUCTID);
			st.setInt(1, productId);
			rs = st.executeQuery();
			if(rs.next()) {
				avgRating = rs.getInt(1);
			}
		} catch(SQLException e) {
			throw new DAOException(e); 
		} finally {
			pool.closeConnection(cn, st, rs);
		}
		return avgRating;
	}
}
