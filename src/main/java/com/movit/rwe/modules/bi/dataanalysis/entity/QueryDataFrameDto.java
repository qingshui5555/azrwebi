package com.movit.rwe.modules.bi.dataanalysis.entity;

import com.movit.rwe.common.persistence.DataEntity;

/**
 * sql生成请求参数
 */
public class QueryDataFrameDto extends DataEntity<QueryDataFrameDto>{
	String taId;
	
	String studyId;
	
	String modelMetaId;
	
	String [] modelColumnMetaIds;

	String [] cohortIds;
	
	String [] cohortNames;
	
	String [] modelColumnNames;
	
	public String getTaId() {
		return taId;
	}

	public void setTaId(String taId) {
		this.taId = taId;
	}

	public String getStudyId() {
		return studyId;
	}

	public void setStudyId(String studyId) {
		this.studyId = studyId;
	}

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

	public String[] getCohortNames() {
		return cohortNames;
	}

	public void setCohortNames(String[] cohortNames) {
		this.cohortNames = cohortNames;
	}

	public String[] getModelColumnNames() {
		return modelColumnNames;
	}

	public void setModelColumnNames(String[] modelColumnNames) {
		this.modelColumnNames = modelColumnNames;
	}
	
	
}
