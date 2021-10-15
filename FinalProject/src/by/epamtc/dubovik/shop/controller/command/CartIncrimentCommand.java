package by.epamtc.dubovik.shop.controller.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epamtc.dubovik.shop.controller.Page;
import by.epamtc.dubovik.shop.controller.ParameterName;
import by.epamtc.dubovik.shop.entity.Cart;
import by.epamtc.dubovik.shop.service.exception.InvalidException;
import by.epamtc.dubovik.shop.service.exception.ServiceException;
import by.epamtc.dubovik.shop.service.factory.ServiceFactory;
import by.epamtc.dubovik.shop.service.resource.MessageManager;
import by.epamtc.dubovik.shop.service.CartService;

public class CartIncrimentCommand implements ActionCommand {
	
	private final static String ADD_TO_CART_ERROR = "modal.cannotaddtocart";
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String page = null;
		try {
			long productId = Long.parseLong(request.getParameter(ParameterName.PRODUCT_ID));
			
			HttpSession session = request.getSession();
			Cart cart = (Cart)session.getAttribute(ParameterName.CART);
			
			ServiceFactory factory = ServiceFactory.getInstance();
			CartService cartService = factory.getCartService();
			MessageManager messageManager = factory.getMessageManager();
			cart = cartService.createIfDontExist(cart);
			
			boolean isAdded = cartService.increment(cart, productId);
			if(!isAdded) {
				request.setAttribute(ParameterName.MODAL_MESSAGE, 
						messageManager.getProperty(ADD_TO_CART_ERROR, response.getLocale())
						+ cart.get(productId));
			}
			
			session.setAttribute(ParameterName.CART, cart);
			page = (String)session.getAttribute(ParameterName.PREVIOS_URL);
			if(page == null) {
				page = Page.INDEX;
			}
				
			
		} catch (NumberFormatException | ServiceException | InvalidException  e) {
			page = Page.ERROR500;
		}
		request.getRequestDispatcher(page).forward(request, response);
	}

}
