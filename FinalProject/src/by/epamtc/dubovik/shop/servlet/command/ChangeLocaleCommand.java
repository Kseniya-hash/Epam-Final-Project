package by.epamtc.dubovik.shop.servlet.command;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epamtc.dubovik.shop.servlet.Page;
import by.epamtc.dubovik.shop.servlet.ParameterName;

public class ChangeLocaleCommand implements ActionCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String localeString = request.getParameter(ParameterName.LOCALE);
		
		response.setLocale(Locale.forLanguageTag(localeString));
		
		Cookie localeCookie = new Cookie(ParameterName.LOCALE,localeString);
		response.addCookie(localeCookie);
		HttpSession session = request.getSession();
		String page = (String)session.getAttribute(ParameterName.PREVIOS_URL);
		if(page == null) {
			page = Page.INDEX;
		}
		request.getRequestDispatcher(page).forward(request, response);
	}

}
