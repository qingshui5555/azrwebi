package com.movit.rwe.modules.bi.base.entity.hive;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class HiveEtlResult implements Serializable {

	private static final long serialVersionUID = -1L;

	private Long configId;

	private String guid;

	private String etlType;

	private String indicatorType;

	private String indicator;

	private String indicatorDescription;

	private String studyId;

	private String patientId;

	private String visitId;

	private String referenceCode;

	private String result;

	private String resultUnit;

	private String lowerRef;

	private String upperRef;

	private String evaluation;

	private Date assayDate;

	private String createBy;

	private Date createOn;

	private Set<String> unitSet;

	public Long getConfigId() {
		return configId;
	}

	public void setConfigId(Long configId) {
		this.configId = configId;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getEtlType() {
		return etlType;
	}

	public void setEtlType(String etlType) {
		this.etlType = etlType;
	}

	public String getIndicatorType() {
		return indicatorType;
	}

	public void setIndicatorType(String indicatorType) {
		this.indicatorType = indicatorType;
	}

	public String getIndicator() {
		return indicator;
	}

	public void setIndicator(String indicator) {
		this.indicator = indicator;
	}

	public String getIndicatorDescription() {
		return indicatorDescription;
	}

	public void setIndicatorDescription(String indicatorDescription) {
		this.indicatorDescription = indicatorDescription;
	}

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

	public String getReferenceCode() {
		return referenceCode;
	}

	public void setReferenceCode(String referenceCode) {
		this.referenceCode = referenceCode;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getResultUnit() {
		return resultUnit;
	}

	public void setResultUnit(String resultUnit) {
		this.resultUnit = resultUnit;
	}

	public String getLowerRef() {
		return lowerRef;
	}

	public void setLowerRef(String lowerRef) {
		this.lowerRef = lowerRef;
	}

	public String getUpperRef() {
		return upperRef;
	}

	public void setUpperRef(String upperRef) {
		this.upperRef = upperRef;
	}

	public String getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(String evaluation) {
		this.evaluation = evaluation;
	}

	public Date getAssayDate() {
		return assayDate;
	}

	public void setAssayDate(Date assayDate) {
		this.assayDate = assayDate;
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

	public Set<String> getUnitSet() {
		return unitSet;
	}

	public void setUnitSet(Set<String> unitSet) {
		this.unitSet = unitSet;
	}
}
