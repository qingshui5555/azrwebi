package com.movit.rwe.modules.bi.dashboard.vo;

import java.util.Date;

public class MedDosingScheduleVo {

	private String treatmentId;

	private String guid;

	private String drug;

	private String target;

	private Double dosing;

	private String dosingUnit;

	private Date dosingDate;

	private String treatmentSolution;

	private String treatmentType;

	private String treatmentCurativeEffect;

	private String treatmentCycle;

	private Date treatmentEndDate;

	private String frequency;

	private String frequencyUnit;

	private String cycle;

	public String getTreatmentId() {
		return treatmentId;
	}

	public void setTreatmentId(String treatmentId) {
		this.treatmentId = treatmentId;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
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

	public String getTreatmentSolution() {
		return treatmentSolution;
	}

	public void setTreatmentSolution(String treatmentSolution) {
		this.treatmentSolution = treatmentSolution;
	}

	public String getTreatmentType() {
		return treatmentType;
	}

	public void setTreatmentType(String treatmentType) {
		this.treatmentType = treatmentType;
	}

	public String getTreatmentCurativeEffect() {
		return treatmentCurativeEffect;
	}

	public void setTreatmentCurativeEffect(String treatmentCurativeEffect) {
		this.treatmentCurativeEffect = treatmentCurativeEffect;
	}

	public String getTreatmentCycle() {
		return treatmentCycle;
	}

	public void setTreatmentCycle(String treatmentCycle) {
		this.treatmentCycle = treatmentCycle;
	}

	public Date getTreatmentEndDate() {
		return treatmentEndDate;
	}

	public void setTreatmentEndDate(Date treatmentEndDate) {
		this.treatmentEndDate = treatmentEndDate;
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

	public String getCycle() {
		return cycle;
	}

	public void setCycle(String cycle) {
		this.cycle = cycle;
	}

}
