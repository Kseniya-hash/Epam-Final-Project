package by.epamtc.dubovik.shop.servlet.command;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epamtc.dubovik.shop.entity.Price;
import by.epamtc.dubovik.shop.entity.Product;
import by.epamtc.dubovik.shop.service.PriceService;
import by.epamtc.dubovik.shop.service.ProductService;
import by.epamtc.dubovik.shop.service.exception.ServiceException;
import by.epamtc.dubovik.shop.service.factory.ServiceFactory;
import by.epamtc.dubovik.shop.servlet.Page;
import by.epamtc.dubovik.shop.servlet.ParameterName;

public class RedactProductCommand implements ActionCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ProductService productService = ServiceFactory.getInstance().getProductService();
		PriceService priceService = ServiceFactory.getInstance().getPriceService();
		Product product = null;
		String page = null;
		
		try {
			int productId = Integer.parseInt(request.getParameter(ParameterName.PRODUCT_ID));
			product = productService.takeProductInfo(productId);
			System.out.println("command:" + request.getCharacterEncoding());
			//transferParametersFromRequest(request, product);
			
			product.setName(request.getParameter(ParameterName.PRODUCT_NAME));
			System.out.println(product.getName());
			product.setDescription(request.getParameter(ParameterName.PRODUCT_DESCRIPTION).trim()); 
			byte[] b = product.getDescription().getBytes(StandardCharsets.ISO_8859_1);
			product.setDescription(new String(b, StandardCharsets.UTF_8));
			System.out.println("d   " + product.getDescription());
			product.setWeight(takeIntegerWithNull(request, ParameterName.PRODUCT_WEIGHT));
			product.setLength(takeIntegerWithNull(request, ParameterName.PRODUCT_LENGTH));
			product.setHigh(takeIntegerWithNull(request, ParameterName.PRODUCT_HIGH));
			product.setWidth(takeIntegerWithNull(request, ParameterName.PRODUCT_WIDTH));
			product.setQuantity(takeIntegerWithNull(request, ParameterName.PRODUCT_QUANTITY));
			productService.redactProductInfo(product);
			
			Price price = new Price();
			price.setPurchasePrice(takePrice(request, ParameterName.PURCHASE_PRICE));
			price.setPurchasePrice(takePrice(request, ParameterName.SELLING_PRICE));
			priceService.changePrice(price);
			
			product = productService.takeProductInfo(productId);
			page = Page.PRODUCT;
		} catch (NumberFormatException | ServiceException e) {
			page = Page.ERROR404;
			//TODO: other error page 
		}
		
		request.setAttribute(ParameterName.PRODUCT, product);
		request.getRequestDispatcher(page).forward(request, response);
	}
	
	private void transferParametersFromRequest(HttpServletRequest request, Product product) {
		product.setName(request.getParameter(ParameterName.PRODUCT_NAME));
		product.setDescription(request.getParameter(ParameterName.PRODUCT_DESCRIPTION).trim());
		product.setWeight(takeIntegerWithNull(request, ParameterName.PRODUCT_WEIGHT));
		product.setLength(takeIntegerWithNull(request, ParameterName.PRODUCT_LENGTH));
		product.setHigh(takeIntegerWithNull(request, ParameterName.PRODUCT_HIGH));
		product.setWidth(takeIntegerWithNull(request, ParameterName.PRODUCT_WIDTH));
		product.setWidth(takeIntegerWithNull(request, ParameterName.PRODUCT_QUANTITY));
	}
	
	private Integer takeIntegerWithNull(HttpServletRequest request, String paramName){
		String str = request.getParameter(paramName);
		Integer result = null;
		
		if( str != null && str.length() > 0) {
			 result = Integer.parseInt(str);
		}
		
		return result;
	}
	
	private int takePrice(HttpServletRequest request, String paramName){
		String str = request.getParameter(paramName);
		int price = 0;
		
		if( str != null && str.length() > 0) {
			double toDouble = Double.parseDouble(str);
			price = (int)(toDouble * 100);
		}
		
		return price;
	}

}
