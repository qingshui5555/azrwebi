package com.movit.rwe.modules.bi.dashboard.vo;

public class SideEffectVo {

	private String effectionValue;

	private String effectionName;

	private String parentEffectionName;

	private int count;

	private String drug;

	public String getEffectionValue() {
		return effectionValue;
	}

	public void setEffectionValue(String effectionValue) {
		this.effectionValue = effectionValue;
	}

	public String getEffectionName() {
		return effectionName;
	}

	public void setEffectionName(String effectionName) {
		this.effectionName = effectionName;
	}

	public String getParentEffectionName() {
		return parentEffectionName;
	}

	public void setParentEffectionName(String parentEffectionName) {
		this.parentEffectionName = parentEffectionName;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getDrug() {
		return drug;
	}

	public void setDrug(String drug) {
		this.drug = drug;
	}

}
