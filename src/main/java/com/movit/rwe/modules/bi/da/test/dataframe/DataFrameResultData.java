package com.movit.rwe.modules.bi.da.test.dataframe;

import com.movit.rwe.modules.bi.da.enums.DataTypeEnum;

public class DataFrameResultData {
	
	Object dataValue;
	
	DataTypeEnum dataType;
	
	public String getShowLable(){
		return dataValue.toString();
	}

	public Object getDataValue() {
		return dataValue;
	}

	public void setDataValue(Object dataValue) {
		this.dataValue = dataValue;
	}

	public DataTypeEnum getDataType() {
		return dataType;
	}

	public void setDataType(DataTypeEnum dataType) {
		this.dataType = dataType;
	}
	
	

}
