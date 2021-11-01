package by.epamtc.dubovik.shop.service.impl;

import java.util.List;

import by.epamtc.dubovik.shop.dao.CommentDAO;
import by.epamtc.dubovik.shop.dao.DAOException;
import by.epamtc.dubovik.shop.dao.DAOFactory;
import by.epamtc.dubovik.shop.entity.Comment;
import by.epamtc.dubovik.shop.service.CommentService;
import by.epamtc.dubovik.shop.service.exception.InvalidException;
import by.epamtc.dubovik.shop.service.exception.ServiceException;
import by.epamtc.dubovik.shop.service.validation.CommentValidation;
import by.epamtc.dubovik.shop.service.validation.ValidationFactory;

public class CommentServiceImpl implements CommentService {

	@Override
	public List<Comment> findComments(long productId)
			throws ServiceException {
		
		CommentDAO commentDAO = 
				DAOFactory.getInstance().getCommentDAO();
		List<Comment> comments = null;
		try {
			comments = commentDAO.findByProduct(productId);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return comments;
	}

	@Override
	public boolean createComment(Comment comment) 
			throws InvalidException, ServiceException {
		
		CommentValidation validator = 
				ValidationFactory.getInstance().getCommentValidation();
		if(!validator.isValid(comment)) {
			throw new InvalidException("Invalid comment");
		}
		
		boolean isCreated = false;
		CommentDAO commentDAO = DAOFactory.getInstance().getCommentDAO();
		try {
			isCreated = commentDAO.create(comment);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		
		return isCreated;
	}

}
