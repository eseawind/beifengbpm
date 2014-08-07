package com.beifengbpm.api.listener;

import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class SysContextListener implements ServletContextListener {

	private Timer timer=null;
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		timer.cancel();
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		timer=new Timer();
		timer.schedule(new OverTimeTask(), 0, 60*1000);
	}

}
