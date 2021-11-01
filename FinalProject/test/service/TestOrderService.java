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
import by.epamtc.dubovik.shop.dao.OrderDAO;
import by.epamtc.dubovik.shop.entity.Cart;
import by.epamtc.dubovik.shop.entity.Order;
import by.epamtc.dubovik.shop.entity.Sale;
import by.epamtc.dubovik.shop.entity.Price;
import by.epamtc.dubovik.shop.service.OrderService;
import by.epamtc.dubovik.shop.service.PriceService;
import by.epamtc.dubovik.shop.service.ServiceFactory;
import by.epamtc.dubovik.shop.service.exception.InvalidException;
import by.epamtc.dubovik.shop.service.exception.ServiceException;

@PrepareForTest(fullyQualifiedNames = {QualifiedNamesMapping.DAO, 
		QualifiedNamesMapping.SERVICE})
@RunWith(PowerMockRunner.class)
public class TestOrderService {
	
	@Mock
	private DAOFactory daoFactory;
	
	@Mock
	private OrderDAO orderDAO;
	
	@Mock
	private ServiceFactory serviceFactory;
	
	@Mock
	private PriceService priceService;
	
	private OrderService orderService;
	
	@Before
	public void setUp() {
		PowerMockito.mockStatic(DAOFactory.class);
		PowerMockito.when(DAOFactory.getInstance())
				.thenReturn(daoFactory);
		PowerMockito.when(daoFactory.getOrderDAO())
				.thenReturn(orderDAO);
		
		orderService = ServiceFactory.getInstance()
				.getOrderService();
		
		PowerMockito.mockStatic(ServiceFactory.class);
		PowerMockito.when(ServiceFactory.getInstance())
				.thenReturn(serviceFactory);
		PowerMockito.when(serviceFactory.getPriceService())
				.thenReturn(priceService);
	}
	
	@Test
	public void makeOrderTest() {
		try {
			PowerMockito.when(orderDAO.create(Mockito.any()))
					.thenReturn(true);
			
			Cart cart = new Cart();
			cart.put(1, 1);
			cart.put(2, 3);
			
			boolean expected = true;
			boolean actual = orderService.makeOrder(1, cart);
			Assert.assertEquals(expected, actual);
		} catch (InvalidException | DAOException | ServiceException e) {
			Assert.fail(e.getMessage());
		} 
	}
	
	@Test
	public void makeOrderTestFalse() {
		try {
			PowerMockito.when(orderDAO.create(Mockito.any()))
					.thenReturn(false);
			
			Cart cart = new Cart();
			cart.put(1, 1);
			cart.put(2, 3);
			
			boolean expected = false;
			boolean actual = orderService.makeOrder(1, cart);
			Assert.assertEquals(expected, actual);
		} catch (InvalidException | DAOException | ServiceException e) {
			Assert.fail(e.getMessage());
		} 
	}
	
	@Test(expected = InvalidException.class)
	public void makeOrderTestNullCart() 
			throws DAOException, ServiceException, InvalidException {
		
		Cart cart = null;
			
		orderService.makeOrder(1, cart);
	}
	
	@Test(expected = InvalidException.class)
	public void makeOrderTestInvalidCart() 
			throws DAOException, ServiceException, InvalidException {
		
		Cart cart = new Cart();
		cart.put(1, -1);
		
		orderService.makeOrder(1, cart);
	}
	
	@Test(expected = ServiceException.class)
	public void makeOrderTestServiceException() 
			throws InvalidException, ServiceException, DAOException {
		
		PowerMockito.when(orderDAO.create(Mockito.any()))
				.thenThrow(DAOException.class);
			
		Cart cart = new Cart();
		cart.put(1, 1);
		cart.put(2, 3);
		
		orderService.makeOrder(1, cart);
	}

	@Test
	public void findOrderByIdTest() {
		try {
			Order current = new Order();
			current.setId(1);
			current.setUserId(2);
			List<Sale> sales = new ArrayList<>();
			sales.add(new Sale(1, 1, 1));
			sales.add(new Sale(1, 2, 3));
			current.setSales(sales);
			
			PowerMockito.when(orderDAO.findById(Mockito.anyLong()))
					.thenReturn(current);
			
			Order actual = orderService.findOrderById(1);
			Assert.assertEquals(current, actual);
		} catch (ServiceException | DAOException e) {
			Assert.fail(e.getMessage());
		} 
	}
	
	@Test
	public void findOrderByIdTestNullOrder() {
		try {
			Order current = null;
			
			PowerMockito.when(orderDAO.findById(Mockito.anyLong()))
					.thenReturn(current);
			
			Order actual = orderService.findOrderById(2);
			Assert.assertEquals(current, actual);
		} catch (ServiceException | DAOException e) {
			Assert.fail(e.getMessage());
		} 
	}
	
	@Test(expected = ServiceException.class)
	public void findOrderByIdTestServiceException() 
			throws ServiceException, DAOException {
		
		PowerMockito.when(orderDAO.findById(Mockito.anyLong()))
				.thenThrow(DAOException.class);
			
		orderService.findOrderById(2);
	}
	
