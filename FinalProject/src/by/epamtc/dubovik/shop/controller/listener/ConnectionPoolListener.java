package by.epamtc.dubovik.shop.controller.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import by.epamtc.dubovik.shop.dao.connectionpool.ConnectionPool;

public class ConnectionPoolListener implements ServletContextListener {

   
    public ConnectionPoolListener() {
       
    }

    public void contextDestroyed(ServletContextEvent arg0)  { 
    	 ConnectionPool pool = ConnectionPool.getInstance();
         pool.dispose();
    }

    public void contextInitialized(ServletContextEvent arg0)  { 
        ConnectionPool pool = ConnectionPool.getInstance();
        pool.initPoolData();
    }
	
}
