package com.movit.rwe.modules.bi.da.test.dataframe;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.movit.rwe.modules.bi.base.entity.mysql.PatientGroup;
import com.movit.rwe.modules.bi.da.test.set.ConhortChosen;
import com.movit.rwe.modules.bi.da.test.set.DataModelParamChosen;

public class DataFrameResults {

	/**
	 * 选择了哪些属性
	 */
	List<DataModelParamChosen> dataModelParamChosenList;

	/**
	 * 选择了哪些cohorts
	 */
	ConhortChosen conhortChosen = new ConhortChosen();

	/**
	 * 数据库返回的结果数据流
	 */
	List<DataFrameResult> resultList = new ArrayList<DataFrameResult>();

//	Map<PatientGroup, List<DataFrameResult>> resultMapForCohorts = new HashMap<PatientGroup, List<DataFrameResult>>();
	Map<PatientGroup, List<DataFrameResult>> resultMapForCohorts = new LinkedHashMap<PatientGroup, List<DataFrameResult>>(); 
	
	public List<DataModelParamChosen> getDataModelParamChosenList() {
		return dataModelParamChosenList;
	}

	public void setDataModelParamChosenList(List<DataModelParamChosen> dataModelParamChosenList) {
		this.dataModelParamChosenList = dataModelParamChosenList;
	}

	public List<DataFrameResult> getResultList() {
		return resultList;
	}

	public void setResultList(List<DataFrameResult> resultList) {
		this.resultList = resultList;
	}

	public ConhortChosen getConhortChosen() {
		return conhortChosen;
	}

	public void setConhortChosen(ConhortChosen conhortChosen) {
		this.conhortChosen = conhortChosen;
	}

	public Map<PatientGroup, List<DataFrameResult>> getResultMapForCohorts() {
		return resultMapForCohorts;
	}

	public void setResultMapForCohorts(Map<PatientGroup, List<DataFrameResult>> resultMapForCohorts) {
		this.resultMapForCohorts = resultMapForCohorts;
	}

	

}
