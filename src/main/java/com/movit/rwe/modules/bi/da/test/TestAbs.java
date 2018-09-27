package com.movit.rwe.modules.bi.da.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movit.rwe.modules.bi.base.dao.hive.DataAnalysisDao;
import com.movit.rwe.modules.bi.base.entity.mysql.PatientGroup;
import com.movit.rwe.modules.bi.da.enums.ParamSetTypeEnum;
import com.movit.rwe.modules.bi.da.test.dataframe.DataFrameInvoke;
import com.movit.rwe.modules.bi.da.test.dataframe.DataFrameResult;
import com.movit.rwe.modules.bi.da.test.dataframe.DataFrameResultData;
import com.movit.rwe.modules.bi.da.test.dataframe.DataFrameResults;
import com.movit.rwe.modules.bi.da.test.model.TestModel;
import com.movit.rwe.modules.bi.da.test.model.TestModelInvoke;
import com.movit.rwe.modules.bi.da.test.rresult.DaException;
import com.movit.rwe.modules.bi.da.test.rresult.RResult;
import com.movit.rwe.modules.bi.da.test.set.ConhortChosen;
import com.movit.rwe.modules.bi.da.test.set.DataModelParamChosen;
import com.movit.rwe.modules.bi.da.test.set.TestExtendAtt;

/**
 * 
  * @ClassName: TestAbs
  * @Description: 测试方法的抽象类
  * @author wade.chen@movit-tech.com
  * @date 2016年5月20日 下午1:09:41
  *
 */
@Service
public abstract class TestAbs {
	
	public static Logger logger = Logger.getLogger(TestAbs.class);
	
	@Autowired
	DataAnalysisDao analysisDao;
	
	@Autowired
	TestHelper testHelper;
	
	/**
	 * sql 查询木板 固定占位符
	 */
	private static final String SQL_TEMPLATE_PLACEHOLDER_TA= "#{taId}";
	private static final String SQL_TEMPLATE_PLACEHOLDER_STUDY_ID = "#{studyId}";
	private static final String SQL_TEMPLATE_PLACEHOLDER_GROUP_IDS = "#{groupids}";
	private static final String SQL_TEMPLATE_PLACEHOLDER_GROUP_IDS_IN = "#{grouoIds_in}";
	private static final String SQL_TEMPLATE_PLACEHOLDER_COHORT_IDS_IN = "#{cohortIds_in}";
	
	/**
	 * 查询返回的默认结果，sql语句必须返回这两个字段
	 */
	private static final String QUERY_DEFAULT_COLUMN_PATIENT_CODE = "ecode";
	private static final String QUERY_DEFAULT_COLUMN_PATIENT_GROUP_IDS = "groupids";
	
	/**
	 * 
	  * getTestName
	  *
	  * @Title: getTestName
	  * @Description: 获取测试方法名字
	  * @return String    返回类型
	  * @throws
	 */
	abstract public String getTestName();
	
	/**
	 * 
	  *
	  * @Title: getParamCountLimitLeft
	  * @Description: 选择数据模型指标数量限制；left 左边界 right 右边界  left=right 则限制定数量
	  * @return int    返回类型
	  * @throws
	 */
	abstract public int getParamCountLimitLeft();
	
	/**
	 * 
	  *
	  * @Title: getParamCountLimitRight
	  * @Description: 选择数据模型指标数量限制；left 左边界 right 右边界  left=right 则限制定数量
	  * @return int    返回类型
	  * @throws
	 */
	abstract public int getParamCountLimitRight();
	
	/**
	 * 
	  *
	  * @Title: getCohortCountLimitLeft
	  * @Description: 选择cohort&group数量限制；left 左边界 right 右边界  left=right 则限制定数量
	  * @return int    返回类型
	  * @throws
	 */
	abstract public int getCohortCountLimitLeft();
	
	/**
	 * 
	  *
	  * @Title: getPCohortCountLimitRight
	  * @Description: 选择cohort&group数量限制；left 左边界 right 右边界  left=right 则限制定数量
	  * @return int    返回类型
	  * @throws
	 */
	abstract public int getPCohortCountLimitRight();
	
