package com.movit.rwe.modules.bi.da.test.dataframe;

import java.util.ArrayList;
import java.util.List;

public class DataFrameHandleResult {

	String [] modelColumnNameArray;
	
	List <String[]>stringDataList = new ArrayList<String[]>();
	
	List <double[]>doubleDataList = new ArrayList<double[]>();;
	
	public String[] getModelColumnNameArray() {
		return modelColumnNameArray;
	}

	public void setModelColumnNameArray(String[] modelColumnNameArray) {
		this.modelColumnNameArray = modelColumnNameArray;
	}

	public List<String[]> getStringDataList() {
		return stringDataList;
	}

	public void setStringDataList(List<String[]> stringDataList) {
		this.stringDataList = stringDataList;
	}

	public List<double[]> getDoubleDataList() {
		return doubleDataList;
	}

	public void setDoubleDataList(List<double[]> doubleDataList) {
		this.doubleDataList = doubleDataList;
	}
	
}
