package by.epamtc.dubovik.shop.controller.wrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import by.epamtc.dubovik.shop.controller.util.RequestUtil;
import by.epamtc.dubovik.shop.controller.util.RequestUtilFactory;
import by.epamtc.dubovik.shop.entity.UserLogged;

public class UserRoleRequestWrapper extends HttpServletRequestWrapper {
	
	private HttpServletRequest realRequest;

	public UserRoleRequestWrapper(HttpServletRequest request) {
		super(request);
		
		this.realRequest = request;
	}

	@Override
	public boolean isUserInRole(String role) {
		RequestUtil requestUtil = RequestUtilFactory.getInstance().getRequestUtil();
		UserLogged user = requestUtil.takeUserLogged(realRequest);
		
		boolean isInRole = false;
		if(user != null && user.getRole() != null && user.getRole().equals(role)) {
			isInRole = true;
		} else {
			isInRole = this.realRequest.isUserInRole(role);
		}
		return isInRole;
	}
}
