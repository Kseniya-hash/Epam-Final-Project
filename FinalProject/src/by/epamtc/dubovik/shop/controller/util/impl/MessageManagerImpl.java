package by.epamtc.dubovik.shop.controller.util.impl;

import java.util.Locale;
import java.util.ResourceBundle;

import by.epamtc.dubovik.shop.controller.util.MessageManager;

public class MessageManagerImpl implements MessageManager {
	
	private final static String RESOURCE_FILE = "resources.pagecontent";
	
	public String getProperty(String key, Locale locale) {
		ResourceBundle  resourceBundle = ResourceBundle.getBundle(RESOURCE_FILE, locale);
		return resourceBundle.getString(key);
	}

}
