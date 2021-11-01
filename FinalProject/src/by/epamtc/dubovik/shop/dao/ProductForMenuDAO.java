package by.epamtc.dubovik.shop.dao;

import java.util.List;

import by.epamtc.dubovik.shop.entity.ProductForMenu;

public interface ProductForMenuDAO {

	/**
	 * Find a list of products sorted by rating. 
	 * If offset is too big returns an empty list.
	 * @param offset - count of products to skip at the beginning
	 * @param count - count of products to return
	 * @return list of products for menu
	 * @throws DAOException
	 */
	public List<ProductForMenu> findSortedByRating (int offset, int count) 
			throws DAOException;
	
	/**
	 * Find a list of products sorted by comment count decreasing. 
	 * If offset is too big returns an empty list.
	 * @param offset - count of products to skip at the beginning
	 * @param count - count of products to return
	 * @return list of products for menu
	 * @throws DAOException
	 */
	public List<ProductForMenu> findSortedByCommentCount (int offset, int count) 
			throws DAOException;
	
	/**
	 * Find a list of products sorted by price increasing. 
	 * If offset is too big returns an empty list.
	 * @param offset - count of products to skip at the beginning
	 * @param count - count of products to return
	 * @return list of products for menu
	 * @throws DAOException
	 */
	public List<ProductForMenu> findSortedByPriceInc (int offset, int count) 
			throws DAOException;
	
	/**
	 * Find a list of products sorted by price decreasing. 
	 * If offset is too big returns an empty list.
	 * @param offset - count of products to skip at the beginning
	 * @param count - count of products to return
	 * @return list of products for menu
	 * @throws DAOException
	 */
	public List<ProductForMenu> findSortedByPriceDesc (int offset, int count) 
			throws DAOException;
	
	/**
	 * Count all products.
	 * @return count of products
	 * @throws DAOException
	 */
	public int countAll () throws DAOException;
	
}
