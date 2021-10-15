package by.epamtc.dubovik.shop.service.util;

import javax.servlet.http.HttpServletRequest;

import by.epamtc.dubovik.shop.entity.Product;
import by.epamtc.dubovik.shop.entity.UserLogged;

public interface RequestUtil {
	
	public UserLogged takeUserLogged(HttpServletRequest request);
	
	public String takeCommand(HttpServletRequest request);
	
	public Integer takeIntegerWithNull(HttpServletRequest request, String paramName);
	
	public String takeString(HttpServletRequest request, String paramName);
	
	public Integer takePriceValue(HttpServletRequest request, String paramName);
	
	public void transferParametersFromRequest(HttpServletRequest request, Product product);
	
	public int currentPage(Integer currentPage, int lastPage);

}
