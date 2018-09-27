package com.movit.rwe.modules.bi.base.entity.mysql;

import com.movit.rwe.common.persistence.DataEntity;

/**
 * 
 * @Project : az-rwe-bi-web
 * @Title : Cohort.java
 * @Package com.movit.rwe.modules.bi.base.entity.mysql
 * @Description : 组对象
 *
 */
public class Cohort extends DataEntity<Cohort>{

	private static final long serialVersionUID = -6388898835927033561L;
	
	/**
	 * 研究ID
	 */
	private String studyId;
	/**
	 * 群组名称
	 */
	private String cohortName;
	/**
	 * 群组编码
	 */
	private String cohortEcode;
	/**
	 * 病人数量
	 */
	private String patientCount;
	
	public String getStudyId() {
		return studyId;
	}
	public void setStudyId(String studyId) {
		this.studyId = studyId;
	}
	public String getCohortName() {
		return cohortName;
	}
	public void setCohortName(String cohortName) {
		this.cohortName = cohortName;
	}
	public String getCohortEcode() {
		return cohortEcode;
	}
	public void setCohortEcode(String cohortEcode) {
		this.cohortEcode = cohortEcode;
	}
	public String getPatientCount() {
		return patientCount;
	}
	public void setPatientCount(String patientCount) {
		this.patientCount = patientCount;
	}
	
}
