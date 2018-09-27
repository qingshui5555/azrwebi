package com.movit.rwe.modules.bi.dataanalysis.entity.enums;

import com.movit.rwe.modules.bi.dataanalysis.common.DataAnalysisEnumInterface;

public enum TtestEnum implements DataAnalysisEnumInterface {
	// spring中service的bean名
	serviceName(true, false, "string", "ttestService", "ttest分析服务名"),
	// jsp页面名称，用于dataAnalysisMain.jsp中approach下拉列表选择
	// value为跳转的页面名（不包含后缀），descript为显示名
	jspName(true, false, "string", "ttest", "t.test"), 
	//x对应数据库中配置的列名称x
	x(false, true, "number", "", "向量x，必须"), 
	//y对应数据库中配置的列名称y
	y(false, false,"number", "", "向量y，非必须"), 
	mu(true, false, "number", "0", "mu默认为0"), 
	alternative(true, false, "string", "two.sided","alternative默认为two.sided"), 
	confLevel(true, false, "number", "0.95", "confLevel默认为0.95"), 
	paired(true, false, "string", "FALSE", "paired默认为FALSE");

	//此枚举value值是否可空
	boolean notNull;
	// 在dataframe中是否必须
	boolean requiredInDataFrame;
	// 字段值类型
	String type;
	// 具体指
	String value;
	// 描述
	String description;

	private TtestEnum(boolean notNull, boolean requiredInDataFrame, String type, String value, String description) {
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
