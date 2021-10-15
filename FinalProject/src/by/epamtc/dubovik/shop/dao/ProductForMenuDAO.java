package by.epamtc.dubovik.shop.dao;

import java.util.List;

import by.epamtc.dubovik.shop.entity.ProductForMenu;

public interface ProductForMenuDAO {

	public List<ProductForMenu> findSortedByRating (int offset, int count) throws DAOException;
	public List<ProductForMenu> findSortedByCommentCount (int offset, int count) throws DAOException;
	public List<ProductForMenu> findSortedByPriceInc (int offset, int count) throws DAOException;
	public List<ProductForMenu> findSortedByPriceDesc (int offset, int count) throws DAOException;
	public int countAll () throws DAOException;
	
}
