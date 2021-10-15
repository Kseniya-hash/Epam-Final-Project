package by.epamtc.dubovik.shop.controller.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.epamtc.dubovik.shop.controller.ParameterName;

public class PreviousRequestFilter implements Filter {
	
	private final String GET = "GET";

	public void doFilter(ServletRequest req, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		
		HttpSession session = request.getSession();
		String previousUrl = (String)session.getAttribute(ParameterName.CURRENT_URL);
		session.setAttribute(ParameterName.PREVIOS_URL, previousUrl);
		
		if(GET.equals(request.getMethod())) {
			StringBuilder currentUrl = new StringBuilder(request.getServletPath() + "?");
			Enumeration<String> params = request.getParameterNames();
			while(params.hasMoreElements()) {
				String param = params.nextElement();
				currentUrl.append(param + "=" + request.getParameter(param) + "&");
			}
			String currentUrlString = currentUrl.toString();
			session.setAttribute(ParameterName.CURRENT_URL, currentUrlString);
		}
		chain.doFilter(request, response);
	}
	
	public void init(FilterConfig fConfig) throws ServletException {
	}
	
	public void destroy() {}

}
