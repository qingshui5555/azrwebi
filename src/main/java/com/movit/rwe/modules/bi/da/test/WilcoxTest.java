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
import com.movit.rwe.modules.bi.dataanalysis.service.WilcoxTestService;

@Service(value = "da_test_wilcoxtest")
public class WilcoxTest extends TestAbs {

	@Autowired
	private WilcoxTestService wilcoxTestService;
	@Autowired
	private I18NUtils i18NUtils;

	@Override
	public String getTestName() {
		return "Wilcox";
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
	public int getCohortCountLimitLeft() {
		return 1;
	}

	@Override
	public int getPCohortCountLimitRight() {
		return 2;
	}

	@Override
	public ParamSetTypeEnum getParamSetType() {
		return ParamSetTypeEnum.PARAMSET_DEFAULT;
	}

	@Override
	public String dataModelParamsChosenCheck(List<DataModelParamChosen> chosenParams) {
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
//						DataTypeEnum dataTypeEnum = dfrd.getDataType();
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
			}

			// R连接
			RConnection conn = RserveConnectionFactory.createConnection();

			// 参数转化
			String alternative = eAtt.get("alternative").toString();
			String mu = eAtt.get("mu").toString();
			String paired = eAtt.get("paired").toString();
			String confLevel = eAtt.get("confLevel").toString();

			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("alternative", alternative);
			paramMap.put("mu", mu);
			paramMap.put("paired", paired);
			paramMap.put("confLevel", confLevel);

			Map result = wilcoxTestService.wilcoxTest(conn, dataList, paramMap);

			// 返回结果
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
		return "modules/bi/da/extendAtt/WilcoxTest";
	}

	@Override
	public String getTestResultViewModelUrl() {
		return "modules/bi/da/result/WilcoxTest";
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
