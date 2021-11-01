package by.epamtc.dubovik.shop.service.util.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import by.epamtc.dubovik.shop.service.exception.ServiceException;
import by.epamtc.dubovik.shop.service.util.EncryptUtil;

public class EncryptUtilImpl implements EncryptUtil {
	
	private final static String ALGORITHM = "MD5";
	private final static int SALT_LENGTH = 16;

	@Override
	public byte[] incryptPassword(byte[] password) 
			throws ServiceException {
		byte[] incrypted = null;
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[SALT_LENGTH];
		random.nextBytes(salt);
		incrypted = incryptPassword(password, salt);
		return incrypted;
	}

	@Override
	public boolean cheakPassword(byte[] plain, byte[] encrypted) 
			throws ServiceException {
		Decoder decoder = Base64.getDecoder();
		byte[] decoded = decoder.decode(encrypted);
		byte[] salt = new byte[SALT_LENGTH];
		copyBytes(salt, decoded, 0, decoded.length - salt.length, salt.length);
		plain = incryptPassword(plain, salt);
		return Arrays.equals(encrypted,plain);
	}
	
	private byte[] incryptPassword(byte[] password, byte[] salt) 
			throws ServiceException {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance(ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			throw new ServiceException(e);
		}
		md.update(salt);
		byte[] incryptedWithSalt = addSaltToPassword(md.digest(password), salt);
		Encoder encoder = Base64.getEncoder();
		incryptedWithSalt = encoder.encode(incryptedWithSalt);
		return incryptedWithSalt;
	}
	
	@Override
	public void clearArray(byte[] array) {
		for(int i = 0; i < array.length; i++) {
			array[i] = 0;
		}
	}
	
	private byte[] addSaltToPassword(byte[] password, byte[] salt) {
		byte[] passwordWithSalt = 
				new byte[password.length + salt.length];
		copyBytes(passwordWithSalt, password, 0, 0, password.length);
		copyBytes(passwordWithSalt, salt, password.length, 0, salt.length);
		return passwordWithSalt;
	}
	
	private void copyBytes(byte[] receiver, 
							byte[] source, 
							int startReceiver, 
							int startSource, 
							int count) {
		for(int i = 0; i < count; i++) {
			if(receiver.length - startReceiver > i) {
				receiver[startReceiver + i] = source[startSource + i];
			}
		}
	}
	
	@Override
	public long takeSecureRandom() {
		SecureRandom random = new SecureRandom();
		long secureRandom = random.nextLong();
		return secureRandom;
	}
}
