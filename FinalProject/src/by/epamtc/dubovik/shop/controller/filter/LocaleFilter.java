package by.epamtc.dubovik.shop.controller.filter;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epamtc.dubovik.shop.controller.ParameterName;

public class LocaleFilter implements Filter {

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) 
			throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)resp;
		Cookie[] cookies = request.getCookies();
		String localeFromCookie = null;
		for(int i = 0; i < cookies.length; i++) {
			if(ParameterName.LOCALE.equals(cookies[i].getName())){
				localeFromCookie = cookies[i].getValue();
			}
		}
		if(localeFromCookie != null) {
			response.setLocale(Locale.forLanguageTag(localeFromCookie));
		}
		chain.doFilter(request, response);
	}
	public void init(FilterConfig fConfig) throws ServletException {}

	public void destroy() {}
}
