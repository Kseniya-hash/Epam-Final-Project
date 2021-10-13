package by.epamtc.dubovik.shop.servlet.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epamtc.dubovik.shop.entity.UserForLogin;
import by.epamtc.dubovik.shop.entity.UserLogged;
import by.epamtc.dubovik.shop.service.LoginService;
import by.epamtc.dubovik.shop.service.UserService;
import by.epamtc.dubovik.shop.service.exception.InvalidException;
import by.epamtc.dubovik.shop.service.exception.ServiceException;
import by.epamtc.dubovik.shop.service.factory.ServiceFactory;
import by.epamtc.dubovik.shop.service.resource.MessageManager;
import by.epamtc.dubovik.shop.servlet.Page;
import by.epamtc.dubovik.shop.servlet.ParameterName;

public class LoginCommand implements ActionCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String page = Page.LOGIN;
		String login = request.getParameter(ParameterName.LOGIN);
		byte[] password = request.getParameter(ParameterName.PASSWORD).getBytes();
		UserForLogin unauthorized = new UserForLogin(login, password);
		
		UserService loginService = ServiceFactory.getInstance().getUserService();
		//LoginService loginService = ServiceFactory.getInstance().getLoginService(); 
		UserLogged user = null;
		try {
			user = loginService.authorize(unauthorized);
		} catch (ServiceException e) {
			//ERROR PAGE
		} catch (InvalidException e) {
			request.setAttribute("errorLoginPassMessage", 
					MessageManager.getProperty("message.empthyloginerror"));
		}

		if(user != null) {
			if(!user.getIsBlacklisted()) {
				HttpSession session = request.getSession();
				session.setAttribute(ParameterName.USER, user);
				page = Page.INDEX;
			} else {
				request.setAttribute("errorLoginPassMessage", 
						MessageManager.getProperty("message.empthyloginerror"));
			}
			//page = Page.INDEX;
		} else {
			request.setAttribute("errorLoginPassMessage", 
					MessageManager.getProperty("message.loginerror"));
		}
		request.getRequestDispatcher(page).forward(request, response);
	}

}
