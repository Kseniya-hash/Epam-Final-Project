package by.epamtc.dubovik.shop.service.util.impl;

import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.epamtc.dubovik.shop.controller.ParameterName;
import by.epamtc.dubovik.shop.entity.Product;
import by.epamtc.dubovik.shop.entity.UserLogged;
import by.epamtc.dubovik.shop.service.factory.ServiceFactory;
import by.epamtc.dubovik.shop.service.util.RequestUtil;

public class RequestUtilImpl implements RequestUtil {
	
	public UserLogged takeUserLogged(HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserLogged user = (UserLogged)session.getAttribute(ParameterName.USER);
		return user;
	}
	
	public String takeCommand(HttpServletRequest request) {
		return request.getParameter(ParameterName.COMMAND);
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
	
	public Integer takePriceValue(HttpServletRequest request, String paramName){
		String str = request.getParameter(paramName);
		Integer price = null;
		
		if( str != null && str.length() > 0) {
			double toDouble = Double.parseDouble(str) * 100;
			price = (int) Math.round(toDouble);
		}
		return price;
	}
	
	public void transferParametersFromRequest(HttpServletRequest request, Product product) {
		RequestUtil requestUtil = ServiceFactory.getInstance().getRequestUtil();
		product.setName(requestUtil.takeString(request, ParameterName.PRODUCT_NAME));
		product.setCategoryId(Integer.parseInt(request.getParameter(ParameterName.PRODUCT_CATEGORY)));
		product.setDescription(requestUtil.takeString(request, ParameterName.PRODUCT_DESCRIPTION).trim());
		product.setWeight(requestUtil.takeIntegerWithNull(request, ParameterName.PRODUCT_WEIGHT));
		product.setLength(requestUtil.takeIntegerWithNull(request, ParameterName.PRODUCT_LENGTH));
		product.setHigh(requestUtil.takeIntegerWithNull(request, ParameterName.PRODUCT_HIGH));
		product.setWidth(requestUtil.takeIntegerWithNull(request, ParameterName.PRODUCT_WIDTH));
		product.setQuantity(Integer.parseInt(request.getParameter(ParameterName.PRODUCT_QUANTITY)));
		String photoPath = requestUtil.takeString(request, ParameterName.PRODUCT_PHOTO);
		if(photoPath != null && photoPath.length() != 0) {
			product.setPhotoPath(photoPath);
		}
	}

	public int currentPage(Integer currentPage, int lastPage) {
		int firstPage = 1;
		if(currentPage == null || currentPage < firstPage) {
			currentPage = firstPage;
		}
		if(currentPage != null && currentPage > lastPage) {
			currentPage = lastPage;
		}
		return currentPage;
	}
}
