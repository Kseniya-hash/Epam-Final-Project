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
import by.epamtc.dubovik.shop.dao.UserDAO;
import by.epamtc.dubovik.shop.entity.User;
import by.epamtc.dubovik.shop.entity.UserForLogin;
import by.epamtc.dubovik.shop.entity.UserLogged;
import by.epamtc.dubovik.shop.service.ServiceFactory;
import by.epamtc.dubovik.shop.service.UserService;
import by.epamtc.dubovik.shop.service.exception.InvalidException;
import by.epamtc.dubovik.shop.service.exception.ServiceException;
import by.epamtc.dubovik.shop.service.exception.UserAlreadyExistException;

@PrepareForTest(fullyQualifiedNames = QualifiedNamesMapping.DAO)
@RunWith(PowerMockRunner.class)
public class TestUserService {
	
	@Mock
	private DAOFactory daoFactory;
	
	@Mock
	private UserDAO userDAO;
	
	private UserService userService;
	
	@Before
	public void setUp() {
		PowerMockito.mockStatic(DAOFactory.class);
		PowerMockito.when(DAOFactory.getInstance())
				.thenReturn(daoFactory);
		PowerMockito.when(daoFactory.getUserDAO())
				.thenReturn(userDAO);
		userService = ServiceFactory.getInstance()
				.getUserService();
	}
	
	@Test
	public void authorizeTest() {
		try {
			UserForLogin user = new UserForLogin();
			user.setLogin("admin");
			user.setPassword(new String("Pass_123").getBytes());
			
			UserLogged expected = new UserLogged();
			expected.setId(1);
			expected.setLogin("admin");
			expected.setRole("admin");
			
			byte[] encryptedPassword = 
					new String("f/PSCiJ47KQwiwTs20PiZJhWl9OBEZZx1yJmeUmjDVs=").getBytes();
		
			PowerMockito.when(userDAO.findPassword(user.getLogin()))
					.thenReturn(encryptedPassword);
			
			PowerMockito.when(userDAO.findLoggedUser(user.getLogin()))
			.thenReturn(expected);
			
			UserLogged actual = userService.authorize(user);
			Assert.assertEquals(expected, actual);
		} catch (ServiceException | DAOException | InvalidException e) {
			Assert.fail(e.getMessage());
		} 
	}
	
	@Test
	public void authorizeTestWrongPassword() {
		try {
			UserForLogin user = new UserForLogin();
			user.setLogin("admin");
			user.setPassword(new String("Pass_123").getBytes());
			
			UserLogged userLogged = new UserLogged();
			userLogged.setId(1);
			userLogged.setLogin("admin");
			userLogged.setRole("admin");
			
			byte[] encryptedPassword = 
					new String("f/PSCiJ47KQwiwTs20PiZJhWl9OB").getBytes();
		
			PowerMockito.when(userDAO.findPassword(user.getLogin()))
					.thenReturn(encryptedPassword);
			
			PowerMockito.when(userDAO.findLoggedUser(user.getLogin()))
			.thenReturn(userLogged);
			
			UserLogged expected = null;
			UserLogged actual = userService.authorize(user);
			Assert.assertEquals(expected, actual);
		} catch (ServiceException | DAOException | InvalidException e) {
			Assert.fail(e.getMessage());
		} 
	}
	
	@Test
	public void authorizeTestNotFound() {
		try {
			UserForLogin user = new UserForLogin();
			user.setLogin("admin");
			user.setPassword(new String("Pass_123").getBytes());
			
			UserLogged expected = null;
			
			PowerMockito.when(userDAO.findPassword(user.getLogin()))
					.thenReturn(null);
			
			PowerMockito.when(userDAO.findLoggedUser(user.getLogin()))
			.thenReturn(expected);
			
			UserLogged actual = userService.authorize(user);
			Assert.assertEquals(expected, actual);
		} catch (ServiceException | DAOException | InvalidException e) {
			Assert.fail(e.getMessage());
		} 
	}
	
	@Test(expected = InvalidException.class)
	public void authorizeTestInvalidUser() 
			throws ServiceException, InvalidException {
		
		UserForLogin user = new UserForLogin();
		user.setLogin("admin");
			
		userService.authorize(user);
	}

