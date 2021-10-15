package by.epamtc.dubovik.shop.controller.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epamtc.dubovik.shop.controller.Page;
import by.epamtc.dubovik.shop.controller.ParameterName;
import by.epamtc.dubovik.shop.entity.UserForLogin;
import by.epamtc.dubovik.shop.entity.UserLogged;
import by.epamtc.dubovik.shop.service.UserService;
import by.epamtc.dubovik.shop.service.exception.InvalidException;
import by.epamtc.dubovik.shop.service.exception.ServiceException;
import by.epamtc.dubovik.shop.service.factory.ServiceFactory;
import by.epamtc.dubovik.shop.service.resource.MessageManager;

public class LoginCommand implements ActionCommand {

	private final static String EMPTY_LOGIN_ERROR = "modal.empthyloginerror";
	private final static String BLACKLIST_ERROR = "modal.blacklist";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String page = Page.LOGIN;
		String login = request.getParameter(ParameterName.LOGIN);
		byte[] password = request.getParameter(ParameterName.PASSWORD).getBytes();
		UserForLogin unauthorized = new UserForLogin(login, password);
		
		ServiceFactory factory = ServiceFactory.getInstance();
		UserService loginService = factory.getUserService();
		MessageManager messageManager = factory.getMessageManager();
		
		UserLogged user = null;
		String messageForModal = null;
		try {
			user = loginService.authorize(unauthorized);
		} catch (ServiceException e) {
			page = Page.ERROR500;
		} catch (InvalidException e) {
			messageForModal = EMPTY_LOGIN_ERROR;
		}

		if(!user.getIsBlacklisted()) {
			HttpSession session = request.getSession();
			session.setAttribute(ParameterName.USER, user);
			page = Page.INDEX;
		} else {
			messageForModal = BLACKLIST_ERROR;
		}
		if(messageForModal != null) {
			request.setAttribute(ParameterName.MODAL_MESSAGE, 
					messageManager.getProperty(messageForModal, response.getLocale()));
		}
		request.getRequestDispatcher(page).forward(request, response);
	}

}
