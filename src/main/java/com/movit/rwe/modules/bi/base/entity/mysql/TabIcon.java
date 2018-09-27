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
public class TabIcon extends DataEntity<TabIcon>{

	private static final long serialVersionUID = -6388898835927033561L;
	
	private String name;
	
	private String icon;
	
	private Integer sort;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	
}
