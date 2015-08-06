package mango.condor.servlet.dao;

import java.sql.ResultSet;

/**
 * Copyright (c) 2011-2012 by 广州游爱 Inc.
 * @Author Create by 梁健荣
 * @Date 2015年3月16日 下午6:46:12
 * @Description 
 */
public interface Buildself<T> {
	T buildself(ResultSet rs);
}
