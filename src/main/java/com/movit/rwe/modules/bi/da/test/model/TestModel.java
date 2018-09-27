package com.movit.rwe.modules.bi.da.test.model;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import com.google.gson.Gson;
import com.movit.rwe.modules.bi.base.entity.mysql.DaTestModel;
import com.movit.rwe.modules.bi.da.test.TestAbs;
import com.movit.rwe.modules.bi.da.test.TestFactory;
import com.movit.rwe.modules.bi.da.test.dataframe.DataFrameInvoke;

public class TestModel extends DaTestModel {

	private static final long serialVersionUID = 1L;

	Gson gson = new Gson();
	
	private TestModelSummary summary;
	
	private DataFrameInvoke dataFrameInvoke;
	
	private TestAbs testInstance;
	
	public TestModel(TestModelSummary summary, DataFrameInvoke dataFrameInvoke) {
		super();
		this.summary = summary;
		this.setSummaryJsonStr(gson.toJson(summary, TestModelSummary.class));
		
		this.dataFrameInvoke = dataFrameInvoke;
		this.setDaConfJsonStr(gson.toJson(dataFrameInvoke,DataFrameInvoke.class));
		
	}

	public TestModel(DaTestModel dtm){
		BeanUtils.copyProperties(dtm, this);
		
		if(!StringUtils.isEmpty(this.getSummaryJsonStr())){
			this.setSummary(gson.fromJson(this.getSummaryJsonStr(), TestModelSummary.class));
		}
		
		if(!StringUtils.isEmpty(this.getDaConfJsonStr())){
			this.setDataFrameInvoke(gson.fromJson(this.getDaConfJsonStr(), DataFrameInvoke.class));
		}
	}

	public TestModelSummary getSummary() {
		return summary;
	}

	public void setSummary(TestModelSummary summary) {
		this.summary = summary;
	}

	public DataFrameInvoke getDataFrameInvoke() {
		return dataFrameInvoke;
	}

	public void setDataFrameInvoke(DataFrameInvoke dataFrameInvoke) {
		this.dataFrameInvoke = dataFrameInvoke;
	}
	
	public TestAbs getTestInstance(){
		if(testInstance!=null || dataFrameInvoke==null)
			return testInstance;
		
		testInstance = TestFactory.getTestByTestName(this.dataFrameInvoke.getTestName());
		return testInstance;
	}
	
	

}
