package com.movit.rwe.modules.bi.da.test.dataframe;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.ArrayUtils;

import com.movit.rwe.modules.bi.base.entity.mysql.BmdModelMeta;
import com.movit.rwe.modules.bi.da.test.set.ConhortChosen;
import com.movit.rwe.modules.bi.da.test.set.DataModelParamChosen;
import com.movit.rwe.modules.bi.da.test.set.TestExtendAtt;

public class DataFrameInvoke {
	
	String taId;
	
	String testName;
	
	String studyId;
	
	BmdModelMeta modelMeta;
	
	String testModelName;
	
	String testModelId;
	
	List<DataModelParamChosen> chosenParams = new ArrayList<DataModelParamChosen>();
	
	ConhortChosen conhortChosen = new ConhortChosen();
	
	TestExtendAtt extAtt;

	String formula;
	
	String[] formulaSymbol = null;
	
	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public String[] getFormulaSymbol() {
		return formulaSymbol;
	}

	public void setFormulaSymbol(String[] formulaSymbol) {
		this.formulaSymbol = formulaSymbol;
	}

	public String getTaId() {
		return taId;
	}

	public void setTaId(String taId) {
		this.taId = taId;
	}

	public String getStudyId() {
		return studyId;
	}

	public void setStudyId(String studyId) {
		this.studyId = studyId;
	}

	public BmdModelMeta getModelMeta() {
		return modelMeta;
	}

	public void setModelMeta(BmdModelMeta modelMeta) {
		this.modelMeta = modelMeta;
	}

	public List<DataModelParamChosen> getChosenParams() {
		return chosenParams;
	}

	public void setChosenParams(List<DataModelParamChosen> chosenParams) {
		this.chosenParams = chosenParams;
	}
	public ConhortChosen getConhortChosen() {
		return conhortChosen;
	}

	public void setConhortChosen(ConhortChosen conhortChosen) {
		this.conhortChosen = conhortChosen;
	}

	public TestExtendAtt getExtAtt() {
		return extAtt;
	}

	public void setExtAtt(TestExtendAtt extAtt) {
		this.extAtt = extAtt;
	}
	
	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}
	
	public String getCohortIdsInSql(){
		Long[] cohortIds = conhortChosen.getCohortIds();
		StringBuffer sb = new StringBuffer("");
		if (!ArrayUtils.isEmpty(cohortIds)) {
			for (Long cohortId : cohortIds) {
				sb.append("'" + cohortId + "'");
			}
		}
		if(sb.equals("")){
			sb.append("'null'");
		}
		return sb.insert(0, "(").append(")").toString();
	}

	public String getGroupIdsInSql(){
		StringBuffer sb = new StringBuffer("");
		String[] groups = conhortChosen.getGroups();
		if (!ArrayUtils.isEmpty(groups)) {
			Set<Integer> groupList = new TreeSet<Integer>();
			for (String group : groups) {
				int groupInt = Integer.parseInt(group);
				groupList.add(groupInt);
			}
			for (Integer group : groupList) {
				sb.append("G_" + group + ",%");
			}
		}
		if(sb.equals("")){
			sb.append("'null'");
		}
		return sb.insert(0, "(").append(")").toString();
	}
	
	public String getCohortLikeQueryStr() {
		
		Long[] cohortIds = conhortChosen.getCohortIds();
		StringBuffer sb = new StringBuffer("");
		if (!ArrayUtils.isEmpty(cohortIds)) {
			for (Long cohortId : cohortIds) {
				sb.append("C_" + cohortId + ",%");
			}
		}

		String[] groups = conhortChosen.getGroups();
		if (!ArrayUtils.isEmpty(groups)) {
			Set<Integer> groupList = new TreeSet<Integer>();
			for (String group : groups) {
				int groupInt = Integer.parseInt(group);
				groupList.add(groupInt);
			}
			for (Integer group : groupList) {
				sb.append("G_" + group + ",%");
			}
		}
		return sb.length() == 0 ? "" : sb.insert(0, "%").toString();
	}

	public String getTestModelName() {
		return testModelName;
	}

	public void setTestModelName(String testModelName) {
		this.testModelName = testModelName;
	}

	public String getTestModelId() {
		return testModelId;
	}

	public void setTestModelId(String testModelId) {
		this.testModelId = testModelId;
	}
	
	

	
	

}
