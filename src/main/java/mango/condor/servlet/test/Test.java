package mango.condor.servlet.test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.List;

import mango.condor.servlet.util.CloseUtil;
import mango.condor.servlet.util.FileUtil;
import mango.condor.servlet.util.PathUtil;
import mango.condor.servlet.util.StreamUtil;
import mango.condor.servlet.util.UrlUtil;
import mango.condor.servlet.util.UrlUtil.TokenModel;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * Copyright (c) 2011-2012 by 广州游爱 Inc.
 * @Author Create by 梁健荣
 * @Date 2015年3月24日 下午9:26:54
 * @Description 
 */
public class Test {
	
	public static final String appID = "wx6776ef2dc1c6e121";
	public static final String AppSecret = "b9a8892a055eb91f285f9f8712012c6e";
	public static final String tokenUrl = String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s",appID,AppSecret);
	public static final String media_Url = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=%s";
	public static final String mediaListVal = "{\"type\":\"%s\",\"offset\":\"0\",\"count\":\"20\"}";
	public static final String getMedialUrl = "https://api.weixin.qq.com/cgi-bin/material/get_material?access_token=%s";
	public static final String mediaVal = "{\"media_id\":\"%s\"}";
	public static final String saveImgPath = "img/";
	public static final String saveImgUrl = "";
	
	public static NewsModel newsModel;
	public static ImgModel imgModel;

	public static NewsModel flushNewsModel() throws JsonSyntaxException, IOException {
		String mediaListVal_news = String.format(mediaListVal,"news");
		String newsResult = UrlUtil.sendPost(getMediaListUrl(), mediaListVal_news);
		NewsModel newsModel = new Gson().fromJson(newsResult,NewsModel.class);
		
		Test.newsModel = newsModel;
		return Test.newsModel;
	}
	
	public static ImgModel flushImgModel() throws JsonSyntaxException, IOException {
		String mediaListVal_img = String.format(mediaListVal,"image");
		String imgRresult = UrlUtil.sendPost(getMediaListUrl(), mediaListVal_img);
		ImgModel imgModel = new Gson().fromJson(imgRresult,ImgModel.class);
		
		Test.imgModel = imgModel;
		return Test.imgModel;
	}
	
	private static String getMediaListUrl() throws JsonSyntaxException, IOException{
		TokenModel tokenModel = new Gson().fromJson(UrlUtil.sendGet(tokenUrl), TokenModel.class);
		return String.format(media_Url, tokenModel.access_token);
	}
	
	public static String getImageUrl(String MEDIA_ID, String imgName) throws IOException{
		File file = new File(String.format("%s%s%s", PathUtil.getClassPath(), saveImgPath, imgName));
		if(!FileUtil.isCanRead(file)){
			TokenModel tokenModel = new Gson().fromJson(UrlUtil.sendGet(tokenUrl), TokenModel.class);
			file = saveImage(file.getPath(), MEDIA_ID, tokenModel.access_token);
		}
		return String.format("%s%s", saveImgUrl, imgName);
	}
	public static File saveImage(String fileLocal, String MEDIA_ID, String tokenPass) throws IOException{
		String url = String.format(getMedialUrl, tokenPass);
		String val = String.format(mediaVal,MEDIA_ID);
		return UrlUtil.sendPostReturnFile(url, fileLocal, false, false, val);
	}
	
//	public static void main(String[] args) throws IOException {
//		System.out.println(getImageUrl("8796428418", "研发团宣传长条.jpg"));
//	}
	
	public class NewsModel{
		public final int total_count;
		public final int item_count;
		public final List<NewItem> item;
		public NewsModel(int total_count, int item_count, List<NewItem> item) {
			this.total_count = total_count;
			this.item_count = item_count;
			this.item = Collections.unmodifiableList(item);
		}
	}
	public class NewItem{
		public final String media_id;
		public final Content content;
		public final long update_time;
		public NewItem(String media_id, Content content, long update_time) {
			this.media_id = media_id;
			this.content = content;
			this.update_time = update_time;
		}
	}
	public class Content{
		public final List<NewsItem> news_item;
		public Content(List<NewsItem> news_item) {
			this.news_item = Collections.unmodifiableList(news_item);
		}
	}
	public class NewsItem{
		public final String title;
		public final String author;
		public final String digest;
		public final String content;
		public final String content_source_url;
		public final String thumb_media_id;
		public final String show_cover_pic;
		public NewsItem(String title, String author, String digest,
				String content, String content_source_url,
				String thumb_media_id, String show_cover_pic) {
			this.title = title;
			this.author = author;
			this.digest = digest;
			this.content = content;
			this.content_source_url = content_source_url;
			this.thumb_media_id = thumb_media_id;
			this.show_cover_pic = show_cover_pic;
		}
	}
	
	public class ImgModel{
		public final List<ImgItem> item;
		public ImgModel(List<ImgItem> item) {
			super();
			this.item = Collections.unmodifiableList(item);
		}
	}
	public class ImgItem{
		public final String media_id;
		public final String name;
		public final long update_time;
		public ImgItem(String media_id, String name, long update_time) {
			super();
			this.media_id = media_id;
			this.name = name;
			this.update_time = update_time;
		}
	}
	
	public static void main(String[] args) throws IOException {
		
		URL url = new URL("");
		String val = "";
		
		URLConnection connection = url.openConnection();
		connection.setRequestProperty("accept", "*/*");
		connection.setRequestProperty("connection", "Keep-Alive");
		connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
		connection.setConnectTimeout(5000);

        // 发送POST请求必须设置如下两行
        connection.setDoOutput(true);
        connection.setDoInput(true);
        PrintWriter out = null;
        try{
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(connection.getOutputStream());
            // 发送请求参数
            out.print(val);
            out.flush();
        } finally{
        	CloseUtil.close(out);
        }

		
		System.out.println(StreamUtil.fromInputStreamToString(connection.getInputStream()));
	}
}
