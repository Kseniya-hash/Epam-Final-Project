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
import by.epamtc.dubovik.shop.dao.ProductDAO;
import by.epamtc.dubovik.shop.dao.ProductForCartDAO;
import by.epamtc.dubovik.shop.entity.Cart;
import by.epamtc.dubovik.shop.entity.Product;
import by.epamtc.dubovik.shop.entity.ProductForCart;
import by.epamtc.dubovik.shop.service.CartService;
import by.epamtc.dubovik.shop.service.ServiceFactory;
import by.epamtc.dubovik.shop.service.exception.InvalidException;
import by.epamtc.dubovik.shop.service.exception.ServiceException;

@PrepareForTest(fullyQualifiedNames = QualifiedNamesMapping.DAO)
@RunWith(PowerMockRunner.class)
public class TestCartService {
	
	@Mock
	private DAOFactory daoFactory;
	    
	@Mock
	private ProductDAO productDAO;
	 
	@Mock
	private ProductForCartDAO productForCartDAO;

	private CartService cartService;
	 
	@Before
	public void setUp() {
		PowerMockito.mockStatic(DAOFactory.class);
		PowerMockito.when(DAOFactory.getInstance())
				.thenReturn(daoFactory);
		PowerMockito.when(daoFactory.getProductDAO())
				.thenReturn(productDAO);
		PowerMockito.when(daoFactory.getProductForCartDAO())
				.thenReturn(productForCartDAO);
		cartService = ServiceFactory.getInstance().getCartService();
	}
	
