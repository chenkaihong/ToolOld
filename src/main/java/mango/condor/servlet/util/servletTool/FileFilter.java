package mango.condor.servlet.util.servletTool;

import org.apache.commons.fileupload.FileItem;

/**
 * Copyright (c) 2011-2012 by 广州游爱 Inc.
 * @Author Create by ckh
 * @Date 2015年3月23日 下午3:30:25
 * @Description 
 */
public interface FileFilter {
	boolean process(FileItem fi);
}
