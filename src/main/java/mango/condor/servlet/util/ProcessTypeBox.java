package mango.condor.servlet.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import mango.condor.servlet.process.ActiveCodeManager;
import mango.condor.servlet.process.EventProcess;
import mango.condor.servlet.process.ProcessInter;

/**
 * Copyright (c) 2011-2012 by 广州游爱 Inc.
 * @Author Create by 梁健荣
 * @Date 2015年3月18日 下午6:11:04
 * @Description 
 */
public class ProcessTypeBox {
	public static final Map<String,ProcessInter> TypeBox;
	static{
		Map<String,ProcessInter> temp = new HashMap<String,ProcessInter>();
		temp.put("text", ActiveCodeManager.defaultChoice);
		temp.put("event", EventProcess.defaultChoice);
		TypeBox = Collections.unmodifiableMap(temp);
	}
}
