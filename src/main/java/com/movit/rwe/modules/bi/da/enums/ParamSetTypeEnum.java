package com.movit.rwe.modules.bi.da.enums;

import org.apache.commons.lang.StringUtils;

public enum ParamSetTypeEnum {
	PARAMSET_DEFAULT("默认设置","/bi/da/paramset/default"),
	PARAMSET_SURVIVAL("Survival 的属性设置界面","/bi/da/paramset/survival"),
	PARAMSET_ANOVA("ANOVA 的属性设置界面","/bi/da/paramset/anova"),
	PARAMSET_LINEARREGRESSION("Linear regression 的属性设置界面","/bi/da/paramset/linearRegression"),
	PARAMSET_LOGISTICREGRESSION("Logistic Regression 的属性设置界面","/bi/da/paramset/logisticRegression"),
	PARAMSET_KSVM("Ksvm 的属性设置界面","/bi/da/paramset/ksvm")
	;
	
	
	private ParamSetTypeEnum(String typeName, String setCtlUrl) {
		this.typeName = typeName;
		this.setCtlUrl = setCtlUrl;
	}

	String typeName;
	
	String setCtlUrl;

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getSetCtlUrl() {
		return setCtlUrl;
	}

	public void setSetCtlUrl(String setCtlUrl) {
		this.setCtlUrl = setCtlUrl;
	}
	
	public static ParamSetTypeEnum loadByTypeName(String typeName){
		if(StringUtils.isEmpty(typeName)){
			return null;
		}
		for(ParamSetTypeEnum pste:ParamSetTypeEnum.values()){
			if(pste.getTypeName().equals(typeName.trim())){
				return pste;
			}
		}
		return null;
	}

}
