package by.epamtc.dubovik.shop.servlet.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epamtc.dubovik.shop.entity.OrderForView;
import by.epamtc.dubovik.shop.service.OrderForViewService;
import by.epamtc.dubovik.shop.service.OrderService;
import by.epamtc.dubovik.shop.service.exception.InvalidException;
import by.epamtc.dubovik.shop.service.exception.ServiceException;
import by.epamtc.dubovik.shop.service.factory.ServiceFactory;
import by.epamtc.dubovik.shop.servlet.Page;
import by.epamtc.dubovik.shop.servlet.ParameterName;

public class ToPayOrderCommand implements ActionCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OrderForViewService orderForViewService = ServiceFactory.getInstance().getOrderForViewService();
		OrderService orderService = ServiceFactory.getInstance().getOrderService();
		OrderForView order = null;
		
		String page = Page.PAY_ORDER;
		try {
			int orderId = Integer.parseInt(request.getParameter(ParameterName.ORDER_ID));
			order = orderForViewService.takeById(orderId);
			request.setAttribute(ParameterName.ORDER, order);
			
			int price = orderService.calculatePrice(orderId);
			
			request.setAttribute(ParameterName.PRICE, price);
		} catch (ServiceException | InvalidException e) {
			page = Page.ERROR404;
		}
		request.getRequestDispatcher(page).forward(request, response);
		
	}

}
