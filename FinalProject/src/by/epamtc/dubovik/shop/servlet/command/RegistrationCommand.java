package by.epamtc.dubovik.shop.servlet.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epamtc.dubovik.shop.entity.User;
import by.epamtc.dubovik.shop.service.exception.InvalidException;
import by.epamtc.dubovik.shop.service.exception.ServiceException;
import by.epamtc.dubovik.shop.service.exception.UserAlreadyExistException;
import by.epamtc.dubovik.shop.service.factory.ServiceFactory;
import by.epamtc.dubovik.shop.service.RegisterService;
import by.epamtc.dubovik.shop.service.UserService;
import by.epamtc.dubovik.shop.service.resource.MessageManager;
import by.epamtc.dubovik.shop.servlet.Page;
import by.epamtc.dubovik.shop.servlet.ParameterName;

public class RegistrationCommand implements ActionCommand {
	
	private static final String ERROR_ATTR = "errorRegistrationMessage";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String page = Page.REGISTRATION;
		User user = new User();
		takeUserParamsFromRequest(request, user);
		byte[] passwordRepeat = request.getParameter(ParameterName.PASSWORD_REPEAT).getBytes();
		UserService registerLogic = ServiceFactory.getInstance().getUserService();
		//RegisterService registerLogic = ServiceFactory.getInstance().getRegisterService();
		boolean registerFlag = false;
		try {
			registerFlag = registerLogic.register(user, passwordRepeat);
			if(registerFlag) {
				page = Page.LOGIN;
			} else {
				request.setAttribute(ERROR_ATTR, 
						MessageManager.getProperty("message.registrationerror"));
			}
		} catch (ServiceException e) {
			// TODO error  page
		} catch (UserAlreadyExistException e) {
			request.setAttribute(ERROR_ATTR, 
					MessageManager.getProperty("message.userexisterror"));
		} catch (InvalidException e) {
			request.setAttribute(ERROR_ATTR, 
					MessageManager.getProperty("message.invalidregistrationerror"));
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
