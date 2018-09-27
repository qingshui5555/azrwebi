package com.movit.rwe.modules.bi.da.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movit.rwe.common.utils.I18NUtils;
import com.movit.rwe.modules.bi.base.dao.mysql.BmdModelColumnMetaDao;
import com.movit.rwe.modules.bi.base.dao.mysql.BmdModelMetaDao;
import com.movit.rwe.modules.bi.base.dao.mysql.DaTestModelDao;
import com.movit.rwe.modules.bi.base.dao.mysql.PatientGroupDao;
import com.movit.rwe.modules.bi.base.entity.enums.PatientGroupType;
import com.movit.rwe.modules.bi.base.entity.mysql.BmdModelColumnMeta;
import com.movit.rwe.modules.bi.base.entity.mysql.BmdModelMeta;
import com.movit.rwe.modules.bi.base.entity.mysql.DaTestModel;
import com.movit.rwe.modules.bi.base.entity.mysql.PatientGroup;
import com.movit.rwe.modules.bi.da.enums.DataTypeEnum;
import com.movit.rwe.modules.bi.da.test.dataframe.DataFrameGroup;
import com.movit.rwe.modules.bi.da.test.dataframe.DataFrameHandleResult;
import com.movit.rwe.modules.bi.da.test.dataframe.DataFrameInvoke;
import com.movit.rwe.modules.bi.da.test.dataframe.DataFrameResult;
import com.movit.rwe.modules.bi.da.test.dataframe.DataFrameResultData;
import com.movit.rwe.modules.bi.da.test.dataframe.DataFrameResults;
import com.movit.rwe.modules.bi.da.test.model.TestModelInvoke;
import com.movit.rwe.modules.bi.da.test.rresult.DaException;
import com.movit.rwe.modules.bi.da.test.set.DataModelParamChosen;
import com.movit.rwe.modules.bi.dataanalysis.util.RserveUtil;

/**
 * 
  * @ClassName: TestHelper
  * @Description: 测试方法的一些帮助类，主要是做转型、分装
  * @author wade.chen@movit-tech.com
  * @date 2016年5月23日 上午10:38:45
  *
 */
@Service 
public class TestHelper {
	
	@Autowired
	BmdModelMetaDao bmdModelMetaDao;
	
	@Autowired
	BmdModelColumnMetaDao bmdModelColumnMetaDao;
	
	@Autowired
	PatientGroupDao patientGroupDao;
	
	@Autowired
	DaTestModelDao daTestModelDao;
	
	@Autowired
	private I18NUtils i18NUtils;
	
	private static final String TA_ID = "taId";
	
	private static final String TEST_NAME = "testName";
	
	private static final String STUDY_ID = "studyId";
	
	private static final String TEST_MODEL_ID = "testModelId";
	
	private static final String MODEL_META_ID = "modelMetaId";
	
	private static final String MODEL_COLUMN_IDS = "modelColumnIds[]";

	private static final String COHORT_IDS = "cohortIds[]";
	
	private static final String GROUP_IDS = "groupIds[]";
	
	private static final String FORMULA = "formula";
	
	private static final String FORMULA_SYMBOL = "formulaSymbol[]";
//	var invokeParams={
//			taId:'',
//			testName:'',
//			studyId:'',
//			modelMetaId:'',
//			modelColumnIds:[],
//			cohortIds:[],
//			groupIds:[],
//			extAtt:{}
//			
//	}
	
	
	public TestModelInvoke toTestModelInvoke(HttpServletRequest request){
		TestModelInvoke tmi = new TestModelInvoke();
		
		DataFrameInvoke invoke = this.toDataFrameInvoke(request);
		tmi.setDataFtameInvoke(invoke);
		
		String name = request.getParameter("daTestModelName");
		String remarks = request.getParameter("daTestModelRemarks");
		String daTestModelId = request.getParameter("daTestModelId");
		
		tmi.setName(name);
		tmi.setRemarks(remarks);
		tmi.setDaTestModelId(daTestModelId);
		
		return tmi;
	}
	
