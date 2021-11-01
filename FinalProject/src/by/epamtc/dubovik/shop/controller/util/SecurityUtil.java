package by.epamtc.dubovik.shop.controller.util;


import javax.servlet.http.HttpServletRequest;

public interface SecurityUtil {
	
	
	public boolean isProtectedCommand(HttpServletRequest request);

	public boolean hasPermission(HttpServletRequest request);
	
}
