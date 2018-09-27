package com.movit.rwe.modules.bi.dataanalysis.common;

import org.apache.batik.dom.util.HashTable;
import org.apache.commons.lang3.StringUtils;
import org.rosuda.REngine.Rserve.RConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.movit.rwe.modules.bi.dataanalysis.entity.ConnectionBean;

public class RserveConnectionPool {

	private static Logger logger = LoggerFactory.getLogger(RserveConnectionPool.class);
	
	//连接池活动状态，默认正常
	private static boolean isActive = true;
	//连接池
	private static HashTable pool = new HashTable();
	
	//创建当前用户的连接
	public static synchronized void createConnection(String userId) throws Exception{
		if(StringUtils.isNotBlank(userId)){
			ConnectionBean connectionBean = new ConnectionBean();
//			RConnection conn = RserveConnectionFactory.createConnection();
//			connectionBean.setConnection(conn);
			pool.put(userId, connectionBean);
			logger.info("连接池中新增用户ID为["+userId+"]的Rserve连接！");
		}else{
			throw new Exception("创建Rserve连接时，用户ID不能为空");
		}
	}
	
	//获得当前用户的连接
    public static synchronized RConnection getConnection(String userId) throws Exception{
    	if(StringUtils.isNotBlank(userId)){
    		ConnectionBean connectionBean = (ConnectionBean)pool.get(userId);
    		RConnection conn = null;
//	    	RConnection conn = (RConnection)pool.get(userId);
	    	if(connectionBean==null){
	    		createConnection(userId);
	    		connectionBean = (ConnectionBean)pool.get(userId);
	    		conn = connectionBean.getConnection();
//	    		conn = (RConnection)pool.get(userId);
//	    		throw new Exception("连接池中用户ID为["+userId+"]的Rserve连接不存在！");
	    	}else{
	    		conn = connectionBean.getConnection();
	    	}
	    	logger.info("从连接池中获取用户ID为["+userId+"]的Rserve连接");
	    	return conn;
    	}else{
    		throw new Exception("用户ID为空无法获取Rserve连接！");
    	}
    }
    
//    //回收连接
//    public void releaseConn(RConnection conn) {
//    	
//    }
    
    //断开当前用户的连接
    public static synchronized void closeConnection(String userId) throws Exception{
    	if(StringUtils.isNotBlank(userId)){
	    	RConnection conn = (RConnection)pool.get(userId);
	    	if(conn!=null){
	    		conn.close();
	    	}
	    	logger.info("从连接池中断开用户ID为["+userId+"]的Rserve连接");
    	}else{
    		throw new Exception("用户ID为空无法关闭Rserve连接！");
    	}
    }
    
    //判断连接是否可用
    public static boolean isValid(RConnection conn){
    	if(conn!=null&&conn.isConnected()){
    		return true;
    	}else 
    		return false;
    }
    //连接池是否活动状态
    public static boolean isActive(){
    	return isActive;
    }
    
    //定时器，检查连接池
//    public void cheackPool(){
//    	if(dbBean.isCheakPool()){
//            new Timer().schedule(new TimerTask() {
//            @Override
//            public void run() {
//            // 1.对线程里面的连接状态
//            // 2.连接池最小 最大连接数
//            // 3.其他状态进行检查，因为这里还需要写几个线程管理的类，暂时就不添加了
//            System.out.println("空线池连接数："+freeConnection.size());
//            System.out.println("活动连接数：："+activeConnection.size());
//            System.out.println("总的连接数："+contActive);
//                }
//            },dbBean.getLazyCheck(),dbBean.getPeriodCheck());
//        }
//    }
    
    //销毁连接池
    public static synchronized void destroyPool(){
    	int poolSize = pool.size();
    	for(int i=0;i<pool.size();i++){
    		RConnection conn = (RConnection)pool.item(i);
    		conn.close();
    	}
    	pool.clear();
    	isActive = false;
    	logger.info("销毁Rserve连接池");
    }
    
}
