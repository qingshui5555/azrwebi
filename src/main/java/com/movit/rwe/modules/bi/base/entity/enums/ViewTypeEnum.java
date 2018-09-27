package com.movit.rwe.modules.bi.base.entity.enums;

public enum ViewTypeEnum {
	Default_View(0,"Default View"),
	Custom_View(1,"Custom View"),
	Da_View(2,"data analysis View");
	
	int typeId;
	String typeName;
	
	private ViewTypeEnum(int typeId, String typeName) {
		this.typeId = typeId;
		this.typeName = typeName;
	}
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	
	

}
