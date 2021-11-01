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
import by.epamtc.dubovik.shop.dao.OrderForViewDAO;
import by.epamtc.dubovik.shop.entity.OrderForView;
import by.epamtc.dubovik.shop.service.OrderForViewService;
import by.epamtc.dubovik.shop.service.ServiceFactory;
import by.epamtc.dubovik.shop.service.exception.ServiceException;

@PrepareForTest(fullyQualifiedNames = QualifiedNamesMapping.DAO)
@RunWith(PowerMockRunner.class)
public class TestOrderForViewService {
	
	@Mock
	private DAOFactory daoFactory;
	
	@Mock
	private OrderForViewDAO orderForViewDAO;
	
	private OrderForViewService orderForViewService;
	
	@Before
	public void setUp() {
		PowerMockito.mockStatic(DAOFactory.class);
		PowerMockito.when(DAOFactory.getInstance())
				.thenReturn(daoFactory);
		PowerMockito.when(daoFactory.getOrderForViewDAO())
				.thenReturn(orderForViewDAO);
		orderForViewService = ServiceFactory.getInstance()
				.getOrderForViewService();
	}
	
	@Test
	public void findOrdersForUserTest() {
		try {
			List<OrderForView> orders = new ArrayList<>();
			OrderForView current = new OrderForView();
			current.setId(1);
			current.setUserId(2);
			current.setUserLogin("vict");
			orders.add(current);
			current = new OrderForView();
			current.setId(2);
			current.setUserId(2);
			current.setUserLogin("vict");
			orders.add(current);
			
			PowerMockito.when(orderForViewDAO.findByUser(Mockito.anyLong()))
					.thenReturn(orders);
			
			List<OrderForView> actual = orderForViewService.findOrders(2);
			Assert.assertArrayEquals(orders.toArray(), actual.toArray());
		} catch (ServiceException | DAOException e) {
			Assert.fail(e.getMessage());
		} 
	}
	
	@Test
	public void findOrdersForUserTestEmpty() {
		try {
			List<OrderForView> orders = new ArrayList<>();
			PowerMockito.when(orderForViewDAO.findByUser(Mockito.anyLong()))
					.thenReturn(orders);
			
			List<OrderForView> actual = orderForViewService.findOrders(2);
			Assert.assertArrayEquals(orders.toArray(), actual.toArray());
		} catch (ServiceException | DAOException e) {
			Assert.fail(e.getMessage());
		} 
	}
	
	@Test(expected = ServiceException.class)
	public void findOrdersForUserTestServiceException() 
			throws ServiceException, DAOException {
		PowerMockito.when(orderForViewDAO.findByUser(Mockito.anyLong()))
				.thenThrow(DAOException.class);
			
		orderForViewService.findOrders(2);
	}
	
	@Test
	public void findOrdersAllTest() {
		try {
			List<OrderForView> orders = new ArrayList<>();
			OrderForView current = new OrderForView();
			current.setId(1);
			current.setUserId(2);
			current.setUserLogin("vict");
			orders.add(current);
			current = new OrderForView();
			current.setId(2);
			current.setUserId(3);
			current.setUserLogin("mari");
			orders.add(current);
			
			PowerMockito.when(orderForViewDAO.findAll(0, 2))
					.thenReturn(orders);
			
			List<OrderForView> actual = orderForViewService.findOrders(1, 2);
			Assert.assertArrayEquals(orders.toArray(), actual.toArray());
		} catch (ServiceException | DAOException e) {
			Assert.fail(e.getMessage());
		} 
	}
	
	@Test
	public void findOrdersAllTestEmpty() {
		try {
			List<OrderForView> orders = new ArrayList<>();
			PowerMockito.when(orderForViewDAO.findAll(0, 2))
					.thenReturn(orders);
			
			List<OrderForView> actual = orderForViewService.findOrders(1, 2);
			Assert.assertArrayEquals(orders.toArray(), actual.toArray());
		} catch (ServiceException | DAOException e) {
			Assert.fail(e.getMessage());
		} 
	}
	
	@Test(expected = ServiceException.class)
	public void findOrdersAllTestServiceException() 
			throws ServiceException, DAOException {
		PowerMockito.when(orderForViewDAO.findAll(0, 2))
				.thenThrow(DAOException.class);
			
		orderForViewService.findOrders(1, 2);
	}

	
	@Test
	public void countAllTest() {
		try {
			int expected = 10;
			PowerMockito.when(orderForViewDAO.countAll())
					.thenReturn(expected);
			
			int actual = orderForViewService.countAll();
			Assert.assertEquals(expected, actual);
		} catch (ServiceException | DAOException e) {
			Assert.fail(e.getMessage());
		} 
	}
	
	@Test(expected = ServiceException.class)
	public void countAllTestServiceException() 
			throws ServiceException, DAOException {
		
		PowerMockito.when(orderForViewDAO.countAll())
				.thenThrow(DAOException.class);
			
		orderForViewService.countAll();
	}
	
	@Test
	public void findByIdTest() {
		try {
			OrderForView current = new OrderForView();
			current.setId(1);
			current.setUserId(2);
			current.setUserLogin("vict");
			
			PowerMockito.when(orderForViewDAO.findById(Mockito.anyLong()))
					.thenReturn(current);
			
			OrderForView actual = orderForViewService.findById(2);
			Assert.assertEquals(current, actual);
		} catch (ServiceException | DAOException e) {
			Assert.fail(e.getMessage());
		} 
	}
	
	@Test
	public void findByIdTestNullOrder() {
		try {
			OrderForView current = null;
			
			PowerMockito.when(orderForViewDAO.findById(Mockito.anyLong()))
					.thenReturn(current);
			
			OrderForView actual = orderForViewService.findById(2);
			Assert.assertEquals(current, actual);
		} catch (ServiceException | DAOException e) {
			Assert.fail(e.getMessage());
		} 
	}
	
	@Test(expected = ServiceException.class)
	public void findByIdTestServiceException() 
			throws ServiceException, DAOException {
		
		PowerMockito.when(orderForViewDAO.findById(Mockito.anyLong()))
				.thenThrow(DAOException.class);
			
		orderForViewService.findById(2);
	}
}
