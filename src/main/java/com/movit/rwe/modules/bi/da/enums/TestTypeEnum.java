package com.movit.rwe.modules.bi.da.enums;

public enum TestTypeEnum {
	
	NOMAL(1,"正态分布方法"),
	ABNOMAL(2,"偏态分布方法");
	
	private TestTypeEnum(int code ,String desc){
		this.code = code;
		this.desc = desc;
	}
	
	int code;
	String desc;
	
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
