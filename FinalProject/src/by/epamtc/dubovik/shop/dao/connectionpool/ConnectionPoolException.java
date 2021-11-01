package by.epamtc.dubovik.shop.dao.connectionpool;

public class ConnectionPoolException extends RuntimeException {

	private static final long serialVersionUID = -1327184459528187583L;

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