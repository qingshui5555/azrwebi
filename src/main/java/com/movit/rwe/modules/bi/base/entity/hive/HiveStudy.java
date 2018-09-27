package com.movit.rwe.modules.bi.base.entity.hive;

import java.io.Serializable;
import java.util.Date;

public class HiveStudy implements Serializable {

	private static final long serialVersionUID = -1L;

	private String guid;

	private String studyName;

	private String studyCode;

	private String createBy;

	private Date createOn;

	private Long patientCount;

	private String taId;

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getStudyName() {
		return studyName;
	}

	public void setStudyName(String studyName) {
		this.studyName = studyName;
	}

	public String getStudyCode() {
		return studyCode;
	}

	public void setStudyCode(String studyCode) {
		this.studyCode = studyCode;
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

	public String getTaId() {
		return taId;
	}

	public void setTaId(String taId) {
		this.taId = taId;
	}
}
