package by.epamtc.dubovik.shop.controller.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epamtc.dubovik.shop.controller.Page;
import by.epamtc.dubovik.shop.controller.ParameterName;
import by.epamtc.dubovik.shop.entity.Cart;
import by.epamtc.dubovik.shop.entity.ProductForCart;
import by.epamtc.dubovik.shop.service.CartService;
import by.epamtc.dubovik.shop.service.exception.InvalidException;
import by.epamtc.dubovik.shop.service.exception.ServiceException;
import by.epamtc.dubovik.shop.service.factory.ServiceFactory;

public class ShowCartCommand implements ActionCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String page = Page.CART;
		HttpSession session = request.getSession();
		Cart cart = (Cart)session.getAttribute(ParameterName.CART);
		if(cart == null) {
			cart = new Cart();
		}
		CartService cartService = ServiceFactory.getInstance().getCartService();
		List<ProductForCart> products = null;
		
		try {
			products = cartService.takeProducts(cart);
		} catch (ServiceException | InvalidException e) {
			page = Page.ERROR500;
		}
		request.setAttribute(ParameterName.PRODUCTS, products);
		request.getRequestDispatcher(page).forward(request, response);
		
	}
	
	

}
