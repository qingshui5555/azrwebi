package com.movit.rwe.modules.bi.base.entity.mysql;

import com.movit.rwe.common.persistence.DataEntity;

/**
 * 
 * @Title: Patients.java
 * @Package com.movit.rwe.modules.bi.base.entity.mysql
 * @Description: 患者类
 * @author Yuri.Zheng
 * @date 2016年5月30日 下午2:46:20
 */
public class Patients extends DataEntity<Patients> {

	private static final long serialVersionUID = -6388898835927033561L;

	/**
	 * 病历号
	 */
	private String eCode;

	public String geteCode() {
		return eCode;
	}

	public void seteCode(String eCode) {
		this.eCode = eCode;
	}

}
