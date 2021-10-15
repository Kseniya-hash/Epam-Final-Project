package by.epamtc.dubovik.shop.service;

import java.util.List;

import by.epamtc.dubovik.shop.entity.ProductForMenu;
import by.epamtc.dubovik.shop.service.exception.ServiceException;
import by.epamtc.dubovik.shop.service.impl.sortproduct.SortType;

public interface ProductForMenuService {

	public List<ProductForMenu> takeSortedList(SortType type, int page, int count) throws ServiceException;
	public int countAll() throws ServiceException;
	
}
