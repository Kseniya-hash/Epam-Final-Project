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
import by.epamtc.dubovik.shop.dao.ProductCategoryDAO;
import by.epamtc.dubovik.shop.entity.ProductCategory;
import by.epamtc.dubovik.shop.service.ProductCategoryService;
import by.epamtc.dubovik.shop.service.ServiceFactory;
import by.epamtc.dubovik.shop.service.exception.ServiceException;

@PrepareForTest(fullyQualifiedNames = QualifiedNamesMapping.DAO)
@RunWith(PowerMockRunner.class)
public class TestProductCategoryService {

	@Mock
	private DAOFactory daoFactory;
	
	@Mock
	private ProductCategoryDAO productCategoryDAO;
	
	private ProductCategoryService productCategoryService;
	
	@Before
	public void setUp() {
		PowerMockito.mockStatic(DAOFactory.class);
		PowerMockito.when(DAOFactory.getInstance())
				.thenReturn(daoFactory);
		PowerMockito.when(daoFactory.getProductCategoryDAO())
				.thenReturn(productCategoryDAO);
		productCategoryService = ServiceFactory.getInstance()
				.getProductCategoryService();
	}
	
	@Test
	public void findOrdersAllTest() {
		try {
			List<ProductCategory> categories = new ArrayList<>();
			ProductCategory current = new ProductCategory(1, "phone");
			categories.add(current);
			current = new ProductCategory(2, "laptop");
			categories.add(current);
			
			PowerMockito.when(productCategoryDAO.findAll())
					.thenReturn(categories);
			
			List<ProductCategory> actual = productCategoryService
					.findAllCategories();
			Assert.assertArrayEquals(categories.toArray(), actual.toArray());
		} catch (ServiceException | DAOException e) {
			Assert.fail(e.getMessage());
		} 
	}
	
	@Test
	public void findOrdersAllTestEmpty() {
		try {
			List<ProductCategory> categories = new ArrayList<>();
			PowerMockito.when(productCategoryDAO.findAll())
					.thenReturn(categories);
			
			List<ProductCategory> actual = productCategoryService
					.findAllCategories();
			Assert.assertArrayEquals(categories.toArray(), actual.toArray());
		} catch (ServiceException | DAOException e) {
			Assert.fail(e.getMessage());
		} 
	}
	
	@Test(expected = ServiceException.class)
	public void findOrdersAllTestServiceException() 
			throws ServiceException, DAOException {
		
		PowerMockito.when(productCategoryDAO.findAll()).
				thenThrow(DAOException.class);
			
		productCategoryService.findAllCategories();
	}
	
	@Test
	public void findByNameTest() {
		try {
			ProductCategory current = new ProductCategory(1, "phone");
			
			PowerMockito.when(productCategoryDAO.findByName(Mockito.anyString()))
					.thenReturn(current);
			
			ProductCategory actual = productCategoryService.findByName("phone");
			Assert.assertEquals(current, actual);
		} catch (ServiceException | DAOException e) {
			Assert.fail(e.getMessage());
		} 
	}
	
	@Test
	public void findByNameTestNullOrder() {
		try {
			ProductCategory current = null;
			
			PowerMockito.when(productCategoryDAO.findByName(Mockito.anyString()))
					.thenReturn(current);
	
			ProductCategory actual = productCategoryService.findByName("phone");
			Assert.assertEquals(current, actual);
		} catch (ServiceException | DAOException e) {
			Assert.fail(e.getMessage());
		} 
	}
	
	@Test(expected = ServiceException.class)
	public void findByNameTestServiceException() 
			throws ServiceException, DAOException {
		
		PowerMockito.when(productCategoryDAO.findByName(Mockito.anyString()))
				.thenThrow(DAOException.class);
			
		productCategoryService.findByName("phone");
	}
	
	@Test
	public void findByIdTest() {
		try {
			ProductCategory current = new ProductCategory(1, "phone");
			
			PowerMockito.when(productCategoryDAO.findById(Mockito.anyLong()))
					.thenReturn(current);
			
			ProductCategory actual = productCategoryService.findById(1);
			Assert.assertEquals(current, actual);
		} catch (ServiceException | DAOException e) {
			Assert.fail(e.getMessage());
		} 
	}
	
	@Test
	public void findByIdTestNullOrder() {
		try {
			ProductCategory current = null;
			
			PowerMockito.when(productCategoryDAO.findById(Mockito.anyLong()))
					.thenReturn(current);
			
			ProductCategory actual = productCategoryService.findById(1);
			Assert.assertEquals(current, actual);
		} catch (ServiceException | DAOException e) {
			Assert.fail(e.getMessage());
		} 
	}
	
	@Test(expected = ServiceException.class)
	public void findByIdTestServiceException() 
			throws ServiceException, DAOException {
		
		PowerMockito.when(productCategoryDAO.findById(Mockito.anyLong()))
				.thenThrow(DAOException.class);
			
		productCategoryService.findById(1);
	}
	
	@Test
	public void createCategoryTest() {
		ProductCategory current = new ProductCategory(1, "phone");
			
		try {
			PowerMockito.when(productCategoryDAO.create(current))
					.thenReturn(true);
			
			boolean expected = true;
			boolean actual = productCategoryService.createCategory(current);
			Assert.assertEquals(expected, actual);
		} catch (DAOException | ServiceException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void createCommentTestDAOReturnFalse() {
		ProductCategory current = new ProductCategory(1, "phone");
			
		try {
			PowerMockito.when(productCategoryDAO.create(current))
					.thenReturn(false);
			
			boolean expected = false;
			boolean actual = productCategoryService.createCategory(current);
			Assert.assertEquals(expected, actual);
		} catch (DAOException | ServiceException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test(expected = ServiceException.class)
	public void createCommentTestServiceException() 
			throws DAOException, ServiceException {
		
		ProductCategory current = new ProductCategory(1, "phone");
		
		PowerMockito.when(productCategoryDAO.create(current))
				.thenThrow(DAOException.class);
		
		productCategoryService.createCategory(current);
	}
}
