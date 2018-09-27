package com.movit.rwe.modules.bi.base.entity.hive;

import java.io.Serializable;
import java.util.Date;

public class Treatment implements Serializable {

	private static final long serialVersionUID = -1L;

	private String studyId;

	private String patientId;

	private String visitId;

	private Date visitOn;

	private String groupId;

	private Integer line;

	private String therapyType;

	private String drugName;

	private Double dailyDose;

	private Date startDate;

	private Date endDate;

	private Boolean onGoing = false;

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

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public Integer getLine() {
		return line;
	}

	public void setLine(Integer line) {
		this.line = line;
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

	public Double getDailyDose() {
		return dailyDose;
	}

	public void setDailyDose(Double dailyDose) {
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

	public Boolean getOnGoing() {
		return onGoing;
	}

	public void setOnGoing(Boolean onGoing) {
		this.onGoing = onGoing;
	}

}