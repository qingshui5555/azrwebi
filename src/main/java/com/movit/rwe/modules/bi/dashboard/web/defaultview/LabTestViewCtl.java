package com.movit.rwe.modules.bi.dashboard.web.defaultview;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.movit.rwe.common.config.GlobalConstant;
import com.movit.rwe.modules.bi.base.entity.mysql.TabView;
import com.movit.rwe.modules.bi.base.entity.vo.HiveLabTestCountVo;
import com.movit.rwe.modules.bi.base.entity.vo.HiveResultVo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.movit.rwe.common.utils.CookieUtils;
import com.movit.rwe.common.utils.I18NUtils;
import com.movit.rwe.modules.bi.dashboard.common.ViewInvokeParams;
import com.movit.rwe.modules.bi.dashboard.service.LabTestService;
import com.movit.rwe.modules.bi.dashboard.vo.ConfigVo;

import net.sf.json.JSONArray;

@Controller
@RequestMapping(value = "${biPath}/dfView/labTest")
public class LabTestViewCtl {

	private static Logger logger = LoggerFactory.getLogger(LabTestViewCtl.class);

	@Autowired
	private LabTestService labTestService;

	@Autowired
	private I18NUtils i18NUtils;

	@RequestMapping("config")
	public ModelAndView config(ViewInvokeParams viewInvokeParams) {
		String studyId = viewInvokeParams.getStudyId();
		String tabViewId = viewInvokeParams.getTabViewId();
		logger.info("@@@In LabTestViewCtl.config begin: studyId=" + studyId + ", tabViewId=" + tabViewId);

		TabView tabView = labTestService.queryTabViewByTabViewId(tabViewId);
		String configJson = "";
		String viewChartHeight = "400";
		if(tabView != null && !StringUtils.isBlank(tabView.getViewChartHeight())){
			configJson = tabView.getConfigJson();
			viewChartHeight = tabView.getViewChartHeight();
		}
		List<ConfigVo> configVoList = null;
		Integer begin = 1;
		String unit = null;
		if(!StringUtils.isEmpty(configJson)&&!"{}".equals(configJson)) {
			JSONArray jsonArr = JSONArray.fromObject(configJson);
			unit = jsonArr.getJSONObject(0).get("unit").toString();
			configVoList = labTestService.queryListConfigVo(JSONArray.fromObject(jsonArr.getJSONObject(1)));
			ConfigVo vo = configVoList.get(configVoList.size() - 1);
			begin = vo.getOrder() + 1;
		}

		ModelAndView mav = new ModelAndView("modules/bi/dashboard/dfView/labTestConfig");
		mav.addObject(GlobalConstant.STR_ATTR_UNIT, unit);
		mav.addObject(GlobalConstant.STR_ATTR_LAB_TEST_CONFIG_LIST, labTestService.queryListLabTestConfig(studyId, begin, configVoList));
		mav.addObject(GlobalConstant.STR_ATTR_CONFIG_VO_LIST, configVoList);
		mav.addObject(GlobalConstant.STR_ATTR_VIEW_CHART_HEIGHT, viewChartHeight);

		logger.info("@@@In LabTestViewCtl.config end");
		return mav;
	}

//	@RequestMapping("/index")
//	public ModelAndView showChartById(ViewInvokeParams viewInvokeParams, HttpServletRequest request) {
//		String studyId = viewInvokeParams.getStudyId();
//		String tabViewId = viewInvokeParams.getTabViewId();
//		Boolean allPatientsFlag = viewInvokeParams.getAllPatientsFlag();
//		String cohortIdStr = StringUtils.join(viewInvokeParams.getCohorts(), GlobalConstant.STR_SPLIT);
//		logger.info("@@@In LabTestViewCtl.showChartById begin: studyId=" + studyId + ", tabViewId=" + tabViewId);
//
//		TabView tabView = labTestService.queryTabViewByTabViewId(tabViewId);
//		String viewChartHeight = "400";
//		if(tabView != null && !StringUtils.isBlank(tabView.getViewChartHeight())){
//			viewChartHeight = tabView.getViewChartHeight();
//		}
//
//		String configJson = "{}";
//		if(tabView != null){
//			configJson = tabView.getConfigJson();
//		}
//
//		String style = CookieUtils.getCookie(request, "_change_style");
//		if(StringUtils.isEmpty(style)){
//			style = "white";
//		}
//
//		ModelAndView mav = new ModelAndView("modules/bi/dashboard/dfView/labTestView");
//		mav.addObject(GlobalConstant.STR_ATTR_COHORT_IDS, cohortIdStr);
//		mav.addObject(GlobalConstant.STR_ATTR_ALL_PATIENTS_FLAG, allPatientsFlag);
//		mav.addObject(GlobalConstant.STR_ATTR_CONFIG_JSON, configJson);
//		mav.addObject(GlobalConstant.STR_ATTR_LAB_TEST_INDICATOR_MAP, labTestService.queryIndicatorMap(configJson));
//		mav.addObject(GlobalConstant.STR_ATTR_VIEW_CHART_HEIGHT, viewChartHeight);
//		mav.addObject(GlobalConstant.STR_ATTR_STUDY_ID, studyId);
//		mav.addObject(GlobalConstant.STR_ATTR_STYLE, style);
//
//		logger.info("@@@In LabTestViewCtl.showChartById end");
//		return mav;
//	}
	
