package com.movit.rwe.modules.bi.base.entity.hive;

import java.io.Serializable;

public class HivePatientGroup implements Serializable {

	private static final long serialVersionUID = -1L;

	private String guid;

	private String groupType;

	private String groupName;

	private Long groupCount;

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Long getGroupCount() {
		return groupCount;
	}

	public void setGroupCount(Long groupCount) {
		this.groupCount = groupCount;
	}
}
