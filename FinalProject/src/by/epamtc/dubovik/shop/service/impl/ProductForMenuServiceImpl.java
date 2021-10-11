package by.epamtc.dubovik.shop.service.impl;

import java.util.List;

import by.epamtc.dubovik.shop.dao.DAOException;
import by.epamtc.dubovik.shop.dao.ProductForMenuDAO;
import by.epamtc.dubovik.shop.dao.factory.DAOFactory;
import by.epamtc.dubovik.shop.entity.ProductForMenu;
import by.epamtc.dubovik.shop.service.ProductForMenuService;
import by.epamtc.dubovik.shop.service.exception.ServiceException;
import by.epamtc.dubovik.shop.service.impl.sortproduct.ProductSort;
import by.epamtc.dubovik.shop.service.impl.sortproduct.SortMapping;
import by.epamtc.dubovik.shop.service.impl.sortproduct.SortType;

public class ProductForMenuServiceImpl implements ProductForMenuService {

	//private static final int OFFSET = 0;
	private static final int COUNT = 3;
	
	@Override
	public List<ProductForMenu> takeSortedList(SortType type, int page) throws ServiceException {
		List<ProductForMenu> products = null;
		DAOFactory factory = DAOFactory.getInstance();
		ProductForMenuDAO productForMenuDAO = factory.getProductForMenuDAO();
		ProductSort currentSorting = SortMapping.getInstance().takeByKey(type);
		try {
			int offset = (page - 1) * COUNT;
			products = currentSorting.takeList(productForMenuDAO, offset, COUNT);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return products;
	}
}
