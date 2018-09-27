package com.movit.rwe.modules.bi.dataanalysis.entity;

import java.util.List;
import java.util.Map;

public class CheckDataAnalysisDto {
	//列数组
	private String [] modelColumnNameArray;
	//data frame查询结果
	private List<Map> dataFrameList;
	
	public String[] getModelColumnNameArray() {
		return modelColumnNameArray;
	}
	
	public void setModelColumnNameArray(String[] modelColumnNameArray) {
		this.modelColumnNameArray = modelColumnNameArray;
	}
	
	public List<Map> getDataFrameList() {
		return dataFrameList;
	}
	
	public void setDataFrameList(List<Map> dataFrameList) {
		this.dataFrameList = dataFrameList;
	}
	
}
