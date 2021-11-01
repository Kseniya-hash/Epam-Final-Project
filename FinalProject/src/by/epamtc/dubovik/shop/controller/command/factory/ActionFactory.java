package by.epamtc.dubovik.shop.controller.command.factory;

import javax.servlet.http.HttpServletRequest;

import by.epamtc.dubovik.shop.controller.ParameterName;
import by.epamtc.dubovik.shop.controller.command.*;

public class ActionFactory {
	
	public ActionCommand defineCommand(HttpServletRequest request) {
		/*ActionCommand current = null;
		CommandMapping actionMap = CommandMapping.getInstance();
		String action = request.getParameter(ParameterName.COMMAND);
		if(action != null && !action.isEmpty() && actionMap.containsKey(action)) {
			current = actionMap.get(action);
		}
		*/
		
		CommandMapping actionMap = CommandMapping.getInstance();
		String action = request.getParameter(ParameterName.COMMAND);
		ActionCommand current = actionMap.get(action);
		return current;
	}

}
