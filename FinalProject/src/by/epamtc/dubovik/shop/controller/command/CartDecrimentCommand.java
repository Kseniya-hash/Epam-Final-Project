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
import by.epamtc.dubovik.shop.entity.Cart;
import by.epamtc.dubovik.shop.service.CartService;
import by.epamtc.dubovik.shop.service.ServiceFactory;
import by.epamtc.dubovik.shop.service.exception.InvalidException;

public class CartDecrimentCommand implements ActionCommand {

	private static Logger logger = LogManager.getLogger();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		Cart cart = (Cart)session.getAttribute(ParameterName.CART);
		CartService cartService = ServiceFactory.getInstance().getCartService();
		cart = cartService.createIfDontExist(cart);
		String page = (String)session.getAttribute(ParameterName.PREVIOS_URL);
		
		try {
			long productId = Long.parseLong(request
					.getParameter(ParameterName.PRODUCT_ID));
			cartService.decrement(cart, productId);
			
			session.setAttribute(ParameterName.CART, cart);
		} catch (NumberFormatException | InvalidException  e) {
			logger.error(e);
			page = Page.ERROR500;
		}
		
		request.getRequestDispatcher(page).forward(request, response);
	}

}
