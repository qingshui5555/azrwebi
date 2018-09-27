package com.movit.rwe.modules.bi.base.entity.hive;

public class EffectionDict {
	private String guid;

	private String effectionName;

	private String parentEffectionGuid;

	private String conceptId;

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getEffectionName() {
		return effectionName;
	}

	public void setEffectionName(String effectionName) {
		this.effectionName = effectionName;
	}

	public String getParentEffectionGuid() {
		return parentEffectionGuid;
	}

	public void setParentEffectionGuid(String parentEffectionGuid) {
		this.parentEffectionGuid = parentEffectionGuid;
	}

	public String getConceptId() {
		return conceptId;
	}

	public void setConceptId(String conceptId) {
		this.conceptId = conceptId;
	}

}