	/**
	 * 
	  * getTestExtendAttViewModelUrl
	  * @Title: getTestExtendAttViewModelUrl
	  * @Description: 获取特有的参数设置界面JSP 路径
	  * @param @return    
	  * @return String    返回类型
	  * @throws
	 */
	abstract public String getTestExtendAttViewModelUrl();
	
	/**
	 * 
	  * getTestResultViewModelUrl
	  * @Title: getTestResultViewModelUrl
	  * @Description: 获取test计算结果输出界面jsp 路径
	  * @param @return    
	  * @return String    返回类型
	  * @throws
	 */	
	abstract public String getTestResultViewModelUrl();
	
	/**
	  *
	  * @Title: getParamSetType
	  * @Description: 获取数据模型配置类型
	  * @return ParamSetTypeEnum    返回类型
	  * @throws
	 */
	abstract public ParamSetTypeEnum getParamSetType();
	
	/**
	 * 
	  * dataModelParamsChosenCheck
	  *
	  * @Title: dataModelParamsChosenCheck
	  * @Description: 对已经选的数据模型、属性进行校验
	  * @param  chosenParams
	  * @return String    返回类型,如果为null 说明校验通过
	  * @throws
	 */
	abstract public String dataModelParamsChosenCheck(List<DataModelParamChosen> chosenParams);
	
	/**
	 * 
	  * cohortsChosenCheck
	  *
	  * @Title: cohortsChosenCheck
	  * @Description: 已选cohort的check检查
	  * @param conhortChosen    
	  * @return String    返回类型,如果为null 说明校验通过
	  * @throws
	 */
	abstract public String cohortsChosenCheck(ConhortChosen conhortChosen);
	
	/**
	 * 
	  * isNeedTestModel
	  *
	  * @Title: isNeedTestModel
	  * @Description: 计算的时候是否需要测试模型（如果为true则说明该测试方法在使用前需要先选择、新建测试模型）;大部分方法都不需要，因此不做为抽象方法；如果需要模型 各个测试方法自己重写该方法
	  * @return boolean    返回类型
	  * @throws
	 */
	public boolean isNeedTestModel(){
		return false;
	}
	
	public boolean isNeedTestModelExtendAtt(){
		return false;
	}
	
	public String getTestModelResultViewUrl(){
		return null;
	}
	
	public String getTestModelExtendAttViewUrl(){
		return null;
	}
	
	public TestExtendAtt getTestModelExtendAtt(HttpServletRequest request){
		return null;
	}
	
	
	/**
	 * 
	  * invokeTestModel
	  * @Title: invokeTestModel
	  * @Description: 生成计算模型;大部分方法都不需要，因此不做为抽象方法；如果需要模型 各个测试方法自己重写该方法;
	  * 注：modelInvoke.daTestModelId 为null时为新建，不为null时为update；本方法只需要想rserver请求model name，不需要做db持久化，上层代码会统一处理
	  * @param modelInvoke
	  * @return    
	  * @return DaTestModel    返回类型
	  * @throws
	 */
	public TestModel invokeTestModel(DataFrameResults results,TestModelInvoke modelInvoke){
		return null;
	}
	
	/**
	 * 
	  * loadDataFrameResult
	  *
	  * @Title: loadDataFrameResult
	  * @Description: 通用的result查询方法 
	  * @param  invoke
	  * @return DataFrameResult    返回类型
	  * @throws
	 */
	public DataFrameResults loadDataFrameResult(DataFrameInvoke invoke){
		
		String querySql = querySqlBuild(invoke);
		
		List<Map> resultMapList =  analysisDao.executeSql(querySql);
		
		return resultMapList2DataFrameResults(resultMapList,invoke);
	}
	
