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
public class BmdModelMeta extends DataEntity<BmdModelMeta>{

	private static final long serialVersionUID = -6388898835927033561L;
	
	/**
	 * 模型名
	 */
	private String modelName;
	
	/**
	 * 查询SDL
	 */
	private String querySql;
	
	/**
	 * 排序
	 */
	private Integer sort;

	private String studyId;
	
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getQuerySql() {
		return querySql;
	}
	public void setQuerySql(String querySql) {
		this.querySql = querySql;
	}

	public String getStudyId() {
		return studyId;
	}

	public void setStudyId(String studyId) {
		this.studyId = studyId;
	}
}
