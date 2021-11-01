package service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import by.epamtc.dubovik.shop.dao.DAOException;
import by.epamtc.dubovik.shop.dao.DAOFactory;
import by.epamtc.dubovik.shop.dao.ProductForMenuDAO;
import by.epamtc.dubovik.shop.entity.ProductForMenu;
import by.epamtc.dubovik.shop.service.ProductForMenuService;
import by.epamtc.dubovik.shop.service.ServiceFactory;
import by.epamtc.dubovik.shop.service.exception.ServiceException;
import by.epamtc.dubovik.shop.service.impl.sortproduct.SortType;

@PrepareForTest(fullyQualifiedNames = QualifiedNamesMapping.DAO)
@RunWith(PowerMockRunner.class)
public class TestProductForMenuService {

	@Mock
	private DAOFactory daoFactory;
	
	@Mock
	private ProductForMenuDAO productForMenuDAO;
	
	private ProductForMenuService productForMenuService;
	
	@Before
	public void setUp() {
		PowerMockito.mockStatic(DAOFactory.class);
		PowerMockito.when(DAOFactory.getInstance())
				.thenReturn(daoFactory);
		PowerMockito.when(daoFactory.getProductForMenuDAO())
				.thenReturn(productForMenuDAO);
		productForMenuService = ServiceFactory.getInstance()
				.getProductForMenuService();
	}
	
	@Test
	public void findSortedListTestByRating() {
		try {
			List<ProductForMenu> products = new ArrayList<>();
			ProductForMenu current = new ProductForMenu();
			current.setId(1);
			current.setName("Product");
			current.setRating(5);
			current.setSellingPrice(10000);
			products.add(current);
			current = new ProductForMenu();
			current.setId(2);
			current.setName("Product2");
			current.setRating(4);
			current.setCommentCount(3);
			current.setSellingPrice(11000);
			products.add(current);
			
			PowerMockito.when(productForMenuDAO
					.findSortedByRating(Mockito.anyInt(), Mockito.anyInt()))
					.thenReturn(products);
			
			List<ProductForMenu> actual = productForMenuService
					.findSortedList(SortType.RATING, 1, 2);
			Assert.assertArrayEquals(products.toArray(), actual.toArray());
		} catch (ServiceException | DAOException e) {
			Assert.fail(e.getMessage());
		} 
	}
	
	@Test
	public void findSortedListTestPriceInc() {
		try {
			List<ProductForMenu> products = new ArrayList<>();
			ProductForMenu current = new ProductForMenu();
			current.setId(1);
			current.setName("Product");
			current.setRating(5);
			current.setSellingPrice(10000);
			products.add(current);
			current = new ProductForMenu();
			current.setId(2);
			current.setName("Product2");
			current.setRating(4);
			current.setCommentCount(3);
			current.setSellingPrice(11000);
			products.add(current);
			
			PowerMockito.when(productForMenuDAO
					.findSortedByPriceInc(Mockito.anyInt(), Mockito.anyInt()))
					.thenReturn(products);
			
			List<ProductForMenu> actual = productForMenuService
					.findSortedList(SortType.PRICE_INC, 1, 2);
			Assert.assertArrayEquals(products.toArray(), actual.toArray());
		} catch (ServiceException | DAOException e) {
			Assert.fail(e.getMessage());
		} 
	}
	
	@Test
	public void findSortedListTestPriceDesc() {
		try {
			List<ProductForMenu> products = new ArrayList<>();
			ProductForMenu current = new ProductForMenu();
			current.setId(2);
			current.setName("Product2");
			current.setRating(4);
			current.setCommentCount(3);
			current.setSellingPrice(11000);
			products.add(current);
			current = new ProductForMenu();
			current.setId(1);
			current.setName("Product");
			current.setRating(5);
			current.setSellingPrice(10000);
			products.add(current);
			
			PowerMockito.when(productForMenuDAO
					.findSortedByPriceDesc(Mockito.anyInt(), Mockito.anyInt()))
					.thenReturn(products);
			
			List<ProductForMenu> actual = productForMenuService
					.findSortedList(SortType.PRICE_DESC, 1, 2);
			Assert.assertArrayEquals(products.toArray(), actual.toArray());
		} catch (ServiceException | DAOException e) {
			Assert.fail(e.getMessage());
		} 
	}
	
	@Test
	public void findSortedListTestCommentCount() {
		try {
			List<ProductForMenu> products = new ArrayList<>();
			ProductForMenu current = new ProductForMenu();
			current.setId(2);
			current.setName("Product2");
			current.setRating(4);
			current.setCommentCount(3);
			current.setSellingPrice(11000);
			products.add(current);
			current = new ProductForMenu();
			current.setId(1);
			current.setName("Product");
			current.setRating(5);
			current.setSellingPrice(10000);
			products.add(current);
			
			PowerMockito.when(productForMenuDAO
					.findSortedByCommentCount(Mockito.anyInt(), Mockito.anyInt()))
					.thenReturn(products);
			
			List<ProductForMenu> actual = productForMenuService
					.findSortedList(SortType.COMMENT_COUNT, 1, 2);
			Assert.assertArrayEquals(products.toArray(), actual.toArray());
		} catch (ServiceException | DAOException e) {
			Assert.fail(e.getMessage());
		} 
	}
	
	@Test
	public void findSortedListTestEmptyList() {
		try {
			List<ProductForMenu> products = new ArrayList<>();
			
			PowerMockito.when(productForMenuDAO
					.findSortedByPriceInc(Mockito.anyInt(), Mockito.anyInt()))
					.thenReturn(products);
			
			List<ProductForMenu> actual = productForMenuService
					.findSortedList(SortType.COMMENT_COUNT, 1, 2);
			Assert.assertArrayEquals(products.toArray(), actual.toArray());
		} catch (ServiceException | DAOException e) {
			Assert.fail(e.getMessage());
		} 
	}
	
	@Test(expected = ServiceException.class)
	public void findSortedListTestServiceException() 
			throws DAOException, ServiceException {
		
		PowerMockito.when(productForMenuDAO
				.findSortedByPriceInc(Mockito.anyInt(), Mockito.anyInt()))
				.thenThrow(DAOException.class);
			
		productForMenuService.findSortedList(SortType.PRICE_INC, 1, 2);
	}
	
	@Test
	public void countAllTest() {
		try {
			int expected = 10;
			PowerMockito.when(productForMenuDAO.countAll())
					.thenReturn(expected);
			
			int actual = productForMenuService.countAll();
			Assert.assertEquals(expected, actual);
		} catch (ServiceException | DAOException e) {
			Assert.fail(e.getMessage());
		} 
	}
	
	@Test(expected = ServiceException.class)
	public void countAllTestServiceException() 
			throws ServiceException, DAOException {
		
		PowerMockito.when(productForMenuDAO.countAll())
				.thenThrow(DAOException.class);
			
		productForMenuService.countAll();
	}
}