	@Test(expected = ServiceException.class)
	public void authorizeTestExceptionInfindPassword() 
			throws DAOException, ServiceException, InvalidException {
		
		UserForLogin user = new UserForLogin();
		user.setLogin("admin");
		user.setPassword(new String("Pass_123").getBytes());
			
		PowerMockito.when(userDAO.findPassword(user.getLogin()))
				.thenThrow(DAOException.class);
			
		userService.authorize(user);
	}
	
	@Test(expected = ServiceException.class)
	public void authorizeTestExceptionInFindLoggedUser() 
			throws DAOException, ServiceException, InvalidException  {
		
		UserForLogin user = new UserForLogin();
		user.setLogin("admin");
		user.setPassword(new String("Pass_123").getBytes());
			
		byte[] encryptedPassword = 
				new String("f/PSCiJ47KQwiwTs20PiZJhWl9OBEZZx1yJmeUmjDVs=").getBytes();
		
		PowerMockito.when(userDAO.findPassword(user.getLogin()))
				.thenReturn(encryptedPassword);
			
		PowerMockito.when(userDAO.findLoggedUser(user.getLogin()))
				.thenThrow(DAOException.class);
			
		userService.authorize(user);
	}
	
	@Test
	public void registerTest() {
		try {
			User user = new User();
			user.setLogin("user");
			user.setPassword(new String("Pass_123").getBytes());
			user.setPhone("375296123456");
			user.setName("Мария");
			
			
			byte[] passwordRepeat = new String("Pass_123").getBytes();
		
			PowerMockito.when(userDAO.create(Mockito.any()))
					.thenReturn(true);
			
			PowerMockito.when(userDAO.findUserInfoByLogin(user.getLogin()))
			.thenReturn(null);
			
			boolean expected = true;
			boolean actual = userService.register(user, passwordRepeat);
			
			Assert.assertEquals(expected, actual);
		} catch (ServiceException | DAOException | 
				InvalidException | UserAlreadyExistException e) {
			Assert.fail(e.getMessage());
		} 
	}
	
	@Test
	public void registerTestDaoReturnFalse() {
		try {
			User user = new User();
			user.setLogin("user");
			user.setPassword(new String("Pass_123").getBytes());
			user.setPhone("375296123456");
			user.setName("Мария");
			
			
			byte[] passwordRepeat = new String("Pass_123").getBytes();
		
			PowerMockito.when(userDAO.create(Mockito.any()))
					.thenReturn(false);
			
			PowerMockito.when(userDAO.findUserInfoByLogin(user.getLogin()))
			.thenReturn(null);
			
			boolean expected = false;
			boolean actual = userService.register(user, passwordRepeat);
			
			Assert.assertEquals(expected, actual);
		} catch (ServiceException | DAOException | 
				InvalidException | UserAlreadyExistException e) {
			Assert.fail(e.getMessage());
		} 
	}
	
	@Test(expected = InvalidException.class)
	public void registerTestPasswordNotEqual() 
			throws DAOException, 
			ServiceException, 
			InvalidException, 
			UserAlreadyExistException {
		
		User user = new User();
		user.setLogin("user");
		user.setPassword(new String("Pass_123").getBytes());
		user.setPhone("375296123456");
		user.setName("Мария");
			
		byte[] passwordRepeat = new String("Pass_12345").getBytes();
		
		PowerMockito.when(userDAO.create(Mockito.any()))
				.thenReturn(true);
			
		PowerMockito.when(userDAO.findUserInfoByLogin(user.getLogin()))
				.thenReturn(null);
		
		userService.register(user, passwordRepeat);
	}
	
	@Test(expected = InvalidException.class)
	public void registerTestInvalidUser() 
			throws DAOException, 
			ServiceException, 
			InvalidException, 
			UserAlreadyExistException {
		User user = new User();
		user.setLogin("user");
		user.setPassword(new String("Pass_123").getBytes());
		user.setPhone("375296123456345");
		user.setName("Мария");
			
		byte[] passwordRepeat = new String("Pass_123").getBytes();
		
		PowerMockito.when(userDAO.create(Mockito.any()))
				.thenReturn(true);
			
		PowerMockito.when(userDAO.findUserInfoByLogin(user.getLogin()))
				.thenReturn(null);
		
		userService.register(user, passwordRepeat);
	}
	
