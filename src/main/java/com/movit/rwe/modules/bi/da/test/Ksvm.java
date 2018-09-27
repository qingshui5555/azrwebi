package com.movit.rwe.modules.bi.da.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.math.RandomUtils;
import org.rosuda.REngine.Rserve.RConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movit.rwe.modules.bi.da.enums.ParamSetTypeEnum;
import com.movit.rwe.modules.bi.da.test.dataframe.DataFrameHandleResult;
import com.movit.rwe.modules.bi.da.test.dataframe.DataFrameInvoke;
import com.movit.rwe.modules.bi.da.test.dataframe.DataFrameResults;
import com.movit.rwe.modules.bi.da.test.model.TestModel;
import com.movit.rwe.modules.bi.da.test.model.TestModelInvoke;
import com.movit.rwe.modules.bi.da.test.model.TestModelSummary;
import com.movit.rwe.modules.bi.da.test.rresult.DaException;
import com.movit.rwe.modules.bi.da.test.rresult.RResult;
import com.movit.rwe.modules.bi.da.test.set.ConhortChosen;
import com.movit.rwe.modules.bi.da.test.set.DataModelParamChosen;
import com.movit.rwe.modules.bi.da.test.set.TestExtendAtt;
import com.movit.rwe.modules.bi.dataanalysis.common.RserveConnectionFactory;
import com.movit.rwe.modules.bi.dataanalysis.service.KsvmService;
@Service(value="da_test_ksvm")
public class Ksvm extends TestAbs {

	@Autowired
	private KsvmService ksvmService;
	
	@Override
	public String getTestName() {
		return "Ksvm";
	}

