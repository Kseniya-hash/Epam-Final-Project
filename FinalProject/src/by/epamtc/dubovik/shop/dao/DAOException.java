package by.epamtc.dubovik.shop.dao;

public class DAOException extends Exception {
	
	private static final long serialVersionUID = -5689224749065678826L;

	public DAOException() {}
	
	public DAOException(String s) {
		super(s);
	}
		
	public DAOException(String s, Throwable cause) {
		super(s, cause);
	}
		
	public DAOException(Throwable cause) {
		super(cause);
	}
}
