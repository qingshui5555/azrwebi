package com.movit.rwe.modules.bi.da.enums;

/**
 * 
 * @ClassName: DataTypeEnum
 * @Description: 数据类型
 * @author wade.chen@movit-tech.com
 * @date 2016年5月20日 下午1:37:21
 *
 */
public enum DataTypeEnum {

	Num("num", "数字型"), 
	Text("text", "文本型")
	
	;
	String code;

	String desc;

	private DataTypeEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public static DataTypeEnum loadByCode(String code) {
		for (DataTypeEnum en : DataTypeEnum.values()) {
			if (en.getCode().equals(code.trim())) {
				return en;
			}
		}
		return null;
	}

}
