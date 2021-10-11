package by.epamtc.dubovik.shop.connectionpool;

public class ConnectionPoolException extends RuntimeException {

	public ConnectionPoolException() {}
	
	public ConnectionPoolException(String s) {
		super(s);
	}
		
	public ConnectionPoolException(String s, Throwable cause) {
		super(s, cause);
	}
		
	public ConnectionPoolException(Throwable cause) {
		super(cause);
	}
}