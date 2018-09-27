package com.movit.rwe.modules.bi.base.entity.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author henry 2018/07/06
 */
public class HiveBiomarkerTestConfigVo implements Serializable {

	private static final long serialVersionUID = -1L;

	private String measure;

	private String biomarker;

	private String site;

	private String exon;

	private String changeType;

	private String detectionTime;

	private String detectionResult;

	public String getMeasure() {
		return measure;
	}

	public void setMeasure(String measure) {
		this.measure = measure;
	}

	public String getBiomarker() {
		return biomarker;
	}

	public void setBiomarker(String biomarker) {
		this.biomarker = biomarker;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getExon() {
		return exon;
	}

	public void setExon(String exon) {
		this.exon = exon;
	}

	public String getChangeType() {
		return changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

	public String getDetectionTime() {
		return detectionTime;
	}

	public void setDetectionTime(String detectionTime) {
		this.detectionTime = detectionTime;
	}

	public String getDetectionResult() {
		return detectionResult;
	}

	public void setDetectionResult(String detectionResult) {
		this.detectionResult = detectionResult;
	}
}
