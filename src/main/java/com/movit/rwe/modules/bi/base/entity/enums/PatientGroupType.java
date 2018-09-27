package com.movit.rwe.modules.bi.base.entity.enums;

public enum PatientGroupType {
	
	STYDY_GROUP(0,"study下的用户分组"),COHORT(1,"用户自定义cohort分组");
	
	int code;
	
	String desc;
	
	private PatientGroupType(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	
	public static String getDesc(int code){
		for(PatientGroupType patientGroupType:PatientGroupType.values()){
			if(code == patientGroupType.getCode()){
				return patientGroupType.getDesc();
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
	
	public static PatientGroupType loadByCode(int code){
		for(PatientGroupType patientGroupType:PatientGroupType.values()){
			if(code == patientGroupType.getCode()){
				return patientGroupType;
			}
		}
		return null;
	}
	
	
}
