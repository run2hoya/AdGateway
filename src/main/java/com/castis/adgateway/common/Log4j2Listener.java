package com.castis.adgateway.common;

import com.castis.adgateway.common.setting.VersionSetting;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.slf4j.MDC;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.File;

@WebListener
public class Log4j2Listener implements ServletContextListener{
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		MDC.put(VersionSetting.PRODUCT_VERSION_KEY, VersionSetting.PRODUCT_VERSION);
		LoggerContext context = (LoggerContext)LogManager.getContext(false);
		context.setConfigLocation(new File(System.getProperty("catalina.home") + "/conf/adGateway/adGateway.log4j2.xml").toURI());
		context.reconfigure();
			
	}
}
