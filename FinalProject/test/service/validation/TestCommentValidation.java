package service.validation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import by.epamtc.dubovik.shop.entity.Comment;
import by.epamtc.dubovik.shop.service.validation.CommentValidation;
import by.epamtc.dubovik.shop.service.validation.ValidationFactory;

public class TestCommentValidation {
	
	private CommentValidation validator = 
			ValidationFactory.getInstance().getCommentValidation();
	
	private Comment comment = new Comment();
	
	@Before
	public void setUp() {
		comment.setId(1);
		comment.setProductId(1);
		comment.setUserId(1);
		comment.setUserLogin("mari");
		comment.setText("ok");
		comment.setRating(5);
	}
	
	@Test
	public void isValidTest() {
		boolean expected = true;
		boolean actual = validator.isValid(comment);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void isValidTestNoText() {
		comment.setText(null);
		
		boolean expected = true;
		boolean actual = validator.isValid(comment);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void isValidTestNoEmptyText() {
		comment.setText("");
		
		boolean expected = true;
		boolean actual = validator.isValid(comment);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void isValidTestNoRating() {
		comment.setRating(null);
		
		boolean expected = false;
		boolean actual = validator.isValid(comment);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void isValidTestNoTextNoRating() {
		comment.setText(null);
		comment.setRating(null);
		
		boolean expected = false;
		boolean actual = validator.isValid(comment);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void isValidTestEmptyTextNoRating() {
		comment.setText("");
		comment.setRating(null);
		
		boolean expected = false;
		boolean actual = validator.isValid(comment);
		
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void isValidTestRatingTooBig() {
		comment.setRating(6);
		
		boolean expected = false;
		boolean actual = validator.isValid(comment);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void isValidTestRatingTooSmall() {
		comment.setRating(0);
		
		boolean expected = false;
		boolean actual = validator.isValid(comment);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void isValidTestNullComment() {
		Comment comment = null;
		
		boolean expected = false;
		boolean actual = validator.isValid(comment);
		Assert.assertEquals(expected, actual);
	}
}
