package by.epamtc.dubovik.shop.service;

import java.util.List;

import by.epamtc.dubovik.shop.entity.Comment;
import by.epamtc.dubovik.shop.service.exception.InvalidException;
import by.epamtc.dubovik.shop.service.exception.ServiceException;

public interface CommentService {
	
	public List<Comment> takeComments(long productId) throws ServiceException;
	public boolean createComment(Comment comment) throws ServiceException, InvalidException;

}
