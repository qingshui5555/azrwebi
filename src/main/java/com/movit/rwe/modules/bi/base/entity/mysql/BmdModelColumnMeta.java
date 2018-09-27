package com.movit.rwe.modules.bi.base.entity.mysql;

import com.movit.rwe.common.persistence.DataEntity;

/**
 * 
 * @Project : az-rwe-bi-web
 * @Title : BmdModelColumnMeta.java
 * @Package com.movit.rwe.modules.bi.base.entity.mysql
 * @Description : 数据分析模型列对象
 *
 */
public class BmdModelColumnMeta extends DataEntity<BmdModelColumnMeta>{

	private static final long serialVersionUID = -6388898835927033562L;
	
	/**
	 * 模型ID
	 */
	private String modelMetaId;
	
	/**
	 * 模型列名
	 */
	private String modelColumnName;
	
	/**
	 * 模型列单位
	 */
	private String modelColumnUnit;
	
	/**
	 * 模型列类型
	 */
	private String modelColumnType;
	
	/**
	 * 模型列页面展示名称
	 */
	private String modelColumnNameLab;

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

	public String getModelColumnName() {
		return modelColumnName;
	}

	public void setModelColumnName(String modelColumnName) {
		this.modelColumnName = modelColumnName;
	}

	public String getModelColumnUnit() {
		return modelColumnUnit;
	}

	public void setModelColumnUnit(String modelColumnUnit) {
		this.modelColumnUnit = modelColumnUnit;
	}

	public String getModelColumnType() {
		return modelColumnType;
	}

	public void setModelColumnType(String modelColumnType) {
		this.modelColumnType = modelColumnType;
	}

	public String getModelColumnNameLab() {
		return modelColumnNameLab;
	}

	public void setModelColumnNameLab(String modelColumnNameLab) {
		this.modelColumnNameLab = modelColumnNameLab;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	
}
