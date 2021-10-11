package by.epamtc.dubovik.shop.dao;

import java.util.List;

import by.epamtc.dubovik.shop.dao.generic.GenericIntIdDAO;
import by.epamtc.dubovik.shop.entity.Comment;

public interface CommentDAO extends GenericIntIdDAO<Comment> {

	public List<Comment> findByProduct(int productId, int offset, int count) throws DAOException;
	public List<Comment> findByUser(int userId, int offset, int count) throws DAOException;
	public int calculateCommentCount(int productId) throws DAOException;
	public int calculateAverageRating(int productId) throws DAOException;
	
}
