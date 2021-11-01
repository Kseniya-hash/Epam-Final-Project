package service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
import by.epamtc.dubovik.shop.dao.PriceDAO;
import by.epamtc.dubovik.shop.entity.Price;
import by.epamtc.dubovik.shop.service.PriceService;
import by.epamtc.dubovik.shop.service.ServiceFactory;
import by.epamtc.dubovik.shop.service.exception.ServiceException;

@PrepareForTest(fullyQualifiedNames = QualifiedNamesMapping.DAO)
@RunWith(PowerMockRunner.class)
public class TestPriceService {
	
	private static DateTimeFormatter formatter = 
			DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	
	@Mock
	private DAOFactory daoFactory;
	
	@Mock
	private PriceDAO priceDAO;
	
	private PriceService priceService;
	
	private Price currentPrice;
	
	@Before
	public void setUpPrice() {
		currentPrice = new Price();
		currentPrice.setId(1);
		currentPrice.setProductId(1);
		currentPrice.setPurchasePrice(1000);
		currentPrice.setSellingPrice(1500);
		currentPrice.setDate(LocalDateTime.parse("2021-04-08 12:30", formatter));
	}
	
	@Before
	public void setUp() {
		PowerMockito.mockStatic(DAOFactory.class);
		PowerMockito.when(DAOFactory.getInstance())
				.thenReturn(daoFactory);
		PowerMockito.when(daoFactory.getPriceDAO())
				.thenReturn(priceDAO);
		priceService = ServiceFactory.getInstance()
				.getPriceService();
	}
	
	@Test
	public void findPriceByProductTest() {
		try {
			PowerMockito.when(priceDAO.findByProduct(Mockito.anyLong()))
					.thenReturn(currentPrice);
			
			Price actual = priceService.findPriceByProduct(1);
			Assert.assertEquals(currentPrice, actual);
		} catch (ServiceException | DAOException e) {
			Assert.fail(e.getMessage());
		} 
	}
	
	@Test
	public void findPriceByProductTestNullPrice() {
		try {
			Price current = null;
			
			PowerMockito.when(priceDAO.findByProduct(Mockito.anyLong()))
					.thenReturn(current);
			
			Price actual = priceService.findPriceByProduct(1);
			Assert.assertEquals(current, actual);
		} catch (ServiceException | DAOException e) {
			Assert.fail(e.getMessage());
		} 
	}
	
	@Test(expected = ServiceException.class)
	public void findPriceByProductTestServiceException() 
			throws ServiceException, DAOException {
		
		PowerMockito.when(priceDAO
				.findByProduct(Mockito.anyLong()))
				.thenThrow(DAOException.class);
			
		priceService.findPriceByProduct(1);
	}
	
	@Test
	public void findPriceByProductDateTest() {
		try {
			PowerMockito.when(priceDAO.findByProduct(Mockito.anyLong(), Mockito.any()))
					.thenReturn(currentPrice);
			
			Price actual = priceService.findPriceByProduct(1, LocalDateTime.now());
			Assert.assertEquals(currentPrice, actual);
		} catch (ServiceException | DAOException e) {
			Assert.fail(e.getMessage());
		} 
	}
	
	@Test
	public void findPriceByProductDateTestNullPrice() {
		try {
			Price current = null;

			PowerMockito.when(priceDAO.findByProduct(Mockito.anyLong(), Mockito.any()))
					.thenReturn(current);
			
			Price actual = priceService.findPriceByProduct(1, LocalDateTime.now());
			Assert.assertEquals(current, actual);
		} catch (ServiceException | DAOException e) {
			Assert.fail(e.getMessage());
		} 
	}
	
	@Test(expected = ServiceException.class)
	public void findPriceByProductDateTestServiceException() 
			throws ServiceException, DAOException {
		
		PowerMockito.when(priceDAO.findByProduct(Mockito.anyLong(), Mockito.any()))
				.thenThrow(DAOException.class);
		
		priceService.findPriceByProduct(1, LocalDateTime.now());
	}
	
