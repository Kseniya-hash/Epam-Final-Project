package by.epamtc.dubovik.shop.servlet.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epamtc.dubovik.shop.service.factory.ServiceFactory;
import by.epamtc.dubovik.shop.servlet.Page;
import by.epamtc.dubovik.shop.servlet.ParameterName;

/**
 * Servlet Filter implementation class CSRFFilter
 */
public class CSRFFilter implements Filter {

	private final String POST = "POST";
	
    /**
     * Default constructor. 
     */
    public CSRFFilter() {
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
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)resp;
		
		if(POST.equals(request.getMethod())) {
			String tokenFromRequest = request.getParameter(ParameterName.CSRF_TOKIN);
		
			Cookie[] cookies = request.getCookies();
			String tokenFromCookie = null;
			for(int i = 0; i < cookies.length; i++) {
				if(ParameterName.CSRF_TOKIN.equals(cookies[i].getName())){
					tokenFromCookie = cookies[i].getValue();
				}
			}
			if(tokenFromRequest == null || !tokenFromRequest.equals(tokenFromCookie)) {
				String page = request.getParameter(ParameterName.PREVIOS_URL);
				if(page == null) {
					page = Page.INDEX;
				}
				request.getRequestDispatcher(page).forward(request, response);
				return;
			}
		}
		long token = ServiceFactory.getInstance().getEncryptUtil().takeSecureRandom();
		request.setAttribute(ParameterName.CSRF_TOKIN, token);
		Cookie csrfCookie = new Cookie(ParameterName.CSRF_TOKIN, Long.toString(token));
		response.addCookie(csrfCookie);
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
