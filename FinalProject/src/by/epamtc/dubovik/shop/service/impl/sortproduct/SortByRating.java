package by.epamtc.dubovik.shop.service.impl.sortproduct;

import java.util.List;

import by.epamtc.dubovik.shop.dao.DAOException;
import by.epamtc.dubovik.shop.dao.ProductForMenuDAO;
import by.epamtc.dubovik.shop.entity.ProductForMenu;

public class SortByRating implements ProductSort {

	@Override
	public List<ProductForMenu> takeList(ProductForMenuDAO productForMenuDAO, int offset, int count)
			throws DAOException {
		return productForMenuDAO.findSortedByRating(offset, count);
	}

}
