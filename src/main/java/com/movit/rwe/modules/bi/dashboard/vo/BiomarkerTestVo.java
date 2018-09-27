package com.movit.rwe.modules.bi.dashboard.vo;

public class BiomarkerTestVo {

	private Integer amount; //biomarker所对应的人数
	private String biomarker;
	private String evaluation;
	
	public String getEvaluation() {
		return evaluation;
	}
	public void setEvaluation(String evaluation) {
		this.evaluation = evaluation;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public String getBiomarker() {
		return biomarker;
	}
	public void setBiomarker(String biomarker) {
		this.biomarker = biomarker;
	}
	
}
