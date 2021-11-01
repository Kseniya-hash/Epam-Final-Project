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
import by.epamtc.dubovik.shop.controller.util.RequestUtil;
import by.epamtc.dubovik.shop.controller.util.RequestUtilFactory;
import by.epamtc.dubovik.shop.entity.ProductForMenu;
import by.epamtc.dubovik.shop.service.ProductForMenuService;
import by.epamtc.dubovik.shop.service.ServiceFactory;
import by.epamtc.dubovik.shop.service.exception.ServiceException;
import by.epamtc.dubovik.shop.service.impl.sortproduct.SortType;

public class ProductSortCommand implements ActionCommand {
	
	private static Logger logger = LogManager.getLogger();
	
	private final static int COUNT_ON_PAGE = 2;
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		ProductForMenuService productForMenuService = 
				ServiceFactory.getInstance().getProductForMenuService();
		RequestUtil  requestUtil = RequestUtilFactory.getInstance().getRequestUtil();
		
		List<ProductForMenu> products = null;
		String sortOption = request.getParameter(ParameterName.SORT);
		String page = Page.LIST;
		SortType type = SortType.valueOf(sortOption.toUpperCase());
		
		try {
			Integer currentPage = requestUtil
					.takeIntegerWithNull(request, ParameterName.PAGENUMBER);
			int lastPage = (int)Math
					.ceil((double)productForMenuService.countAll() / COUNT_ON_PAGE);
			currentPage = requestUtil.currentPage(currentPage, lastPage);
			products = productForMenuService
					.findSortedList(type, currentPage, COUNT_ON_PAGE);
			request.setAttribute(ParameterName.SORT, type);
			request.setAttribute(ParameterName.PRODUCTS, products);
			request.setAttribute(ParameterName.PAGENUMBER, currentPage);
			request.setAttribute(ParameterName.LASTPAGENUMBER, lastPage);
		} catch (ServiceException e) {
			logger.error(e);
			
			page = Page.ERROR500;
		}
	
		request.getRequestDispatcher(page).forward(request, response);
	}

}
