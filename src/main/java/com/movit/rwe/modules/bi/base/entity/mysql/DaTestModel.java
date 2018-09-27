package com.movit.rwe.modules.bi.base.entity.mysql;

import com.movit.rwe.common.persistence.DataEntity;

public class DaTestModel  extends DataEntity<DaTestModel>{

	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = 1L;
	
	String name;
	
	String daTestName;//DaTestEnum.name
	
	String modelName;
	
	String summaryJsonStr;
	
	String daConfJsonStr;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDaTestName() {
		return daTestName;
	}

	public void setDaTestName(String daTestName) {
		this.daTestName = daTestName;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getSummaryJsonStr() {
		return summaryJsonStr;
	}

	public void setSummaryJsonStr(String summaryJsonStr) {
		this.summaryJsonStr = summaryJsonStr;
	}

	public String getDaConfJsonStr() {
		return daConfJsonStr;
	}

	public void setDaConfJsonStr(String daConfJsonStr) {
		this.daConfJsonStr = daConfJsonStr;
	}
	
	

}
