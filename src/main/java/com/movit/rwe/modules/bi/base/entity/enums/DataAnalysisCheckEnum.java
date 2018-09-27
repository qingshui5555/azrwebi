package com.movit.rwe.modules.bi.base.entity.enums;

public enum DataAnalysisCheckEnum {
	TTEST(1,1),
	SURVIVALANALYSIS(2,2);
	
	private int modelColumnNumber;
	private int cohortNumber;
	
	private DataAnalysisCheckEnum(int modelColumnNumber,int cohortNumber){
		this.modelColumnNumber = modelColumnNumber;
		this.cohortNumber = cohortNumber;
	}

	public int getModelColumnNumber() {
		return modelColumnNumber;
	}

	public int getCohortNumber() {
		return cohortNumber;
	}
	
}
