package com.movit.rwe.modules.bi.dashboard.vo;

public class ConfigVo {

	private String id;

	private Integer order;

	private String indicator;

	private String resultUnit;

	private Double lowerRef;

	private Double upperRef;
	
	
	public ConfigVo() {
		super();
	}

	public ConfigVo(String id, String resultUnit, Double lowerRef, Double upperRef) {
		super();
		this.id = id;
		this.resultUnit = resultUnit;
		this.lowerRef = lowerRef;
		this.upperRef = upperRef;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getIndicator() {
		return indicator;
	}

	public void setIndicator(String indicator) {
		this.indicator = indicator;
	}

	public String getResultUnit() {
		return resultUnit;
	}

	public void setResultUnit(String resultUnit) {
		this.resultUnit = resultUnit;
	}

	public Double getLowerRef() {
		return lowerRef;
	}

	public void setLowerRef(Double lowerRef) {
		this.lowerRef = lowerRef;
	}

	public Double getUpperRef() {
		return upperRef;
	}

	public void setUpperRef(Double upperRef) {
		this.upperRef = upperRef;
	}
	
}
