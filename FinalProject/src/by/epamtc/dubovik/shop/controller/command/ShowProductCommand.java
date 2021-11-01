package by.epamtc.dubovik.shop.controller.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epamtc.dubovik.shop.controller.Page;
import by.epamtc.dubovik.shop.controller.ParameterName;
import by.epamtc.dubovik.shop.entity.Comment;
import by.epamtc.dubovik.shop.entity.Price;
import by.epamtc.dubovik.shop.entity.Product;
import by.epamtc.dubovik.shop.service.CommentService;
import by.epamtc.dubovik.shop.service.PriceService;
import by.epamtc.dubovik.shop.service.ProductService;
import by.epamtc.dubovik.shop.service.ServiceFactory;
import by.epamtc.dubovik.shop.service.exception.ServiceException;

public class ShowProductCommand implements ActionCommand {
	
	private static Logger logger = LogManager.getLogger();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		ServiceFactory factory  = ServiceFactory.getInstance();
		
		ProductService productService = factory.getProductService();
		PriceService priceService = factory.getPriceService();
		CommentService commentService = factory.getCommentService();
		
		String page = Page.PRODUCT;
		Product product = null;
		Price price = null;
		List<Comment> comments = null;
		try {
			long productId = Long.parseLong(request
					.getParameter(ParameterName.PRODUCT_ID));
			
			product = productService.findProductInfo(productId);
			price = priceService.findPriceByProduct(productId);
			comments = commentService.findComments(productId);
			
			request.setAttribute(ParameterName.PRODUCT, product);
			request.setAttribute(ParameterName.PRICE, price);
			request.setAttribute(ParameterName.COMMENTS, comments);
		} catch (NumberFormatException e) {
			logger.error(e);
			
			page = Page.ERROR404;
		} catch (ServiceException e) {
			logger.error(e);
			
			page = Page.ERROR500;
		}

		request.getRequestDispatcher(page).forward(request, response);
	}

}
