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
import by.epamtc.dubovik.shop.entity.Cart;
import by.epamtc.dubovik.shop.entity.UserLogged;
import by.epamtc.dubovik.shop.service.OrderService;
import by.epamtc.dubovik.shop.service.ServiceFactory;
import by.epamtc.dubovik.shop.service.exception.InvalidException;
import by.epamtc.dubovik.shop.service.exception.ServiceException;

public class MakeOrderCommand implements ActionCommand {
	
	private static Logger logger = LogManager.getLogger();

	private final static String ORDER_ERROR = "modal.ordererror";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		Cart cart = (Cart)session.getAttribute(ParameterName.CART);
		UserLogged user = (UserLogged)session.getAttribute(ParameterName.USER);
		String page = Page.USER_ORDERS;
		
		ServiceFactory factory = ServiceFactory.getInstance();
		OrderService orderService  = factory.getOrderService();
		MessageManager messageManager = 
				RequestUtilFactory.getInstance().getMessageManager();
		
		try {
			boolean isOrdered = orderService.makeOrder(user.getId(), cart);
			if(!isOrdered) {
				request.setAttribute(ParameterName.MODAL_MESSAGE, 
						messageManager.getProperty(ORDER_ERROR, response.getLocale()));
				page = Page.CART;
			}
			session.setAttribute(ParameterName.CART, null);
		} catch (InvalidException e) {
			logger.error(e);
			
			page = Page.ERROR404;
		} catch (ServiceException e) {
			page = Page.ERROR500;
		}
		
		request.getRequestDispatcher(page).forward(request, response);
	}

}
