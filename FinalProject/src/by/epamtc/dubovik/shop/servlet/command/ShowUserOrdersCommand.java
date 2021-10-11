package by.epamtc.dubovik.shop.servlet.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epamtc.dubovik.shop.entity.OrderForView;
import by.epamtc.dubovik.shop.entity.UserLogged;
import by.epamtc.dubovik.shop.service.OrderForViewService;
import by.epamtc.dubovik.shop.service.exception.ServiceException;
import by.epamtc.dubovik.shop.service.factory.ServiceFactory;
import by.epamtc.dubovik.shop.servlet.Page;
import by.epamtc.dubovik.shop.servlet.ParameterName;

public class ShowUserOrdersCommand implements ActionCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OrderForViewService orderForViewService = ServiceFactory.getInstance().getOrderForViewService();
		List<OrderForView> orders = null;
		
		HttpSession session = request.getSession();
		UserLogged user = (UserLogged)session.getAttribute(ParameterName.USER);
		String page = null;
		try {
			orders = orderForViewService.takeUserOrders(user.getId());
			page = Page.USER_ORDERS;
		} catch (ServiceException e) {
			page = Page.ERROR404;
		}
		request.setAttribute(ParameterName.ORDERS, orders);
		request.getRequestDispatcher(page).forward(request, response);
		
	}

}
