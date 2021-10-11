package by.epamtc.dubovik.shop.service.exception;

public class ServiceException extends Exception {

	private static final long serialVersionUID = -5030912824276729718L;

	public ServiceException() {}
	
	public ServiceException(String s) {
		super(s);
	}
		
	public ServiceException(String s, Throwable cause) {
		super(s, cause);
	}
		
	public ServiceException(Throwable cause) {
		super(cause);
	}
}
