package by.epamtc.dubovik.shop.controller.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epamtc.dubovik.shop.controller.Page;
import by.epamtc.dubovik.shop.controller.ParameterName;
import by.epamtc.dubovik.shop.controller.command.factory.CommandMapping;
import by.epamtc.dubovik.shop.entity.ProductCategory;
import by.epamtc.dubovik.shop.service.ProductCategoryService;
import by.epamtc.dubovik.shop.service.exception.ServiceException;
import by.epamtc.dubovik.shop.service.factory.ServiceFactory;

public class ToCreateProductCommand implements ActionCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute(ParameterName.COMMAND, CommandMapping.CREATE_PRODUCT);
		String page = Page.REDACT_PRODUCT;
		
		ServiceFactory factory = ServiceFactory.getInstance();
		ProductCategoryService categoryService = factory.getProductCategoryService();
		List<ProductCategory> categories = null;
		try {
			categories = categoryService.takeAllCategories();
		} catch (ServiceException e) {
			page = Page.ERROR404;
		}
		request.setAttribute(ParameterName.CATEGORIES, categories);
		request.getRequestDispatcher(page).forward(request, response);
		
	}

}
