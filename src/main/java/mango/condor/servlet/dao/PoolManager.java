package mango.condor.servlet.dao;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * Copyright (c) 2011-2012 by 广州游爱 Inc.
 *
 * @Author Create by ckh
 * @Date 2015-3-16 下午7:09:47
 * @Description
 */
public class PoolManager {
	private static final Logger logger = Logger.getLogger(PoolManager.class.getName());

	private static ComboPooledDataSource pool;

	public static void init() throws IOException, PropertyVetoException {
		logger.info("加载地址池配置项...");
		pool = new ComboPooledDataSource();// 直接使用即可，不用显示的配置，其会自动识别配置文件
		Properties p = new Properties();
		p.load(PoolManager.class.getClassLoader().getResourceAsStream("c3p0-default.properties"));
		pool.setJdbcUrl(p.getProperty(("c3p0.jdbcUrl")));
		pool.setDriverClass(p.getProperty(("c3p0.driverClass")));
		pool.setUser(p.getProperty("c3p0.user"));
		pool.setPassword(p.getProperty("c3p0.password"));
		pool.setMaxPoolSize(Integer.parseInt(p.getProperty(("c3p0.maxPoolSize"))));
		pool.setMinPoolSize(Integer.parseInt(p.getProperty(("c3p0.minPoolSize"))));
		pool.setAcquireIncrement(Integer.parseInt(p.getProperty(("c3p0.acquireIncrement"))));
		pool.setInitialPoolSize(Integer.parseInt(p.getProperty(("c3p0.initialPoolSize"))));
		pool.setMaxIdleTime(Integer.parseInt(p.getProperty(("c3p0.maxIdleTime"))));
		pool.setMaxStatements(Integer.parseInt(p.getProperty(("c3p0.maxStatements"))));
	}

	public static String getSingleString(String sql, String columName, String ...val) throws SQLException {
		Connection ct = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ct = pool.getConnection();
			ps = ct.prepareStatement(sql);
			if(val != null && val.length > 0){
				for(int i = 0;i < val.length;i++){
					String parm = val[i];
					ps.setString(i+1, parm);
				}
			}
		
			rs = ps.executeQuery();
			while(rs.next()){
				return rs.getString(columName);
			}
		}finally{
			close(ct, ps, rs);
		}
		return null;
	}
	
	public static boolean insert(String sql, String ...val) throws SQLException{
		Connection ct = null;
		PreparedStatement ps = null;
		try{
			ct = pool.getConnection();
			ps = ct.prepareStatement(sql);
			if(val != null && val.length > 0){
				for(int i = 0;i < val.length;i++){
					String parm = val[i];
					ps.setString(i+1, parm);
				}
			}
		
			return ps.executeUpdate() > 0;
		}finally{
			close(ct, ps);
		}
	}
	
	public static <T> T select(String sql, Buildself<T> newModel, String ...val) throws Exception{
		Connection ct = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ct = pool.getConnection();
			ps = ct.prepareStatement(sql);
			if(val != null && val.length > 0){
				for(int i = 0;i < val.length;i++){
					String parm = val[i];
					ps.setString(i+1, parm);
				}
			}
			rs = ps.executeQuery();
			return newModel.buildself(rs);
		}finally{
			close(ct, ps, rs);
		}
	}
	
	private static void close(Connection ct,PreparedStatement ps,ResultSet rs){
		try {
			if(rs != null){
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			rs = null;
		}
		try {
			if(ps != null){
				ps.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			ps = null;
		}
		try {
			if(ct != null){
				ct.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			ct = null;
		}
	}
	
	private static void close(Connection ct,PreparedStatement ps){
		try {
			if(ps != null){
				ps.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			ps = null;
		}
		try {
			if(ct != null){
				ct.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			ct = null;
		}
	}
}
