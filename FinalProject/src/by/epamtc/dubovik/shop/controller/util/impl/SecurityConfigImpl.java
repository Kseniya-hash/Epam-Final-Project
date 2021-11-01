package by.epamtc.dubovik.shop.controller.util.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import by.epamtc.dubovik.shop.controller.command.factory.CommandMapping;
import by.epamtc.dubovik.shop.controller.util.SecurityConfig;

public class SecurityConfigImpl implements SecurityConfig {

	public static final String ROLE_ADMIN = "admin";
	public static final String ROLE_USER = "user";
	
	private static final Map<String, List<String>> mapConfig = new HashMap<String, List<String>>();

	public SecurityConfigImpl() {
		
		List<String> commandsForUser = new ArrayList<String>();
		
		commandsForUser.add(CommandMapping.SHOW_CART);
		commandsForUser.add(CommandMapping.CART_INCRIMENT);
		commandsForUser.add(CommandMapping.CART_DECRIMENT);
		commandsForUser.add(CommandMapping.CART_REMOVE);
		commandsForUser.add(CommandMapping.MAKE_ORDER);
		commandsForUser.add(CommandMapping.PAY_FOR_ORDER);
		commandsForUser.add(CommandMapping.SHOW_USER_ORDERS);
		commandsForUser.add(CommandMapping.TO_PAY_ORDER);
		commandsForUser.add(CommandMapping.CREATE_COMMENT);

		mapConfig.put(ROLE_USER, commandsForUser);

		List<String> commandsForAdmin = new ArrayList<String>();
		
		
		commandsForAdmin.add(CommandMapping.SHOW_CART);
		commandsForAdmin.add(CommandMapping.CART_INCRIMENT);
		commandsForAdmin.add(CommandMapping.CART_DECRIMENT);
		commandsForAdmin.add(CommandMapping.CART_REMOVE);
		commandsForAdmin.add(CommandMapping.MAKE_ORDER);
		commandsForAdmin.add(CommandMapping.PAY_FOR_ORDER);
		commandsForAdmin.add(CommandMapping.SHOW_USER_ORDERS);
		commandsForAdmin.add(CommandMapping.TO_PAY_ORDER);
		commandsForAdmin.add(CommandMapping.CREATE_COMMENT);

		commandsForAdmin.add(CommandMapping.REDACT_PRODUCT);
		commandsForAdmin.add(CommandMapping.TO_REDACT_PRODUCT_PAGE);
		commandsForAdmin.add(CommandMapping.ADD_CATEGORY);
		commandsForAdmin.add(CommandMapping.CREATE_PRODUCT);
		commandsForAdmin.add(CommandMapping.SHOW_CUSTOMER_ORDERS);
		commandsForAdmin.add(CommandMapping.TO_CREATE_PRODUCT_PAGE);
		
		mapConfig.put(ROLE_ADMIN, commandsForAdmin);
	}

	public Set<String> getAllRoles() {
		return mapConfig.keySet();
	}

	public List<String> getCommandsForRole(String role) {
		return mapConfig.get(role);
	}
}
