package com.movit.rwe.modules.bi.base.entity.vo;

import java.io.Serializable;
import java.util.Date;

public class HiveTreatmentCountVo implements Serializable {

	private static final long serialVersionUID = -1L;

	private String lineStr;

	private String therapyType;

	private String drugName;

	private String patientIdStr;

	private Integer patientCount;

	private Integer timesDay;

	public String getTherapyType() {
		return therapyType;
	}

	public void setTherapyType(String therapyType) {
		this.therapyType = therapyType;
	}

	public String getLineStr() {
		return lineStr;
	}

	public void setLineStr(String lineStr) {
		this.lineStr = lineStr;
	}

	public String getDrugName() {
		return drugName;
	}

	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}

	public String getPatientIdStr() {
		return patientIdStr;
	}

	public void setPatientIdStr(String patientIdStr) {
		this.patientIdStr = patientIdStr;
	}

	public Integer getPatientCount() {
		return patientCount;
	}

	public void setPatientCount(Integer patientCount) {
		this.patientCount = patientCount;
	}

	public Integer getTimesDay() {
		return timesDay;
	}

	public void setTimesDay(Integer timesDay) {
		this.timesDay = timesDay;
	}
}