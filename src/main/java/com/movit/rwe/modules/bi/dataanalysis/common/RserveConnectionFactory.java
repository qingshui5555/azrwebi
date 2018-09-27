package com.movit.rwe.modules.bi.dataanalysis.common;

import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

import com.movit.rwe.common.config.Global;

public class RserveConnectionFactory {

	private static final String RSERVE_IP = Global.getConfig("rserve.ip");
	private static final int RSERVE_PORT = Integer.parseInt(Global.getConfig("rserve.port"));
	
	public static RConnection createConnection() throws RserveException{
		RConnection conn = new RConnection(RSERVE_IP,RSERVE_PORT);
		return conn;
	}
}
