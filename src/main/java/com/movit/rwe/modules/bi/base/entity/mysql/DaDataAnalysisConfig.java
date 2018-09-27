package com.movit.rwe.modules.bi.base.entity.mysql;

import com.movit.rwe.common.persistence.DataEntity;

/**
 * 
 * @Project : az-rwe-bi-web
 * @Title : BmdModelMeta.java
 * @Package com.movit.rwe.modules.bi.base.entity.mysql
 * @Description : 数据分析模型对象
 *
 */
public class DaDataAnalysisConfig extends DataEntity<DaDataAnalysisConfig>{

	private static final long serialVersionUID = -6388898835927033563L;
	
	/**
	 * 模型ID
	 */
	private String modelMetaId;
	
	/**
	 * 所有模型列ID
	 */
	private String modelColumnMetaIds;
	
	/**
	 * 分析名称
	 */
	private String analysisName;
	
	/**
	 * 分析类型
	 */
	private String analysisType;
	
	/**
	 * 分析执行SQL
	 */
	private String analysisQuerySql;
	
	/**
	 * 分析配置情况
	 */
	private String analysisConfig;
	
	/**
	 * 结果
	 */
	private String resultContent;
	
	/**
	 * 排序
	 */
	private Integer sort;

	public String getModelMetaId() {
		return modelMetaId;
	}

	public void setModelMetaId(String modelMetaId) {
		this.modelMetaId = modelMetaId;
	}

	public String getModelColumnMetaIds() {
		return modelColumnMetaIds;
	}

	public void setModelColumnMetaIds(String modelColumnMetaIds) {
		this.modelColumnMetaIds = modelColumnMetaIds;
	}

	public String getAnalysisName() {
		return analysisName;
	}

	public void setAnalysisName(String analysisName) {
		this.analysisName = analysisName;
	}

	public String getAnalysisType() {
		return analysisType;
	}

	public void setAnalysisType(String analysisType) {
		this.analysisType = analysisType;
	}

	public String getAnalysisQuerySql() {
		return analysisQuerySql;
	}

	public void setAnalysisQuerySql(String analysisQuerySql) {
		this.analysisQuerySql = analysisQuerySql;
	}

	public String getAnalysisConfig() {
		return analysisConfig;
	}

	public void setAnalysisConfig(String analysisConfig) {
		this.analysisConfig = analysisConfig;
	}

	public String getResultContent() {
		return resultContent;
	}

	public void setResultContent(String resultContent) {
		this.resultContent = resultContent;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	
}
