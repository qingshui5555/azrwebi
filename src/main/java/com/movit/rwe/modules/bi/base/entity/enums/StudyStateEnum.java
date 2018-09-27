package com.movit.rwe.modules.bi.base.entity.enums;

public enum StudyStateEnum {
	
	open(0,"打开状态"),closed(1,"关闭状态");
	
	
	int code;
	
	String desc;

	private StudyStateEnum(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	
	public static String getDesc(int code){
		for(StudyStateEnum stateEnum:StudyStateEnum.values()){
			if(code == stateEnum.getCode()){
				return stateEnum.getDesc();
			}
		}
		return null;
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
