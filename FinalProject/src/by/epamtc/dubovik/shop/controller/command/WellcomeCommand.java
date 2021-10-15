package by.epamtc.dubovik.shop.controller.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epamtc.dubovik.shop.controller.Page;
import by.epamtc.dubovik.shop.controller.ParameterName;
import by.epamtc.dubovik.shop.entity.ProductForMenu;
import by.epamtc.dubovik.shop.service.ProductForMenuService;
import by.epamtc.dubovik.shop.service.exception.ServiceException;
import by.epamtc.dubovik.shop.service.factory.ServiceFactory;
import by.epamtc.dubovik.shop.service.impl.sortproduct.SortType;

public class WellcomeCommand implements ActionCommand {
	
	private final static int COUNT = 3;
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String page = Page.LIST;
		ProductForMenuService productForMenuLogic = ServiceFactory.getInstance().getProductForMenuService();
		List<ProductForMenu> products = null;
		try {
			products = productForMenuLogic.takeSortedList(SortType.RATING, 1, COUNT);
		} catch (ServiceException e) {
			page = Page.ERROR404;
		}
		request.setAttribute(ParameterName.PRODUCTS, products);
		request.getRequestDispatcher(page).forward(request, response);
	}

}
