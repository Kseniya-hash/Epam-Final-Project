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
import by.epamtc.dubovik.shop.entity.OrderForView;
import by.epamtc.dubovik.shop.service.OrderForViewService;
import by.epamtc.dubovik.shop.service.ServiceFactory;
import by.epamtc.dubovik.shop.service.exception.ServiceException;

public class ShowCustomerOrdersCommand implements ActionCommand {

	private static Logger logger = LogManager.getLogger();
	
	private static final int COUNT_ON_PAGE = 5;
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		OrderForViewService orderForViewService = 
				ServiceFactory.getInstance().getOrderForViewService();
		RequestUtil  requestUtil = 
				RequestUtilFactory.getInstance().getRequestUtil();
		
		List<OrderForView> orders = null;
		
		String page = Page.CUSTOMER_ORDERS;
		try {
			Integer currentPage = requestUtil
					.takeIntegerWithNull(request, ParameterName.PAGENUMBER);
			
			int lastPage = (int)Math
					.ceil((double)orderForViewService.countAll() / COUNT_ON_PAGE);
			
			currentPage = requestUtil.currentPage(currentPage, lastPage);
			orders = orderForViewService.findOrders(currentPage, COUNT_ON_PAGE);
			
			request.setAttribute(ParameterName.ORDERS, orders);
			request.setAttribute(ParameterName.PAGENUMBER, currentPage);
			request.setAttribute(ParameterName.LASTPAGENUMBER, lastPage);
		} catch (ServiceException e) {
			logger.error(e);
			
			page = Page.ERROR500;
		}

		request.getRequestDispatcher(page).forward(request, response);
		
	}

}