	public void changePriceTest() {
		try {
			Price newPrice = new Price();
			newPrice.setId(1);
			newPrice.setProductId(1);
			newPrice.setPurchasePrice(1200);
			newPrice.setSellingPrice(1700);
			
			PowerMockito.when(priceDAO.findByProduct(Mockito.anyLong()))
					.thenReturn(currentPrice);
			PowerMockito.when(priceDAO.create(Mockito.any()))
					.thenReturn(true);
			
			boolean expected = true;
			boolean actual = priceService.changePrice(newPrice);
			Assert.assertEquals(expected, actual);
			
		} catch (ServiceException | DAOException e) {
			Assert.fail(e.getMessage());
		} 
	}
	
	public void changePriceTestPriceNotChanged() {
		try {
			Price newPrice = new Price();
			newPrice.setId(1);
			newPrice.setProductId(1);
			newPrice.setPurchasePrice(1000);
			newPrice.setSellingPrice(1500);
			
			PowerMockito.when(priceDAO.findByProduct(Mockito.anyLong()))
					.thenReturn(currentPrice);
			PowerMockito.when(priceDAO.create(Mockito.any()))
					.thenReturn(true);
			
			boolean expected = false;
			boolean actual = priceService.changePrice(newPrice);
			Assert.assertEquals(expected, actual);
			
		} catch (ServiceException | DAOException e) {
			Assert.fail(e.getMessage());
		} 
	}
	
	public void changePriceTestPriceWasNull() {
		try {
			Price current = null;
			
			Price newPrice = new Price();
			newPrice.setId(1);
			newPrice.setProductId(1);
			newPrice.setPurchasePrice(1000);
			newPrice.setSellingPrice(1500);
			
			PowerMockito.when(priceDAO.findByProduct(Mockito.anyLong()))
					.thenReturn(current);
			PowerMockito.when(priceDAO.create(Mockito.any()))
					.thenReturn(true);
			
			boolean expected = true;
			boolean actual = priceService.changePrice(newPrice);
			Assert.assertEquals(expected, actual);
			
		} catch (ServiceException | DAOException e) {
			Assert.fail(e.getMessage());
		} 
	}
	
	public void changePriceTestInvalidNewPrice() {
		try {
			Price newPrice = new Price();
			newPrice.setId(1);
			newPrice.setProductId(1);
			newPrice.setPurchasePrice(-1000);
			newPrice.setSellingPrice(1500);
			
			PowerMockito.when(priceDAO.findByProduct(Mockito.anyLong()))
					.thenReturn(currentPrice);
			PowerMockito.when(priceDAO.create(Mockito.any()))
					.thenReturn(true);
			
			boolean expected = false;
			boolean actual = priceService.changePrice(newPrice);
			Assert.assertEquals(expected, actual);
			
		} catch (ServiceException | DAOException e) {
			Assert.fail(e.getMessage());
		} 
	}
	
	public void changePriceTestDAOReturnFalse() {
		try {
			Price newPrice = new Price();
			newPrice.setId(1);
			newPrice.setProductId(1);
			newPrice.setPurchasePrice(1000);
			newPrice.setSellingPrice(1700);
			
			PowerMockito.when(priceDAO.findByProduct(Mockito.anyLong()))
					.thenReturn(currentPrice);
			PowerMockito.when(priceDAO.create(Mockito.any()))
					.thenReturn(false);
			
			boolean expected = false;
			boolean actual = priceService.changePrice(newPrice);
			Assert.assertEquals(expected, actual);
			
		} catch (ServiceException | DAOException e) {
			Assert.fail(e.getMessage());
		} 
	}
	
	@Test(expected = ServiceException.class)
	public void changePriceTestServiceExceptionInFind() 
			throws ServiceException, DAOException {
		Price newPrice = new Price();
		newPrice.setId(1);
		newPrice.setProductId(1);
		newPrice.setPurchasePrice(1000);
		newPrice.setSellingPrice(1700);
		
		PowerMockito.when(priceDAO.findByProduct(Mockito.anyLong()))
				.thenThrow(DAOException.class);
		PowerMockito.when(priceDAO.create(Mockito.any()))
				.thenReturn(true);
			
		priceService.changePrice(newPrice);
	}
}
