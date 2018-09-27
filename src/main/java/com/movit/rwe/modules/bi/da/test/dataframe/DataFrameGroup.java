package com.movit.rwe.modules.bi.da.test.dataframe;

import com.movit.rwe.modules.bi.da.enums.DataTypeEnum;

public class DataFrameGroup {

	public String column;
	public DataTypeEnum dataType;
	public Object[] valueArray;
	
	public String getColumn() {
		return column;
	}
	public void setColumn(String column) {
		this.column = column;
	}
	public DataTypeEnum getDataType() {
		return dataType;
	}
	public void setDataType(DataTypeEnum dataType) {
		this.dataType = dataType;
	}
	public Object[] getValueArray() {
		return valueArray;
	}
	public void setValueArray(Object[] valueArray) {
		this.valueArray = valueArray;
	}
	
	
}
