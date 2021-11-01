package by.epamtc.dubovik.shop.controller.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epamtc.dubovik.shop.controller.Page;
import by.epamtc.dubovik.shop.controller.ParameterName;
import by.epamtc.dubovik.shop.controller.util.MessageManager;
import by.epamtc.dubovik.shop.controller.util.RequestUtil;
import by.epamtc.dubovik.shop.controller.util.RequestUtilFactory;
import by.epamtc.dubovik.shop.entity.Price;
import by.epamtc.dubovik.shop.entity.Product;
import by.epamtc.dubovik.shop.service.PriceService;
import by.epamtc.dubovik.shop.service.ProductService;
import by.epamtc.dubovik.shop.service.ServiceFactory;
import by.epamtc.dubovik.shop.service.exception.InvalidException;
import by.epamtc.dubovik.shop.service.exception.ServiceException;

public class CreateProductCommand implements ActionCommand {
	
	private static Logger logger = LogManager.getLogger();

	private final static String INVALID_PRODUCT = "modal.product";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		ServiceFactory factory = ServiceFactory.getInstance();
		
		ProductService productService = factory.getProductService();
		PriceService priceService = factory.getPriceService();
		RequestUtilFactory requestUtilFactory = RequestUtilFactory.getInstance();
		RequestUtil requestUtil = requestUtilFactory.getRequestUtil();
		
		Product product = null;
		String page = null;
		
		try {
			product = new Product();
			
			requestUtil.takeProduct(request, product);
			productService.createProduct(product);
			
			product = productService.findProductInfo(product.getName());
			request.setAttribute(ParameterName.PRODUCT, product);
			
			long productId = product.getId();
			Price price = new Price();
			price.setProductId(productId);
			price.setPurchasePrice(requestUtil
					.takePriceValue(request, ParameterName.PURCHASE_PRICE));
			price.setSellingPrice(requestUtil
					.takePriceValue(request, ParameterName.SELLING_PRICE));
			
			priceService.changePrice(price);
			
			price = priceService.findPriceByProduct(productId);
			request.setAttribute(ParameterName.PRICE, price);
			page = Page.PRODUCT;
		} catch (InvalidException e) {
			MessageManager messageManager = requestUtilFactory.getMessageManager();
			request.setAttribute(ParameterName.MODAL_MESSAGE, 
					messageManager.getProperty(INVALID_PRODUCT, response.getLocale()));
		} catch (NumberFormatException | ServiceException e) {
			logger.error(e);
			
			page = Page.ERROR500;
		}
		
		request.getRequestDispatcher(page).forward(request, response);
	}

}
