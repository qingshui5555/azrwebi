package com.movit.rwe.modules.bi.base.entity.hive;

import java.sql.Timestamp;

public class PatientEvent implements Comparable<PatientEvent>{
	
	private String guid;
	
	private String patientId;
	
	private String studyId;
	
	private String referenceCode;

	private String eventName;

	private String eventType;

	private Timestamp eventDatetime;

	private String assessmentResponse;
	
	private Integer survivalDay;
	
	public Integer getSurvivalDay() {
		return survivalDay;
	}

	public void setSurvivalDay(Integer survivalDay) {
		this.survivalDay = survivalDay;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public String getStudyId() {
		return studyId;
	}

	public void setStudyId(String studyId) {
		this.studyId = studyId;
	}

	public String getReferenceCode() {
		return referenceCode;
	}

	public void setReferenceCode(String referenceCode) {
		this.referenceCode = referenceCode;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public Timestamp getEventDatetime() {
		return eventDatetime;
	}

	public void setEventDatetime(Timestamp eventDatetime) {
		this.eventDatetime = eventDatetime;
	}

	public String getAssessmentResponse() {
		return assessmentResponse;
	}

	public void setAssessmentResponse(String assessmentResponse) {
		this.assessmentResponse = assessmentResponse;
	}

	@Override
	public int compareTo(PatientEvent o) {
		// TODO Auto-generated method stub
		if(this.survivalDay>o.getSurvivalDay()) {
			return 1;
		}else {
			return -1;
		}
		
	}

}
