package com.bizislife.core.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener, ServletContextListener, HttpSessionAttributeListener{

	public void attributeAdded(HttpSessionBindingEvent event) {
		// TODO Auto-generated method stub
		System.out.println("SessionListener.attributeAdded");
		
	}

	public void attributeRemoved(HttpSessionBindingEvent event) {
		// TODO Auto-generated method stub
		System.out.println("SessionListener.attributeRemoved");
		
	}

	public void attributeReplaced(HttpSessionBindingEvent event) {
		// TODO Auto-generated method stub
		System.out.println("SessionListener.attributeReplaced");
		
	}

	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		System.out.println("SessionListener.contextInitialized");
		
	}

	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		System.out.println("SessionListener.contextDestroyed");
		
	}

	public void sessionCreated(HttpSessionEvent se) {
		// TODO Auto-generated method stub
		System.out.println("SessionListener.sessionCreated");
		
	}

	public void sessionDestroyed(HttpSessionEvent se) {
		// TODO Auto-generated method stub
		System.out.println("SessionListener.sessionDestroyed");
		
	}

}
