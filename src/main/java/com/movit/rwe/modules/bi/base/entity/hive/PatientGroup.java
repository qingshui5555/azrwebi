package com.movit.rwe.modules.bi.base.entity.hive;

/**
 * 
 * @Project : az-rwe-bi-web
 * @Title : PatientGroup.java
 * @Package com.movit.rwe.common.entity
 * @Description : 患者组
 * @author Yuri.Zheng
 * @email Yuri.Zheng@movit-tech.com
 * @date 2016年3月1日 下午1:41:35
 *
 */
public class PatientGroup {

	private String guid;

	private String studyId;

	private String taId;

	private String groupName;

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

	public String getTaId() {
		return taId;
	}

	public void setTaId(String taId) {
		this.taId = taId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

}
