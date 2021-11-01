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
import by.epamtc.dubovik.shop.service.OrderService;
import by.epamtc.dubovik.shop.service.ServiceFactory;
import by.epamtc.dubovik.shop.service.exception.InvalidException;
import by.epamtc.dubovik.shop.service.exception.ServiceException;

public class PayForOrderCommand implements ActionCommand {
	
	private static Logger logger = LogManager.getLogger();
	
	private final static String CARD_ERROR = "modal.cardnumbererror";
	private final static String PAY_ERROR = "modal.payerror";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		String page = "Controller?command=user_orders";
		String message = null;
		
		ServiceFactory factory = ServiceFactory.getInstance();
		OrderService orderService = factory.getOrderService();
		MessageManager messageManager = 
				RequestUtilFactory.getInstance().getMessageManager();
		
		try {
			long cardNumber = Long.parseLong(request.getParameter(ParameterName.CARD));
			long orderId = Long.parseLong(request.getParameter(ParameterName.ORDER_ID));
			
			try {
				boolean isPaid = orderService.payForOrder(orderId, cardNumber);
				if(!isPaid) {
					message = PAY_ERROR;
				}
			} catch (ServiceException | InvalidException e) {
				logger.error(e);
				
				page = Page.ERROR500;
			}
		} catch (NumberFormatException e) {
			logger.error(e);
			
			message = CARD_ERROR;
		}
		if(message != null) {
			request.setAttribute(ParameterName.MODAL_MESSAGE, 
					messageManager.getProperty(message, response.getLocale()));
		}

		request.getRequestDispatcher(page).forward(request, response);
	}

}
