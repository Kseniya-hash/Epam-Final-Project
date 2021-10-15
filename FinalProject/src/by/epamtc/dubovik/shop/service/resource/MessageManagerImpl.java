package by.epamtc.dubovik.shop.service.resource;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessageManagerImpl implements MessageManager {
	
	private final static String RESOURCE_FILE = "resources.pagecontent";
	
	public String getProperty(String key, Locale locale) {
		ResourceBundle  resourceBundle = ResourceBundle.getBundle(RESOURCE_FILE, locale);
		return resourceBundle.getString(key);
	}

}
