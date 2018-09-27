package com.movit.rwe.modules.bi.base.entity.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class HiveTreatmentVo implements Serializable {

	private static final long serialVersionUID = -1L;

	private String studyId;

	private String patientId;

	private String visitId;

	private Date visitOn;

	private String lineStr;

	private String therapyType;

	private String drugName;

	private BigDecimal dailyDose;

	private Date startDate;

	private Date endDate;

	private String cycle;

	private String frequency;

	public String getStudyId() {
		return studyId;
	}

	public void setStudyId(String studyId) {
		this.studyId = studyId;
	}

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public String getVisitId() {
		return visitId;
	}

	public void setVisitId(String visitId) {
		this.visitId = visitId;
	}

	public Date getVisitOn() {
		return visitOn;
	}

	public void setVisitOn(Date visitOn) {
		this.visitOn = visitOn;
	}

	public String getLineStr() {
		return lineStr;
	}

	public void setLineStr(String lineStr) {
		this.lineStr = lineStr;
	}

	public String getTherapyType() {
		return therapyType;
	}

	public void setTherapyType(String therapyType) {
		this.therapyType = therapyType;
	}

	public String getDrugName() {
		return drugName;
	}

	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}

	public BigDecimal getDailyDose() {
		return dailyDose;
	}

	public void setDailyDose(BigDecimal dailyDose) {
		this.dailyDose = dailyDose;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getCycle() {
		return cycle;
	}

	public void setCycle(String cycle) {
		this.cycle = cycle;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
}