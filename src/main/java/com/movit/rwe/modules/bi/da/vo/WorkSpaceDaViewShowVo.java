package com.movit.rwe.modules.bi.da.vo;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.movit.rwe.modules.bi.base.entity.mysql.DaView;
import com.movit.rwe.modules.bi.da.test.dataframe.DataFrameInvoke;

public class WorkSpaceDaViewShowVo extends DaView{
	
	Gson gson = new Gson();
	
	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = 1L;

	String taName;
	
	String studyName;
	
	DataFrameInvoke invoke;

	public String getTaName() {
		return taName;
	}

	public void setTaName(String taName) {
		this.taName = taName;
	}

	public String getStudyName() {
		return studyName;
	}

	public void setStudyName(String studyName) {
		this.studyName = studyName;
	}

	public DataFrameInvoke getInvoke() {
		if(invoke==null && !StringUtils.isEmpty(this.getConfJsonStr())){
			invoke = gson.fromJson(this.getConfJsonStr(), DataFrameInvoke.class);
		}
		return invoke;
	}

	public void setInvoke(DataFrameInvoke invoke) {
		this.invoke = invoke;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
