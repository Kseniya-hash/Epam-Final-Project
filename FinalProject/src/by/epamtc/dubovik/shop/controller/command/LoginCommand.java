package by.epamtc.dubovik.shop.controller.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epamtc.dubovik.shop.controller.Page;
import by.epamtc.dubovik.shop.controller.ParameterName;
import by.epamtc.dubovik.shop.controller.util.MessageManager;
import by.epamtc.dubovik.shop.controller.util.RequestUtilFactory;
import by.epamtc.dubovik.shop.entity.UserForLogin;
import by.epamtc.dubovik.shop.entity.UserLogged;
import by.epamtc.dubovik.shop.service.ServiceFactory;
import by.epamtc.dubovik.shop.service.UserService;
import by.epamtc.dubovik.shop.service.exception.InvalidException;
import by.epamtc.dubovik.shop.service.exception.ServiceException;

public class LoginCommand implements ActionCommand {
	
	private static Logger logger = LogManager.getLogger();

	private final static String EMPTY_LOGIN_ERROR = "modal.empthyloginerror";
	private final static String BLACKLIST_ERROR = "modal.blacklist";
	private final static String USER_NOT_FOUND_ERROR = "modal.usernotfound";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		String page = Page.LOGIN;
		String login = request.getParameter(ParameterName.LOGIN);
		byte[] password = request.getParameter(ParameterName.PASSWORD).getBytes();
		UserForLogin unauthorized = new UserForLogin(login, password);
		ServiceFactory factory = ServiceFactory.getInstance();
		UserService loginService = factory.getUserService();
		MessageManager messageManager = 
				RequestUtilFactory.getInstance().getMessageManager();
		UserLogged user = null;
		String messageForModal = null;
		try {
			user = loginService.authorize(unauthorized);
			if(user != null) {
				if(!user.getIsBlacklisted()) {
					HttpSession session = request.getSession();
					session.setAttribute(ParameterName.USER, user);
					page = Page.INDEX;
				} else {
					messageForModal = BLACKLIST_ERROR;
				}
			} else {
				messageForModal = USER_NOT_FOUND_ERROR;
			}
		} catch (ServiceException e) {
			logger.error(e);
			
			page = Page.ERROR500;
		} catch (InvalidException e) {
			logger.error(e);
			
			messageForModal = EMPTY_LOGIN_ERROR;
		}
		
		if(messageForModal != null) {
			request.setAttribute(ParameterName.MODAL_MESSAGE, 
					messageManager.getProperty(messageForModal, response.getLocale()));
		}
		
		request.getRequestDispatcher(page).forward(request, response);
	}

}