	/**
	 * 
	  * querySqlBuild
	  *
	  * @Title: querySqlBuild
	  * @Description: 等到底层查询语句
	  * @param  invoke
	  * @return String    返回类型
	  * @throws
	 */
	public String querySqlBuild(DataFrameInvoke invoke){
		//拼接具体的查询参数groupids like '%C_169,%' or groupids like '%C_69%'
		ConhortChosen cc = invoke.getConhortChosen();
		PatientGroup [] cohortsObj = cc.getCohortsObj();
		PatientGroup [] groupsObj = cc.getGroupsObj();
		StringBuffer cohortAndGroupIds = new StringBuffer();
		cohortAndGroupIds.append("(");
		for(PatientGroup pg:cohortsObj){
			cohortAndGroupIds.append(" groupids like '%").append("C_").append(pg.getId()).append(",%' or ");
		}
		for(PatientGroup pg:groupsObj){
			cohortAndGroupIds.append(" groupids like '%").append("G_").append(pg.getId()).append(",%' or ");
		}
		String cohortAndGroupIdsStr = cohortAndGroupIds.substring(0, cohortAndGroupIds.lastIndexOf("or"));
		cohortAndGroupIdsStr = cohortAndGroupIdsStr +")";
		
		String sqlTemplate = invoke.getModelMeta().getQuerySql();
		sqlTemplate = sqlTemplate
				.replace(TestAbs.SQL_TEMPLATE_PLACEHOLDER_TA, invoke.getTaId())
				.replace(TestAbs.SQL_TEMPLATE_PLACEHOLDER_STUDY_ID, invoke.getStudyId())
				.replace(TestAbs.SQL_TEMPLATE_PLACEHOLDER_GROUP_IDS,cohortAndGroupIdsStr);
				//.replace(TestAbs.SQL_TEMPLATE_PLACEHOLDER_GROUP_IDS,invoke.getCohortLikeQueryStr())
//				.replace(TestAbs.SQL_TEMPLATE_PLACEHOLDER_GROUP_IDS," true");
//				.replace(TestAbs.SQL_TEMPLATE_PLACEHOLDER_COHORT_IDS_IN, invoke.getCohortIdsInSql())
//				.replace(TestAbs.SQL_TEMPLATE_PLACEHOLDER_GROUP_IDS_IN, invoke.getGroupIdsInSql());
		return sqlTemplate;
	}
	/**
	 * 
	  * resultMapList2DataFrameResult
	  *
	  * @Title: resultMapList2DataFrameResult
	  * @Description: 将返回map类型封装成数据模型
	  * @param resultMapList
	  * @return DataFrameResult    返回类型
	  * @throws
	 */
	@SuppressWarnings("rawtypes")
	public DataFrameResults resultMapList2DataFrameResults(List<Map> resultMapList,DataFrameInvoke invoke){
		if(resultMapList==null || resultMapList.size()==0){
			return null;
		}
		List<DataModelParamChosen> chosenParams = invoke.getChosenParams();
		
		DataFrameResults results = new DataFrameResults();
		results.setDataModelParamChosenList(chosenParams);
		results.setConhortChosen(invoke.getConhortChosen());
		
		Map<String, List<DataFrameResult>> resultMapForCohortIds = new LinkedHashMap<String, List<DataFrameResult>>();
		
		if(results.getConhortChosen().getCohortIds()!=null && results.getConhortChosen().getCohortIds().length>0){
			for(Long cohortId:results.getConhortChosen().getCohortIds()){
				resultMapForCohortIds.put("C_"+cohortId, new ArrayList<DataFrameResult>());
			}
		}
		if(results.getConhortChosen().getGroups()!=null && results.getConhortChosen().getGroups().length>0){
			for(String groupId:results.getConhortChosen().getGroups()){
				resultMapForCohortIds.put("G_"+groupId, new ArrayList<DataFrameResult>());
			}
		}
		
		for(Map lineMap : resultMapList){
			DataFrameResult result = new DataFrameResult();
			result.setPatientCode(lineMap.get(TestAbs.QUERY_DEFAULT_COLUMN_PATIENT_CODE).toString());
			result.setCohortName(lineMap.get(TestAbs.QUERY_DEFAULT_COLUMN_PATIENT_GROUP_IDS).toString());
			//直接获取值改为当改病人某个指标为空时此条数据作废
			boolean flag = true;
			for(DataModelParamChosen dpc:chosenParams){
				/*DataFrameResultData drd = new DataFrameResultData();
				drd.setDataType(dpc.getDataType());
				drd.setDataValue(lineMap.get(dpc.getModelColumnName()));
				result.getParamDataList().add(drd);*/
				//将group_column_作为分组向量数据，值为当前病人的组名
				if(!"group_column_".equals(dpc.getModelColumnName())){
					//查询出的map中的key都被impala转为小写，所以这里也用小写获取对应key的值
					String ModelColumnValue = lineMap.get(dpc.getModelColumnName().toLowerCase())==null?
							"":lineMap.get(dpc.getModelColumnName().toLowerCase()).toString();
					//结果集中其中有值为空则不放入
					if(StringUtils.isBlank(ModelColumnValue)){
						flag = false;
						break;
					}else{
						DataFrameResultData drd = new DataFrameResultData();
						drd.setDataType(dpc.getDataType());
						drd.setDataValue(ModelColumnValue);
						result.getParamDataList().add(drd);
					}
				}else{
					//加入组的值,这里组的值为多组名，后面会处理成单组名
					DataFrameResultData drd = new DataFrameResultData();
					drd.setDataType(dpc.getDataType());
					drd.setDataValue(result.getCohortName());
					result.getParamDataList().add(drd);
				}
				
			}
			
			if(flag){
				//加入数据集中
				results.getResultList().add(result);
				
				Iterator<String> it = resultMapForCohortIds.keySet().iterator();
				while(it.hasNext()){
					String key = it.next();
					if(result.getCohortName().indexOf(key+",")>-1){
						resultMapForCohortIds.get(key).add(result);
					}
				}
			}
		}
		
		Map<PatientGroup, List<DataFrameResult>> resultMapForCohorts = new LinkedHashMap<PatientGroup, List<DataFrameResult>>();
		Iterator<String> it = resultMapForCohortIds.keySet().iterator();
		while(it.hasNext()){
			String key = it.next();
			String[] keys = key.split("_");
			PatientGroup pg = testHelper.loadPatientGroup(keys[1], keys[0].equals("G")?0:1);
			resultMapForCohorts.put(pg, resultMapForCohortIds.get(key));
		}
		results.setResultMapForCohorts(resultMapForCohorts);

		
		return results;
	}
	
