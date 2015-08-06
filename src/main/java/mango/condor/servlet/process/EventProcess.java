package mango.condor.servlet.process;

import mango.condor.servlet.entity.ReceiveXmlEntity;
import mango.condor.servlet.util.PropertiesUtil;
import mango.condor.servlet.util.ResultXmlUtil;

/**
 * Copyright (c) 2011-2012 by 广州游爱 Inc.
 * @Author Create by ckh
 * @Date 2015年3月18日 下午6:14:01
 * @Description 
 */
public class EventProcess implements ProcessInter{
	public static final EventProcess defaultChoice = new EventProcess();

	@Override
	public String process(ReceiveXmlEntity xmlEntity) {
		String eventKey = xmlEntity.getEventKey();
		System.out.println("process evenProcess: " + eventKey);
		if(eventKey == null){
			return null;
		}
		String result = PropertiesUtil.getProperties(eventKey); 
		System.out.println("process result: " + result);
		return result;
	}

	@Override
	public String resultXml(ReceiveXmlEntity xmlEntity, String cotent) {
		String eventKey = xmlEntity.getEventKey();
		System.out.println("resultXml evenProcess: " + eventKey);
		String result = null;
		if(eventKey.endsWith("_text")){
			result = ResultXmlUtil.produceText(xmlEntity, cotent);
		}else if(eventKey.endsWith("_news")){
			result = ResultXmlUtil.produceNews(xmlEntity, cotent);
		}else if(eventKey.endsWith("_img")){
			result = ResultXmlUtil.produceImg(xmlEntity, cotent);
		}
		return result;
	}
}