	/**
	 * 
	  * toDataFrameInvoke
	  * @Title: toDataFrameInvoke
	  * @Description: dataFrame
	  * @param @param request
	  * @param @return    
	  * @return DataFrameInvoke    返回类型
	  * @throws
	 */
	public DataFrameInvoke toDataFrameInvoke(HttpServletRequest request){
		DataFrameInvoke invoke = new DataFrameInvoke();
		String taId = request.getParameter(TestHelper.TA_ID);
		String testName = request.getParameter(TestHelper.TEST_NAME);
		String studyId = request.getParameter(TestHelper.STUDY_ID);
		String modelMetaId = request.getParameter(TestHelper.MODEL_META_ID);
		BmdModelMeta bmdModelMeta =  bmdModelMetaDao.get(modelMetaId);
		String testModelId = request.getParameter(TestHelper.TEST_MODEL_ID);
		
		invoke.setTaId(taId);
		invoke.setTestName(testName);
		invoke.setStudyId(studyId);
		invoke.setModelMeta(bmdModelMeta);
		invoke.setTestModelId(testModelId);
		
		if(!StringUtils.isEmpty(invoke.getTestModelId())){
			DaTestModel dtm = daTestModelDao.get(invoke.getTestModelId());
			invoke.setTestModelName(dtm.getModelName());
		}
		
		String[] modelColumnIds = request.getParameterValues(TestHelper.MODEL_COLUMN_IDS);
		String[] cohorts = request.getParameterValues(TestHelper.COHORT_IDS);
		Long[] cohortIds = new Long[cohorts.length];
		for(int i=0; i<cohortIds.length; i++){
			cohortIds[i] = Long.parseLong(cohorts[i]);
		}
		String[] groupIds = request.getParameterValues(TestHelper.GROUP_IDS);
		
		invoke.getConhortChosen().setCohortIds(cohortIds);
		invoke.getConhortChosen().setGroups(groupIds);
		
		invoke.getConhortChosen().setCohortsObj(new PatientGroup[cohortIds==null?0:cohortIds.length]);
		invoke.getConhortChosen().setGroupsObj(new PatientGroup[groupIds==null?0:groupIds.length]);
		
		for(int i=0;cohortIds!=null&&i<cohortIds.length;i++){
			Long cohortId=cohortIds[i];
			PatientGroup pg = patientGroupDao.loadByTypeAndId(cohortId+"", 1);
			invoke.getConhortChosen().getCohortsObj()[i]=pg;
		}
		
		for(int i=0;groupIds!=null&&i<groupIds.length;i++){
			String groupId=groupIds[i];
			PatientGroup pg = patientGroupDao.loadByTypeAndId(groupId, 0);
			invoke.getConhortChosen().getGroupsObj()[i]=pg;
		}
		
		for(String modelColumnId:modelColumnIds){
//			BmdModelColumnMeta mcm = bmdModelColumnMetaDao.get(modelColumnId);
//			DataModelParamChosen param = new DataModelParamChosen(mcm);
//			invoke.getChosenParams().add(param);
			//支持分组的逻辑
			BmdModelColumnMeta mcm = null;
			if("group_column_".equals(modelColumnId)){
				mcm = new BmdModelColumnMeta();
				mcm.setId("group_column_");
				mcm.setModelColumnName("group_column_");
				mcm.setModelColumnNameLab("Group");
				mcm.setModelColumnType("text");
			}else{
				mcm = bmdModelColumnMetaDao.get(modelColumnId);
			}
			DataModelParamChosen param = new DataModelParamChosen(mcm);
			invoke.getChosenParams().add(param);
		}
		
		String formula = request.getParameter(TestHelper.FORMULA);
		invoke.setFormula(formula);
		String [] formulaSymbol = request.getParameterValues(TestHelper.FORMULA_SYMBOL);
		invoke.setFormulaSymbol(formulaSymbol);
		
		return invoke;
	}
	
	public PatientGroup loadPatientGroup(String id,PatientGroupType type){
		return patientGroupDao.loadByTypeAndId(id, type.getCode());
	}
	
