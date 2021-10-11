package by.epamtc.dubovik.shop.servlet.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epamtc.dubovik.shop.servlet.Page;

public class ToLoginPageCommand implements ActionCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.getRequestDispatcher(Page.LOGIN).forward(request, response);
	}

}
