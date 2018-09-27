package com.movit.rwe.modules.bi.base.entity.hive;

public class SideEffect {
	private String guid;

	private String effectionName;

	private String effectionGuid;

	private String effectionValue;

	private String parentEffectionGuid;

	private String parentEffectionName;

	private String patientId;

	private String treatmentGuid;

	private String description;

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getEffectionName() {
		return effectionName;
	}

	public void setEffectionName(String effectionName) {
		this.effectionName = effectionName;
	}

	public String getEffectionGuid() {
		return effectionGuid;
	}

	public void setEffectionGuid(String effectionGuid) {
		this.effectionGuid = effectionGuid;
	}

	public String getEffectionValue() {
		return effectionValue;
	}

	public void setEffectionValue(String effectionValue) {
		this.effectionValue = effectionValue;
	}

	public String getParentEffectionGuid() {
		return parentEffectionGuid;
	}

	public void setParentEffectionGuid(String parentEffectionGuid) {
		this.parentEffectionGuid = parentEffectionGuid;
	}

	public String getParentEffectionName() {
		return parentEffectionName;
	}

	public void setParentEffectionName(String parentEffectionName) {
		this.parentEffectionName = parentEffectionName;
	}

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public String getTreatmentGuid() {
		return treatmentGuid;
	}

	public void setTreatmentGuid(String treatmentGuid) {
		this.treatmentGuid = treatmentGuid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
