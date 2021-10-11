package by.epamtc.dubovik.shop.servlet.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epamtc.dubovik.shop.entity.Cart;
import by.epamtc.dubovik.shop.entity.UserLogged;
import by.epamtc.dubovik.shop.service.OrderService;
import by.epamtc.dubovik.shop.service.exception.InvalidException;
import by.epamtc.dubovik.shop.service.exception.ServiceException;
import by.epamtc.dubovik.shop.service.factory.ServiceFactory;
import by.epamtc.dubovik.shop.servlet.Page;
import by.epamtc.dubovik.shop.servlet.ParameterName;

public class MakeOrderCommand implements ActionCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Cart cart = (Cart)session.getAttribute(ParameterName.CART);
		UserLogged user = (UserLogged)session.getAttribute(ParameterName.USER);
		String page = null;
		OrderService orderService  = ServiceFactory.getInstance().getOrderService();
		try {
			boolean isOrdered = orderService.makeOrder(user.getId(), cart);
			if(!isOrdered) {
				request.setAttribute(ParameterName.MODAL_MESSAGE, "К сожалению вы не можете сделать закааз, так как часть "
						+ "товаров закончилась.");
			}
			session.setAttribute(ParameterName.CART, null);
		} catch (ServiceException | InvalidException e) {
			page = Page.ERROR404;
		}
		
		page = Page.CART;
		request.getRequestDispatcher(page).forward(request, response);
	}

}
