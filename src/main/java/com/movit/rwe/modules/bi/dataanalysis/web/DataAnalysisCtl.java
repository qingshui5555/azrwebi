package com.movit.rwe.modules.bi.dataanalysis.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.movit.rwe.common.web.BaseController;
import com.movit.rwe.modules.bi.base.entity.enums.DataAnalysisCheckEnum;
import com.movit.rwe.modules.bi.base.entity.mysql.BmdModelColumnMeta;
import com.movit.rwe.modules.bi.base.entity.mysql.DaDataAnalysisConfig;
import com.movit.rwe.modules.bi.base.entity.mysql.Study;
import com.movit.rwe.modules.bi.base.entity.mysql.Ta;
import com.movit.rwe.modules.bi.dataanalysis.common.DataAnalysisConstants;
import com.movit.rwe.modules.bi.dataanalysis.entity.QueryDataFrameDto;
import com.movit.rwe.modules.bi.dataanalysis.service.BmdModelColumnMetaService;
import com.movit.rwe.modules.bi.dataanalysis.service.BmdModelMetaService;
import com.movit.rwe.modules.bi.dataanalysis.service.DaCohortService;
import com.movit.rwe.modules.bi.dataanalysis.service.DaDataAnalysisConfigService;
import com.movit.rwe.modules.bi.dataanalysis.service.DataAnalysisService;
import com.movit.rwe.modules.bi.dataanalysis.service.RserveClient;
import com.movit.rwe.modules.bi.sys.service.StudyService;
import com.movit.rwe.modules.bi.sys.service.TaService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "${biPath}/dataAnalysis/")
public class DataAnalysisCtl extends BaseController {
	
	public static Logger logger = Logger.getLogger(DataAnalysisCtl.class);
	
	@Autowired
	private TaService taService;
	@Autowired
	private StudyService studyService;
	@Autowired
	private DaCohortService dataAnalysisConhortService;
	@Autowired
	private BmdModelMetaService bmdModelMetaService;
	@Autowired
	private BmdModelColumnMetaService bmdModelColumnMetaService;
	@Autowired
	private DaDataAnalysisConfigService daDataAnalysisConfigService;
	@Autowired
	private DataAnalysisService dataAnalysisService;
	@Autowired
	private RserveClient rserveClient;
	
