package com.movit.rwe.modules.bi.dataanalysis.common;

/**
 * sql生成请求参数
 */
public class GenerateSqlParams {

	String modelMetaId;
	
	String [] modelColumnMetaIds;

	String [] cohortIds;
	
	public String getModelMetaId() {
		return modelMetaId;
	}

	public void setModelMetaId(String modelMetaId) {
		this.modelMetaId = modelMetaId;
	}

	public String[] getModelColumnMetaIds() {
		return modelColumnMetaIds;
	}

	public void setModelColumnMetaIds(String[] modelColumnMetaIds) {
		this.modelColumnMetaIds = modelColumnMetaIds;
	}

	public String[] getCohortIds() {
		return cohortIds;
	}

	public void setCohortIds(String[] cohortIds) {
		this.cohortIds = cohortIds;
	}
	
	
}
