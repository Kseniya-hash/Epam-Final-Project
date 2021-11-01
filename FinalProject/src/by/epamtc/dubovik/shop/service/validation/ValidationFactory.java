package by.epamtc.dubovik.shop.service.validation;

import by.epamtc.dubovik.shop.service.validation.impl.*;

public class ValidationFactory {
	
	private final PriceValidation priceValidation;
	private final ProductValidation productValidation;
	private final UserForLoginValidation userForLoginValidation;
	private final UserValidation userValidation;
	private final CartValidation cartValidation;
	private final CommentValidation commentValidation;
	
	private ValidationFactory() {
		priceValidation = new PriceValidationImpl();
		productValidation = new ProductValidationImpl();
		userForLoginValidation = new UserForLoginValidationImpl();
		userValidation = new UserValidationImpl();
		cartValidation = new CartValidationImpl();
		commentValidation = new CommentValidationImpl();
	}
	
	private static class SigletonHolder {
		private final static ValidationFactory INSTANCE = new ValidationFactory();
	}
	
	public static ValidationFactory getInstance() {
		return SigletonHolder.INSTANCE;
	}

	public PriceValidation getPriceValidation() {
		return priceValidation;
	}
	
	public ProductValidation getProductValidation() {
		return productValidation;
	}
	
	public UserForLoginValidation getUserForLoginValidation() {
		return userForLoginValidation;
	}
	
	public UserValidation getUserValidation() {
		return userValidation;
	}
	
	public CartValidation getCartValidation() {
		return cartValidation;
	}
	
	public CommentValidation getCommentValidation() {
		return commentValidation;
	}
}
