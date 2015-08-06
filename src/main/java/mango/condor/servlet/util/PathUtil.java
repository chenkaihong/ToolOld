package mango.condor.servlet.util;

import java.io.File;

/**
 * Copyright (c) 2011-2012 by 广州游爱 Inc.
 * @Author Create by ckh
 * @Date 2015年3月20日 上午9:53:42
 * @Description 
 */
public class PathUtil {
	public static String getClassPath(){
		return PathUtil.class.getResource("/").getPath();
	}
	public static String getWebappPath(){
		return new File(PathUtil.class.getResource("/").getPath()).getParentFile().getParentFile().getPath() + "\\";
	}
}
