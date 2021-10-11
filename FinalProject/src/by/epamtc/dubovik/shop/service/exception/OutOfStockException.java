package by.epamtc.dubovik.shop.service.exception;

public class OutOfStockException  extends Throwable {
	
	private static final long serialVersionUID = -4173482178881654105L;

	public OutOfStockException() {
		super();
	}
	
	public OutOfStockException(String s) {
		super(s);
	}
	
	public OutOfStockException(String s, Throwable t) {
		super(s,t);
	}

}
