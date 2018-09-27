package com.movit.rwe.modules.bi.sys.vo;

import java.util.Date;

public class StudyVo {

	private String id;

	private String taName;

	private String studyCode;

	private String studyName;

	private Integer tabNum;

	private Date lastModifyDate;

	private String dashboardId;

	private String taId;

	private String remarks;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTaName() {
		return taName;
	}

	public void setTaName(String taName) {
		this.taName = taName;
	}

	public String getStudyCode() {
		return studyCode;
	}

	public void setStudyCode(String studyCode) {
		this.studyCode = studyCode;
	}

	public String getStudyName() {
		return studyName;
	}

	public void setStudyName(String studyName) {
		this.studyName = studyName;
	}

	public Integer getTabNum() {
		return tabNum;
	}

	public void setTabNum(Integer tabNum) {
		this.tabNum = tabNum;
	}

	public Date getLastModifyDate() {
		return lastModifyDate;
	}

	public void setLastModifyDate(Date lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}

	public String getDashboardId() {
		return dashboardId;
	}

	public void setDashboardId(String dashboardId) {
		this.dashboardId = dashboardId;
	}

	public String getTaId() {
		return taId;
	}

	public void setTaId(String taId) {
		this.taId = taId;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
