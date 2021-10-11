package by.epamtc.dubovik.shop.servlet.command.factory;

import javax.servlet.http.HttpServletRequest;

import by.epamtc.dubovik.shop.servlet.command.*;

public class ActionFactory {
	private final static String COMMAND = "command";
	
	public ActionCommand defineCommand(HttpServletRequest request) {
		ActionCommand current = new EmptyCommand();
		CommandMapping actionMap = CommandMapping.getInstance();
		String action = request.getParameter(COMMAND);
		if(action != null && !action.isEmpty() && actionMap.containsKey(action)) {
			current = actionMap.get(action);
		}
		
		return current;
	}

}
