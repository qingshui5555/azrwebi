package com.movit.rwe.modules.bi.base.entity.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class HivePayerCostVo implements Serializable {

	private static final long serialVersionUID = -1L;

	private String studyId;

	private String type;

	private BigDecimal amount;

	private String unit;

	public String getStudyId() {
		return studyId;
	}

	public void setStudyId(String studyId) {
		this.studyId = studyId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
}
