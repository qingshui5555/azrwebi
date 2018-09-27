package com.movit.rwe.modules.bi.da.test.dataframe;

import java.util.ArrayList;
import java.util.List;

public class DataFrameResult {

	String patientCode;

	String cohortName;

	List<DataFrameResultData> paramDataList = new ArrayList<DataFrameResultData>();

	public String getPatientCode() {
		return patientCode;
	}

	public void setPatientCode(String patientCode) {
		this.patientCode = patientCode;
	}

	public String getCohortName() {
		return cohortName;
	}

	public void setCohortName(String cohortName) {
		this.cohortName = cohortName;
	}

	public List<DataFrameResultData> getParamDataList() {
		return paramDataList;
	}

	public void setParamDataList(List<DataFrameResultData> paramDataList) {
		this.paramDataList = paramDataList;
	}

}
