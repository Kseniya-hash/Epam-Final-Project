package by.epamtc.dubovik.shop.service.util;


import javax.servlet.http.HttpServletRequest;

public interface SecurityUtil {
	
	
	public boolean isProtectedCommand(HttpServletRequest request);

	public boolean hasPermission(HttpServletRequest request);
	
}
