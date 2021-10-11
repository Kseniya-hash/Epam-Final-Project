package by.epamtc.dubovik.shop.servlet.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epamtc.dubovik.shop.entity.Cart;
import by.epamtc.dubovik.shop.entity.ProductForCart;
import by.epamtc.dubovik.shop.service.CartService;
import by.epamtc.dubovik.shop.service.exception.InvalidException;
import by.epamtc.dubovik.shop.service.exception.ServiceException;
import by.epamtc.dubovik.shop.service.factory.ServiceFactory;
import by.epamtc.dubovik.shop.servlet.Page;
import by.epamtc.dubovik.shop.servlet.ParameterName;

public class ShowCartCommand implements ActionCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		Cart cart = (Cart)session.getAttribute(ParameterName.CART);
		CartService cartService = ServiceFactory.getInstance().getCartService();
		List<ProductForCart> products = null;
		
		try {
			products = cartService.takeProducts(cart);
		} catch (ServiceException | InvalidException e) {
			// TODO ERROR PAGE
		}
		
		String page = Page.CART;
		request.setAttribute(ParameterName.PRODUCTS, products);
		request.getRequestDispatcher(page).forward(request, response);
		
	}
	
	

}
