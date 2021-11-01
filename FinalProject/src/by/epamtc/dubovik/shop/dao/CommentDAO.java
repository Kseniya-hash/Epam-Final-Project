package by.epamtc.dubovik.shop.dao;

import java.util.List;

import by.epamtc.dubovik.shop.dao.generic.GenericDAO;
import by.epamtc.dubovik.shop.entity.Comment;

public interface CommentDAO extends GenericDAO<Comment> {

	/**
	 * Find a list of comments for specific product. If product does not have any comments
	 * or product does not exist return empty list.
	 * @param productId - id of specific product
	 * @return list of comments for specific product
	 * @throws DAOException
	 */
	public List<Comment> findByProduct(long productId) throws DAOException;
}
