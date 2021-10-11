package by.epamtc.dubovik.shop.service.util;

import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.epamtc.dubovik.shop.entity.UserLogged;

public class RequestUtil {
	
	private static final String USER_ATTRIBUTE = "user";
	private final static String COMMAND = "command"; 
	
	public static UserLogged takeUserLogged(HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserLogged user = (UserLogged)session.getAttribute(USER_ATTRIBUTE);
		return user;
	}
	
	public static String getCommand(HttpServletRequest request) {
		return request.getParameter(COMMAND);
	}
	
	public Integer takeIntegerWithNull(HttpServletRequest request, String paramName){
		String str = request.getParameter(paramName);
		Integer result = null;
		
		if( str != null && str.length() > 0) {
			 result = Integer.parseInt(str);
		}
		
		return result;
	}
	
	public String takeString(HttpServletRequest request, String paramName) {
		String paramValue = request.getParameter(paramName); 
		byte[] paramValueArray = paramValue.getBytes(StandardCharsets.ISO_8859_1);
		paramValue = new String(paramValueArray, StandardCharsets.UTF_8);
		
		return paramValue; 
	}

}
