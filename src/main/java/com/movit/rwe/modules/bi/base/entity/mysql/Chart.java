package com.movit.rwe.modules.bi.base.entity.mysql;

import com.movit.rwe.common.persistence.DataEntity;

/**
 * 
 * @Project : az-rwe-bi-web
 * @Title : Study.java
 * @Package com.movit.rwe.modules.bi.base.entity.mysql
 * @Description : 图表对象
 * @author Wade.chen
 * @email Wade.chen@movit-tech.com
 * @date 2016年3月1日 下午1:35:45
 *
 */
public class Chart extends DataEntity<Chart>{

	private static final long serialVersionUID = -6388898835927033560L;
	
	/**
	 * chart名称
	 */
	String name;
	
	/**
	 * 标题
	 */
	String title;
	
	/**
	 * 图表html内容
	 */
	String content;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
	
	

}