	@Test(expected = UserAlreadyExistException.class)
	public void registerTestUserAlreadyExist() 
			throws DAOException, 
			ServiceException, 
			InvalidException, 
			UserAlreadyExistException {
		User user = new User();
		user.setLogin("user");
		user.setPassword(new String("Pass_123").getBytes());
		user.setPhone("375296123456");
		user.setName("Мария");
			
		byte[] passwordRepeat = new String("Pass_123").getBytes();
		
		PowerMockito.when(userDAO.create(Mockito.any()))
				.thenReturn(true);
			
		PowerMockito.when(userDAO.findUserInfoByLogin(user.getLogin()))
				.thenReturn(user);
		
		userService.register(user, passwordRepeat);
	}
	
	@Test(expected = ServiceException.class)
	public void registerTestUserServiceException() 
			throws DAOException, 
			ServiceException, 
			InvalidException, 
			UserAlreadyExistException {
		User user = new User();
		user.setLogin("user");
		user.setPassword(new String("Pass_123").getBytes());
		user.setPhone("375296123456");
		user.setName("Мария");
			
		byte[] passwordRepeat = new String("Pass_123").getBytes();
		
		PowerMockito.when(userDAO.create(Mockito.any()))
				.thenReturn(true);
			
		PowerMockito.when(userDAO.findUserInfoByLogin(user.getLogin()))
				.thenThrow(DAOException.class);
		
		userService.register(user, passwordRepeat);
	}
	
	@Test
	public void blacklistUserTest() {
		try {
			User user = new User();
			user.setId(2);
			user.setLogin("user");
			user.setPassword(new String("Pass_123").getBytes());
			user.setPhone("375296123456");
			user.setName("Мария");
		
			PowerMockito.when(userDAO.findById(Mockito.anyLong()))
					.thenReturn(user);
			
			PowerMockito.when(userDAO.update(Mockito.any()))
					.thenReturn(true);
			
			boolean expected = true;
			boolean actual = userService.blacklistUser(2);
			Assert.assertEquals(expected, actual);
		} catch (ServiceException | DAOException e) {
			Assert.fail(e.getMessage());
		} 
	}
	
	@Test
	public void blacklistUserTestAlreadyBlacklisted() {
		try {
			User user = new User();
			user.setId(2);
			user.setLogin("user");
			user.setPassword(new String("Pass_123").getBytes());
			user.setPhone("375296123456");
			user.setName("Мария");
			user.setIsBlacklisted(true);
		
			PowerMockito.when(userDAO.findById(Mockito.anyLong()))
					.thenReturn(user);
			
			PowerMockito.when(userDAO.update(Mockito.any()))
					.thenReturn(true);
			
			boolean expected = true;
			boolean actual = userService.blacklistUser(2);
			Assert.assertEquals(expected, actual);
		} catch (ServiceException | DAOException e) {
			Assert.fail(e.getMessage());
		} 
	}
	
	@Test
	public void blacklistUserTestUpdateFalse() {
		try {
			User user = new User();
			user.setId(2);
			user.setLogin("user");
			user.setPassword(new String("Pass_123").getBytes());
			user.setPhone("375296123456");
			user.setName("Мария");
		
			PowerMockito.when(userDAO.findById(Mockito.anyLong()))
					.thenReturn(user);
			
			PowerMockito.when(userDAO.update(Mockito.any()))
					.thenReturn(false);
			
			boolean expected = false;
			boolean actual = userService.blacklistUser(2);
			Assert.assertEquals(expected, actual);
		} catch (ServiceException | DAOException e) {
			Assert.fail(e.getMessage());
		} 
	}
	
	@Test
	public void blacklistUserTestUserNotFound() {
		try {
		
			PowerMockito.when(userDAO.findById(Mockito.anyLong()))
					.thenReturn(null);
			
			PowerMockito.when(userDAO.update(Mockito.any()))
					.thenReturn(true);
			
			boolean expected = false;
			boolean actual = userService.blacklistUser(2);
			Assert.assertEquals(expected, actual);
		} catch (ServiceException | DAOException e) {
			Assert.fail(e.getMessage());
		} 
	}
	
	@Test(expected = ServiceException.class)
	public void blacklistUserTestExceptionInFindById() 
			throws DAOException, ServiceException {
		
		PowerMockito.when(userDAO.findById(Mockito.anyLong()))
				.thenThrow(DAOException.class);
			
			PowerMockito.when(userDAO.update(Mockito.any()))
					.thenReturn(true);
			
		userService.blacklistUser(2);
	}
}
