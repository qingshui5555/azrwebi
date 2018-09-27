package com.movit.rwe.modules.bi.dashboard.vo;

public class BiomarkerBoxPlotVo {
	String biomarker;

	String resultType;

	String groupName;

	Double score;

	public String getBiomarker() {
		return biomarker;
	}

	public void setBiomarker(String biomarker) {
		this.biomarker = biomarker;
	}

	public String getResultType() {
		return resultType;
	}

	public void setResultType(String resultType) {
		this.resultType = resultType;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

}
