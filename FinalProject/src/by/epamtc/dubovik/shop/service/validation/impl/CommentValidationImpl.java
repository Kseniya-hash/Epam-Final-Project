package by.epamtc.dubovik.shop.service.validation.impl;

import by.epamtc.dubovik.shop.entity.Comment;
import by.epamtc.dubovik.shop.service.validation.CommentValidation;

public class CommentValidationImpl implements CommentValidation {

	private final int MIN_RATING = 1;
	private final int MAX_RATING = 5;
	
	@Override
	public boolean isValid(Comment comment) {
		boolean isValid = false;
		if(comment != null && isValidRating(comment.getRating()) &&
			(textNotEmpty(comment.getText()) || comment.getRating() != null)) {
			isValid = true;
		}
		return isValid;
	}

	private boolean isValidRating (Integer rating) {
		return rating != null && (rating >= MIN_RATING && rating <= MAX_RATING);
	}
	
	private boolean textNotEmpty(String text) {
		return text != null && text.length() != 0;
	}
	
}
