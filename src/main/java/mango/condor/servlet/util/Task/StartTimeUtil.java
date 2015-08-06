package mango.condor.servlet.util.Task;

import java.text.ParseException;
import java.util.Calendar;
import java.util.regex.Pattern;

/**
 * Copyright (c) 2011-2012 by 广州游爱 Inc.
 * @Author Create by 梁健荣
 * @Date 2015年4月16日 下午2:08:16
 * @Description 
 */
public class StartTimeUtil {
	
	/**
	 * 获取固定时刻开启时间,格式如下: HH:mm:ss
	 * @param startTime 格式如下: HH:mm:ss
	 * @return
	 * @throws ParseException
	 */
	public static long momentTime(String startTime){
		Pattern pattern = Pattern.compile("\\d{2}:\\d{2}:\\d{2}");
		if(!pattern.matcher(startTime).find()){
			throw new RuntimeException("Must post like this < 14:30:25 > - " + startTime);
		}
		Calendar now = Calendar.getInstance();
		String[] temp = startTime.split(":");
		now.set(Calendar.HOUR_OF_DAY, Integer.parseInt(temp[0]));
		now.set(Calendar.MINUTE, Integer.parseInt(temp[1]));
		now.set(Calendar.SECOND, Integer.parseInt(temp[2]));
		return now.getTimeInMillis();
	}
	
	/**
	 * 设定多少秒以后开始执行秒循环任务
	 * @param secondLater 多少秒开始任务,如果输入为0,则代表从当前任务添加时刻开始执行,若任务较多,可能会导致任务进入下一个秒循环周期,推荐使用立即开始标志确保立即执行的任务
	 * @return
	 */
	public static long delaySeconds(int secondLater){
		return System.currentTimeMillis() + (secondLater * 1000L);
	}
	
}


//获取循环类型(年循环,月循环,周循环,日循环,小时循环,分钟循环,秒循环)
//(基础启动时间_执行步长: 如果基础启动时间小于当前时间,则会根据基础启动时间进行下次任务时间的计算,
//若基础启动时间为0000-00-00 00:00:00_1,则以当前时间为基础时间,立刻执行一次,然后计算下次时间)
//private CycleType getCycleType(String cycleDescription) throws Exception{
//	// 每年1月15号 13:50:40 	- 2015-04-01 13:50:40
//	// 每月1号 13:30:00	  	- 04-01 13:30:30
//	// 每周2 15:30:00		- 02 15:30:00
//	// 每日 12:30:00			- 12:30:00
//	// 每小时				- hour
//	// 每分钟				- min
//	// 每秒					- sec
//	Pattern patternYear = Pattern.compile("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}_\\d+");
//	Pattern patternMonth = Pattern.compile("\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}_\\d+");
//	Pattern patternWeek = Pattern.compile("[0][1-7] \\d{2}:\\d{2}:\\d{2}_\\d+");
//	Pattern patternDay = Pattern.compile("\\d{2}:\\d{2}:\\d{2}_\\d+");
//	Pattern patternHour = Pattern.compile("hour_\\d+");
//	Pattern patternMin = Pattern.compile("min_\\d+");
//	Pattern patternSecond = Pattern.compile("sec_\\d+");
//	
//	Map<CycleType, Pattern> patternMap = new HashMap<CycleType, Pattern>();
//	patternMap.put(CycleType.Year, patternYear);
//	patternMap.put(CycleType.Month, patternMonth);
//	patternMap.put(CycleType.Week, patternWeek);
//	patternMap.put(CycleType.Day, patternDay);
//	patternMap.put(CycleType.Hour, patternHour);
//	patternMap.put(CycleType.Min, patternMin);
//	patternMap.put(CycleType.Second, patternSecond);
//	
//	for(Entry<CycleType, Pattern> patternEntry : patternMap.entrySet()){
//		if(patternEntry.getValue().matcher(cycleDescription).find()){
//			return patternEntry.getKey();
//		}
//	}
//	throw new Exception("I not know this CycleDescription! - " + cycleDescription);
//}
