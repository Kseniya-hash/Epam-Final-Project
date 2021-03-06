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
import by.epamtc.dubovik.shop.service.OrderService;
import by.epamtc.dubovik.shop.service.ServiceFactory;
import by.epamtc.dubovik.shop.service.exception.ServiceException;

public class DeliverOrderCommand implements ActionCommand {
	
	private static Logger logger = LogManager.getLogger();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		OrderService orderService = ServiceFactory.getInstance().getOrderService();
		HttpSession session = request.getSession();
		String page = (String)session.getAttribute(ParameterName.PREVIOS_URL);
		
		try {
			long orderId = Long.parseLong(request
					.getParameter(ParameterName.ORDER_ID));
			orderService.deliverOrder(orderId);
		} catch (ServiceException e) {
			logger.error(e);
			
			page = Page.ERROR500;
		}
		
		request.getRequestDispatcher(page).forward(request, response);
	}

}
