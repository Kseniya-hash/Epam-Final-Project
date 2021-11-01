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
import by.epamtc.dubovik.shop.service.ServiceFactory;
import by.epamtc.dubovik.shop.service.UserService;
import by.epamtc.dubovik.shop.service.exception.ServiceException;

public class BlacklistUserCommand implements ActionCommand {

	private static Logger logger = LogManager.getLogger();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		UserService userService = ServiceFactory.getInstance().getUserService();
		HttpSession session = request.getSession();
		String page = (String)session.getAttribute(ParameterName.PREVIOS_URL);
		
		try {
			long userId = Long.parseLong(request.getParameter(ParameterName.USER_ID));
			userService.blacklistUser(userId);
		} catch (NumberFormatException | ServiceException e) {
			logger.error(e);
			page = Page.ERROR500;
		}
		
		request.getRequestDispatcher(page).forward(request, response);
	}

}
