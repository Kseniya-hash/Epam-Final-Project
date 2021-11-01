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
import by.epamtc.dubovik.shop.controller.command.factory.CommandMapping;
import by.epamtc.dubovik.shop.entity.Price;
import by.epamtc.dubovik.shop.entity.Product;
import by.epamtc.dubovik.shop.entity.ProductCategory;
import by.epamtc.dubovik.shop.service.PriceService;
import by.epamtc.dubovik.shop.service.ProductCategoryService;
import by.epamtc.dubovik.shop.service.ProductService;
import by.epamtc.dubovik.shop.service.ServiceFactory;
import by.epamtc.dubovik.shop.service.exception.ServiceException;

public class ToRedactProductCommand implements ActionCommand {
	
	private static Logger logger = LogManager.getLogger();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		ServiceFactory factory = ServiceFactory.getInstance();
		
		ProductService productService = factory.getProductService();
		PriceService priceService = factory.getPriceService();
		ProductCategoryService categoryService = factory.getProductCategoryService();
		
		Product product = null;
		Price price = null;
		List<ProductCategory> categories = null;
		String page = Page.REDACT_PRODUCT;
		
		try {
			long productId = Long.parseLong(request
					.getParameter(ParameterName.PRODUCT_ID));
			
			product = productService.findProductInfo(productId);
			price = priceService.findPriceByProduct(productId);
			categories = categoryService.findAllCategories();
			
			request.setAttribute(ParameterName.PRODUCT, product);
			request.setAttribute(ParameterName.PRICE, price);
			request.setAttribute(ParameterName.CATEGORIES, categories);
			
			request.setAttribute(ParameterName.COMMAND, CommandMapping.REDACT_PRODUCT);
		} catch (NumberFormatException | ServiceException e) {
			logger.error(e);
			
			page = Page.ERROR404;
		}
	
		request.getRequestDispatcher(page).forward(request, response);
	}

}
