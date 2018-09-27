package com.movit.rwe.modules.bi.base.entity.hive;

import java.util.Date;

/**
 * 
 * @Project : az-rwe-bi-web
 * @Title : MedDosingSchedule.java
 * @Package com.movit.rwe.modules.treatment.entity
 * @Description : 用药计划表
 * @author Yuri.Zheng
 * @email Yuri.Zheng@movit-tech.com
 * @date 2016年3月1日 下午2:07:33
 *
 */
public class MedDosingSchedule {

	private String guid;

	private String taGuid;

	private String patientGuid;

	private String studyGuid;

	private String treatmentId;

	private String route;

	private String frequency;

	private String frequencyUnit;

	private Boolean isCombination;

	private String drug;

	private String target;

	private Double dosing;

	private String dosingUnit;

	private Date dosingDate;

	private Date startDate;

	private Date endDate;

	private String comment;

	private String actionTaken;

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getTaGuid() {
		return taGuid;
	}

	public void setTaGuid(String taGuid) {
		this.taGuid = taGuid;
	}

	public String getPatientGuid() {
		return patientGuid;
	}

	public void setPatientGuid(String patientGuid) {
		this.patientGuid = patientGuid;
	}

	public String getStudyGuid() {
		return studyGuid;
	}

	public void setStudyGuid(String studyGuid) {
		this.studyGuid = studyGuid;
	}

	public String getTreatmentId() {
		return treatmentId;
	}

	public void setTreatmentId(String treatmentId) {
		this.treatmentId = treatmentId;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getFrequencyUnit() {
		return frequencyUnit;
	}

	public void setFrequencyUnit(String frequencyUnit) {
		this.frequencyUnit = frequencyUnit;
	}

	public Boolean getIsCombination() {
		return isCombination;
	}

	public void setIsCombination(Boolean isCombination) {
		this.isCombination = isCombination;
	}

	public String getDrug() {
		return drug;
	}

	public void setDrug(String drug) {
		this.drug = drug;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public Double getDosing() {
		return dosing;
	}

	public void setDosing(Double dosing) {
		this.dosing = dosing;
	}

	public String getDosingUnit() {
		return dosingUnit;
	}

	public void setDosingUnit(String dosingUnit) {
		this.dosingUnit = dosingUnit;
	}

	public Date getDosingDate() {
		return dosingDate;
	}

	public void setDosingDate(Date dosingDate) {
		this.dosingDate = dosingDate;
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getActionTaken() {
		return actionTaken;
	}

	public void setActionTaken(String actionTaken) {
		this.actionTaken = actionTaken;
	}

}
