package mango.condor.servlet.process;

import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import mango.condor.servlet.dao.PoolManager;
import mango.condor.servlet.domain.ActivityCodeAndOpenID;
import mango.condor.servlet.entity.ReceiveXmlEntity;
import mango.condor.servlet.util.ResultXmlUtil;


/**
 * Copyright (c) 2011-2012 by 广州游爱 Inc.
 * @Author Create by ckh
 * @Date 2015年3月16日 上午10:31:19
 * @Description 
 */
public class ActiveCodeManager implements ProcessInter{
	
	public static final ActiveCodeManager defaultChoice = new ActiveCodeManager();
	
	private static final String getActivityCodeSql = "SELECT activeCode FROM active_group a LEFT JOIN actvie_code b ON a.groupId=b.fkGroupId LEFT JOIN activityCode_openID c USING(activeCode) WHERE activityId = ? AND c.activeCode IS NULL LIMIT 0,1";
	private static final String insertOpenID = "INSERT INTO activityCode_openID VALUE(?,?,?)";
	private static final String selOpenID = "SELECT openID,activeCode FROM activityCode_openID WHERE contentID = ?";
	private static final String contentID = "72";
	private static Map<String,Map<String,String>> activityCodeCache;
	
	public static void init(){
		activityCodeCache = new ConcurrentHashMap<String,Map<String,String>>();
		Map<String, String> contentIDMap = null;
		try {
			contentIDMap = PoolManager.select(selOpenID, ActivityCodeAndOpenID.defualt, contentID+"");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		activityCodeCache.put(contentID, contentIDMap);
		System.out.println("Finish ActiveCodeManager init -- Map: " + contentIDMap);
	}

	private String getActivityCode(String contentID,String openID){
		Map<String, String> activityCodeMap = activityCodeCache.get(contentID);
		String activityCode = activityCodeMap.get(openID);
		if(activityCode == null || activityCode.isEmpty()){
			synchronized (ActiveCodeManager.class) {
				try {
					activityCode = PoolManager.getSingleString(getActivityCodeSql, "activeCode", contentID);
					if(activityCode == null || activityCode.isEmpty()){
						return "激活码还没有准备好哦!请联系客服咨询! errID: 1617";
					}
					// 将openID 与 activityID的对应关系插入数据库
					if(!PoolManager.insert(insertOpenID, openID,activityCode,contentID)){
						activityCode = null;
					}
					if(activityCode == null || activityCode.isEmpty()){
						return "激活码还没有准备好哦!请联系客服咨询! errID: 1618";
					}
				} catch (SQLException e) {
					e.printStackTrace();
					return "激活码还没有准备好哦!请联系客服咨询! errID: 1507";
				}
				activityCodeMap.put(openID, activityCode);
			}
		}
		activityCode = String.format("亲,关注礼包-激活码为: %s",activityCode);
		return activityCode;
	}

	@Override
	public String process(ReceiveXmlEntity xmlEntity) {
		String content = xmlEntity.getContent();
		//暂时没有映射全部contentID的需求,有需求再改成动态获取
		if(content == null || content.isEmpty() || !"我爱比武招亲".equals(content)){
			return null;
		}
		String result = getActivityCode(contentID,xmlEntity.getFromUserName());
		return result;
	}

	@Override
	public String resultXml(ReceiveXmlEntity xmlEntity,String cotent) {
		return ResultXmlUtil.produceText(xmlEntity, cotent);
	}
}