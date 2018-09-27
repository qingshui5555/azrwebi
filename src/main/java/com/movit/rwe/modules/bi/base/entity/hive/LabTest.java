package com.movit.rwe.modules.bi.base.entity.hive;

import java.sql.Timestamp;
import java.util.Set;

public class LabTest {

	private String guid;
	private String studyId;
	private String indicator;
	private String resultUnit;
	private String result;
	private Double lowerRef;
	private Double upRef;
	private String patientId;
	private Timestamp assayDate;
	private Set<String> unitSet;
	private String referenceCode;
	
	public String getReferenceCode() {
		return referenceCode;
	}
	public void setReferenceCode(String referenceCode) {
		this.referenceCode = referenceCode;
	}
	public String getPatientId() {
		return patientId;
	}
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	public Timestamp getAssayDate() {
		return assayDate;
	}
	public void setAssayDate(Timestamp assayDate) {
		this.assayDate = assayDate;
	}
	public Set<String> getUnitSet() {
		return unitSet;
	}
	public void setUnitSet(Set<String> unitSet) {
		this.unitSet = unitSet;
	}
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
	public String getIndicator() {
		return indicator;
	}
	public void setIndicator(String indicator) {
		this.indicator = indicator;
	}
	public String getResultUnit() {
		return resultUnit;
	}
	public void setResultUnit(String resultUnit) {
		this.resultUnit = resultUnit;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public Double getLowerRef() {
		return lowerRef;
	}
	public void setLowerRef(Double lowerRef) {
		this.lowerRef = lowerRef;
	}
	public Double getUpRef() {
		return upRef;
	}
	public void setUpRef(Double upRef) {
		this.upRef = upRef;
	}
	
}
