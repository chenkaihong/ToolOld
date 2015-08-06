package mango.condor.servlet.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Copyright (c) 2011-2012 by 广州游爱 Inc.
 * @Author Create by ckh
 * @Date 2015年3月20日 上午9:41:17
 * @Description 
 */
public class PropertiesUtil {
	private static final Properties properties = new Properties();
	
	private static ReadWriteLock lock = new ReentrantReadWriteLock();
	private static Lock wLock = lock.writeLock();
	private static Lock rLock = lock.readLock();
	
	public static void initialization(){
		FileReader fr = null;
		BufferedReader br = null;
		wLock.lock();
		try {
			fr = new FileReader(new File(PathUtil.getClassPath() + "EvenProperties.properties"));
			br = new BufferedReader(fr);
			properties.clear();
			properties.load(br);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		} finally{
			wLock.unlock();
			CloseUtil.close(br, fr);
		}
	}
	
	public static String getProperties(String key){
		String value = null;
		rLock.lock();
		try{
			value = properties.getProperty(key);
		}finally{
			rLock.unlock();
		}
		return value;
	}
}
