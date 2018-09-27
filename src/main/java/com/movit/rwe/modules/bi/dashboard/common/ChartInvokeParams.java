package com.movit.rwe.modules.bi.dashboard.common;

/**
 * chart请求参数对象
 * @author wade
 *
 */
public class ChartInvokeParams {

	String token;
	
	String studyId;
	
	String patientId;
	
	String[] cohorts;
	
	String chartId;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
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

	public String[] getCohorts() {
		return cohorts;
	}

	public void setCohorts(String[] cohorts) {
		this.cohorts = cohorts;
	}

	public String getChartId() {
		return chartId;
	}

	public void setChartId(String chartId) {
		this.chartId = chartId;
	}


	
}
