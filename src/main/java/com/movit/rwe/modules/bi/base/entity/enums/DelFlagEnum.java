package com.movit.rwe.modules.bi.base.entity.enums;

public enum DelFlagEnum {
	
	normal(0,"正常状态"),deled(1,"已经删除");
	
	
	int code;
	
	String desc;

	private DelFlagEnum(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	

}
