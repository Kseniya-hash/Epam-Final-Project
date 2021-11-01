package by.epamtc.dubovik.shop.controller.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epamtc.dubovik.shop.controller.Page;
import by.epamtc.dubovik.shop.controller.ParameterName;
import by.epamtc.dubovik.shop.controller.util.MessageManager;
import by.epamtc.dubovik.shop.controller.util.RequestUtilFactory;
import by.epamtc.dubovik.shop.entity.User;
import by.epamtc.dubovik.shop.service.exception.InvalidException;
import by.epamtc.dubovik.shop.service.exception.ServiceException;
import by.epamtc.dubovik.shop.service.exception.UserAlreadyExistException;
import by.epamtc.dubovik.shop.service.ServiceFactory;
import by.epamtc.dubovik.shop.service.UserService;

public class RegistrationCommand implements ActionCommand {
	
	private static Logger logger = LogManager.getLogger();

	private final static String REGISTRATION_ERROR = "modal.registrationerror";
	private final static String USER_EXIST_ERROR = "modal.userexisterror";
	private final static String INVALID_REGISTRATION_ERROR = "modal.invalidregistrationerror";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		String page = Page.REGISTRATION;
		User user = new User();
		takeUserParamsFromRequest(request, user);
		byte[] passwordRepeat = request
				.getParameter(ParameterName.PASSWORD_REPEAT).getBytes();
		
		UserService registerLogic = 
				ServiceFactory.getInstance().getUserService();
		MessageManager messageManager = 
				RequestUtilFactory.getInstance().getMessageManager();
		
		boolean registerFlag = false;
		String messageForModal = null;
		try {
			registerFlag = registerLogic.register(user, passwordRepeat);
			if(registerFlag) {
				page = Page.LOGIN;
			} else {
				messageForModal = REGISTRATION_ERROR;
			}
		} catch (ServiceException e) {
			logger.error(e);
			
			page = Page.ERROR500;
		} catch (UserAlreadyExistException e) {
			logger.error(e);
			
			messageForModal = USER_EXIST_ERROR;
		} catch (InvalidException e) {
			logger.error(e);
			
			messageForModal = INVALID_REGISTRATION_ERROR;
		}
		if(messageForModal != null) {
			request.setAttribute(ParameterName.MODAL_MESSAGE, 
					messageManager.getProperty(messageForModal, response.getLocale()));
		}
		
		request.getRequestDispatcher(page).forward(request, response);
	}
	
	private void takeUserParamsFromRequest(HttpServletRequest request, User user) {
		user.setPhone(request.getParameter(ParameterName.PHONE));
		user.setLogin(request.getParameter(ParameterName.LOGIN));
		user.setName(request.getParameter(ParameterName.NAME));
		user.setEMail(request.getParameter(ParameterName.EMAIL));
		user.setPassword(request.getParameter(ParameterName.PASSWORD).getBytes());
	}

}
