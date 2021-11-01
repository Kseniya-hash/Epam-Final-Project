package by.epamtc.dubovik.shop.controller.util.impl;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import by.epamtc.dubovik.shop.controller.util.RequestUtilFactory;
import by.epamtc.dubovik.shop.controller.util.SecurityConfig;
import by.epamtc.dubovik.shop.controller.util.SecurityUtil;

public class SecurityUtilImpl  implements SecurityUtil {
	
	public boolean isProtectedCommand(HttpServletRequest request) {
		RequestUtilFactory factory = RequestUtilFactory.getInstance();
		
		String command = factory.getRequestUtil().takeCommand(request);
		SecurityConfig securityConfig = factory.getSecurityConfig();
		Set<String> roles = securityConfig.getAllRoles();

		boolean isProtected = false;
		
		for (String role : roles) {
			List<String> commands = securityConfig.getCommandsForRole(role);
			if (commands != null && commands.contains(command)) {
				isProtected = true;
			}
		}
		return isProtected;
	}

	public boolean hasPermission(HttpServletRequest request) {
		RequestUtilFactory factory = RequestUtilFactory.getInstance();
		
		SecurityConfig securityConfig = factory.getSecurityConfig();
		String command = factory.getRequestUtil().takeCommand(request);
		
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
