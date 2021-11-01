package by.epamtc.dubovik.shop.controller.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epamtc.dubovik.shop.controller.Page;
import by.epamtc.dubovik.shop.controller.ParameterName;
import by.epamtc.dubovik.shop.controller.command.factory.CommandMapping;
import by.epamtc.dubovik.shop.controller.util.RequestUtil;
import by.epamtc.dubovik.shop.controller.util.RequestUtilFactory;
import by.epamtc.dubovik.shop.controller.util.SecurityUtil;
import by.epamtc.dubovik.shop.controller.wrapper.UserRoleRequestWrapper;
import by.epamtc.dubovik.shop.entity.UserLogged;

public class SecurityFilter implements Filter {

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		RequestUtilFactory factory = RequestUtilFactory.getInstance();
		
		RequestUtil requestUtil = factory.getRequestUtil();
		SecurityUtil securityUtil = factory.getSecurityUtil();
		UserLogged loginedUser = requestUtil.takeUserLogged(request);
		HttpServletRequest wrapRequest = request;
		if (loginedUser != null) {
			wrapRequest = new UserRoleRequestWrapper(request);
		}
		
		if (securityUtil.isProtectedCommand(request)) {
			
			boolean hasPermission = securityUtil.hasPermission(wrapRequest);
			if (!hasPermission) {
				response.sendRedirect(request.getContextPath() + 
						Page.CONTROLLER +"?" + 
						ParameterName.COMMAND + "=" + 
						CommandMapping.TO_LOGIN_PAGE);
				return;
			}
		}

		chain.doFilter(request, response);
	}
	
	public void init(FilterConfig fConfig) throws ServletException {
	}
	
	public void destroy() {}

}