	public PatientGroup loadPatientGroup(String id,int patientGroupType){ 
		return patientGroupDao.loadByTypeAndId(id, patientGroupType);
	}
	
	
	public DataFrameHandleResult handleTestParam(DataFrameResults results,String type) throws DaException{
		if(results==null){
			throw new DaException(i18NUtils.get("da.index.analysisparamterfromsampledata")+" "+i18NUtils.get("da.exception.mustbemorethan"),"",null);
		}
		List <DataModelParamChosen> dataModelParamChosenList= results.getDataModelParamChosenList();
		Map<PatientGroup, List<DataFrameResult>> resultMapForCohorts = results.getResultMapForCohorts();

		String [] modelColumnNameArray = new String[dataModelParamChosenList.size()];
		DataFrameHandleResult dataFrameHandleResult = new DataFrameHandleResult();
		
		//循环取列名及值
		for(int i=0;i<dataModelParamChosenList.size();i++){
			List <Object>valueList = new ArrayList<Object>();
			DataModelParamChosen dataModelParamChosen = dataModelParamChosenList.get(i);
			//获取列名,放入数组,统一转为小写，impala返回字段都为小写需要与它对应
			//获取列名,放入数组,统一去除空格，Rserve中列名不能使用空格
			//modelColumnNameArray[i] = dataModelParamChosen.getModelColumnName();
			modelColumnNameArray[i] = dataModelParamChosen.getModelColumnName()
					.toLowerCase().replace(" ", "");
			
			//获取值,放入数组中
			for (Map.Entry<PatientGroup, List<DataFrameResult>> entry : resultMapForCohorts.entrySet()) {
				PatientGroup patientGroup = entry.getKey();
				List<DataFrameResult> dataFrameResultList = entry.getValue();
				for(DataFrameResult dfr:dataFrameResultList){
					List<DataFrameResultData> paramDataList = dfr.getParamDataList();
					//获取列索引对应的具体值
					DataFrameResultData dataFrameResultData = paramDataList.get(i);
					Object dataValueObject = dataFrameResultData.getDataValue();
					valueList.add(dataValueObject);
				}
			}
			Object [] objectArray = valueList.toArray();
			
			if("double".equals(type)){
				double [] doubleArray = RserveUtil.objectArrayToDoubleArray(objectArray);
				dataFrameHandleResult.getDoubleDataList().add(doubleArray);
			}else if("String".equals(type)){
				String [] stringArray = RserveUtil.objectArrayToStringArray(objectArray);
				dataFrameHandleResult.getStringDataList().add(stringArray);
			}
			
		}
		
		dataFrameHandleResult.setModelColumnNameArray(modelColumnNameArray);
		
		return dataFrameHandleResult;
	}
	
