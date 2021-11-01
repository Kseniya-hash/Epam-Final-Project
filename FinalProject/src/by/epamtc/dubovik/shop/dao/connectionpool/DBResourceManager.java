package by.epamtc.dubovik.shop.dao.connectionpool;

import java.util.ResourceBundle;

public class DBResourceManager {

	private final static String DATABASE_PROPERTIES = "resources/database";
	private ResourceBundle resource = ResourceBundle.getBundle(DATABASE_PROPERTIES);
	
	private final static DBResourceManager instance = new DBResourceManager();
	
	public static DBResourceManager getInstance() {
		return instance;
	}
	
	public String getValue(String key) {
		return resource.getString(key);
	}
}
