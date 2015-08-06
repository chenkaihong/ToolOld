package mango.condor.servlet.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import mango.condor.servlet.dao.Buildself;

/**
 * Copyright (c) 2011-2012 by 广州游爱 Inc.
 * @Author Create by ckh
 * @Date 2015年3月16日 下午6:52:28
 * @Description 
 */
public class ActivityCodeAndOpenID implements Buildself<Map<String,String>>{
	
	public final static ActivityCodeAndOpenID  defualt = new ActivityCodeAndOpenID();
	
	@Override
	public Map<String,String> buildself(ResultSet rs) {
		Map<String,String> map = new ConcurrentHashMap<String,String>();
		try {
			while(rs.next()){
				map.put(rs.getString("openID"), rs.getString("activeCode"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return map;
	}
}
