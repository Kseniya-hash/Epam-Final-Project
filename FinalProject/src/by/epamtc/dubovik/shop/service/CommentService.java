package by.epamtc.dubovik.shop.service;

import java.util.List;

import by.epamtc.dubovik.shop.entity.Comment;
import by.epamtc.dubovik.shop.service.exception.InvalidException;
import by.epamtc.dubovik.shop.service.exception.ServiceException;

public interface CommentService {
	
	/**
	 * Take a list of comments for specific product. 
	 * If product does not have any comments
	 * or product does not exist return empty list.
	 * @param productId - id of specific product
	 * @return list of comments for specific product
	 * @throws ServiceException
	 */
	public List<Comment> findComments(long productId) 
			throws ServiceException;
	
	/**
	 * Create comment. If unable to create comment return false
	 * @param comment
	 * @return
	 * @throws InvalidException
	 * @throws ServiceException
	 */
	public boolean createComment(Comment comment) 
			throws InvalidException, ServiceException;

}
