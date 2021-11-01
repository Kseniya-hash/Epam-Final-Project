package by.epamtc.dubovik.shop.controller.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epamtc.dubovik.shop.controller.Page;
import by.epamtc.dubovik.shop.controller.ParameterName;
import by.epamtc.dubovik.shop.entity.OrderForView;
import by.epamtc.dubovik.shop.entity.UserLogged;
import by.epamtc.dubovik.shop.service.OrderForViewService;
import by.epamtc.dubovik.shop.service.ServiceFactory;
import by.epamtc.dubovik.shop.service.exception.ServiceException;

public class ShowUserOrdersCommand implements ActionCommand {
	
	private static Logger logger = LogManager.getLogger();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		OrderForViewService orderForViewService = ServiceFactory.getInstance()
				.getOrderForViewService();
		List<OrderForView> orders = null;
		HttpSession session = request.getSession();
		UserLogged user = (UserLogged)session.getAttribute(ParameterName.USER);
		String page = null;
		try {
			orders = orderForViewService.findOrders(user.getId());
			request.setAttribute(ParameterName.ORDERS, orders);
			page = Page.USER_ORDERS;
		} catch (ServiceException e) {
			logger.error(e);
			
			page = Page.ERROR500;
		}
		
		request.getRequestDispatcher(page).forward(request, response);
		
	}

}
