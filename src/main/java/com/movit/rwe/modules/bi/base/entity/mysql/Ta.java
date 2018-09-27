package com.movit.rwe.modules.bi.base.entity.mysql;

import com.movit.rwe.common.persistence.DataEntity;

/**
 * 
  * @ClassName: Ta
  * @Description: Ta实体类
  * @author wade.chen@movit-tech.com
  * @date 2016年3月16日 下午5:54:43
  *
 */
public class Ta extends DataEntity<Ta> {

	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = -2596355488611004905L;
	
	String taName;
	
	String taCode;
	
	int sort;

	public String getTaName() {
		return taName;
	}

	public void setTaName(String taName) {
		this.taName = taName;
	}

	public String getTaCode() {
		return taCode;
	}

	public void setTaCode(String taCode) {
		this.taCode = taCode;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	

}
