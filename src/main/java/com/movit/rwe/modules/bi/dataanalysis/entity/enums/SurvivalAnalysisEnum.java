package com.movit.rwe.modules.bi.dataanalysis.entity.enums;

import com.movit.rwe.modules.bi.dataanalysis.common.DataAnalysisEnumInterface;

public enum SurvivalAnalysisEnum implements DataAnalysisEnumInterface{
	//spring中service的bean名
	serviceName(true,false,"string","survivalAnalysisService","生存分析服务名"),
	//jsp页面名称，用于dataAnalysisMain.jsp中approach下拉列表选择
	//value为跳转的页面名（不包含后缀），descript为显示名
	jspName(true,false,"string","survivalAnalysis","Survival Analysis"),
	//time对应数据库中配置的列名称time
	time(false,true,"number","","开始时间"),
	//time2对应数据库中配置的列名称time2
	time2(false,false,"number","","结束时间"),
	//event对应数据库中配置的列名称event
	event(false,false,"number","","事件"),
	formula(true,false,"string","survival_y~survival_data_frame$group","公式");
	//是否必须
//	boolean required;
	//此枚举value值是否可空
	boolean notNull;
	//在dataframe中是否必须
	boolean requiredInDataFrame;
	//字段值类型
	String type;
	//具体指
	String value;
	//描述
	String description;
	
	private SurvivalAnalysisEnum(boolean notNull,boolean requiredInDataFrame,String type,String value,String description){
		this.notNull = notNull;
		this.requiredInDataFrame = requiredInDataFrame;
		this.type = type;
		this.value = value;
		this.description = description;
	}

	public boolean isNotNull() {
		return notNull;
	}

	public void setNotNull(boolean notNull) {
		this.notNull = notNull;
	}

	public boolean isRequiredInDataFrame() {
		return requiredInDataFrame;
	}

	public void setRequiredInDataFrame(boolean requiredInDataFrame) {
		this.requiredInDataFrame = requiredInDataFrame;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