	@Override
	public int getParamCountLimitLeft() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public int getParamCountLimitRight() {
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	public int getCohortCountLimitLeft() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public int getPCohortCountLimitRight() {
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	public String getTestExtendAttViewModelUrl() {
		// TODO Auto-generated method stub
		return "modules/bi/da/extendAtt/default_no";
	}

	@Override
	public String getTestResultViewModelUrl() {
		return "modules/bi/da/result/ksvm";
	}

	@Override
	public ParamSetTypeEnum getParamSetType() {
		return ParamSetTypeEnum.PARAMSET_KSVM;
	}

	@Override
	public String dataModelParamsChosenCheck(List<DataModelParamChosen> chosenParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String cohortsChosenCheck(ConhortChosen conhortChosen) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RResult testInvoke(DataFrameResults results, TestExtendAtt eAtt,DataFrameInvoke invoke) throws DaException {
		try {
			DataFrameHandleResult dataFrameHandleResult = testHelper.handleTestParam(results, "String");
			String [] modelColumnNameArray = dataFrameHandleResult.getModelColumnNameArray();
			List <String[]>dataList = dataFrameHandleResult.getStringDataList();
			
			//R连接
			RConnection conn = RserveConnectionFactory.createConnection();
	
			//参数转化
//			StringBuffer formula = new StringBuffer();
//			formula.append(modelColumnNameArray[modelColumnNameArray.length-1]).append(" ~ ");
//			for(int m=0;m<modelColumnNameArray.length-1;m++){
//				formula.append(modelColumnNameArray[m]).append("+");
//			}
//			String formulaStr = formula.substring(0,formula.lastIndexOf("+"));
//			
//			String kernel = eAtt.get("kernel").toString();
//			String kparKey = eAtt.get("kparKey").toString();
//			String kparValue = eAtt.get("kparValue").toString();
//			String C = eAtt.get("C").toString();
//			String cross = eAtt.get("cross").toString();
			
			Map <String,String>paramMap = new HashMap<String,String>();
//			paramMap.put("formula", formulaStr);
			paramMap.put("modelName", invoke.getTestModelName());
//			paramMap.put("kernel", kernel);
//			paramMap.put("kparKey", kparKey);
//			paramMap.put("kparValue", kparValue);
//			paramMap.put("C", C);
//			paramMap.put("cross", cross);
			
			//执行方法
			Map result = ksvmService.predict(conn, modelColumnNameArray, dataList, paramMap);
			
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
	public TestExtendAtt getTestExtendAtt(HttpServletRequest request) {
		String kernel = request.getParameter("extAtt[kernel]");
		String kparKey = request.getParameter("extAtt[kparKey]");
		String kparValue = request.getParameter("extAtt[kparValue]");
		String C = request.getParameter("extAtt[C]");
		String cross = request.getParameter("extAtt[cross]");
		
		TestExtendAtt att = new TestExtendAtt();
		att.put("kernel", kernel);
		att.put("kparKey", kparKey);
		att.put("kparValue", kparValue);
		att.put("C", C);
		att.put("cross", cross);
		return att;
	}

	@Override
	public boolean isNeedTestModel() {
		return true;
	}

	@Override
	public String getTestModelResultViewUrl() {
		return "modules/bi/da/testModel/result/ksvm";
	}

	@Override
	public boolean isNeedTestModelExtendAtt() {
		return true;
	}

	@Override
	public String getTestModelExtendAttViewUrl() {
		return "modules/bi/da/testModel/extendAtt/ksvm";
	}

	@Override
	public TestExtendAtt getTestModelExtendAtt(HttpServletRequest request) {
		String kernel = request.getParameter("tmExtAtt[kernel]");
		String kparKey = request.getParameter("tmExtAtt[kparKey]");
		String kparValue = request.getParameter("tmExtAtt[kparValue]");
		String C = request.getParameter("tmExtAtt[C]");
		String cross = request.getParameter("tmExtAtt[cross]");
		
		TestExtendAtt att = new TestExtendAtt();
		att.put("kernel", kernel);
		att.put("kparKey", kparKey);
		att.put("kparValue", kparValue);
		att.put("C", C);
		att.put("cross", cross);
		return att;
	}

	@Override
	public TestModel invokeTestModel(DataFrameResults results, TestModelInvoke modelInvoke) {
		try {
			DataFrameHandleResult dataFrameHandleResult = testHelper.handleTestParam(results, "String");
			String [] modelColumnNameArray = dataFrameHandleResult.getModelColumnNameArray();
			List <String[]>dataList = dataFrameHandleResult.getStringDataList();
			
			//R连接
			RConnection conn = RserveConnectionFactory.createConnection();
	
			//参数转化
			StringBuffer formula = new StringBuffer();
			formula.append(modelColumnNameArray[modelColumnNameArray.length-1]).append(" ~ ");
			for(int m=0;m<modelColumnNameArray.length-1;m++){
				formula.append(modelColumnNameArray[m]).append("+");
			}
			String formulaStr = formula.substring(0,formula.lastIndexOf("+"));
			String kernel = modelInvoke.getTestModelTestExtendAtt().get("kernel").toString();
			String kparKey = modelInvoke.getTestModelTestExtendAtt().get("kparKey").toString();
			String kparValue = modelInvoke.getTestModelTestExtendAtt().get("kparValue").toString();
			String C = modelInvoke.getTestModelTestExtendAtt().get("C").toString();
			String cross = modelInvoke.getTestModelTestExtendAtt().get("cross").toString();
			
			Map <String,String>paramMap = new HashMap<String,String>();
			paramMap.put("formula", formulaStr);
			String modelName = "Ksvm_"+RandomUtils.nextInt()+".RData";
			paramMap.put("modelName", modelName);
			paramMap.put("kernel", kernel);
			paramMap.put("kparKey", kparKey);
			paramMap.put("kparValue", kparValue);
			paramMap.put("C", C);
			paramMap.put("cross", cross);
			
			//执行方法
			Map result = ksvmService.ksvm(conn, modelColumnNameArray, dataList, paramMap);
			
			//R-server返回的测试模型内的结果内容，很重要 一定要在本方法内返回
			TestModelSummary tms = new TestModelSummary();
			tms.putAll(result);
			
			TestModel tm = new TestModel(tms, modelInvoke.getDataFtameInvoke());
			
			//R-server返回的测试模型文件名称，很重要 一定要在本方法内返回
			tm.setModelName(modelName);
			
			//关闭连接
			conn.close();
			
			return tm;
		
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	

}
