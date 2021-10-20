package by.epamtc.dubovik.shop.controller.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epamtc.dubovik.shop.controller.Page;
import by.epamtc.dubovik.shop.controller.ParameterName;
import by.epamtc.dubovik.shop.entity.Price;
import by.epamtc.dubovik.shop.entity.Product;
import by.epamtc.dubovik.shop.service.PriceService;
import by.epamtc.dubovik.shop.service.ProductService;
import by.epamtc.dubovik.shop.service.exception.ServiceException;
import by.epamtc.dubovik.shop.service.factory.ServiceFactory;
import by.epamtc.dubovik.shop.service.resource.MessageManager;
import by.epamtc.dubovik.shop.service.util.RequestUtil;

public class RedactProductCommand implements ActionCommand {

	private final static String PRODUCT_ERROR = "modal.producterror";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServiceFactory factory = ServiceFactory.getInstance();
		
		ProductService productService = factory.getProductService();
		PriceService priceService = factory.getPriceService();
		RequestUtil requestUtil = factory.getRequestUtil();
		
		Product product = null;
		String page = null;
		try {
			long productId = Long.parseLong(request.getParameter(ParameterName.PRODUCT_ID));
			product = productService.takeProductInfo(productId);
			
			requestUtil.transferParametersFromRequest(request, product);
			productService.redactProductInfo(product);
			
			product = productService.takeProductInfo(productId);
			request.setAttribute(ParameterName.PRODUCT, product);
			
			Price price = priceService.takePriceByProduct(productId);
			price.setPurchasePrice(requestUtil.takePriceValue(request, ParameterName.PURCHASE_PRICE));
			price.setSellingPrice(requestUtil.takePriceValue(request, ParameterName.SELLING_PRICE));
			
			priceService.changePrice(price);
			
			price = priceService.takePriceByProduct(productId);
			request.setAttribute(ParameterName.PRICE, price);
			page = Page.PRODUCT;
		} catch (NumberFormatException e){
			MessageManager messageManager = factory.getMessageManager();
			request.setAttribute(ParameterName.MODAL_MESSAGE, 
					messageManager.getProperty(PRODUCT_ERROR, response.getLocale()));
		} catch (ServiceException e) {
			page = Page.ERROR500;
		}
		
		request.getRequestDispatcher(page).forward(request, response);
	}
}
