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
import com.movit.rwe.modules.bi.da.test.dataframe.DataFrameGroup;
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
import com.movit.rwe.modules.bi.dataanalysis.service.LogisticRegressionSevice;
@Service(value="da_test_logisticregression")
public class LogisticRegression extends TestAbs {

	@Autowired
	private LogisticRegressionSevice logisticRegressionSevice;
	
	@Override
	public String getTestName() {
		return "LogisticRegression";
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
		return 1;
	}

	@Override
	public String getTestExtendAttViewModelUrl() {
		// TODO Auto-generated method stub
		return "modules/bi/da/extendAtt/logisticRegression";
	}

	@Override
	public String getTestResultViewModelUrl() {
		return "modules/bi/da/result/logisticRegression";
	}

	@Override
	public ParamSetTypeEnum getParamSetType() {
		return ParamSetTypeEnum.PARAMSET_LOGISTICREGRESSION;
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
//			DataFrameHandleResult dataFrameHandleResult = testHelper.handleTestParam(results, "double");
//			String [] modelColumnNameArray = dataFrameHandleResult.getModelColumnNameArray();
//			List <double[]>dataList = dataFrameHandleResult.getDoubleDataList();
//			
//			//R连接
//			RConnection conn = RserveConnectionFactory.createConnection();
//	
//			//参数转化
//			StringBuffer formula = new StringBuffer();
//			formula.append(modelColumnNameArray[modelColumnNameArray.length-1]).append(" ~ ");
//			for(int m=0;m<modelColumnNameArray.length-1;m++){
//				formula.append(modelColumnNameArray[m]).append("+");
//			}
			List<DataFrameGroup> dataList = testHelper.handleGroupTestParam(results);
			//R连接
			RConnection conn = RserveConnectionFactory.createConnection();
			//参数转化
			StringBuffer formula = new StringBuffer();
			formula.append(dataList.get(dataList.size()-1).getColumn()).append(" ~ ");
			for(int m=0;m<dataList.size()-1;m++){
				formula.append(dataList.get(m).getColumn()).append("+");
			}
			
			String formulaStr = formula.substring(0,formula.lastIndexOf("+"));
			
			String threshold = (String) eAtt.get("threshold");
			
			Map <String,String>paramMap = new HashMap<String,String>();
			paramMap.put("formula", formulaStr);
			paramMap.put("modelName", invoke.getTestModelName());
			paramMap.put("threshold",threshold);
						
			//执行方法
//			Map result = logisticRegressionSevice.predict(conn, modelColumnNameArray, dataList, paramMap);
			Map result = logisticRegressionSevice.predict(conn, dataList, paramMap);
			
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
		String threshold = request.getParameter("extAtt[threshold]");
		
		TestExtendAtt att = new TestExtendAtt();
		att.put("threshold", threshold);
		return att;
	}

	@Override
	public boolean isNeedTestModel() {
		return true;
	}

	@Override
	public String getTestModelResultViewUrl() {
		return "modules/bi/da/testModel/result/logisticRegression";
	}

	@Override
	public TestModel invokeTestModel(DataFrameResults results, TestModelInvoke modelInvoke) {
		try {
//			DataFrameHandleResult dataFrameHandleResult = testHelper.handleTestParam(results, "double");
//			String [] modelColumnNameArray = dataFrameHandleResult.getModelColumnNameArray();
//			List <double[]>dataList = dataFrameHandleResult.getDoubleDataList();
//			
//			//R连接
//			RConnection conn = RserveConnectionFactory.createConnection();
//	
//			//参数转化
//			StringBuffer formula = new StringBuffer();
//			formula.append(modelColumnNameArray[modelColumnNameArray.length-1]).append(" ~ ");
//			for(int m=0;m<modelColumnNameArray.length-1;m++){
//				formula.append(modelColumnNameArray[m]).append("+");
//			}
			List<DataFrameGroup> dataList = testHelper.handleGroupTestParam(results);
			//R连接
			RConnection conn = RserveConnectionFactory.createConnection();
			//参数转化
			StringBuffer formula = new StringBuffer();
			formula.append(dataList.get(dataList.size()-1).getColumn()).append(" ~ ");
			for(int m=0;m<dataList.size()-1;m++){
				formula.append(dataList.get(m).getColumn()).append("+");
			}
			String formulaStr = formula.substring(0,formula.lastIndexOf("+"));

			Map <String,String>paramMap = new HashMap<String,String>();
			paramMap.put("formula", formulaStr);
			String modelName = "LogisticRegression_"+RandomUtils.nextInt()+".RData";
			paramMap.put("modelName", modelName);
			
			//执行方法
//			Map result = logisticRegressionSevice.glm(conn, modelColumnNameArray, dataList, paramMap);
			Map result = logisticRegressionSevice.glm(conn, dataList, paramMap);
			
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
