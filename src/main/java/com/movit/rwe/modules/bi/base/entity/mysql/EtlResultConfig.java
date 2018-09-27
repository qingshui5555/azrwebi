package com.movit.rwe.modules.bi.base.entity.mysql;

import com.movit.rwe.modules.bi.base.entity.BaseEntity;

import java.io.Serializable;

public class EtlResultConfig extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -2596355488611004905L;

	private String studyId;

	private String subject;

	private String keyName;

	private String keyDepend;

	private String keyValue;

	private String description;

	public String getStudyId() {
		return studyId;
	}

	public void setStudyId(String studyId) {
		this.studyId = studyId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getKeyDepend() {
		return keyDepend;
	}

	public void setKeyDepend(String keyDepend) {
		this.keyDepend = keyDepend;
	}

	public String getKeyValue() {
		return keyValue;
	}

	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
