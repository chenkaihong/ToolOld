package mango.condor.servlet.util.customException;


/**
 * Copyright (c) 2011-2012 by 广州游爱 Inc.
 * @Author Create by ckh
 * @Date 2015年3月23日 下午8:24:45
 * @Description 
 */
public class ProcessErrException extends Exception{
	private static final long serialVersionUID = 1L;
	
	public ProcessErrException(String msg){
		super(msg);
	}
}
