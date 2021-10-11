package by.epamtc.dubovik.shop.servlet.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epamtc.dubovik.shop.entity.ProductForMenu;
import by.epamtc.dubovik.shop.service.ProductForMenuService;
import by.epamtc.dubovik.shop.service.exception.ServiceException;
import by.epamtc.dubovik.shop.service.factory.ServiceFactory;
import by.epamtc.dubovik.shop.service.impl.sortproduct.SortType;
import by.epamtc.dubovik.shop.servlet.Page;
import by.epamtc.dubovik.shop.servlet.ParameterName;

public class WellcomeCommand implements ActionCommand {
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String page = Page.LIST;
		ProductForMenuService productForMenuLogic = ServiceFactory.getInstance().getProductForMenuService();
		List<ProductForMenu> products = null;
		try {
			products = productForMenuLogic.takeSortedList(SortType.RATING, 1);
		} catch (ServiceException e) {
			page = Page.ERROR404;
		}
		request.setAttribute(ParameterName.PRODUCTS, products);
		request.getRequestDispatcher(page).forward(request, response);
	}

}
