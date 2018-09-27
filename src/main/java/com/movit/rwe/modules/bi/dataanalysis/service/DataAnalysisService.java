package com.movit.rwe.modules.bi.dataanalysis.service;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movit.rwe.common.service.CrudService;
import com.movit.rwe.modules.bi.base.dao.hive.DataAnalysisDao;
import com.movit.rwe.modules.bi.base.dao.mysql.BmdModelColumnMetaDao;
import com.movit.rwe.modules.bi.base.dao.mysql.BmdModelMetaDao;
import com.movit.rwe.modules.bi.base.dao.mysql.CohortDao;
import com.movit.rwe.modules.bi.base.entity.mysql.BmdModelMeta;
import com.movit.rwe.modules.bi.base.entity.mysql.Cohort;
import com.movit.rwe.modules.bi.dataanalysis.common.DataAnalysisConstants;
import com.movit.rwe.modules.bi.dataanalysis.common.DataAnalysisEnumInterface;
import com.movit.rwe.modules.bi.dataanalysis.entity.QueryDataFrameDto;

@Service
@Transactional
public class DataAnalysisService extends CrudService<CohortDao, Cohort> {
	
	public static Logger logger = Logger.getLogger(DataAnalysisService.class);
	
	@Autowired
	private BmdModelMetaDao bmdModelMetaDao;
	@Autowired
	private BmdModelColumnMetaDao bmdModelColumnMetaDao;
	@Autowired
	private DataAnalysisDao dataAnalysisDao;
	
	/**
	 * 通过request获取前台参数
	 * @param request
	 * @return
	 */
	public QueryDataFrameDto getDataFrameParam(HttpServletRequest request){
		//获取查询data frame参数
		//域ID
		String taId = request.getParameter("dataFrameParam[taId]");
		//研究ID
		String studyId = request.getParameter("dataFrameParam[studyId]");
		//模型ID
		String modelMetaId = request.getParameter("dataFrameParam[modelMetaId]");
		//模型列ID
		String [] modelColumnMetaArray = request.getParameterValues("dataFrameParam[modelColumnMetaArray][]");
		//组ID
		String [] cohortIdArray = request.getParameterValues("dataFrameParam[cohortIdArray][]");
		//组名
		String [] cohortNameArray = request.getParameterValues("dataFrameParam[cohortNameArray][]");
		//模型列名
		String [] modelColumnNameArray = request.getParameterValues("dataFrameParam[modelColumnNameArray][]");
		
		QueryDataFrameDto queryDataFrameDto = new QueryDataFrameDto();
		queryDataFrameDto.setTaId(taId);
		queryDataFrameDto.setStudyId(studyId);
		queryDataFrameDto.setModelMetaId(modelMetaId);
		queryDataFrameDto.setModelColumnMetaIds(modelColumnMetaArray);
		queryDataFrameDto.setCohortIds(cohortIdArray);
		queryDataFrameDto.setCohortNames(cohortNameArray);
		queryDataFrameDto.setModelColumnNames(modelColumnNameArray);
		return queryDataFrameDto;
	}
	
	/**
	 * 查询data frame数据
	 * @param queryDataFrameDto
	 * @return
	 * @throws Exception
	 */
	public List<Map> getDataFrame(QueryDataFrameDto queryDataFrameDto) throws Exception{
		//获取模型数据
		BmdModelMeta bmdModelMeta = bmdModelMetaDao.get(queryDataFrameDto.getModelMetaId());
				
		//拼接具体的查询参数groupids like '%C_169,%' or groupids like '%C_69%'
		StringBuffer cohortIds = new StringBuffer();
		cohortIds.append("(");
		for(String cohortId:queryDataFrameDto.getCohortIds()){
			cohortIds.append(" groupids like '%").append(cohortId).append(",%' or ");
		}
		String cohortIdsStr = cohortIds.substring(0, cohortIds.lastIndexOf("or"));
		cohortIdsStr = cohortIdsStr +")";
		//得到SQL模板
		String querySql = bmdModelMeta.getQuerySql();
		//将sql中的占位符替换为具体参数
		String resultSql = querySql.replace(DataAnalysisConstants.DA_SQLTAG_TA_ID, queryDataFrameDto.getTaId())
				.replace(DataAnalysisConstants.DA_SQLTAG_STUDY_ID, queryDataFrameDto.getStudyId())
				.replace(DataAnalysisConstants.DA_SQLTAG_GROUP_IDS, cohortIdsStr);
		
		//查询data frame数据
		logger.info("执行SQL为："+resultSql);
		List<Map> dataFrameList =  dataAnalysisDao.executeSql(resultSql);
		return dataFrameList;
	}
	
	/**
	 * main页面获取所有的分析方法
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,String>> getApproachList(HttpServletRequest request) throws Exception{
		//返回结果
		List <Map<String,String>> approachList = new ArrayList<Map<String,String>>();
		//枚举包名
		String dataAnalysisEnumpackageUrl = DataAnalysisConstants.DA_ENUM_PACKAGE_NAME.replace(".", "/");
		//将报名改为具体路径
		String filePath = this.getClass().getResource("/").getPath()+ dataAnalysisEnumpackageUrl;
		File file = new File(filePath);
        File[] fileArray = file.listFiles();
        //循环获取枚举名
        for(File childFile:fileArray){
        	Map<String,String> map = new HashMap<String,String>();
        	String childFileName = childFile.getName();
        	String childClassName = childFileName.replace(".class", "");
        	//加载class
        	Class clazz = Class.forName(DataAnalysisConstants.DA_ENUM_PACKAGE_NAME+"."+childClassName);
        	//获取枚举中jspName中的值
        	Field jspNameField = null;
        	try{
        		jspNameField = clazz.getField(DataAnalysisEnumInterface.JSP_NAME);
        	}catch(NoSuchFieldException e){
        		logger.info("枚举["+childClassName+"]中没有找到jspName枚举属性，不作为分析方法进行调用！");
        	}
        	DataAnalysisEnumInterface jspNameEnum = (DataAnalysisEnumInterface)jspNameField.get(jspNameField);
        	map.put(DataAnalysisConstants.DA_MAPKEY_JSP_NAME_DESCRIPTION, jspNameEnum.getDescription());
        	map.put(DataAnalysisConstants.DA_MAPKEY_JSP_NAME_VALUE, jspNameEnum.getValue());
        	
        	//获取枚举中serviceName中的值
        	Field serviceNameField = null;
        	try{
        		serviceNameField = clazz.getField(DataAnalysisEnumInterface.SERVICE_NAME);
        	}catch(NoSuchFieldException e){
        		logger.info("枚举["+childClassName+"]中没有找到serviceName枚举属性，不作为分析方法进行调用！");
        	}
        	DataAnalysisEnumInterface serviceNameEnum = (DataAnalysisEnumInterface)serviceNameField.get(serviceNameField);
        	map.put(DataAnalysisConstants.DA_MAPKEY_SERVICE_NAME_VALUE, serviceNameEnum.getValue());
        	
        	approachList.add(map);
        }
        return approachList;
	}
}