	@Test
	public void calculatePriceTest() {
		try {
			Order current = new Order();
			current.setId(1);
			current.setUserId(2);
			List<Sale> sales = new ArrayList<>();
			sales.add(new Sale(1, 1, 1));
			sales.add(new Sale(1, 2, 3));
			current.setSales(sales);
			
			PowerMockito.when(orderDAO.findById(Mockito.anyLong()))
					.thenReturn(current);
			
			Price priceForFirst = new Price();
			priceForFirst.setId(1);
			priceForFirst.setProductId(1);
			priceForFirst.setPurchasePrice(1000);
			priceForFirst.setSellingPrice(1500);
			
			PowerMockito.when(priceService
					.findPriceByProduct(Mockito.eq(1L), Mockito.any()))
					.thenReturn(priceForFirst);
			
			Price priceForSecond = new Price();
			priceForSecond.setId(2);
			priceForSecond.setProductId(2);
			priceForSecond.setPurchasePrice(1200);
			priceForSecond.setSellingPrice(1700);
			
			PowerMockito.when(priceService
					.findPriceByProduct(Mockito.eq(2L), Mockito.any()))
					.thenReturn(priceForSecond);
			
			int expected = 1500 + 3 * 1700;
			int actual = orderService.calculatePrice(1);
			Assert.assertEquals(expected, actual);
		} catch (ServiceException | InvalidException | DAOException e) {
			Assert.fail(e.getMessage());
		} 
	}
	
	@Test(expected = InvalidException.class)
	public void calculatePriceTestNullOrder() 
			throws ServiceException, InvalidException, DAOException {
		
		PowerMockito.when(orderDAO.findById(Mockito.anyLong()))
				.thenReturn(null);
		
		orderService.calculatePrice(1);
	}
	
	@Test(expected = ServiceException.class)
	public void calculatePriceTestExceptionInfindPrice() 
			throws ServiceException, InvalidException, DAOException {
		
		Order current = new Order();
		current.setId(1);
		current.setUserId(2);
		List<Sale> sales = new ArrayList<>();
		sales.add(new Sale(1, 1, 1));
		sales.add(new Sale(1, 2, 3));
		current.setSales(sales);
		
		PowerMockito.when(orderDAO.findById(Mockito.anyLong()))
				.thenReturn(current);
		
		PowerMockito.when(priceService
				.findPriceByProduct(Mockito.anyLong(), Mockito.any()))
				.thenThrow(ServiceException.class);
		
		orderService.calculatePrice(1);
	}
	
	@Test(expected = ServiceException.class)
	public void calculatePriceTestExceptionInfindOrder() 
			throws ServiceException, InvalidException, DAOException {
		
		PowerMockito.when(orderDAO.findById(Mockito.anyLong()))
			.thenThrow(DAOException.class);
		
		orderService.calculatePrice(1);
	}
	
	@Test
	public void deliverOrderTest() {
		try {
			Order current = new Order();
			current.setId(1);
			current.setUserId(2);
			current.setOrderStatusId(2);
			List<Sale> sales = new ArrayList<>();
			sales.add(new Sale(1, 1, 1));
			sales.add(new Sale(1, 2, 3));
			current.setSales(sales);
			
			PowerMockito.when(orderDAO.findById(Mockito.anyLong()))
					.thenReturn(current);
			PowerMockito.when(orderDAO.update(Mockito.any()))
					.thenReturn(true);
			
			boolean expected = true;
			boolean actual = orderService.deliverOrder(1);
			Assert.assertEquals(expected, actual);
		} catch (ServiceException | DAOException e) {
			Assert.fail(e.getMessage());
		} 
	}
	
	@Test
	public void deliverOrderTestOrderNotPaid() {
		try {
			Order current = new Order();
			current.setId(1);
			current.setUserId(2);
			current.setOrderStatusId(1);
			List<Sale> sales = new ArrayList<>();
			sales.add(new Sale(1, 1, 1));
			sales.add(new Sale(1, 2, 3));
			current.setSales(sales);
			
			PowerMockito.when(orderDAO.findById(Mockito.anyLong()))
					.thenReturn(current);
			PowerMockito.when(orderDAO.update(Mockito.any()))
					.thenReturn(true);
			
			boolean expected = false;
			boolean actual = orderService.deliverOrder(1);
			Assert.assertEquals(expected, actual);
		} catch (ServiceException | DAOException e) {
			Assert.fail(e.getMessage());
		} 
	}
	
	@Test
	public void deliverOrderTestDAOReturnFalse() {
		try {
			Order current = new Order();
			current.setId(1);
			current.setUserId(2);
			current.setOrderStatusId(2);
			List<Sale> sales = new ArrayList<>();
			sales.add(new Sale(1, 1, 1));
			sales.add(new Sale(1, 2, 3));
			current.setSales(sales);
			
			PowerMockito.when(orderDAO.findById(Mockito.anyLong()))
					.thenReturn(current);
			PowerMockito.when(orderDAO.update(Mockito.any()))
					.thenReturn(false);
			
			boolean expected = false;
			boolean actual = orderService.deliverOrder(1);
			Assert.assertEquals(expected, actual);
		} catch (ServiceException | DAOException e) {
			Assert.fail(e.getMessage());
		} 
	}
	
	@Test
	public void deliverOrderTestOrderDontExist() {
		try {
			Order current = null;
			
			PowerMockito.when(orderDAO.findById(Mockito.anyLong()))
					.thenReturn(current);
			PowerMockito.when(orderDAO.update(Mockito.any()))
					.thenReturn(true);
			
			boolean expected = false;
			boolean actual = orderService.deliverOrder(1);
			Assert.assertEquals(expected, actual);
		} catch (ServiceException | DAOException e) {
			Assert.fail(e.getMessage());
		} 
	}
	
	@Test(expected = ServiceException.class)
	public void deliverOrderTestServiceException() 
			throws ServiceException, DAOException {
		
		PowerMockito.when(orderDAO.findById(Mockito.anyLong()))
				.thenThrow(DAOException.class);
			
		orderService.deliverOrder(1);
	}
}
