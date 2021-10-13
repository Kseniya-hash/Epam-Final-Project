package by.epamtc.dubovik.shop.servlet.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epamtc.dubovik.shop.service.UserService;
import by.epamtc.dubovik.shop.service.exception.ServiceException;
import by.epamtc.dubovik.shop.service.factory.ServiceFactory;
import by.epamtc.dubovik.shop.servlet.Page;
import by.epamtc.dubovik.shop.servlet.ParameterName;

public class BlacklistUserCommand implements ActionCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		UserService userService = ServiceFactory.getInstance().getUserService();
		HttpSession session = request.getSession();
		String page = (String)session.getAttribute(ParameterName.PREVIOS_URL);
		if(page == null) {
			page = Page.INDEX;
		}
		try {
			int userId = Integer.parseInt(request.getParameter(ParameterName.USER_ID));
			userService.blacklistUser(userId);
		} catch (NumberFormatException e) {
			page = Page.ERROR404;
		} catch (ServiceException e) {
			page = Page.ERROR404;
		}
		request.getRequestDispatcher(page).forward(request, response);
	}

}
