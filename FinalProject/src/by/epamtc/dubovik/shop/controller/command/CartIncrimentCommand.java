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
import by.epamtc.dubovik.shop.controller.util.MessageManager;
import by.epamtc.dubovik.shop.controller.util.RequestUtilFactory;
import by.epamtc.dubovik.shop.entity.Cart;
import by.epamtc.dubovik.shop.service.exception.InvalidException;
import by.epamtc.dubovik.shop.service.exception.ServiceException;
import by.epamtc.dubovik.shop.service.CartService;
import by.epamtc.dubovik.shop.service.ServiceFactory;

public class CartIncrimentCommand implements ActionCommand {
	
	private static Logger logger = LogManager.getLogger();
	
	private final static String ADD_TO_CART_ERROR = "modal.cannotaddtocart";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		Cart cart = (Cart)session.getAttribute(ParameterName.CART);
		
		ServiceFactory factory = ServiceFactory.getInstance();
		CartService cartService = factory.getCartService();
		cart = cartService.createIfDontExist(cart);
		
		String page = (String)session.getAttribute(ParameterName.PREVIOS_URL);
		
		try {
			long productId = Long.parseLong(request.getParameter(ParameterName.PRODUCT_ID));
			
			boolean isAdded = cartService.increment(cart, productId);
			if(!isAdded) {
				MessageManager messageManager = 
						RequestUtilFactory.getInstance().getMessageManager();
				request.setAttribute(ParameterName.MODAL_MESSAGE, 
						messageManager.getProperty(ADD_TO_CART_ERROR, response.getLocale())
						+ cart.get(productId));
			}
			
			session.setAttribute(ParameterName.CART, cart);
		} catch (NumberFormatException | ServiceException | InvalidException  e) {
			logger.error(e);
			page = Page.ERROR500;
		}

		request.getRequestDispatcher(page).forward(request, response);
	}

}
