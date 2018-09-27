package com.movit.rwe.modules.bi.base.entity.mysql;

import com.movit.rwe.modules.bi.base.entity.BaseEntity;

import java.io.Serializable;

public class EtlTaConfig extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -2596355488611004905L;

	private String taAbbrName;

	private String taFullName;

	private String taCode;

	private Integer taSort;

	public String getTaAbbrName() {
		return taAbbrName;
	}

	public void setTaAbbrName(String taAbbrName) {
		this.taAbbrName = taAbbrName;
	}

	public String getTaFullName() {
		return taFullName;
	}

	public void setTaFullName(String taFullName) {
		this.taFullName = taFullName;
	}

	public String getTaCode() {
		return taCode;
	}

	public void setTaCode(String taCode) {
		this.taCode = taCode;
	}

	public Integer getTaSort() {
		return taSort;
	}

	public void setTaSort(Integer taSort) {
		this.taSort = taSort;
	}

}
