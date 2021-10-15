package by.epamtc.dubovik.shop.controller.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epamtc.dubovik.shop.controller.Page;
import by.epamtc.dubovik.shop.controller.ParameterName;
import by.epamtc.dubovik.shop.entity.Cart;
import by.epamtc.dubovik.shop.service.CartService;
import by.epamtc.dubovik.shop.service.factory.ServiceFactory;

public class CartRemoveCommand implements ActionCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String page = null;
		try {
			long productId = Long.parseLong(request.getParameter(ParameterName.PRODUCT_ID));
			HttpSession session = request.getSession();
			Cart cart = (Cart)session.getAttribute(ParameterName.CART);
			CartService cartService = ServiceFactory.getInstance().getCartService();
			cart = cartService.createIfDontExist(cart);
			
			cartService.remove(cart, productId);
			
			session.setAttribute(ParameterName.CART, cart);
			page = (String)session.getAttribute(ParameterName.PREVIOS_URL);
			if(page == null) {
				page = Page.INDEX;
			}
				
			
		} catch (NumberFormatException e) {
			page = Page.ERROR500;
		}
		request.getRequestDispatcher(page).forward(request, response);
		
	}

}
