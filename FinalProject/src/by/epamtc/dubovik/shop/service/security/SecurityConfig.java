package by.epamtc.dubovik.shop.service.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import by.epamtc.dubovik.shop.servlet.command.factory.CommandMapping;

public class SecurityConfig {
	
	public static final String ROLE_ADMIN = "admin";
	public static final String ROLE_USER = "user";
	
	private static final Map<String, List<String>> mapConfig = new HashMap<String, List<String>>();
	private static final Set<String> securePages = new HashSet<String>();

	static {
		init();
	}

	private static void init() {
		
		List<String> commandsForUser = new ArrayList<String>();

		/*commandsForUser.add(CommandMapping.LOGIN);
		commandsForUser.add(CommandMapping.LOGOUT);
		commandsForUser.add(CommandMapping.PRODUCT_SORT);
		commandsForUser.add(CommandMapping.REGISTRATION);
		commandsForUser.add(CommandMapping.SHOW_PRODUCT);
		commandsForUser.add(CommandMapping.TO_LOGIN_PAGE);
		commandsForUser.add(CommandMapping.TO_REGISTRATION_PAGE);
		commandsForUser.add(CommandMapping.WELLCOME);*/
		commandsForUser.add(CommandMapping.CART_INCRIMENT);
		commandsForUser.add(CommandMapping.CART_DECRIMENT);
		commandsForUser.add(CommandMapping.SHOW_CART);

		mapConfig.put(ROLE_USER, commandsForUser);

		List<String> commandsForAdmin = new ArrayList<String>();

		/*commandsForAdmin.add(CommandMapping.LOGIN);
		commandsForAdmin.add(CommandMapping.LOGOUT);
		commandsForAdmin.add(CommandMapping.PRODUCT_SORT);
		commandsForAdmin.add(CommandMapping.REDACT_PRODUCT);
		commandsForAdmin.add(CommandMapping.REGISTRATION);
		commandsForAdmin.add(CommandMapping.SHOW_PRODUCT);
		commandsForAdmin.add(CommandMapping.TO_LOGIN_PAGE);
		commandsForAdmin.add(CommandMapping.TO_REGISTRATION_PAGE);
		commandsForAdmin.add(CommandMapping.WELLCOME);*/
		commandsForAdmin.add(CommandMapping.CART_INCRIMENT);
		commandsForAdmin.add(CommandMapping.CART_DECRIMENT);
		commandsForAdmin.add(CommandMapping.REDACT_PRODUCT);
		commandsForAdmin.add(CommandMapping.TO_REDACT_PRODUCT_PAGE);
		commandsForAdmin.add(CommandMapping.SHOW_CART);

		mapConfig.put(ROLE_ADMIN, commandsForAdmin);
		
		securePages.add(CommandMapping.REDACT_PRODUCT);
		securePages.add(CommandMapping.CART_INCRIMENT);
		securePages.add(CommandMapping.CART_DECRIMENT);
		securePages.add(CommandMapping.TO_REDACT_PRODUCT_PAGE);
		securePages.add(CommandMapping.SHOW_CART);
	}

	public static Set<String> getAllRoles() {
		return mapConfig.keySet();
	}

	public static List<String> getCommandsForRole(String role) {
		return mapConfig.get(role);
	}
	
	public static boolean isSecureCommand(String command) {
		return securePages.contains(command);
	}

}
