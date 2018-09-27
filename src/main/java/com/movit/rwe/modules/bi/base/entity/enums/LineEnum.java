package com.movit.rwe.modules.bi.base.entity.enums;

import com.movit.rwe.common.utils.StringUtils;

public enum LineEnum {
	FIRST_LINE("First line",1),
	SECOND_LINE("Second line",2),
	THIRD_LINE("Third line",3),
	MULTI_LINE(">Third Line",4);

	String name;
	Integer line;

	LineEnum(String name, Integer line) {
		this.name = name;
		this.line = line;
	}

	public static Integer getLineWithDefault(String name){
		Integer result = null;
		if(!StringUtils.isBlank(name)){
			for(LineEnum lineEnum : LineEnum.values()){
				if(name.equals(lineEnum.getName())){
					result = lineEnum.getLine();
					break;
				}
			}
		}
		return result;
	}

	public static String getNameWithDefault(Integer line){
		String result = null;
		if(line != null){
			for(LineEnum lineEnum : LineEnum.values()){
				if(line.equals(lineEnum.getLine())){
					result = lineEnum.getName();
					break;
				}
			}
		}
		return result;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getLine() {
		return line;
	}

	public void setLine(Integer line) {
		this.line = line;
	}
}
