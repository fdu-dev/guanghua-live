package cn.edu.fudan.anniversary.listener;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;

public class MyContextLoaderListener extends ContextLoaderListener {

	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
	}

	public void contextDestroyed(ServletContextEvent event) {
		super.contextDestroyed(event);
	}
}