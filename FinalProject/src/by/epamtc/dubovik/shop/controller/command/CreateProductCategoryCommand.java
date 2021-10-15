package by.epamtc.dubovik.shop.controller.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epamtc.dubovik.shop.controller.Page;
import by.epamtc.dubovik.shop.controller.ParameterName;
import by.epamtc.dubovik.shop.entity.ProductCategory;
import by.epamtc.dubovik.shop.service.ProductCategoryService;
import by.epamtc.dubovik.shop.service.exception.ServiceException;
import by.epamtc.dubovik.shop.service.factory.ServiceFactory;
import by.epamtc.dubovik.shop.service.util.RequestUtil;

public class CreateProductCategoryCommand implements ActionCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String page = (String)session.getAttribute(ParameterName.PREVIOS_URL);
		ServiceFactory factory = ServiceFactory.getInstance();
		
		ProductCategoryService categoryService = factory.getProductCategoryService();
		RequestUtil requestUtil = factory.getRequestUtil();
		
		ProductCategory category = new ProductCategory();
		category.setName(requestUtil.takeString(request, ParameterName.PRODUCT_CATEGORY));
		try {
			categoryService.createCategory(category);
		} catch (ServiceException e) {
			page = Page.ERROR500;
		}
		request.getRequestDispatcher(page).forward(request, response);
	}

}
