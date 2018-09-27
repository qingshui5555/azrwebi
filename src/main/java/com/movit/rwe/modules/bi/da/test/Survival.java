package com.movit.rwe.modules.bi.da.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.rosuda.REngine.Rserve.RConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movit.rwe.modules.bi.base.entity.mysql.PatientGroup;
import com.movit.rwe.modules.bi.da.enums.ParamSetTypeEnum;
import com.movit.rwe.modules.bi.da.test.dataframe.DataFrameInvoke;
import com.movit.rwe.modules.bi.da.test.dataframe.DataFrameResult;
import com.movit.rwe.modules.bi.da.test.dataframe.DataFrameResultData;
import com.movit.rwe.modules.bi.da.test.dataframe.DataFrameResults;
import com.movit.rwe.modules.bi.da.test.rresult.RResult;
import com.movit.rwe.modules.bi.da.test.set.ConhortChosen;
import com.movit.rwe.modules.bi.da.test.set.DataModelParamChosen;
import com.movit.rwe.modules.bi.da.test.set.TestExtendAtt;
import com.movit.rwe.modules.bi.dataanalysis.common.RserveConnectionFactory;
import com.movit.rwe.modules.bi.dataanalysis.service.SurvivalAnalysisService;
import com.movit.rwe.modules.bi.dataanalysis.util.RserveUtil;

@Service(value="da_test_survival")
public class Survival extends TestAbs{

	@Autowired
	private SurvivalAnalysisService survivalAnalysisService;
	
	@Override
	public String getTestName() {
		return "Survival";
	}

	@Override
	public int getParamCountLimitLeft() {
		return 2;
	}

	@Override
	public int getParamCountLimitRight() {
		return 2;
	}

	@Override
	public 	int getCohortCountLimitLeft() {
		return 2;
	}

	@Override
	public 	int getPCohortCountLimitRight() {
		return -1;
	}

	@Override
	public ParamSetTypeEnum getParamSetType() {
		return ParamSetTypeEnum.PARAMSET_SURVIVAL;
	}

	@Override
	public 	String dataModelParamsChosenCheck(List<DataModelParamChosen> chosenParams) {
		return null;
	}

	@Override
	public String cohortsChosenCheck(ConhortChosen conhortChosen) {
		//获得选择组的数量
		int length = conhortChosen.getCohortIds().length+conhortChosen.getGroups().length;
		if(length<2){
			throw new RuntimeException("请至少选择两个组！");
		}
		return null;
	}

	@Override
	public RResult testInvoke(DataFrameResults results, TestExtendAtt eAtt,DataFrameInvoke invoke) {
		try {
			List <DataModelParamChosen> dataModelParamChosenList= results.getDataModelParamChosenList();
			Map<PatientGroup, List<DataFrameResult>> resultMapForCohorts = results.getResultMapForCohorts();

			String [] modelColumnNameArray = new String[dataModelParamChosenList.size()+1];
			//survival需要添加额外列group
			modelColumnNameArray[dataModelParamChosenList.size()] = "group";
			String [] groupNameArray = new String[resultMapForCohorts.entrySet().size()];
			List <String[]>dataList = new ArrayList<String[]>();
			
			//循环取列名及值
			for(int i=0;i<dataModelParamChosenList.size();i++){
				List <String>valueList = new ArrayList<String>();
				List <String>cohortNameList = new ArrayList<String>();
				DataModelParamChosen dataModelParamChosen = dataModelParamChosenList.get(i);
				//获取列名,放入数组
				modelColumnNameArray[i] = dataModelParamChosen.getModelColumnName();
				int index = 0;
				//获取值,放入数组中
				for (Map.Entry<PatientGroup, List<DataFrameResult>> entry : resultMapForCohorts.entrySet()) {
					PatientGroup patientGroup = entry.getKey();
					List<DataFrameResult> dataFrameResultList = entry.getValue();
					//获取每个值对应的组名称
					String cohortName = patientGroup.getGroupName();
					groupNameArray[index]=cohortName;
					index++;
					for(DataFrameResult dfr:dataFrameResultList){
						List<DataFrameResultData> paramDataList = dfr.getParamDataList();
						//获取列索引对应的具体值
						DataFrameResultData dataFrameResultData = paramDataList.get(i);
						Object dataValueObject = dataFrameResultData.getDataValue();
						String dataValue = dataValueObject.toString();
						valueList.add(dataValue);
						cohortNameList.add(cohortName);
					}
				}
				Object [] objectArray = valueList.toArray();
				String [] stringArray = RserveUtil.objectArrayToStringArray(objectArray);
				dataList.add(stringArray);
				
				if(i==dataModelParamChosenList.size()-1){
					Object [] objectCohortNameArray = cohortNameList.toArray();
					String [] stringCohortNameArray = RserveUtil.objectArrayToStringArray(objectCohortNameArray);
					dataList.add(stringCohortNameArray);
				}
			}
			
			//R连接
			RConnection conn = RserveConnectionFactory.createConnection();
	
			//参数转化
			Map <String,String>paramMap = new HashMap<String,String>();
			
			
			Map result = survivalAnalysisService.survival(conn, modelColumnNameArray, dataList, groupNameArray, paramMap);
			
			RResult rResult = new RResult();
			rResult.setResultMap(result);
			
			//关闭连接
			conn.close();
			
			return rResult;
		
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}

	@Override
	public String getTestExtendAttViewModelUrl() {
		return "modules/bi/da/extendAtt/default_no";
	}

	@Override
	public String getTestResultViewModelUrl() {
		return "modules/bi/da/result/survival";
	}

	@Override
	public TestExtendAtt getTestExtendAtt(HttpServletRequest request) {
		return null;
	}

}
