package dao.jdbcimpl;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import by.epamtc.dubovik.shop.connectionpool.ConnectionPool;
import by.epamtc.dubovik.shop.dao.CommentDAO;
import by.epamtc.dubovik.shop.dao.DAOException;
import by.epamtc.dubovik.shop.dao.factory.DAOFactory;
import by.epamtc.dubovik.shop.entity.Comment;

public class TestCommentJDBC {
	
	@BeforeClass
	public static void initConnectionPool() {
		ConnectionPool pool = ConnectionPool.getInstance();
		pool.initPoolData();
	}
	
	@Test
	public void findAllTestWithoutOffset() throws DAOException{
		CommentDAO commentDAO = DAOFactory.getInstance().getCommentDAO();
		List<Comment> expected = new ArrayList<>();
		expected.add(new Comment(1,2,"vict",1,null,5));
		expected.add(new Comment(2,3,"mari",1,"Так себе",3));
		expected.add(new Comment(3,2,"vict",3,null,4));
		int offset = 0;
		int count = 3;
		List<Comment> actual = commentDAO.findAll(offset, count);
		Assert.assertArrayEquals(expected.toArray(), actual.toArray());
	}
	
	@Test
	public void findAllTestWithOffset() throws DAOException{
		CommentDAO commentDAO = DAOFactory.getInstance().getCommentDAO();
		List<Comment> expected = new ArrayList<>();
		expected.add(new Comment(2,3,"mari",1,"Так себе",3));
		expected.add(new Comment(3,2,"vict",3,null,4));
		int offset = 1;
		int count = 2;
		List<Comment> actual = commentDAO.findAll(offset, count);
		Assert.assertArrayEquals(expected.toArray(), actual.toArray());
	}
	
	@Test
	public void findAllTestWithTooBigOffset() throws DAOException{
		CommentDAO commentDAO = DAOFactory.getInstance().getCommentDAO();
		List<Comment> expected = new ArrayList<>();
		int offset = 10;
		int count = 2;
		List<Comment> actual = commentDAO.findAll(offset, count);
		Assert.assertArrayEquals(expected.toArray(), actual.toArray());
	}
	
	@Test
	public void findByProductTest() throws DAOException{
		CommentDAO commentDAO = DAOFactory.getInstance().getCommentDAO();
		List<Comment> expected = new ArrayList<>();
		expected.add(new Comment(1,2,"vict",1,null,5));
		expected.add(new Comment(2,3,"mari",1,"Так себе",3));
		long productId = 1;
		List<Comment> actual = commentDAO.findByProduct(productId);
		Assert.assertArrayEquals(expected.toArray(), actual.toArray());
	}
	
	@Test
	public void findByProductTestProductDontExist() throws DAOException{
		CommentDAO commentDAO = DAOFactory.getInstance().getCommentDAO();
		List<Comment> expected = new ArrayList<>();
		long productId = 10;
		List<Comment> actual = commentDAO.findByProduct(productId);
		Assert.assertArrayEquals(expected.toArray(), actual.toArray());
	}
	
	@Test
	public void findByIdTest() throws DAOException{
		CommentDAO commentDAO = DAOFactory.getInstance().getCommentDAO();
		Comment expected = new Comment(1,2,"vict",1,null,5);
		long id = 1;
		Comment actual = commentDAO.findById(id);
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void findByIdTestInvalidId() throws DAOException{
		CommentDAO commentDAO = DAOFactory.getInstance().getCommentDAO();
		Comment expected = null;
		long id = 15;
		Comment actual = commentDAO.findById(id);
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void findByUserTest() throws DAOException{
		CommentDAO commentDAO = DAOFactory.getInstance().getCommentDAO();
		List<Comment> expected = new ArrayList<>();
		expected.add(new Comment(1,2,"vict",1,null,5));
		expected.add(new Comment(3,2,"vict",3,null,4));
		long userId = 2;
		List<Comment> actual = commentDAO.findByUser(userId);
		Assert.assertArrayEquals(expected.toArray(), actual.toArray());
	}
	
	@Test
	public void findByUserTestUserDontExist() throws DAOException{
		CommentDAO commentDAO = DAOFactory.getInstance().getCommentDAO();
		List<Comment> expected = new ArrayList<>();
		long userId = 10;
		List<Comment> actual = commentDAO.findByUser(userId);
		Assert.assertArrayEquals(expected.toArray(), actual.toArray());
	}
	
	@Test
	public void calculateCommentCountTest() throws DAOException{
		CommentDAO commentDAO = DAOFactory.getInstance().getCommentDAO();
		int expected = 4;
		long productId = 1;
		int actual = commentDAO.calculateCommentCount(productId);
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void calculateCommentCountTestProductDontExist() throws DAOException{
		CommentDAO commentDAO = DAOFactory.getInstance().getCommentDAO();
		int expected = 0;
		long productId = 10;
		int actual = commentDAO.calculateCommentCount(productId);
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void calculateAverageRatingTest() throws DAOException{
		CommentDAO commentDAO = DAOFactory.getInstance().getCommentDAO();
		int expected = 4;
		long productId = 1;
		int actual = commentDAO.calculateAverageRating(productId);
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void calculateAverageRatingTestProductDontExist() throws DAOException{
		CommentDAO commentDAO = DAOFactory.getInstance().getCommentDAO();
		int expected = 0;
		long productId = 10;
		int actual = commentDAO.calculateAverageRating(productId);
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void createTest() throws DAOException{
		CommentDAO commentDAO = DAOFactory.getInstance().getCommentDAO();
		boolean expected = true;
		Comment comment = new Comment(0,3,"mari",2,"",5);
		boolean actual = commentDAO.create(comment);
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void createTestInvalidProductId() throws DAOException{
		CommentDAO commentDAO = DAOFactory.getInstance().getCommentDAO();
		boolean expected = false;
		Comment comment = new Comment(0,3,"mari",-1,"",5);
		boolean actual = commentDAO.create(comment);
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void createTestInvalidUserId() throws DAOException{
		CommentDAO commentDAO = DAOFactory.getInstance().getCommentDAO();
		boolean expected = false;
		Comment comment = new Comment(0,-3,"mari",1,"",5);
		boolean actual = commentDAO.create(comment);
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void updateTest() throws DAOException{
		CommentDAO commentDAO = DAOFactory.getInstance().getCommentDAO();
		boolean expected = true;
		Comment comment = new Comment(6, 3,"mari",1,"ok",5);
		boolean actual = commentDAO.update(comment);
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void updateTestCommentDontExist() throws DAOException{
		CommentDAO commentDAO = DAOFactory.getInstance().getCommentDAO();
		boolean expected = false;
		Comment comment = new Comment(10, 3,"mari",1,"ok",5);
		boolean actual = commentDAO.update(comment);
		Assert.assertEquals(expected, actual);
	}
	
	@AfterClass
	public static void disposeConnectionPool() {
		ConnectionPool pool = ConnectionPool.getInstance();
		pool.dispose();;
	}
}
