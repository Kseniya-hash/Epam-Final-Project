package by.epamtc.dubovik.shop.service;

import java.util.List;

import by.epamtc.dubovik.shop.entity.ProductForMenu;
import by.epamtc.dubovik.shop.service.exception.ServiceException;
import by.epamtc.dubovik.shop.service.impl.sortproduct.SortType;

public interface ProductForMenuService {

	/**
	 * Find a list of products for specific page given the amount of products on one page.
	 * If no products for this page is found return an empty list
	 * @param type - sort type: RATING - by rating decreasing
	 * 							COMMENT_COUNT - by comment count decreasing
	 * 							PRICE_INC - by price increasing
	 * 							PRICE_DESC - by price decreasing
	 * @param page
	 * @param count
	 * @return
	 * @throws ServiceException
	 */
	public List<ProductForMenu> findSortedList(SortType type, int page, int count) 
			throws ServiceException;
	
	/**
	 * Count all products.
	 * @return count of products=
	 * @throws ServiceException
	 */
	public int countAll() throws ServiceException;
	
}
