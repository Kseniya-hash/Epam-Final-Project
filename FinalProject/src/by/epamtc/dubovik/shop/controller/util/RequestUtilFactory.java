package by.epamtc.dubovik.shop.controller.util;

import by.epamtc.dubovik.shop.controller.util.impl.*;

public class RequestUtilFactory {
	
	private final MessageManager messageManager;
	private final RequestUtil requestUtil;
	private final SecurityConfig securityConfig;
	private final SecurityUtil securityUtil;

	private RequestUtilFactory() {
		messageManager = new MessageManagerImpl();
		requestUtil = new RequestUtilImpl();
		securityConfig = new SecurityConfigImpl();
		securityUtil = new SecurityUtilImpl();
	}
	
	private static class SigletonHolder {
		private final static RequestUtilFactory INSTANCE = new RequestUtilFactory();
	}
	
	public static RequestUtilFactory getInstance() {
		return SigletonHolder.INSTANCE;
	}
	
	public MessageManager getMessageManager() {
		return messageManager;
	}
	
	public RequestUtil getRequestUtil() {
		return requestUtil;
	}
	
	public SecurityConfig getSecurityConfig() {
		return securityConfig;
	}
	
	public SecurityUtil getSecurityUtil() {
		return securityUtil;
	}
}
