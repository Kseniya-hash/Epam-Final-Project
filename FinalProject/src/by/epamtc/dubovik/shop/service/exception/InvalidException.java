package by.epamtc.dubovik.shop.service.exception;

public class InvalidException extends Exception {

	private static final long serialVersionUID = 3571734800575510157L;

	public InvalidException() {}
	
	public InvalidException(String s) {
		super(s);
	}
		
	public InvalidException(String s, Throwable cause) {
		super(s, cause);
	}
		
	public InvalidException(Throwable cause) {
		super(cause);
	}
}
