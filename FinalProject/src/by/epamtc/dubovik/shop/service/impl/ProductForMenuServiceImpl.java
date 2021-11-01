package by.epamtc.dubovik.shop.service.impl;

import java.util.List;

import by.epamtc.dubovik.shop.dao.DAOException;
import by.epamtc.dubovik.shop.dao.DAOFactory;
import by.epamtc.dubovik.shop.dao.ProductForMenuDAO;
import by.epamtc.dubovik.shop.entity.ProductForMenu;
import by.epamtc.dubovik.shop.service.ProductForMenuService;
import by.epamtc.dubovik.shop.service.exception.ServiceException;
import by.epamtc.dubovik.shop.service.impl.sortproduct.ProductSort;
import by.epamtc.dubovik.shop.service.impl.sortproduct.SortMapping;
import by.epamtc.dubovik.shop.service.impl.sortproduct.SortType;

public class ProductForMenuServiceImpl implements ProductForMenuService {
	
	@Override
	public List<ProductForMenu> findSortedList(SortType type, int page, int count)
			throws ServiceException {
		
		List<ProductForMenu> products = null;
		DAOFactory factory = DAOFactory.getInstance();
		ProductForMenuDAO productForMenuDAO = factory.getProductForMenuDAO();
		ProductSort currentSorting = SortMapping.getInstance().takeByKey(type);
		try {
			int offset = (page - 1) * count;
			products = currentSorting.findList(productForMenuDAO, offset, count);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return products;
	}

	@Override
	public int countAll() throws ServiceException {
		int count = 0;
		DAOFactory factory = DAOFactory.getInstance();
		ProductForMenuDAO productForMenuDAO = factory.getProductForMenuDAO();
		try {
			count = productForMenuDAO.countAll();
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return count;
	}
}
