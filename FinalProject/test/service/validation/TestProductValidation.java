package service.validation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import by.epamtc.dubovik.shop.entity.Product;
import by.epamtc.dubovik.shop.service.validation.ProductValidation;
import by.epamtc.dubovik.shop.service.validation.ValidationFactory;

public class TestProductValidation {

	private ProductValidation validator = 
			ValidationFactory.getInstance().getProductValidation();
	
	private Product product = new Product();
	
	@Before
	public void setUp() {
		product.setId(1);
		product.setName("Самокат  Ridex");
		product.setCategoryId(1);
		product.setDescription("Великолепный самокат для детей");
		product.setPhotoPath("image.jpg");
		product.setQuantity(1);
		product.setHigh(100);
		product.setLength(80);
		product.setWidth(30);
		product.setWeight(2000);
	}
	
	@Test
	public void isValidTest() {
		boolean expected = true;
		boolean actual = validator.isValid(product);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void isValidTestNoLengthWidthHigh() {
		product.setLength(null);
		
		boolean expected = true;
		boolean actual = validator.isValid(product);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void isValidTestNoWeight() {
		product.setWeight(null);
		
		boolean expected = true;
		boolean actual = validator.isValid(product);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void isValidTestNoDescription() {
		product.setDescription(null);
		
		boolean expected = true;
		boolean actual = validator.isValid(product);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void isValidTestPhotoPng() {
		product.setPhotoPath("image.png");
		
		boolean expected = true;
		boolean actual = validator.isValid(product);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void isValidTestPhotoJpeg() {
		product.setPhotoPath("image.jpeg");
		
		boolean expected = true;
		boolean actual = validator.isValid(product);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void isValidTestPhotoGif() {
		product.setPhotoPath("image.gif");
		
		boolean expected = true;
		boolean actual = validator.isValid(product);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void isValidTestNoName() {
		product.setName(null);
		
		boolean expected = false;
		boolean actual = validator.isValid(product);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void isValidTestEmptyName() {
		product.setName("");
		
		boolean expected = false;
		boolean actual = validator.isValid(product);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void isValidTestDescriptionTooBig() {
		String description = "Резиновые колеса диаметром 200 мм минимизируют вибрации "
				+ "при передвижении по любым типам городского покрытия. За быстрое пере"
				+ "движение отвечают шустрые подшипники ABEC-9, а дисковые тормоза позв"
				+ "олят избежать неприятных столкновений на вашем пути. Крепкая алюмини"
				+ "евая дека способная выдержать вес до 110 кг. Стильный и лаконичный д"
				+ "изайн приятен глазу, подтверждая ваш статус на дороге.Резиновые коле"
				+ "са диаметром 200 мм минимизируют вибрации при передвижении по любым "
				+ "типам городского покрытия. За быстрое передвижение отвечают шустрые "
				+ "подшипники ABEC-9, а дисковые тормоза позволят избежать неприятных с"
				+ "толкновений на вашем пути. Крепкая алюминиевая дека способная выдерж"
				+ "ать вес до 110 кг. Стильный и лаконичный дизайн приятен глазу, подтв"
				+ "ерждая ваш статус на дороге. Резиновые колеса диаметром 200 мм миним"
				+ "изируют вибрации при передвижении по любым типам городского покрытия"
				+ ". За быстрое передвижение отвечают шустрые подшипники ABEC-9, а диск"
				+ "овые тормоза позволят избежать неприятных столкновений на вашем пути"
				+ ". Крепкая алюминиевая дека способная выдержать вес до 110 кг. Стильн"
				+ "ый и лаконичный дизайн приятен глазу, подтверждая ваш статус на дороге.";
		
		product.setDescription(description);
		
		boolean expected = false;
		boolean actual = validator.isValid(product);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void isValidTestNegativeQuantity() {
		product.setQuantity(-1);
		
		boolean expected = false;
		boolean actual = validator.isValid(product);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void isValidTestZeroHigh() {
		product.setHigh(0);
		
		boolean expected = false;
		boolean actual = validator.isValid(product);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void isValidTestNegativeHigh() {
		product.setHigh(-100);
		
		boolean expected = false;
		boolean actual = validator.isValid(product);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void isValidTestZeroLength() {
		product.setLength(0);
		
		boolean expected = false;
		boolean actual = validator.isValid(product);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void isValidTestNegativeLength() {
		product.setLength(-80);
		
		boolean expected = false;
		boolean actual = validator.isValid(product);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void isValidTestZeroWidth() {
		product.setWidth(0);
		
		boolean expected = false;
		boolean actual = validator.isValid(product);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void isValidTestNegativeWidth() {
		product.setWidth(-30);
		
		boolean expected = false;
		boolean actual = validator.isValid(product);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void isValidTestZeroWeight() {
		product.setWeight(0);
		
		boolean expected = false;
		boolean actual = validator.isValid(product);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void isValidTestNegativeWeight() {
		product.setWeight(-2000);
		
		boolean expected = false;
		boolean actual = validator.isValid(product);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void isValidTestPhotoWrongFormat() {
		product.setPhotoPath("image.txt");
		
		boolean expected = false;
		boolean actual = validator.isValid(product);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void isValidTestPhotoWithoutFormat() {
		product.setPhotoPath("image");
		
		boolean expected = false;
		boolean actual = validator.isValid(product);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void isValidTestNullPhoto() {
		product.setPhotoPath(null);
		
		boolean expected = false;
		boolean actual = validator.isValid(product);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void isValidTestEmptyPhoto() {
		product.setPhotoPath("");
		
		boolean expected = false;
		boolean actual = validator.isValid(product);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void isValidTestNullProduct() {
		Product product = null;
		
		boolean expected = false;
		boolean actual = validator.isValid(product);
		
		Assert.assertEquals(expected, actual);
	}
}
