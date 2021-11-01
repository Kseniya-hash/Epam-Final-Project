package service;

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
import by.epamtc.dubovik.shop.dao.ProductDAO;
import by.epamtc.dubovik.shop.entity.Product;
import by.epamtc.dubovik.shop.service.ProductService;
import by.epamtc.dubovik.shop.service.ServiceFactory;
import by.epamtc.dubovik.shop.service.exception.InvalidException;
import by.epamtc.dubovik.shop.service.exception.ServiceException;

@PrepareForTest(fullyQualifiedNames = QualifiedNamesMapping.DAO)
@RunWith(PowerMockRunner.class)
public class TestProductService {

	@Mock
	private DAOFactory daoFactory;
	
	@Mock
	private ProductDAO productDAO;
	
	private ProductService productService;
	
	private Product currentProduct;
	
	@Before
	public void setUpProduct() {
		currentProduct = new Product();
		currentProduct.setId(1);
		currentProduct.setName("Самокат Ridex");
		currentProduct.setCategoryId(1);
		currentProduct.setPhotoPath("image.jpg");
		currentProduct.setHigh(100);
		currentProduct.setQuantity(2);
	}
	
	@Before
	public void setUp() {
		PowerMockito.mockStatic(DAOFactory.class);
		PowerMockito.when(DAOFactory.getInstance())
				.thenReturn(daoFactory);
		PowerMockito.when(daoFactory.getProductDAO())
				.thenReturn(productDAO);
		productService = ServiceFactory.getInstance()
				.getProductService();
	}
	
	@Test
	public void findProductInfoTest() {
		try {
			PowerMockito.when(productDAO.findById(Mockito.anyLong()))
					.thenReturn(currentProduct);
			
			Product actual = productService.findProductInfo(1);
			Assert.assertEquals(currentProduct, actual);
		} catch (ServiceException | DAOException e) {
			Assert.fail(e.getMessage());
		} 
	}
	
	@Test
	public void findProductInfoTestNullProduct() {
		try {
			Product current = null;
			
			PowerMockito.when(productDAO.findById(Mockito.anyLong()))
					.thenReturn(current);
			
			Product actual = productService.findProductInfo(1);
			Assert.assertEquals(current, actual);
		} catch (ServiceException | DAOException e) {
			Assert.fail(e.getMessage());
		} 
	}
	
	@Test(expected = ServiceException.class)
	public void findProductInfoTestServiceException() 
			throws ServiceException, DAOException {
		
		PowerMockito.when(productDAO
				.findById(Mockito.anyLong()))
				.thenThrow(DAOException.class);
			
		productService.findProductInfo(1);
	}
	
	@Test
	public void findProductInfoByNameTest() {
		try {
			PowerMockito.when(productDAO.findByName(Mockito.anyString()))
					.thenReturn(currentProduct);
			
			Product actual = productService.findProductInfo("Самокат Ridex");
			Assert.assertEquals(currentProduct, actual);
		} catch (ServiceException | DAOException e) {
			Assert.fail(e.getMessage());
		} 
	}
	
	@Test
	public void findProductInfoByNameTestNullProduct() {
		try {
			Product current = null;
			
			PowerMockito.when(productDAO.findByName(Mockito.anyString()))
			.thenReturn(current);
	
			Product actual = productService.findProductInfo("Самокат Ridex");
			Assert.assertEquals(current, actual);
		} catch (ServiceException | DAOException e) {
			Assert.fail(e.getMessage());
		} 
	}
	
	@Test(expected = ServiceException.class)
	public void findProductInfoByNameTestServiceException() 
			throws ServiceException, DAOException {
		
		PowerMockito.when(productDAO
				.findByName(Mockito.anyString()))
				.thenThrow(DAOException.class);
			
		productService.findProductInfo("Самокат Ridex");
	}
	
	@Test
	public void updateProductInfoTest() {
		try {
			PowerMockito.when(productDAO.update(Mockito.any()))
					.thenReturn(true);
			boolean expected = true;
			boolean actual = productService
					.updateProductInfo(currentProduct);
			Assert.assertEquals(expected, actual);
		} catch (InvalidException | DAOException | ServiceException e) {
			Assert.fail(e.getMessage());
		} 
	}
	
	@Test
	public void updateProductInfoTestDAOReturnFalse() {
		try {
			PowerMockito.when(productDAO.update(Mockito.any()))
					.thenReturn(false);
			
			boolean expected = false;
			boolean actual = productService
					.updateProductInfo(currentProduct);
			Assert.assertEquals(expected, actual);
		} catch (InvalidException | DAOException | ServiceException e) {
			Assert.fail(e.getMessage());
		} 
	}
	
	@Test(expected = InvalidException.class)
	public void updateProductInfoTestInvalidProduct() 
			throws ServiceException, InvalidException {
		
		Product product = new Product();
		product.setQuantity(-1);
		
		productService.updateProductInfo(product);
	}
	
	@Test(expected = InvalidException.class)
	public void updateProductInfoTestNullProduct()
			throws ServiceException, InvalidException {
		
		Product current = null;
		
		productService.updateProductInfo(current);
	}
	
	@Test(expected = ServiceException.class)
	public void updateProductInfoTestServiceException()
			throws ServiceException, InvalidException, DAOException {
		
		PowerMockito.when(productDAO.update(Mockito.any()))
				.thenThrow(DAOException.class);

		productService.updateProductInfo(currentProduct);
	}
	
	@Test
	public void createProductTest() {
		try {
			PowerMockito.when(productDAO.create(Mockito.any()))
					.thenReturn(true);
			
			boolean expected = true;
			boolean actual = productService
					.createProduct(currentProduct);
			Assert.assertEquals(expected, actual);
		} catch (InvalidException | DAOException | ServiceException e) {
			Assert.fail(e.getMessage());
		} 
	}
	
	@Test
	public void createProductTestDAOReturnFalse() {
		try {
			PowerMockito.when(productDAO.create(Mockito.any()))
					.thenReturn(false);
			
			boolean expected = false;
			boolean actual = productService
					.createProduct(currentProduct);
			Assert.assertEquals(expected, actual);
		} catch (InvalidException | DAOException | ServiceException e) {
			Assert.fail(e.getMessage());
		} 
	}
	
	@Test(expected = InvalidException.class)
	public void createProductTestInvalidProduct() 
			throws ServiceException, InvalidException {
		
		Product product = new Product();
		product.setQuantity(-1);
		
		productService.createProduct(product);
	}
	
	@Test(expected = InvalidException.class)
	public void createProductTestNullProduct()
			throws ServiceException, InvalidException {
		
		Product current = null;
		
		productService.createProduct(current);
	}
	
	@Test(expected = ServiceException.class)
	public void createProductTestServiceExceptiont()
			throws ServiceException, InvalidException, DAOException {
		
		PowerMockito.when(productDAO.create(Mockito.any()))
				.thenThrow(DAOException.class);

		productService.createProduct(currentProduct);
	}
}
