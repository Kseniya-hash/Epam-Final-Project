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
import by.epamtc.dubovik.shop.service.util.RequestUtil;

public class CreateProductCommand implements ActionCommand {

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
			product = new Product();
			
			requestUtil.transferParametersFromRequest(request, product);
			productService.createProduct(product);
			
			product = productService.takeProductInfo(product.getName());
			request.setAttribute(ParameterName.PRODUCT, product);
			
			long productId = product.getId();
			Price price = new Price();
			price.setProductId(productId);
			price.setPurchasePrice(requestUtil.takePriceValue(request, ParameterName.PURCHASE_PRICE));
			price.setSellingPrice(requestUtil.takePriceValue(request, ParameterName.SELLING_PRICE));
			
			priceService.changePrice(price);
			
			price = priceService.takePriceByProduct(productId);
			request.setAttribute(ParameterName.PRICE, price);
			page = Page.PRODUCT;
		} catch (NumberFormatException e) {
			page = Page.ERROR404;
		} catch (ServiceException e) {
			page = Page.ERROR500;
		}
		
		request.getRequestDispatcher(page).forward(request, response);
	}

}
