package by.epamtc.dubovik.shop.servlet.wrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import by.epamtc.dubovik.shop.entity.UserLogged;
import by.epamtc.dubovik.shop.service.util.RequestUtil;

public class UserRoleRequestWrapper extends HttpServletRequestWrapper {
	
	//private String role = null;
	private HttpServletRequest realRequest;

	public UserRoleRequestWrapper(HttpServletRequest request) {
		super(request);
		
		//this.role = role;
		this.realRequest = request;
	}

	@Override
	public boolean isUserInRole(String role) {
		UserLogged user = RequestUtil.takeUserLogged(realRequest);
		
		boolean isInRole = false;
		if(user != null && user.getRole() != null && user.getRole().equals(role)) {
			isInRole = true;
		} else {
			isInRole = this.realRequest.isUserInRole(role);
		}
		return isInRole;
	}
}