	@RequestMapping("list")
	public ModelAndView list(HttpServletRequest request,ModelAndView model){
		try{
			List<Ta> taList = taService.findList();
			List conhortList = dataAnalysisConhortService.getAllConhortUnionGroup();
			List modelList = bmdModelMetaService.findAllList(null);
			List approachList = dataAnalysisService.getApproachList(request);
			
			DataAnalysisCheckEnum [] dataAnalysisCheckEnum = 
					DataAnalysisCheckEnum.values();
			JSONArray checkJson = new JSONArray();
//			HashMap checkMap = new HashMap();
			for(DataAnalysisCheckEnum dace:dataAnalysisCheckEnum){
				JSONObject json = new JSONObject();
				json.put("modelColumnNumber", dace.getModelColumnNumber());
				json.put("cohortNumber", dace.getCohortNumber());
				checkJson.add(json);
//				HashMap hm = new HashMap();
//				hm.put("modelColumnNumber", dace.getModelColumnNumber());
//				hm.put("cohortNumber", dace.getCohortNumber());
//				checkMap.put(dace.name(), hm);
			}
			
			model.addObject("taList", taList);
			model.addObject("conhortList",conhortList);
			model.addObject("modelList",modelList);
			model.addObject("approachList",approachList);
			model.addObject("checkJson",checkJson.toString());
			model.setViewName("modules/bi/dataanalysis/dataAnalysisMain");
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e);
		}
		return model;
	}
	
	@RequestMapping("getAnalysisParameterByModelMetaId")
	@ResponseBody
	public List<BmdModelColumnMeta> getAnalysisParameterByModelMetaId(@RequestParam String modelMetaId,ModelAndView model){
		BmdModelColumnMeta bmdModelColumnMeta = new BmdModelColumnMeta();
		bmdModelColumnMeta.setModelMetaId(modelMetaId);
		List<BmdModelColumnMeta> list = bmdModelColumnMetaService.findList(bmdModelColumnMeta);
		return list;
	}
	
	@RequestMapping("getStudyByTaId")
	@ResponseBody
	public List<Study> getStudyByTaId(@RequestParam String taId,ModelAndView model){
		List<Study> list = studyService.findListByTaId(taId);
		return list;
	}
	
	@RequestMapping("getDataFrame")
	@ResponseBody
	public List<Map> getDataFrame(HttpServletRequest request,@RequestParam String taId,@RequestParam String studyId,
			@RequestParam String modelMetaId,@RequestParam(value = "modelColumnMetaArray[]") String[] modelColumnMetaArray,
			@RequestParam(value = "cohortIdArray[]") String[] cohortIdArray,ModelAndView model){
		List<Map> list = null;
		try {
			QueryDataFrameDto generateSqlParams = new QueryDataFrameDto();
			generateSqlParams.setTaId(taId);
			generateSqlParams.setStudyId(studyId);
			generateSqlParams.setModelMetaId(modelMetaId);
			generateSqlParams.setModelColumnMetaIds(modelColumnMetaArray);
			generateSqlParams.setCohortIds(cohortIdArray);
			list = dataAnalysisService.getDataFrame(generateSqlParams);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return list;
	}
	
//	@RequestMapping("changeApproach/{approach}")
//	public ModelAndView changeApproach(@PathVariable("approach") String approach,ModelAndView model){
//		model.setViewName("modules/bi/dataanalysis/"+approach);
//		return model;
//	}
	@RequestMapping("changeApproach")
	public ModelAndView changeApproach(@RequestParam String jspName, @RequestParam String serviceName,
			ModelAndView model) {
		model.addObject("serviceName", serviceName);
		model.setViewName("modules/bi/dataanalysis/" + jspName);
		return model;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("calculate")
	@ResponseBody
	public Map calculate(HttpServletRequest request,HttpServletResponse response,ModelAndView model){
		Map resultMap = new HashMap();
		try{
			Map result = rserveClient.calculate(request);
			//1成功
			resultMap.put(DataAnalysisConstants.DA_MAPKEY_RESULT_CODE,
					DataAnalysisConstants.DA_MAPVALUE_RESULT_CODE_SUCC);
			resultMap.put(DataAnalysisConstants.DA_MAPKEY_RESULT_DESC,
					DataAnalysisConstants.DA_MAPVALUE_RESULT_DESC_SUCC);
			resultMap.put(DataAnalysisConstants.DA_MAPKEY_RESULT_DATA, 
					result);
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e);
			//0失败
			resultMap.put(DataAnalysisConstants.DA_MAPKEY_RESULT_CODE
					,DataAnalysisConstants.DA_MAPVALUE_RESULT_CODE_FAIL);
			resultMap.put(DataAnalysisConstants.DA_MAPKEY_RESULT_DESC,
					e.toString());
		}
		return resultMap;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("addToMyWorkspace")
	@ResponseBody
	public Map addToMyWorkspace(HttpServletRequest request,HttpServletResponse response,ModelAndView model){
		Map resultMap = new HashMap();
		try{
			String json = request.getParameter("json");
			JSONObject jsonObject = JSONObject.fromObject(json);
			String serviceName = (String) jsonObject.get("serviceName");
			String workspaceName = (String) jsonObject.get("workspaceName");
			JSONObject dataFrameParamJson = (JSONObject) jsonObject.get("dataFrameParam");
			String modelMetaId = (String) dataFrameParamJson.get("modelMetaId");
			JSONArray modelColumnMetaArrayJson = (JSONArray) dataFrameParamJson.get("modelColumnMetaArray");
			
			DaDataAnalysisConfig daDataAnalysisConfig = new DaDataAnalysisConfig();
			daDataAnalysisConfig.setModelMetaId(modelMetaId);
			daDataAnalysisConfig.setModelColumnMetaIds(modelColumnMetaArrayJson.toString());
			daDataAnalysisConfig.setAnalysisName(workspaceName);
			daDataAnalysisConfig.setAnalysisType(serviceName);
			daDataAnalysisConfig.setAnalysisConfig(jsonObject.toString());
			
			daDataAnalysisConfigService.save(daDataAnalysisConfig);
			
			//1成功
			resultMap.put(DataAnalysisConstants.DA_MAPKEY_RESULT_CODE,
					DataAnalysisConstants.DA_MAPVALUE_RESULT_CODE_SUCC);
			resultMap.put(DataAnalysisConstants.DA_MAPKEY_RESULT_DESC,
					DataAnalysisConstants.DA_MAPVALUE_RESULT_DESC_SUCC);
			resultMap.put(DataAnalysisConstants.DA_MAPKEY_RESULT_DATA, "");
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e);
			//0失败
			resultMap.put(DataAnalysisConstants.DA_MAPKEY_RESULT_CODE
					,DataAnalysisConstants.DA_MAPVALUE_RESULT_CODE_FAIL);
			resultMap.put(DataAnalysisConstants.DA_MAPKEY_RESULT_DESC,
					e.toString());
		}
		return resultMap;
	}
}
