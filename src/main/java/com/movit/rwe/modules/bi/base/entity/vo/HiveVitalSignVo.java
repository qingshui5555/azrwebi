package com.movit.rwe.modules.bi.base.entity.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author henry 2018/07/06
 */
public class HiveVitalSignVo implements Serializable {

	private static final long serialVersionUID = -1L;

	private String studyId;

	private String patientId;

	private BigDecimal bmiValue;

	private BigDecimal sbpValue;

	private String sbpUnit;

	private BigDecimal dbpValue;

	private String dbpUnit;

	private BigDecimal waistValue;

	private String waistUnit;

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

	public BigDecimal getBmiValue() {
		return bmiValue;
	}

	public void setBmiValue(BigDecimal bmiValue) {
		this.bmiValue = bmiValue;
	}

	public BigDecimal getSbpValue() {
		return sbpValue;
	}

	public void setSbpValue(BigDecimal sbpValue) {
		this.sbpValue = sbpValue;
	}

	public String getSbpUnit() {
		return sbpUnit;
	}

	public void setSbpUnit(String sbpUnit) {
		this.sbpUnit = sbpUnit;
	}

	public BigDecimal getDbpValue() {
		return dbpValue;
	}

	public void setDbpValue(BigDecimal dbpValue) {
		this.dbpValue = dbpValue;
	}

	public String getDbpUnit() {
		return dbpUnit;
	}

	public void setDbpUnit(String dbpUnit) {
		this.dbpUnit = dbpUnit;
	}

	public BigDecimal getWaistValue() {
		return waistValue;
	}

	public void setWaistValue(BigDecimal waistValue) {
		this.waistValue = waistValue;
	}

	public String getWaistUnit() {
		return waistUnit;
	}

	public void setWaistUnit(String waistUnit) {
		this.waistUnit = waistUnit;
	}
}
