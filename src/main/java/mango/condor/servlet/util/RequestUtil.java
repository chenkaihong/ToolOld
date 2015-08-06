package mango.condor.servlet.util;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright (c) 2011-2012 by 广州游爱 Inc.
 * @Author Create by 梁健荣
 * @Date 2015年3月25日 下午6:25:02
 * @Description 
 */
public class RequestUtil {
	public static String getUrl(HttpServletRequest request, String path){
		if(path != null && !path.startsWith("/")){
			path = "/" + path;
		}
		return request.getContextPath() + path;
	}
}
