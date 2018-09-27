package com.movit.rwe.modules.bi.da.test.set;

import com.movit.rwe.modules.bi.base.entity.mysql.BmdModelColumnMeta;
import com.movit.rwe.modules.bi.da.enums.DataTypeEnum;

public class DataModelParamChosen {
	
	BmdModelColumnMeta column;
	
	public DataModelParamChosen(BmdModelColumnMeta column) {
		super();
		this.column = column;
	}

	public String getModelColumnName(){
		return column.getModelColumnName();
	}
	
	public String getModelColumnNameLab(){
		return column.getModelColumnNameLab();
	}
	
	public DataTypeEnum getDataType(){
		return DataTypeEnum.loadByCode(column.getModelColumnType());
	}

	public BmdModelColumnMeta getColumn() {
		return column;
	}

	public void setColumn(BmdModelColumnMeta column) {
		this.column = column;
	}
	
	
}