	@Test
	public void incrementTest() {
		try {
			Product product = new Product();
			product.setQuantity(5);
			
			PowerMockito.when(productDAO.findById(Mockito.anyLong()))
					.thenReturn(product);
			
			Cart expectedCart = new Cart();
			expectedCart.put(1, 1);
			boolean expected = true;
			
			Cart actualCart = new Cart();
			boolean actual = cartService.increment(actualCart, 1);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expectedCart, actualCart);
		} catch (ServiceException | InvalidException | DAOException e) {
			Assert.fail(e.getMessage());
		} 
	}
	
	@Test
	public void incrementTestQuantityNotEnought() {
		try {
			Product product = new Product();
			product.setQuantity(1);
		
			PowerMockito.when(productDAO.findById(Mockito.anyLong()))
					.thenReturn(product);
			
			Cart expectedCart = new Cart();
			expectedCart.put(1, 1);
			boolean expected = false;
			
			Cart actualCart = new Cart();
			actualCart.put(1, 1);
			boolean actual = cartService.increment(actualCart, 1);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expectedCart, actualCart);
		} catch (ServiceException | InvalidException | DAOException e) {
			Assert.fail(e.getMessage());
		} 
	}
	
	@Test
	public void incrementTestProductDontExist() {
		try {
			PowerMockito.when(productDAO.findById(Mockito.anyLong()))
					.thenReturn(null);
			Cart expectedCart = new Cart();
			boolean expected = false;
			
			Cart actualCart = new Cart();
			boolean actual = cartService.increment(actualCart, 1);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expectedCart, actualCart);
		} catch (ServiceException | InvalidException | DAOException e) {
			Assert.fail(e.getMessage());
		} 
	}
	
	@Test(expected = ServiceException.class)
	public void incrementTestThrowServiceException() 
			throws ServiceException, InvalidException, DAOException {
		PowerMockito.when(productDAO.findById(Mockito.anyLong()))
				.thenThrow(DAOException.class);
		
		Cart cart = new Cart();
		cartService.increment(cart, 1);
	}
	
	@Test(expected = InvalidException.class)
	public void incrementTestThrowInvalidException() 
			throws ServiceException, InvalidException, DAOException {
		Cart cart = new Cart();
		cart.put(1, -1);
		cartService.increment(cart, 1);
	}
	
	@Test(expected = InvalidException.class)
	public void incrementTestNullCart() 
			throws ServiceException, InvalidException, DAOException {
		Cart cart = null;
		cartService.increment(cart, 1);
	}
	
	@Test
	public void decrimentTest() {
		try {
			Cart expectedCart = new Cart();
			expectedCart.put(1, 2);
			boolean expected = true;
			
			Cart actualCart = new Cart();
			actualCart.put(1, 3);
			
			boolean actual = cartService.decrement(actualCart, 1);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expectedCart, actualCart);
		} catch (InvalidException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void decrimentTestZeroProductInCart() {
		try {
			Cart expectedCart = new Cart();
			expectedCart.put(1, 0);
			boolean expected = false;
			
			Cart actualCart = new Cart();
			actualCart.put(1, 0);
			
			boolean actual = cartService.decrement(actualCart, 1);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expectedCart, actualCart);
		} catch (InvalidException e) {
			Assert.fail(e.getMessage());
		} 
	}
	
	@Test
	public void decrimentTestNoProductInCart() {
		try {
			Cart expectedCart = new Cart();
			expectedCart.put(1, 3);
			boolean expected = false;
			
			Cart actualCart = new Cart();
			actualCart.put(1, 3);
			
			boolean actual = cartService.decrement(actualCart, 2);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expectedCart, actualCart);
		} catch (InvalidException e) {
			Assert.fail(e.getMessage());
		} 
	}
	
	@Test
	public void decrimentTestEmptyCart() {
		try {
			Cart expectedCart = new Cart();
			boolean expected = false;
			
			Cart actualCart = new Cart();
			
			boolean actual = cartService.decrement(actualCart, 2);
			Assert.assertEquals(expected, actual);
			Assert.assertEquals(expectedCart, actualCart);
		} catch (InvalidException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test(expected = InvalidException.class)
	public void decrimentTestNullCart() throws InvalidException {
		Cart cart = null;
		cartService.decrement(cart, 1);
	}
	
	@Test(expected = InvalidException.class)
	public void decrimentTestInvalidCart() throws InvalidException {
		Cart cart = new Cart();
		cart.put(1, -1);
		cartService.decrement(cart, 1);
	}
	
	@Test
	public void removeTest() {
		try {
			Cart expectedCart = new Cart();
			expectedCart.put(1, 3);
			
			Cart actualCart = new Cart();
			actualCart.put(1, 3);
			actualCart.put(2, 2);
			
			cartService.remove(actualCart, 2);
			Assert.assertEquals(expectedCart, actualCart);
		} catch (InvalidException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void removeTestNoProductInCart() {
		try {
			Cart expectedCart = new Cart();
			expectedCart.put(1, 3);
			
			Cart actualCart = new Cart();
			actualCart.put(1, 3);
			
			cartService.remove(actualCart, 2);
			Assert.assertEquals(expectedCart, actualCart);
		} catch (InvalidException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void removeTestEmptyCart() {
		try {
			Cart expectedCart = new Cart();
			
			Cart actualCart = new Cart();
			
			cartService.remove(actualCart, 2);
			Assert.assertEquals(expectedCart, actualCart);
		} catch (InvalidException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test(expected = InvalidException.class)
	public void removeTestNullCart() throws InvalidException {
		Cart actualCart = null;
		cartService.remove(actualCart, 2);
	}
	
	@Test(expected = InvalidException.class)
	public void removeTestInvalidCart() throws InvalidException {
		Cart actualCart = new Cart();
		actualCart.put(2, -1);
		cartService.remove(actualCart, 2);
	}
	
	@Test
	public void createIfDontExistTest() {
		Cart expected = new Cart();
		expected.put(2,  1);
		Cart actual = new Cart();
		actual.put(2,  1);		
		actual = cartService.createIfDontExist(actual);
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void createIfDontExistTestEmptyCart() {
		Cart expected = new Cart();
		Cart actual = new Cart();
		actual = cartService.createIfDontExist(actual);
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void createIfDontExistTestNullCart() {
		Cart expected = new Cart();
		Cart actual = null;
		actual = cartService.createIfDontExist(actual);
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void takeProductsTest() {
		try {
			ProductForCart product = new ProductForCart();
			product.setId(1);
			product.setQuantity(5);
			PowerMockito.when(productForCartDAO.findById(1))
					.thenReturn(product);
			product = new ProductForCart();
			product.setId(2);
			product.setQuantity(3);
			PowerMockito.when(productForCartDAO.findById(2))
					.thenReturn(product);
			
			List<ProductForCart> expected = new ArrayList<>();
			ProductForCart productForExpected = new ProductForCart();
			productForExpected.setId(1);
			productForExpected.setQuantity(1);
			expected.add(productForExpected);
			productForExpected = new ProductForCart();
			productForExpected.setId(2);
			productForExpected.setQuantity(3);
			expected.add(productForExpected);
			
			Cart cart = new Cart();
			cart.put(1, 1);
			cart.put(2, 3);
			List<ProductForCart> actual = cartService.takeProducts(cart);
			Assert.assertArrayEquals(expected.toArray(), actual.toArray());
		} catch (ServiceException | InvalidException | DAOException e) {
			Assert.fail(e.getMessage());
		} 
	}
	
	@Test
	public void takeProductsTestProductNotEnought() {
		try {
			ProductForCart product = new ProductForCart();
			product.setId(1);
			product.setQuantity(5);
			PowerMockito.when(productForCartDAO.findById(1))
					.thenReturn(product);
			product = new ProductForCart();
			product.setId(2);
			product.setQuantity(2);
			PowerMockito.when(productForCartDAO.findById(2))
					.thenReturn(product);
			
			List<ProductForCart> expected = new ArrayList<>();
			ProductForCart productForExpected = new ProductForCart();
			productForExpected.setId(1);
			productForExpected.setQuantity(1);
			expected.add(productForExpected);
			productForExpected = new ProductForCart();
			productForExpected.setId(2);
			productForExpected.setQuantity(2);
			expected.add(productForExpected);
			
			Cart cart = new Cart();
			cart.put(1, 1);
			cart.put(2, 3);
			List<ProductForCart> actual = cartService.takeProducts(cart);
			Assert.assertArrayEquals(expected.toArray(), actual.toArray());
		} catch (ServiceException | InvalidException | DAOException e) {
			Assert.fail(e.getMessage());
		} 
	}
	
	@Test
	public void takeProductsTestProductDontExist() {
		try {
			ProductForCart productId1 = new ProductForCart();
			productId1.setId(1);
			productId1.setQuantity(5);
			PowerMockito.when(productForCartDAO.findById(1))
					.thenReturn(productId1);
			
			List<ProductForCart> expected = new ArrayList<>();
			ProductForCart productForExpected = new ProductForCart();
			productForExpected.setId(1);
			productForExpected.setQuantity(1);
			expected.add(productForExpected);
			
			Cart cart = new Cart();
			cart.put(1, 1);
			cart.put(2, 3);
			List<ProductForCart> actual = cartService.takeProducts(cart);
			Assert.assertArrayEquals(expected.toArray(), actual.toArray());
		} catch (ServiceException | InvalidException | DAOException e) {
			Assert.fail(e.getMessage());
		} 
	}
	
	@Test(expected = InvalidException.class)
	public void takeProductsTestNullCart() 
			throws InvalidException, ServiceException {
		Cart cart = null;
		cartService.takeProducts(cart);
	}
	
	@Test(expected = InvalidException.class)
	public void takeProductsTestInvalidCart() 
			throws InvalidException, ServiceException {
		Cart cart = new Cart();
		cart.put(1, -1);
		cartService.takeProducts(cart);
	}
	
	@Test(expected = ServiceException.class)
	public void takeProductsTestServiceException() 
			throws InvalidException, ServiceException, DAOException {
		PowerMockito.when(productForCartDAO.findById(1)).thenThrow(DAOException.class);
		Cart cart = new Cart();
		cart.put(1, 1);
		cartService.takeProducts(cart);
	}
}
