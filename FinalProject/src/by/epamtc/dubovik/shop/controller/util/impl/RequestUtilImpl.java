package by.epamtc.dubovik.shop.controller.util.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FilenameUtils;

import by.epamtc.dubovik.shop.controller.ParameterName;
import by.epamtc.dubovik.shop.controller.util.RequestUtil;
import by.epamtc.dubovik.shop.entity.Product;
import by.epamtc.dubovik.shop.entity.UserLogged;

public class RequestUtilImpl implements RequestUtil {
	
	@Override
	public UserLogged takeUserLogged(HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserLogged user = (UserLogged)session.getAttribute(ParameterName.USER);
		return user;
	}
	
	@Override
	public String takeCommand(HttpServletRequest request) {
		return request.getParameter(ParameterName.COMMAND);
	}
	
	@Override
	public Integer takeIntegerWithNull(HttpServletRequest request, String paramName){
		String str = request.getParameter(paramName);
		Integer result = null;
		if( str != null && str.length() > 0) {
			 result = Integer.valueOf(str);
		}
		return result;
	}
	
	@Override
	public Integer takePriceValue(HttpServletRequest request, String paramName){
		String str = request.getParameter(paramName);
		Integer price = null;
		
		if( str != null && str.length() > 0) {
			double toDouble = Double.parseDouble(str) * 100;
			price = (int) Math.round(toDouble);
		}
		return price;
	}
	
	@Override
	public void takeProduct(HttpServletRequest request, Product product) 
			throws IOException, ServletException {
		
		product.setName(request
				.getParameter(ParameterName.PRODUCT_NAME));
		product.setCategoryId(Integer.parseInt(request
				.getParameter(ParameterName.PRODUCT_CATEGORY)));
		product.setDescription(request
				.getParameter(ParameterName.PRODUCT_DESCRIPTION).trim());
		product.setWeight(takeIntegerWithNull(request, ParameterName.PRODUCT_WEIGHT));
		product.setLength(takeIntegerWithNull(request, ParameterName.PRODUCT_LENGTH));
		product.setHigh(takeIntegerWithNull(request, ParameterName.PRODUCT_HIGH));
		product.setWidth(takeIntegerWithNull(request, ParameterName.PRODUCT_WIDTH));
		product.setQuantity(Integer
				.parseInt(request.getParameter(ParameterName.PRODUCT_QUANTITY)));
		
		
		Object fileObject = request.getAttribute(ParameterName.PRODUCT_PHOTO);
	    FileItem fileItem = (FileItem) fileObject;
	    if(fileItem != null) {
	    
	    	String fileName = FilenameUtils.getName(fileItem.getName());
	    	
	    	if(fileName != null && fileName.length() != 0) {
	    		product.setPhotoPath(fileName);
	    		product.setPhotoPath(request.getServletContext().getRealPath("images/") + fileName);
	    	}
	    	
	    	product.setPhotoContent(fileItem.getInputStream());
	    }
	}

	@Override
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
