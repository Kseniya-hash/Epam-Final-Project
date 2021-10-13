package by.epamtc.dubovik.shop.service.util.impl;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import by.epamtc.dubovik.shop.service.factory.ServiceFactory;
import by.epamtc.dubovik.shop.service.util.SecurityConfig;
import by.epamtc.dubovik.shop.service.util.SecurityUtil;

public class SecurityUtilImpl  implements SecurityUtil {
	
	public boolean isProtectedCommand(HttpServletRequest request) {
		boolean isProtected = false;
		String command = ServiceFactory.getInstance().getRequestUtil().takeCommand(request);
		SecurityConfig securityConfig = ServiceFactory.getInstance().getSecurityConfig();
		Set<String> roles = securityConfig.getAllRoles();

		for (String role : roles) {
			List<String> commands = securityConfig.getCommandsForRole(role);
			if (commands != null && commands.contains(command)) {
				isProtected = true;
			}
		}
		return isProtected;
	}

	// Проверить имеет ли данный 'request' подходящую роль?
	public boolean hasPermission(HttpServletRequest request) {
		//String urlPattern = UrlPatternUtils.getUrlPattern(request);
		SecurityConfig securityConfig = ServiceFactory.getInstance().getSecurityConfig();
		String command = ServiceFactory.getInstance().getRequestUtil().takeCommand(request);
		
		Set<String> allRoles = securityConfig.getAllRoles();

		for (String role : allRoles) {
			if (!request.isUserInRole(role)) {
				continue;
			}
			List<String> commands = securityConfig.getCommandsForRole(role);
			if (commands != null && commands.contains(command)) {
				return true;
			}
		}
		return false;
	}

}
