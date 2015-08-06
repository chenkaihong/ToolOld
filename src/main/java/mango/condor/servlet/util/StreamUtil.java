package mango.condor.servlet.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Copyright (c) 2011-2012 by 广州游爱 Inc.
 * @Author Create by ckh
 * @Date 2015年3月24日 下午7:22:16
 * @Description 
 */
public class StreamUtil {
	/**
	 * 将InputStream转化为String
	 * @param charType 编码格式
	 * @return
	 * @throws IOException
	 */
	public static String fromInputStreamToString(InputStream stream,String charType) throws IOException{
		BufferedReader br = null;
        InputStreamReader ir = null;
        StringBuilder sb = null;
        try {
            ir = new InputStreamReader(stream,charType);
            br = new BufferedReader(ir);
            sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
            	sb.append(line);
            }
        } finally{
        	CloseUtil.close(stream, br, ir);
        }
		return sb.toString();
	}

	/**
	 * 将InputStream转化为String
	 * @return
	 * @throws IOException
	 */
	public static String fromInputStreamToString(InputStream stream) throws IOException{
		return fromInputStreamToString(stream, "utf-8");
	}
}
