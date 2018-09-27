package com.movit.rwe.modules.bi.base.entity.mysql;

import java.util.Date;

import com.movit.rwe.common.persistence.DataEntity;

/**
 * 
  * @ClassName: Study
  * @Description: study实体类
  * @author wade.chen@movit-tech.com
  * @date 2016年3月16日 下午5:56:20
  *
 */
public class Study extends DataEntity<Study> {

	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = 9185315156719085075L;
	
	String studyName;
	
	String studyCode;
	
	Date startDate;
	
	Date endDate;
	
	String taId;
	
	int sort;
	
	int state;
	
	/**
	 * 该study下的患者
	 */
	int patientCount;

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStudyName() {
		return studyName;
	}

	public void setStudyName(String studyName) {
		this.studyName = studyName;
	}

	public String getStudyCode() {
		return studyCode;
	}

	public void setStudyCode(String studyCode) {
		this.studyCode = studyCode;
	}

	public String getTaId() {
		return taId;
	}

	public void setTaId(String taId) {
		this.taId = taId;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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

	public int getPatientCount() {
		return patientCount;
	}

	public void setPatientCount(int patientCount) {
		this.patientCount = patientCount;
	}
	
	

}
