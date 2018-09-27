package com.movit.rwe.modules.bi.base.entity.hive;

/**
 * 
 * @Project : az-rwe-bi-web
 * @Title : Study.java
 * @Package com.movit.rwe.common.entity
 * @Description : 研究类型
 * @author Yuri.Zheng
 * @email Yuri.Zheng@movit-tech.com
 * @date 2016年3月1日 下午1:35:45
 *
 */
public class Study {

	private String guid;

	private String taId;

	private String name;

	private String code;

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getTaId() {
		return taId;
	}

	public void setTaId(String taId) {
		this.taId = taId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
