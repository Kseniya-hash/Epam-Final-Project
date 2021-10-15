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
import by.epamtc.dubovik.shop.service.util.RequestUtil;

public class ProductSortCommand implements ActionCommand {
	
	private final static int COUNT_ON_PAGE = 2;
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServiceFactory factory = ServiceFactory.getInstance();
		ProductForMenuService productForMenuService = factory.getProductForMenuService();
		RequestUtil  requestUtil = factory.getRequestUtil();
		
		List<ProductForMenu> products = null;
		String sortOption = request.getParameter(ParameterName.SORT);
		String page = Page.LIST;
		SortType type = SortType.valueOf(sortOption.toUpperCase());
		
		try {
			Integer currentPage = requestUtil.takeIntegerWithNull(request, ParameterName.PAGENUMBER);
			int lastPage = (int)Math.ceil((double)productForMenuService.countAll() / COUNT_ON_PAGE);
			currentPage = requestUtil.currentPage(currentPage, lastPage);
			
			products = productForMenuService.takeSortedList(type, currentPage, COUNT_ON_PAGE);
			
			request.setAttribute(ParameterName.SORT, type);
			request.setAttribute(ParameterName.PRODUCTS, products);
			
			request.setAttribute(ParameterName.PAGENUMBER, currentPage);
			request.setAttribute(ParameterName.LASTPAGENUMBER, lastPage);
		} catch (ServiceException e) {
			page = Page.ERROR500;
		}
	
		request.getRequestDispatcher(page).forward(request, response);
	}

}
