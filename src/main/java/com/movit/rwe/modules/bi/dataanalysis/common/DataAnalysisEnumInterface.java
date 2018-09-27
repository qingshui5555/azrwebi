package com.movit.rwe.modules.bi.dataanalysis.common;

public interface DataAnalysisEnumInterface {
	public static final String JSP_NAME = "jspName";
	
	public static final String SERVICE_NAME = "serviceName";
	
	public boolean isNotNull();

	public boolean isRequiredInDataFrame();

	public String getType();

	public String getValue();
	
	public String getDescription();
}
