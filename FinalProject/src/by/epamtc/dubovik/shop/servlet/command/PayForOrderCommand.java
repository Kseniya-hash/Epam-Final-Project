package by.epamtc.dubovik.shop.servlet.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epamtc.dubovik.shop.service.OrderService;
import by.epamtc.dubovik.shop.service.exception.InvalidException;
import by.epamtc.dubovik.shop.service.exception.ServiceException;
import by.epamtc.dubovik.shop.service.factory.ServiceFactory;
import by.epamtc.dubovik.shop.servlet.Page;
import by.epamtc.dubovik.shop.servlet.ParameterName;

public class PayForOrderCommand implements ActionCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String page = "Controller?command=user_orders";
		try {
			long cardNumber = Long.parseLong(request.getParameter(ParameterName.CARD));
			int orderId = Integer.parseInt(request.getParameter(ParameterName.ORDER_ID));
			
			OrderService orderService = ServiceFactory.getInstance().getOrderService();
			try {
				boolean isPaid = orderService.payForOrder(orderId, cardNumber);
			} catch (ServiceException e) {
				page = Page.ERROR404;
			} catch (InvalidException e) {
				page = Page.ERROR404;
			}
		} catch (NumberFormatException e) {
			page = Page.ERROR404;
		}
		request.getRequestDispatcher(page).forward(request, response);
	}

}
