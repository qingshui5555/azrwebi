package com.movit.rwe.modules.bi.da.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.rosuda.REngine.Rserve.RConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movit.rwe.common.utils.I18NUtils;
import com.movit.rwe.modules.bi.da.enums.ParamSetTypeEnum;
import com.movit.rwe.modules.bi.da.test.dataframe.DataFrameHandleResult;
import com.movit.rwe.modules.bi.da.test.dataframe.DataFrameInvoke;
import com.movit.rwe.modules.bi.da.test.dataframe.DataFrameResults;
import com.movit.rwe.modules.bi.da.test.rresult.DaException;
import com.movit.rwe.modules.bi.da.test.rresult.RResult;
import com.movit.rwe.modules.bi.da.test.set.ConhortChosen;
import com.movit.rwe.modules.bi.da.test.set.DataModelParamChosen;
import com.movit.rwe.modules.bi.da.test.set.TestExtendAtt;
import com.movit.rwe.modules.bi.dataanalysis.common.RserveConnectionFactory;
import com.movit.rwe.modules.bi.dataanalysis.service.FisherService;

@Service(value="da_test_fisher")
public class Fisher extends TestAbs{

	@Autowired
	private FisherService fisherService;
	@Autowired
	private I18NUtils i18NUtils;
	
	@Override
	public String getTestName() {
		return "Fisher";
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
		return 1;
	}

	@Override
	public 	int getPCohortCountLimitRight() {
		return 1;
	}

	@Override
	public ParamSetTypeEnum getParamSetType() {
		return ParamSetTypeEnum.PARAMSET_DEFAULT;
	}

	@Override
	public 	String dataModelParamsChosenCheck(List<DataModelParamChosen> chosenParams) {
		return null;
	}

	@Override
	public String cohortsChosenCheck(ConhortChosen conhortChosen) {
		return null;
	}

	@Override
	public RResult testInvoke(DataFrameResults results, TestExtendAtt eAtt,DataFrameInvoke invoke) {
		try {
			DataFrameHandleResult dataFrameHandleResult = testHelper.handleTestParam(results, "String");
			String [] modelColumnNameArray = dataFrameHandleResult.getModelColumnNameArray();
			List <String[]>dataList = dataFrameHandleResult.getStringDataList();
			
			for(int i=0;i<dataList.size();i++){
				if(dataList.get(i).length<=1){
					throw new DaException(i18NUtils.get("da.index.analysisparamterfromsampledata")+" "+i18NUtils.get("da.exception.mustbemorethan", "1"),"",null);
				}
			}
			
			//R连接
			RConnection conn = RserveConnectionFactory.createConnection();
	
			//参数转化
			Map <String,String>paramMap = new HashMap<String,String>();
			
			//执行方法
			Map result = fisherService.fisherTest(conn, modelColumnNameArray, dataList, paramMap);
			
			//返回结果
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
		return "modules/bi/da/result/fisher";
	}

	@Override
	public TestExtendAtt getTestExtendAtt(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
