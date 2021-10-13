package by.epamtc.dubovik.shop.servlet.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epamtc.dubovik.shop.entity.Cart;
import by.epamtc.dubovik.shop.service.CartService;
import by.epamtc.dubovik.shop.service.factory.ServiceFactory;
import by.epamtc.dubovik.shop.servlet.Page;
import by.epamtc.dubovik.shop.servlet.ParameterName;

public class CartRemoveCommand implements ActionCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String page = null;
		int productId = 0;
		try {
			productId = Integer.parseInt(request.getParameter(ParameterName.PRODUCT_ID));
			
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
			page = Page.ERROR404;
			//TODO: other error page 
		}
		request.getRequestDispatcher(page).forward(request, response);
		
	}

}
