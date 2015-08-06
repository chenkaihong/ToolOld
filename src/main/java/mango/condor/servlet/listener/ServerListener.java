package mango.condor.servlet.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import mango.condor.servlet.process.ActiveCodeManager;
import mango.condor.servlet.util.PropertiesUtil;

import org.apache.log4j.Logger;

/**
 * Copyright (c) 2011-2012 by 广州游爱 Inc.
 * 
 * @Author Create by 梁健荣
 * @Date 2013-9-9 下午7:25:02
 * @Description
 */
public class ServerListener implements ServletContextListener {
	private static final Logger logger = Logger.getLogger(ServerListener.class.getName());

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		Runtime runtime = Runtime.getRuntime();

		long preMemory = runtime.totalMemory() - runtime.freeMemory();
		try {
			PropertiesUtil.initialization();
//			PoolManager.init();
//			ActiveCodeManager.init();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}

		// 打印JVM
		long lastMemory = runtime.totalMemory() - runtime.freeMemory();

		long usedMemory = lastMemory - preMemory;
		if (logger.isInfoEnabled()) {
			logger.info(String.format(
					"\r\n" + "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ \r\n" + "虚拟机占用总内存:%s K \r\n"
							+ "虚拟机空闲内存:%s K \r\n" + "当前使用内存:%s K = %s M \r\n"
							+ "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ \r\n", runtime.totalMemory() / 1024,
					runtime.freeMemory() / 1024, usedMemory / 1024, usedMemory / 1024 / 1024));
		}

		logger.info("================startUp condor_active end=============================");
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}
}
