package by.epamtc.dubovik.shop.dao.jdbcimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.epamtc.dubovik.shop.dao.CommentDAO;
import by.epamtc.dubovik.shop.dao.DAOException;
import by.epamtc.dubovik.shop.dao.connectionpool.ConnectionPool;
import by.epamtc.dubovik.shop.dao.jdbcimpl.mapping.CommentMapping;
import by.epamtc.dubovik.shop.dao.jdbcimpl.mapping.UserMapping;
import by.epamtc.dubovik.shop.entity.Comment;

public class CommentJDBC implements CommentDAO {

	private static final String SQL_SELECT_ALL = 
			"SELECT c_id, c_u_id, unq_u_login, c_p_id, c_text, c_rating "
			+ "FROM comments c " + 
			"INNER JOIN users u ON c.c_u_id = u.u_id LIMIT ?,?";
	private static final String SQL_CREATE = 
			"INSERT INTO comments (c_u_id, c_p_id, c_text, c_rating) "
			+ "VALUES (?,?,?,?)";
	private static final String SQL_UPDATE = 
			"UPDATE comments SET c_u_id = ?, c_p_id = ?, c_text = ?, c_rating = ?"
			+ " WHERE c_id = ?";
	
	private static final String SQL_SELECT_BY_ID = 
			"SELECT c_id, c_u_id, unq_u_login, c_p_id, c_text, c_rating "
					+ "FROM comments c " + 
					"INNER JOIN users u ON c.c_u_id = u.u_id WHERE c_id=?";
	private static final String SQL_SELECT_BY_PRODUCTID = 
			"SELECT c_id, c_u_id, unq_u_login, c_p_id, c_text, c_rating "
					+ "FROM comments c " + 
					"INNER JOIN users u ON c.c_u_id = u.u_id WHERE c_p_id=?";
	
	private static final ConnectionPool POOL = ConnectionPool.getInstance();
	
	@Override
	public List<Comment> findAll(int offset, int count) throws DAOException {
		
		List<Comment> comments= new ArrayList<>();
		
		try (Connection cn = POOL.takeConnection(); 
			PreparedStatement st = cn.prepareStatement(SQL_SELECT_ALL)){
			st.setInt(1, offset);
			st.setInt(2, count);
			try(ResultSet rs = st.executeQuery()){
				while(rs.next()) {
					Comment currentComment = takeFromResultSet(rs);
					comments.add(currentComment);
				}
			}
		} catch(SQLException e) {
			throw new DAOException(e); 
		}
		return comments;
	}
	
	private Comment takeFromResultSet(ResultSet resultSet) 
			throws SQLException {
		Comment comment = null;
		
		if (!resultSet.isAfterLast()) {
			comment = new Comment();
			comment.setId(resultSet.getLong(CommentMapping.ID));
			comment.setUserId(resultSet.getLong(CommentMapping.USER_ID));
			comment.setUserLogin(resultSet.getString(UserMapping.LOGIN));
			comment.setProductId(resultSet.getLong(CommentMapping.PRODUCT_ID));
			comment.setText(resultSet.getString(CommentMapping.TEXT));
			comment.setRating(resultSet.getInt(CommentMapping.RATING));
			if(resultSet.wasNull()) {
				comment.setRating(null);
			}
		}
		
		return comment;
	}

	@Override
	public boolean create(Comment entity) throws DAOException {
		
		boolean flag = false;
		
		try (Connection cn = POOL.takeConnection(); 
			PreparedStatement st = cn.prepareStatement(SQL_CREATE)){
			st.setLong(1, entity.getUserId());
			st.setLong(2, entity.getProductId());
			st.setString(3, entity.getText());
			st.setObject(4, entity.getRating(), java.sql.Types.INTEGER);
			flag = st.executeUpdate() > 0;
		} catch(SQLException e) {
			throw new DAOException(e);
		}
		return flag;
	}

	@Override
	public boolean update(Comment entity) throws DAOException {
		
		boolean flag = false;
		try (Connection cn = POOL.takeConnection(); 
				PreparedStatement st = cn.prepareStatement(SQL_UPDATE)){
		
			st.setLong(1, entity.getUserId());
			st.setLong(2, entity.getProductId());
			st.setString(3, entity.getText());
			st.setObject(4, entity.getRating(), java.sql.Types.INTEGER);
			st.setLong(5, entity.getId());
			flag = st.executeUpdate() > 0;
		} catch(SQLException e) {
			throw new DAOException(e);
		}
		return flag;
	}

	@Override
	public Comment findById(long id) throws DAOException {
		
		Comment comment = null;
		
		try (Connection cn = POOL.takeConnection(); 
				PreparedStatement st = cn.prepareStatement(SQL_SELECT_BY_ID)){
		
			st.setLong(1, id);
			
			try(ResultSet rs = st.executeQuery()){
				if(rs.next()) {
					comment = takeFromResultSet(rs);
				}
			}
		} catch(SQLException e) {
			throw new DAOException(e); 
		}
		return comment;
	}

	@Override
	public List<Comment> findByProduct(long productId) 
			throws DAOException {
		
		List<Comment> comments= new ArrayList<>();
		
		try (Connection cn = POOL.takeConnection(); 
				PreparedStatement st = cn.prepareStatement(SQL_SELECT_BY_PRODUCTID)){
		
			st.setLong(1, productId);
			try(ResultSet rs = st.executeQuery()){
				while(rs.next()) {
					Comment currentComment = takeFromResultSet(rs);
					comments.add(currentComment);
				}
			}
		} catch(SQLException e) {
			throw new DAOException(e); 
		}
		return comments;
	}
}
