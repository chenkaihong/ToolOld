package mango.condor.servlet.process;

import mango.condor.servlet.entity.ReceiveXmlEntity;

/**
 * Copyright (c) 2011-2012 by 广州游爱 Inc.
 * @Author Create by ckh
 * @Date 2015年3月16日 下午3:47:52
 * @Description 
 */
public interface ProcessInter {
	String process(ReceiveXmlEntity xmlEntity);
	String resultXml(ReceiveXmlEntity xmlEntity, String cotent);
}