	/**
	 * 
	  * textInvoke
	  *
	  * @Title: textInvoke
	  * @Description: 调用rservice 并且解析结果 封装结果对象
	  * @param  results
	  * @param  eAtt
	  * @return RResult    返回类型
	  * @throws
	 */
	abstract public RResult testInvoke(DataFrameResults results,TestExtendAtt eAtt,DataFrameInvoke invoke) throws DaException;
	
	/**
	 * 
	  * testInvoke
	  *
	  * @Title: testInvoke
	  * @Description: 调用rservice 并且解析结果 封装结果对象
	  * @param  invoke 
	  * @throws DaException    
	  * @return RResult    返回类型
	  * @throws
	 */
	public RResult testInvoke(DataFrameInvoke invoke) throws DaException{
		DataFrameResults results = this.loadDataFrameResult(invoke);
		return this.testInvoke(results, invoke.getExtAtt(),invoke);
	}
	public RResult testInvoke(DataFrameInvoke invoke,DataFrameResults results) throws DaException{
		return this.testInvoke(results, invoke.getExtAtt(),invoke);
	}
	/**
	 * 
	  * getTestExtendAtt
	  *
	  * @Title: getTestExtendAtt
	  * @Description: 封装扩展测试属性
	  * @param @param request
	  * @param @return    
	  * @return TestExtendAtt    返回类型
	  * @throws
	 */
	abstract public TestExtendAtt getTestExtendAtt(HttpServletRequest request);


}
