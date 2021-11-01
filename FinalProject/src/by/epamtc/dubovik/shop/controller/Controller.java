package by.epamtc.dubovik.shop.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epamtc.dubovik.shop.controller.command.ActionCommand;
import by.epamtc.dubovik.shop.controller.command.factory.ActionFactory;

public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Controller() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		processRequest(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		processRequest(request, response);
	}
	
	private void processRequest(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		ActionFactory factory = new ActionFactory();
		ActionCommand command = factory.defineCommand(request);
		
		if(command != null) {
			command.execute(request, response);
		} else {
			request.getRequestDispatcher(Page.ERROR404).forward(request, response);
		}
	}

}
