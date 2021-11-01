package by.epamtc.dubovik.shop.controller.util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import by.epamtc.dubovik.shop.entity.Product;
import by.epamtc.dubovik.shop.entity.UserLogged;

public interface RequestUtil {
	
	public UserLogged takeUserLogged(HttpServletRequest request);
	
	public String takeCommand(HttpServletRequest request);
	
	public Integer takeIntegerWithNull(HttpServletRequest request, String paramName);
	
	public Integer takePriceValue(HttpServletRequest request, String paramName);
	
	public void takeProduct(HttpServletRequest request, Product product) 
			throws IOException, ServletException;
	
	public int currentPage(Integer currentPage, int lastPage);

}