	public List<DataFrameGroup> handleGroupTestParam(DataFrameResults results) throws DaException{
		if(results==null){
			throw new DaException(i18NUtils.get("da.index.analysisparamterfromsampledata")+" "+i18NUtils.get("da.exception.cannotbenull"),"",null);
		}
		List<DataFrameGroup> handleResult = new ArrayList<DataFrameGroup>();
		List <DataModelParamChosen> dataModelParamChosenList= results.getDataModelParamChosenList();
		Map<PatientGroup, List<DataFrameResult>> resultMapForCohorts = results.getResultMapForCohorts();

		String [] modelColumnNameArray = new String[dataModelParamChosenList.size()];
		DataFrameHandleResult dataFrameHandleResult = new DataFrameHandleResult();
		
		//循环取列名及值
		for(int i=0;i<dataModelParamChosenList.size();i++){
			List <Object>valueList = new ArrayList<Object>();
			DataFrameGroup dataFrameGroup = new DataFrameGroup();
			DataModelParamChosen dataModelParamChosen = dataModelParamChosenList.get(i);
			//获取列名,放入数组,统一转为小写，impala返回字段都为小写需要与它对应
			//获取列名,放入数组,统一去除空格，Rserve中列名不能使用空格
			//modelColumnNameArray[i] = dataModelParamChosen.getModelColumnName();
			modelColumnNameArray[i] = dataModelParamChosen.getModelColumnName()
					.toLowerCase().replace(" ", "");
			//dataFrameGroup.setColumn(modelColumnNameArray[i]);
			dataFrameGroup.setColumn(dataModelParamChosen.getModelColumnName());
			//获取值,放入数组中
			for (Map.Entry<PatientGroup, List<DataFrameResult>> entry : resultMapForCohorts.entrySet()) {
				PatientGroup patientGroup = entry.getKey();
				List<DataFrameResult> dataFrameResultList = entry.getValue();
				for(DataFrameResult dfr:dataFrameResultList){
					//如果该列是组，则取组名为值
					if("group_column_".equals(modelColumnNameArray[i])){
						valueList.add(patientGroup.getGroupName());
						dataFrameGroup.setDataType(DataTypeEnum.Text);
					}else{
						List<DataFrameResultData> paramDataList = dfr.getParamDataList();
						//获取列索引对应的具体值，即获取每个病人下对应的不同顺序的值
						DataFrameResultData dataFrameResultData = paramDataList.get(i);
						Object dataValueObject = dataFrameResultData.getDataValue();
						valueList.add(dataValueObject);
						dataFrameGroup.setDataType(dataFrameResultData.getDataType());
					}
				}
			}
			
			Object [] objectArray = valueList.toArray();
			dataFrameGroup.setValueArray(objectArray);
			handleResult.add(dataFrameGroup);
		}
		
		return handleResult;
	}
//	public List<DataFrameGroup> handleGroupTestParam(DataFrameResults results) throws DaException{
//		if(results==null){
//			throw new DaException(i18NUtils.get("da.index.analysisparamterfromsampledata")+" "+i18NUtils.get("da.exception.cannotbenull"),"",null);
//		}
//		List<DataFrameGroup> handleResult = new ArrayList<DataFrameGroup>();
//		List <DataModelParamChosen> dataModelParamChosenList= results.getDataModelParamChosenList();
//		Map<PatientGroup, List<DataFrameResult>> resultMapForCohorts = results.getResultMapForCohorts();
//
//		String [] modelColumnNameArray = new String[dataModelParamChosenList.size()];
//		DataFrameHandleResult dataFrameHandleResult = new DataFrameHandleResult();
//		
//		//循环取列名及值
//		for(int i=0;i<dataModelParamChosenList.size();i++){
//			List <Object>valueList = new ArrayList<Object>();
//			DataFrameGroup dataFrameGroup = new DataFrameGroup();
//			DataModelParamChosen dataModelParamChosen = dataModelParamChosenList.get(i);
//			//获取列名,放入数组,统一转为小写，impala返回字段都为小写需要与它对应
//			//获取列名,放入数组,统一去除空格，Rserve中列名不能使用空格
//			//modelColumnNameArray[i] = dataModelParamChosen.getModelColumnName();
//			modelColumnNameArray[i] = dataModelParamChosen.getModelColumnName()
//					.toLowerCase().replace(" ", "");
//			dataFrameGroup.setColumn(modelColumnNameArray[i]);
//			//获取值,放入数组中
//			for (Map.Entry<PatientGroup, List<DataFrameResult>> entry : resultMapForCohorts.entrySet()) {
//				PatientGroup patientGroup = entry.getKey();
//				List<DataFrameResult> dataFrameResultList = entry.getValue();
//				for(DataFrameResult dfr:dataFrameResultList){
//					List<DataFrameResultData> paramDataList = dfr.getParamDataList();
//					//获取列索引对应的具体值，即获取每个病人下对应的不同顺序的值
//					DataFrameResultData dataFrameResultData = paramDataList.get(i);
//					Object dataValueObject = dataFrameResultData.getDataValue();
//					valueList.add(dataValueObject);
//					dataFrameGroup.setDataType(dataFrameResultData.getDataType());
//				}
//			}
//			
//			Object [] objectArray = valueList.toArray();
//			dataFrameGroup.setValueArray(objectArray);
//			handleResult.add(dataFrameGroup);
//		}
//		
//		return handleResult;
//	}
	
	public List <double[]> handleSimpleTestParam(DataFrameResults results) throws DaException{
		if(results==null){
			throw new DaException(i18NUtils.get("da.index.analysisparamterfromsampledata")+" "+i18NUtils.get("da.exception.cannotbenull"),"",null);
		}
		List <DataModelParamChosen> dataModelParamChosenList= results.getDataModelParamChosenList();
		Map<PatientGroup, List<DataFrameResult>> resultMapForCohorts = results.getResultMapForCohorts();
		//数据向量列表
		List <double[]>dataList = new ArrayList<double[]>();
		
		String [] modelColumnNameArray = new String[dataModelParamChosenList.size()];
		
		DataModelParamChosen dataModelParamChosen = dataModelParamChosenList.get(0);
//		//获取值,放入数组中
		for (Map.Entry<PatientGroup, List<DataFrameResult>> entry : resultMapForCohorts.entrySet()) {
			PatientGroup patientGroup = entry.getKey();
			List<DataFrameResult> dataFrameResultList = entry.getValue();
			List <Object>everyGroupData = new ArrayList<Object>();
			for(DataFrameResult dfr:dataFrameResultList){
				List<DataFrameResultData> paramDataList = dfr.getParamDataList();
				//获取列索引对应的具体值
				for(DataFrameResultData dfrd:paramDataList){
					everyGroupData.add(dfrd.getDataValue());
				}
			}
			Object[] everyGroupDataArray = everyGroupData.toArray();
			double [] doubleArray = RserveUtil.objectArrayToDoubleArray(everyGroupDataArray);
			dataList.add(doubleArray);
		}
		return dataList;
	}
}
