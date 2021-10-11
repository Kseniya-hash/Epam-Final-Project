package by.epamtc.dubovik.shop.servlet.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epamtc.dubovik.shop.entity.Cart;
import by.epamtc.dubovik.shop.entity.UserLogged;
import by.epamtc.dubovik.shop.service.security.SecurityConfig;
import by.epamtc.dubovik.shop.service.util.RequestUtil;
import by.epamtc.dubovik.shop.service.util.SecurityUtil;
import by.epamtc.dubovik.shop.servlet.Page;
import by.epamtc.dubovik.shop.servlet.wrapper.UserRoleRequestWrapper;

/**
 * Servlet Filter implementation class SecurityFilter
 */
public class SecurityFilter implements Filter {

    /**
     * Default constructor. 
     */
    public SecurityFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		UserLogged loginedUser = RequestUtil.takeUserLogged(request);
		
		HttpServletRequest wrapRequest = request;
		if (loginedUser != null) {
			wrapRequest = new UserRoleRequestWrapper(request);
		}
		String command = RequestUtil.getCommand(wrapRequest);
		
		if (SecurityConfig.isSecureCommand(command)) {
			
			boolean hasPermission = SecurityUtil.hasPermission(wrapRequest);
			if (!hasPermission) {
				request.getRequestDispatcher(Page.LOGIN).forward(wrapRequest, response);
				return;
			}
		}

		chain.doFilter(request, response);
	}
	
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
