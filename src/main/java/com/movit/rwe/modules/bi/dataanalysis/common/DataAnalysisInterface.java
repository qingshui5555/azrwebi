package com.movit.rwe.modules.bi.dataanalysis.common;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface DataAnalysisInterface {
	
	public Map execute(HttpServletRequest request) throws Exception;
}
