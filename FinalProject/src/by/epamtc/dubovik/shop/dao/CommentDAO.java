package by.epamtc.dubovik.shop.dao;

import java.util.List;

import by.epamtc.dubovik.shop.dao.generic.GenericDAO;
import by.epamtc.dubovik.shop.entity.Comment;

public interface CommentDAO extends GenericDAO<Comment> {

	public List<Comment> findByProduct(long productId) throws DAOException;
	public List<Comment> findByUser(long userIds) throws DAOException;
	public int calculateCommentCount(long productId) throws DAOException;
	public int calculateAverageRating(long productId) throws DAOException;
	
}
