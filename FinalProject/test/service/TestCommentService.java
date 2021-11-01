package service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import by.epamtc.dubovik.shop.dao.CommentDAO;
import by.epamtc.dubovik.shop.dao.DAOException;
import by.epamtc.dubovik.shop.dao.DAOFactory;
import by.epamtc.dubovik.shop.entity.Comment;
import by.epamtc.dubovik.shop.service.CommentService;
import by.epamtc.dubovik.shop.service.ServiceFactory;
import by.epamtc.dubovik.shop.service.exception.InvalidException;
import by.epamtc.dubovik.shop.service.exception.ServiceException;

@PrepareForTest(fullyQualifiedNames = QualifiedNamesMapping.DAO)
@RunWith(PowerMockRunner.class)
public class TestCommentService {
	
	@Mock
	private DAOFactory daoFactory;
	
	@Mock
	private CommentDAO commentDAO;
	
	private CommentService commentService;
	
	private Comment comment = new Comment();
	
	@Before
	public void setUpComment() {
		comment.setId(1);
		comment.setProductId(1);
		comment.setUserId(2);
		comment.setUserLogin("vict");
		comment.setRating(5);
	}
	
	@Before
	public void setUp() {
		PowerMockito.mockStatic(DAOFactory.class);
		PowerMockito.when(DAOFactory.getInstance())
				.thenReturn(daoFactory);
		PowerMockito.when(daoFactory.getCommentDAO())
				.thenReturn(commentDAO);
		commentService = ServiceFactory.getInstance()
				.getCommentService();
	}
	
	@Test
	public void findCommentsTest() {
		try {
			List<Comment> comments = new ArrayList<>();
			
			comments.add(comment);
			comment = new Comment();
			comment.setId(2);
			comment.setProductId(1);
			comment.setUserId(3);
			comment.setUserLogin("mari");
			comment.setText("Awesome");
			comment.setRating(5);
			comments.add(comment);
			
			PowerMockito.when(commentDAO.findByProduct(Mockito.anyLong()))
					.thenReturn(comments);
			
			List<Comment> actual = commentService.findComments(1);
			Assert.assertArrayEquals(comments.toArray(), actual.toArray());
		} catch (ServiceException | DAOException e) {
			Assert.fail(e.getMessage());
		} 
	}
	
	@Test
	public void findCommentsTestEmpty() {
		try {
			List<Comment> comments = new ArrayList<>();
			PowerMockito.when(commentDAO.findByProduct(Mockito.anyLong()))
					.thenReturn(comments);
			
			List<Comment> actual = commentService.findComments(1);
			Assert.assertArrayEquals(comments.toArray(), actual.toArray());
		} catch (ServiceException | DAOException e) {
			Assert.fail(e.getMessage());
		} 
	}
	
	@Test(expected = ServiceException.class)
	public void findCommentsTestServiceException() 
			throws ServiceException, DAOException {
		PowerMockito.when(commentDAO.findByProduct(Mockito.anyLong()))
				.thenThrow(DAOException.class);
			
		commentService.findComments(1);
	}
	
	@Test
	public void createCommentTest() {
		try {
			
			PowerMockito.when(commentDAO.create(Mockito.any()))
					.thenReturn(true);
			
			boolean expected = true;
			boolean actual = commentService.createComment(comment);
			Assert.assertEquals(expected, actual);
		} catch (InvalidException | DAOException | ServiceException e) {
			Assert.fail(e.getMessage());
		} 
	}
	
	@Test
	public void createCommentTestDAOReturnFalse() {
		try {
			PowerMockito.when(commentDAO.create(Mockito.any()))
					.thenReturn(false);
			
			boolean expected = false;
			boolean actual = commentService.createComment(comment);
			Assert.assertEquals(expected, actual);
		} catch (InvalidException | DAOException | ServiceException e) {
			Assert.fail(e.getMessage());
		} 
	}
	
	@Test(expected = ServiceException.class)
	public void createCommentTestServiceComment() 
			throws ServiceException, InvalidException, DAOException {
		
		PowerMockito.when(commentDAO.create(Mockito.any()))
				.thenThrow(DAOException.class);
		
		commentService.createComment(comment);
	}
	
	@Test(expected = InvalidException.class)
	public void createCommentTestInvalidComment() 
			throws ServiceException, InvalidException {
		comment.setRating(null);
		
		commentService.createComment(comment);
	}
	
	@Test(expected = InvalidException.class)
	public void createCommentTestNullComment()
			throws ServiceException, InvalidException {
		Comment comment = null;
		
		commentService.createComment(comment);
	}

}
