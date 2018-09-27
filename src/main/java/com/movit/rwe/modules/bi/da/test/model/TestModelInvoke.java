package com.movit.rwe.modules.bi.da.test.model;

import com.movit.rwe.modules.bi.da.test.dataframe.DataFrameInvoke;
import com.movit.rwe.modules.bi.da.test.set.TestExtendAtt;

public class TestModelInvoke {
	
	DataFrameInvoke dataFtameInvoke;
	
	String name;
	
	String remarks;
	
	String daTestModelId;
	
	TestExtendAtt testModelTestExtendAtt;

	public DataFrameInvoke getDataFtameInvoke() {
		return dataFtameInvoke;
	}

	public void setDataFtameInvoke(DataFrameInvoke dataFtameInvoke) {
		this.dataFtameInvoke = dataFtameInvoke;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getDaTestModelId() {
		return daTestModelId;
	}

	public void setDaTestModelId(String daTestModelId) {
		this.daTestModelId = daTestModelId;
	}

	public TestExtendAtt getTestModelTestExtendAtt() {
		return testModelTestExtendAtt;
	}

	public void setTestModelTestExtendAtt(TestExtendAtt testModelTestExtendAtt) {
		this.testModelTestExtendAtt = testModelTestExtendAtt;
	}
	
	

}
