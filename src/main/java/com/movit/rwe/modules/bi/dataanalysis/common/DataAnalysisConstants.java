package com.movit.rwe.modules.bi.dataanalysis.common;

public class DataAnalysisConstants {
	//枚举包名
	public static final String DA_ENUM_PACKAGE_NAME = 
			"com.movit.rwe.modules.bi.dataanalysis.entity.enums";
	//sql占位符
	public static final String DA_SQLTAG_TA_ID = "#{taId}";
	public static final String DA_SQLTAG_STUDY_ID = "#{studyId}";
	public static final String DA_SQLTAG_GROUP_IDS = "#{groupids}";
	
	
	public static final String DA_MAPKEY_RESULT_CODE = "resultCode";
	public static final String DA_MAPKEY_RESULT_DESC = "resultDesc";
	public static final String DA_MAPKEY_RESULT_DATA = "resultData";
	public static final String DA_MAPVALUE_RESULT_CODE_SUCC = "1";
	public static final String DA_MAPVALUE_RESULT_CODE_FAIL = "0";
	public static final String DA_MAPVALUE_RESULT_DESC_SUCC = "成功";
	
	public static final String DA_MAPKEY_JSP_NAME_DESCRIPTION = "jspNameDescription";
	public static final String DA_MAPKEY_JSP_NAME_VALUE= "jspNameValue";
	public static final String DA_MAPKEY_SERVICE_NAME_VALUE = "serviceNameValue";
}
