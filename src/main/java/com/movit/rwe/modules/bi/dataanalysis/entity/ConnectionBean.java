package com.movit.rwe.modules.bi.dataanalysis.entity;

import org.rosuda.REngine.Rserve.RConnection;

import com.movit.rwe.modules.bi.dataanalysis.common.RserveConnectionFactory;

public class ConnectionBean {

	private RConnection connection;
	private boolean isActive;
	
	public ConnectionBean() throws Exception{
		connection = RserveConnectionFactory.createConnection();
	}
	
	public RConnection getConnection() {
		return connection;
	}
	public void setConnection(RConnection connection) {
		this.connection = connection;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	
}
