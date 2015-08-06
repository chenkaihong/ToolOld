package mango.condor.servlet.util;

import java.util.Date;

import mango.condor.servlet.entity.ReceiveXmlEntity;

/**
 * Copyright (c) 2011-2012 by 广州游爱 Inc.
 * @Author Create by 梁健荣
 * @Date 2015年3月20日 下午12:28:47
 * @Description 
 */
public class ResultXmlUtil {
	public static String produceText(ReceiveXmlEntity xmlEntity,String cotent){
		StringBuffer sb = new StringBuffer();
		Date date = new Date();
		sb.append("<xml><ToUserName><![CDATA[");
		sb.append(xmlEntity.getFromUserName());
		sb.append("]]></ToUserName><FromUserName><![CDATA[");
		sb.append(xmlEntity.getToUserName());
		sb.append("]]></FromUserName><CreateTime>");
		sb.append(date.getTime());
		sb.append("</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[");
		sb.append(cotent);
		sb.append("]]></Content><FuncFlag>0</FuncFlag></xml>");
		System.out.println("resultXml: " + sb.toString());
		return sb.toString();
	}
	
	public static String produceImg(ReceiveXmlEntity xmlEntity,String cotent){
		StringBuffer sb = new StringBuffer();
		Date date = new Date();
		sb.append("<xml><ToUserName><![CDATA[");
		sb.append(xmlEntity.getFromUserName());
		sb.append("]]></ToUserName><FromUserName><![CDATA[");
		sb.append(xmlEntity.getToUserName());
		sb.append("]]></FromUserName><CreateTime>");
		sb.append(date.getTime());
		sb.append("</CreateTime><MsgType><![CDATA[image]]></MsgType><Image><MediaId><![CDATA[");
		sb.append(cotent);
		sb.append("]]></MediaId></Image><FuncFlag>1</FuncFlag></xml>");
		System.out.println("resultXml: " + sb.toString());
		return sb.toString();
	}
	
	public static String produceNews(ReceiveXmlEntity xmlEntity,String newsName){
		StringBuffer sb = new StringBuffer();
		Date date = new Date();
		// 暂时支持单news的发布,如果有需求再开发多news的发布
		sb.append("<xml><ToUserName><![CDATA[");
		sb.append(xmlEntity.getFromUserName());
		sb.append("]]></ToUserName><FromUserName><![CDATA[");
		sb.append(xmlEntity.getToUserName());
		sb.append("]]></FromUserName><CreateTime>");
		sb.append(date.getTime());
		sb.append("</CreateTime><MsgType><![CDATA[news]]></MsgType>");
		sb.append("<ArticleCount>");
		sb.append(1);
		sb.append("</ArticleCount>");
		sb.append("<Articles>");
		sb.append("<item>");
		sb.append("<Title><![CDATA[");
		sb.append(PropertiesUtil.getProperties(String.format("%s_%s",newsName,"Title")));
		sb.append("]]></Title><Description><![CDATA[");
		sb.append(PropertiesUtil.getProperties(String.format("%s_%s",newsName,"Description")));
		sb.append("]]></Description><PicUrl><![CDATA[");
		sb.append(PropertiesUtil.getProperties(String.format("%s_%s",newsName,"PicUrl")));
		sb.append("]]></PicUrl><Url><![CDATA[");
		sb.append(PropertiesUtil.getProperties(String.format("%s_%s",newsName,"Url")));
		sb.append("]]></Url>");
		sb.append("</item></Articles><FuncFlag>2</FuncFlag></xml>");
		System.out.println("resultXml: " + sb.toString());
		return sb.toString();
	}
}
