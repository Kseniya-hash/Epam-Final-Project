package by.epamtc.dubovik.shop.service.util;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import by.epamtc.dubovik.shop.service.security.SecurityConfig;

public class SecurityUtil {
	
	//private final static String COMMAND = "command"; 
	/*
	public static boolean isSecurityPage(HttpServletRequest request) {
		//String urlPattern = UrlPatternUtils.getUrlPattern(request);
		String command = UrlPatternUtils.getCommand(request);
		
		Set<String> roles = SecurityConfig.getAllRoles();

		for (String role : roles) {
			/*List<String> urlPatterns = SecurityConfig.getUrlPatternsForRole(role);
			if (urlPatterns != null && urlPatterns.contains(urlPattern)) {
				return true;
			}/
			List<String> commands = SecurityConfig.getCommandsForRole(role);
			if (commands != null && commands.contains(command)) {
				return true;
			}
		}
		return false;
	}*/

	// Проверить имеет ли данный 'request' подходящую роль?
	public static boolean hasPermission(HttpServletRequest request) {
		//String urlPattern = UrlPatternUtils.getUrlPattern(request);
		String command = RequestUtil.getCommand(request);
		
		Set<String> allRoles = SecurityConfig.getAllRoles();

		for (String role : allRoles) {
			if (!request.isUserInRole(role)) {
				continue;
			}
			List<String> commands = SecurityConfig.getCommandsForRole(role);
			if (commands != null && commands.contains(command)) {
				return true;
			}
		}
		return false;
	}
	
	

}