	@RequestMapping("/getData")
	@ResponseBody
	public Map<String, Object> getLabTestData(ViewInvokeParams viewInvokeParams) {
		String studyId = viewInvokeParams.getStudyId();
		String indicator = viewInvokeParams.getIndicator();
		String configJson = viewInvokeParams.getConfigJson();
		logger.info("@@@In LabTestViewCtl.getLabTestData begin: studyId=" + studyId + ", indicator=" + indicator);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		String[] cohortIds = null;
		if(!viewInvokeParams.getAllPatientsFlag() && !StringUtils.isBlank(viewInvokeParams.getCohortIds())){
			String[] cohorts = viewInvokeParams.getCohortIds().split(GlobalConstant.STR_SPLIT);
			cohortIds = new String[cohorts.length];
			for(int i=0; i<cohortIds.length; i++){
				cohortIds[i] = cohorts[i];
			}
		}

		Map<String,String> map = labTestService.queryMapUpperAndLower(configJson, indicator);
		String unitX = map.get("unit");
		String lowerRef = map.get("lowerRef");
		String upperRef = map.get("upperRef");
		String resultUnit = map.get("resultUnit");
		String indicatorDes = "";
		List<HiveResultVo> dataList = labTestService.queryListLabTestData(studyId, indicator, resultUnit, cohortIds);
		if(dataList!=null && dataList.size()>0) {
			if(StringUtils.isEmpty(unitX) || "labtest.day".equals(unitX)) {
				labTestService.loadLabTestDataByDay(dataList, resultMap);
			} else {
				labTestService.loadLabTestDataByWeek(dataList, resultMap);
			}
			indicatorDes = dataList.get(0).getDescription();
		} else {
			resultMap.put("x", new ArrayList<String>());
			resultMap.put("y", new ArrayList<Object>());
			resultMap.put("tooltipList", new ArrayList<String>());
			resultMap.put("maxX", 0);
		}
		resultMap.put("indicator", indicator);
		resultMap.put("indicatorDes", indicatorDes);
		resultMap.put("lowerRef", lowerRef);
		resultMap.put("upperRef", upperRef);
		resultMap.put("unitX", i18NUtils.get(unitX));
		resultMap.put("resultUnit", resultUnit);

		logger.info("@@@In LabTestViewCtl.getLabTestData end");
		return resultMap;
	}

	@RequestMapping("/index")
	public ModelAndView showChartById(ViewInvokeParams viewInvokeParams, HttpServletRequest request) {
		String studyId = viewInvokeParams.getStudyId();
		String tabViewId = viewInvokeParams.getTabViewId();
		Boolean allPatientsFlag = viewInvokeParams.getAllPatientsFlag();
		String [] cohortIds = viewInvokeParams.getCohorts();
		logger.info("@@@In LabTestViewCtl.showChartById begin: studyId=" + studyId + ", tabViewId=" + tabViewId);

		TabView tabView = labTestService.queryTabViewByTabViewId(tabViewId);
		String viewChartHeight = "400";
		if(tabView != null && !StringUtils.isBlank(tabView.getViewChartHeight())){
			viewChartHeight = tabView.getViewChartHeight();
		}

		String configJson = "{}";
		if(tabView != null){
			configJson = tabView.getConfigJson();
		}

		String style = CookieUtils.getCookie(request, "_change_style");
		if(StringUtils.isEmpty(style)){
			style = "white";
		}
		Map<String, String> map = labTestService.queryIndicatorMap(configJson);
		String[] indicators = new String[map.size()];
		int i = 0;
		for(Map.Entry<String, String> entry : map.entrySet()){
			indicators[i++] = entry.getKey();
		}
		Integer patientCount = labTestService.countPatient(studyId, cohortIds, allPatientsFlag);
		List<HiveLabTestCountVo> voList = labTestService.queryListLabTestTableData(studyId, cohortIds, indicators, patientCount);
		Map<String, String> indicatorMap = new LinkedHashMap<String, String>();
		for(HiveLabTestCountVo vo : voList){
			indicatorMap.put(vo.getIndicator(), vo.getDescription());
		}
		ModelAndView mav = new ModelAndView("modules/bi/dashboard/dfView/labTestTableView");
		mav.addObject(GlobalConstant.STR_ATTR_COHORT_IDS, StringUtils.join(cohortIds, GlobalConstant.STR_SPLIT));
		mav.addObject(GlobalConstant.STR_ATTR_ALL_PATIENTS_FLAG, allPatientsFlag);
		mav.addObject(GlobalConstant.STR_ATTR_CONFIG_JSON, configJson);
		mav.addObject(GlobalConstant.STR_ATTR_LAB_TEST_INDICATOR_MAP, indicatorMap);
		mav.addObject(GlobalConstant.STR_ATTR_LAB_TEST_TABLE_LIST, voList);
		mav.addObject(GlobalConstant.STR_ATTR_VIEW_CHART_HEIGHT, viewChartHeight);
		mav.addObject(GlobalConstant.STR_ATTR_STUDY_ID, studyId);
		mav.addObject(GlobalConstant.STR_ATTR_STYLE, style);



		logger.info("@@@In LabTestViewCtl.showChartById end");
		return mav;
	}
}
