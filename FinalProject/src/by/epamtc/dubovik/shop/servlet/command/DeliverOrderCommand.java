package by.epamtc.dubovik.shop.servlet.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epamtc.dubovik.shop.service.OrderService;
import by.epamtc.dubovik.shop.service.exception.InvalidException;
import by.epamtc.dubovik.shop.service.exception.ServiceException;
import by.epamtc.dubovik.shop.service.factory.ServiceFactory;
import by.epamtc.dubovik.shop.servlet.Page;
import by.epamtc.dubovik.shop.servlet.ParameterName;

public class DeliverOrderCommand implements ActionCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		OrderService orderService = ServiceFactory.getInstance().getOrderService();
		HttpSession session = request.getSession();
		String page = (String)session.getAttribute(ParameterName.PREVIOS_URL);
		if(page == null) {
			page = Page.INDEX;
		}
		try {
			int orderId = Integer.parseInt(request.getParameter(ParameterName.ORDER_ID));
			orderService.deliverOrder(orderId);
		} catch (NumberFormatException e) {
			page = Page.ERROR404;
		} catch (ServiceException e) {
			page = Page.ERROR404;
		} catch (InvalidException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.getRequestDispatcher(page).forward(request, response);
		
	}

}
