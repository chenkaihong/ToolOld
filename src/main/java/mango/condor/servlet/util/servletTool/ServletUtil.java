package mango.condor.servlet.util.servletTool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import mango.condor.servlet.util.CloseUtil;
import mango.condor.servlet.util.FileUtil;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;

/**
 * Copyright (c) 2011-2012 by 广州游爱 Inc.
 * @Author Create by ckh
 * @Date 2015年3月25日 下午12:47:24
 * @Description 
 */
public class ServletUtil {
	
	/**
	 * 将以multipart/form-data报文头提交的HttpServletRequest封装为JspFileItemModel,封装过程中使用FileFilter对文件进行过滤,如果过滤不通过则会抛出FileUploadException异常
	 * @see JspFileItemModel
	 * @see FileFilter
	 * @param request
	 * @param fileFilter	自定义文件过滤器,返回boolean,true: 需要过滤,抛出异常 - false: 通过过滤
	 * @return
	 * @throws FileUploadException
	 */
	public static JspFileItemModel getJSPForm(HttpServletRequest request,FileFilter fileFilter) throws FileUploadException{
		int maxFileSize = 2000 * 1024;		// 内存存储文件大小 2M
		int maxMemSize = 1500 * 1024;		// 允许上传文件大小 1.5M

		// 验证上传内容了类型
		String contentType = request.getContentType();
		Map<String, String> textMap = new HashMap<String, String>();
		Map<String, FileItem> fileMap = new HashMap<String, FileItem>();
		if ((contentType.indexOf("multipart/form-data") >= 0)) {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// 设置内存中存储文件的最大值
			factory.setSizeThreshold(maxMemSize);
			// 创建一个新的文件上传处理程序
			ServletFileUpload upload = new ServletFileUpload(factory);
			// 设置最大上传的文件大小
			upload.setSizeMax(maxFileSize);
			
			@SuppressWarnings("unchecked")
			Iterator<FileItem> fileIterator = upload.parseRequest(request).iterator();
			while (fileIterator.hasNext ()) {
	            FileItem fi = fileIterator.next();
	            String jspName = fi.getFieldName();
            	String fileName = fi.getName();
            	
            	if(!fi.isFormField()){
            		// 使用自定义过滤器,过滤不应该传入的文件名称
            		if(StringUtils.isEmpty(fileName)){
            			throw new FileUploadException(String.format("@ %s @ is empty!You need to choose a file!", jspName));
            		}
                	if(fileFilter.process(fi)){
                		throw new FileUploadException("Filter --> This file can't be upload! - " + fileName);
                	}
            		fileMap.put(jspName, fi);
            	}else{
            		textMap.put(jspName, fi.getString());
            	}
	        }
		}
		return new JspFileItemModel(textMap, fileMap);
	}
	
	/**
	 * 使用FileItem进行文件保存,文件是以JSP上传来的完整文件进行存储
	 * @param fi jsp使用multipart/form-data类型回传的数据流封装, 基于commons-fileupload-1.2.2.jar
	 * @param isMakeDir 如果路径不合法,是否创建父路径
	 * 
	 * @throws FileUploadException 写入失败时会抛出异常,由使用者进行处理
	 * @throws FileNotFoundException 
	 */
	public static void saveFile(FileItem fi, String fileLocal, boolean isMakeDir) throws FileUploadException, FileNotFoundException{
		File file = new File(fileLocal);
		FileUtil.saveCheck(file, isMakeDir);
        try {
			fi.write(file) ;
		} catch (Exception e) {
			e.printStackTrace();
			throw new FileUploadException("Writting err! - " + fi.getName(), e);
		}
	}
	
	/**
	 * servlet返回值发送,发送完毕后手动关闭资源
	 * @param os
	 * @param charsetName	发送编码
	 * @param vals			发送参数
	 */
	public static void outPutString(OutputStream os,String charsetName, String ...vals){
		try {
			for(String val : vals){
				os.write(val.getBytes(charsetName));
			}
			os.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			CloseUtil.close(os);
		}
	}
	
	public static class JspFileItemModel{
		/** 文本格式的容器	Map<jspName,jspcontent> */
		public final Map<String,String> textMap;
		/** 文件格式的容器	Map<jspName,fileItem> */
		public final Map<String,FileItem> fileMap;
		
		public JspFileItemModel(Map<String, String> textMap, Map<String, FileItem> fileMap) {
			this.textMap = Collections.unmodifiableMap(textMap);
			this.fileMap = Collections.unmodifiableMap(fileMap);
		}
	}
}
