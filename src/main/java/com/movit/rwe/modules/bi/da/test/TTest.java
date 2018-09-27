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
import com.movit.rwe.modules.bi.da.test.dataframe.DataFrameInvoke;
import com.movit.rwe.modules.bi.da.test.dataframe.DataFrameResults;
import com.movit.rwe.modules.bi.da.test.rresult.DaException;
import com.movit.rwe.modules.bi.da.test.rresult.RResult;
import com.movit.rwe.modules.bi.da.test.set.ConhortChosen;
import com.movit.rwe.modules.bi.da.test.set.DataModelParamChosen;
import com.movit.rwe.modules.bi.da.test.set.TestExtendAtt;
import com.movit.rwe.modules.bi.dataanalysis.common.RserveConnectionFactory;
import com.movit.rwe.modules.bi.dataanalysis.service.TtestService;

@Service(value="da_test_ttest")
public class TTest extends TestAbs{

	@Autowired
	private TtestService ttestService;
	@Autowired
	private I18NUtils i18NUtils;
	@Autowired
	private TestHelper testHelper;
	
	@Override
	public String getTestName() {
		return "T-Test";
	}

	@Override
	public int getParamCountLimitLeft() {
		return 1;
	}

	@Override
	public int getParamCountLimitRight() {
		return 1;
	}

	@Override
	public 	int getCohortCountLimitLeft() {
		return 1;
	}

	@Override
	public 	int getPCohortCountLimitRight() {
		return 2;
	}
	
	@Override
	public String getTestExtendAttViewModelUrl() {
		return "modules/bi/da/extendAtt/ttest";
	}

	@Override
	public String getTestResultViewModelUrl() {
		return "modules/bi/da/result/ttest";
	}

	@Override
	public ParamSetTypeEnum getParamSetType() {
		return ParamSetTypeEnum.PARAMSET_DEFAULT;
	}

	@Override
	public 	String dataModelParamsChosenCheck(List<DataModelParamChosen> chosenParams) {
		if(chosenParams.size()>1){
			throw new RuntimeException("只能选择一个指标！");
		}
		return null;
	}

	@Override
	public String cohortsChosenCheck(ConhortChosen conhortChosen) {
		return null;
	}

	@Override
	public RResult testInvoke(DataFrameResults results, TestExtendAtt eAtt,DataFrameInvoke invoke) {
		try {
//			if(results==null){
//				throw new DaException(i18NUtils.get("da.index.analysisparamterfromsampledata")+" "+i18NUtils.get("da.exception.mustbemorethan", "1"),"",null);
//			}
//			List <DataModelParamChosen> dataModelParamChosenList= results.getDataModelParamChosenList();
//			Map<PatientGroup, List<DataFrameResult>> resultMapForCohorts = results.getResultMapForCohorts();
//			//数据向量列表
//			List <double[]>dataList = new ArrayList<double[]>();
//			
//			String [] modelColumnNameArray = new String[dataModelParamChosenList.size()];
//			
//			DataModelParamChosen dataModelParamChosen = dataModelParamChosenList.get(0);
////			//获取值,放入数组中
//			for (Map.Entry<PatientGroup, List<DataFrameResult>> entry : resultMapForCohorts.entrySet()) {
//				PatientGroup patientGroup = entry.getKey();
//				List<DataFrameResult> dataFrameResultList = entry.getValue();
//				List <Object>everyGroupData = new ArrayList<Object>();
//				for(DataFrameResult dfr:dataFrameResultList){
//					List<DataFrameResultData> paramDataList = dfr.getParamDataList();
//					//获取列索引对应的具体值
//					for(DataFrameResultData dfrd:paramDataList){
//						everyGroupData.add(dfrd.getDataValue());
//					}
//				}
//				Object[] everyGroupDataArray = everyGroupData.toArray();
//				double [] doubleArray = RserveUtil.objectArrayToDoubleArray(everyGroupDataArray);
//				dataList.add(doubleArray);
//			}
			List <double[]>dataList = testHelper.handleSimpleTestParam(results);
					
			for(int i=0;i<dataList.size();i++){
				//数据长度必须大于1
				if(dataList.get(i).length<=1){
					throw new DaException(i18NUtils.get("da.exception.theamountofdata")+" "+i18NUtils.get("da.exception.mustbemorethan", "1"),"",null);
				}
				//数据不能相同
				boolean flag = true;
				for(int j=1;j<dataList.get(i).length;j++){
					if(dataList.get(i)[0]!=dataList.get(i)[j]){
						flag = false;
						break;
					}
				}
				if(flag){
					throw new DaException(i18NUtils.get("da.exception.dataineachgroup")+" "+i18NUtils.get("da.exception.cannotbeallthesamevalues"),"",null);
				}
			}
			
			//R连接
			RConnection conn = RserveConnectionFactory.createConnection();
	
			//参数转化
			String alternative = eAtt.get("alternative").toString();
			String mu = eAtt.get("mu").toString();
			String paired = eAtt.get("paired").toString();
			String confLevel = eAtt.get("confLevel").toString();
			
			Map <String,String>paramMap = new HashMap<String,String>();
			paramMap.put("alternative", alternative);
			paramMap.put("mu", mu);
			paramMap.put("paired", paired);
			paramMap.put("confLevel", confLevel);
			
			//执行方法
			Map result = ttestService.ttest(conn, dataList, paramMap);
			
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
		String alternative = request.getParameter("extAtt[alternative]");
		String mu = request.getParameter("extAtt[mu]");
		String paired = request.getParameter("extAtt[paired]");
		String confLevel = request.getParameter("extAtt[confLevel]");
		
		TestExtendAtt att = new TestExtendAtt();
		att.put("alternative", alternative);
		att.put("mu", mu);
		att.put("paired", paired);
		att.put("confLevel", confLevel);
		
		return att;
	}

}
