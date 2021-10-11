package by.epamtc.dubovik.shop.service.exception;

public class UserAlreadyExistException extends Exception {
	
	private static final long serialVersionUID = -3011349443622062030L;

	public UserAlreadyExistException() {}
	
	public UserAlreadyExistException(String s) {
		super(s);
	}
		
	public UserAlreadyExistException(String s, Throwable cause) {
		super(s, cause);
	}
		
	public UserAlreadyExistException(Throwable cause) {
		super(cause);
	}

}
