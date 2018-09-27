package com.movit.rwe.modules.bi.base.entity.hive;

import java.io.Serializable;
import java.util.Date;

public class HiveCohort implements Serializable {

	private static final long serialVersionUID = -1L;

	private String guid;

	private String studyId;

	private String cohortName;

	private String createBy;

	private Date createOn;

	private Long patientCount;

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

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

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateOn() {
		return createOn;
	}

	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}

	public Long getPatientCount() {
		return patientCount;
	}

	public void setPatientCount(Long patientCount) {
		this.patientCount = patientCount;
	}

}
