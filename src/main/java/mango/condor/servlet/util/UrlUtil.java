package mango.condor.servlet.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 * Copyright (c) 2011-2012 by 广州游爱 Inc.
 * @Author Create by 梁健荣
 * @Date 2015年3月23日 上午11:34:10
 * @Description 
 */
public class UrlUtil {
	
	/**
	 * 使用get方式进行url访问并且返回String
	 * @param url 需要访问的url
	 */
	public static String sendGet(String urlLocal) throws IOException{
		URL url = new URL(urlLocal);
		URLConnection conn = getRetrunConnection(url, SendTpye.GET);
        return StreamUtil.fromInputStreamToString(conn.getInputStream());
	}
	
	/**
	 * 使用post方式进行url访问并且返回String
	 * @param url 需要访问的url
	 * @param val 需要传入的参数
	 */
	public static String sendPost(String urlLocal, String ...val) throws IOException{
		URL url = new URL(urlLocal);
        URLConnection conn = getRetrunConnection(url, SendTpye.POST, val);
        return StreamUtil.fromInputStreamToString(conn.getInputStream());
	}
	
	/**
	 * 使用get方式进行url访问并且将返回内容保存在文件中
	 * @param url 需要访问的url
	 */
	public static File sendGetReturnFile(String urlLocal, String fileLocal, boolean isCover, boolean isMakDir) throws IOException{
		URL url = new URL(urlLocal);
		URLConnection conn = getRetrunConnection(url, SendTpye.GET);
        return FileUtil.fromStreamToFile(conn.getInputStream(), fileLocal, isCover, isMakDir);
	}
	
	/**
	 * 使用post方式进行url访问并且将返回内容保存在文件中
	 * @param url 需要访问的url
	 * @param val 需要传入的参数
	 */
	public static File sendPostReturnFile(String urlLocal, String fileLocal, boolean isCover, boolean isMakDir, String ...val) throws IOException{
		URL url = new URL(urlLocal);
        URLConnection conn = getRetrunConnection(url, SendTpye.POST, val);
        return FileUtil.fromStreamToFile(conn.getInputStream(), fileLocal, isCover, isMakDir);
	}
	
	/**
	 * 获取已经发出请求的connection
	 */
	private static URLConnection getRetrunConnection(URL url, SendTpye sendTpye, String ...val) throws IOException{
		URLConnection connection = url.openConnection();
		connection.setRequestProperty("accept", "*/*");
		connection.setRequestProperty("connection", "Keep-Alive");
		connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
		connection.setConnectTimeout(5000);
		switch(sendTpye){
		case GET:
			connection.connect();
			break;
		case POST:
            // 发送POST请求必须设置如下两行
            connection.setDoOutput(true);
            connection.setDoInput(true);
            PrintWriter out = null;
            try{
	            // 获取URLConnection对象对应的输出流
	            out = new PrintWriter(connection.getOutputStream());
	            // 发送请求参数
	            if(val != null && val.length > 0){
	            	for(String s : val){
	            		out.print(s);
	            	}
	            }
	            out.flush();
            } finally{
            	CloseUtil.close(out);
            }
			break;
		default:
			throw new IOException("You must choose a sendType!");
		}
		return connection;
	}
	
//	public static void main(String[] args) throws JsonSyntaxException, IOException {
//		String appID = "wx6776ef2dc1c6e121";
//		String AppSecret = "b9a8892a055eb91f285f9f8712012c6e";
//		String tokenUrl = String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s",appID,AppSecret);
//		TokenModel tokenModel = new Gson().fromJson(UrlUtil.sendGet(tokenUrl), TokenModel.class);
//		
//		String mediaListUrl = String.format("https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=%s", tokenModel.access_token);
//		String mediaListVal = "{\"type\":\"image\",\"offset\":\"0\",\"count\":\"20\"}";
//		System.out.println(UrlUtil.sendPost(mediaListUrl, mediaListVal));
//		
////		String MEDIA_ID = "8796428418";
////		String medialUrl = String.format("https://api.weixin.qq.com/cgi-bin/material/get_material?access_token=%s", tokenModel.access_token);
////		String mediaVal = String.format("{\"media_id\":\"%s\"}",MEDIA_ID);
////		System.out.println(UrlUtil.sendPostReturnFile(medialUrl, "d:/img/test.jpg", true, true, mediaVal));
//	}
	
	public class TokenModel{
		public final String access_token;
		public final String expires_in;
		
		public TokenModel(String access_token, String expires_in) {
			super();
			this.access_token = access_token;
			this.expires_in = expires_in;
		}
	}
}

enum SendTpye{
	GET,POST
}