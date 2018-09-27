package com.movit.rwe.modules.bi.dashboard.web.defaultview;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.movit.rwe.common.config.GlobalConstant;
import com.movit.rwe.modules.bi.base.entity.mysql.TabView;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.movit.rwe.common.utils.CookieUtils;
import com.movit.rwe.modules.bi.dashboard.common.ViewInvokeParams;
import com.movit.rwe.modules.bi.dashboard.service.TreatmentJourneyService;

@Controller
@RequestMapping("${biPath}/dfView/treatmentJourney")
public class TreatmentJourneyViewCtl {

	private static Logger logger = LoggerFactory.getLogger(TreatmentJourneyViewCtl.class);

	@Autowired
	TreatmentJourneyService treatmentJourneyService;

	@RequestMapping("/index")
	public ModelAndView showChartById(ViewInvokeParams viewInvokeParams, HttpServletRequest request) {
		String studyId = viewInvokeParams.getStudyId();
		String tabViewId = viewInvokeParams.getTabViewId();
		Boolean allPatientsFlag = viewInvokeParams.getAllPatientsFlag();
		String cohortIdStr = StringUtils.join(viewInvokeParams.getCohorts(), GlobalConstant.STR_SPLIT);
		logger.info("@@@In TreatmentJourneyViewCtl.showChartById begin: studyId=" + studyId + ", tabViewId=" + tabViewId);

		TabView tabView = treatmentJourneyService.queryTabViewByTabViewId(tabViewId);
		String viewChartHeight = "400";
		if(tabView != null && !StringUtils.isBlank(tabView.getViewChartHeight())){
			viewChartHeight = tabView.getViewChartHeight();
		}

		String style = CookieUtils.getCookie(request, "_change_style");
		if(StringUtils.isEmpty(style)){
			style = "white";
		}

		ModelAndView mav = new ModelAndView("modules/bi/dashboard/dfView/treatmentJourneyView");
		mav.addObject(GlobalConstant.STR_ATTR_COHORT_IDS, cohortIdStr);
		mav.addObject(GlobalConstant.STR_ATTR_ALL_PATIENTS_FLAG, allPatientsFlag);
		mav.addObject(GlobalConstant.STR_ATTR_VIEW_CHART_HEIGHT, viewChartHeight);
		mav.addObject(GlobalConstant.STR_ATTR_STUDY_ID, studyId);
		mav.addObject(GlobalConstant.STR_ATTR_STYLE, style);

		logger.info("@@@In TreatmentJourneyViewCtl.showChartById end");
		return mav;
	}

	@RequestMapping("/getData")
	@ResponseBody
	public List<Map<String, Object>> getData(ViewInvokeParams viewInvokeParams) {
		String studyId = viewInvokeParams.getStudyId();
		String[] cohortIds = null;
		if(!StringUtils.isBlank(viewInvokeParams.getCohortIds())){
			cohortIds = viewInvokeParams.getCohortIds().split(GlobalConstant.STR_SPLIT);
		}
		logger.info("@@@In TreatmentJourneyViewCtl.getData begin: studyId=" + studyId + ", cohort=" + cohortIds);
		List<Map<String, Object>> resultList = treatmentJourneyService.queryListAllTreatmentJourney(studyId, cohortIds);
		logger.info("@@@In TreatmentJourneyViewCtl.getData end");
		return resultList;
	}

	@RequestMapping("/getFirstLevelData")
	@ResponseBody
	public Map<String, Object> getFirstLevelData(ViewInvokeParams viewInvokeParams) {
		String studyId = viewInvokeParams.getStudyId();
		String[] cohortIds = null;
		if(!StringUtils.isBlank(viewInvokeParams.getCohortIds())){
			cohortIds = viewInvokeParams.getCohortIds().split(GlobalConstant.STR_SPLIT);
		}
		Integer max = viewInvokeParams.getMax();
		logger.info("@@@In TreatmentJourneyViewCtl.getFirstLevelData begin: studyId=" + viewInvokeParams.getStudyId() + ", cohort=" + viewInvokeParams.getCohortIds() + ", maxCount=" + viewInvokeParams.getMax());
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> resultList = treatmentJourneyService.queryListFirstLevelTreatmentJourney(studyId, cohortIds, max);
		resultMap.put("name", "");
		resultMap.put("value", 0);
		resultMap.put("style", "white");
		resultMap.put("line", "fixTheme");
		resultMap.put("children", resultList);
		logger.info("@@@In TreatmentJourneyViewCtl.getFirstLevelData end");
		return resultMap;
	}

	@RequestMapping("/getSecondData")
	@ResponseBody
	public List<Map<String, Object>> getSecondData(ViewInvokeParams viewInvokeParams) {
		String studyId = viewInvokeParams.getStudyId();
		String[] cohortIds = null;
		if(!StringUtils.isBlank(viewInvokeParams.getCohortIds())){
			cohortIds = viewInvokeParams.getCohortIds().split(GlobalConstant.STR_SPLIT);
		}
		Integer max = viewInvokeParams.getMax();
		Integer curLevel = viewInvokeParams.getLevel();
		String patientIdStr = viewInvokeParams.getPatientIdStr();
		logger.info("@@@In TreatmentJourneyViewCtl.getSecondData begin: studyId=" + viewInvokeParams.getStudyId() + ", cohort=" + viewInvokeParams.getCohortIds() + ", maxCount=" + viewInvokeParams.getMax());
		List<Map<String, Object>> resultList = treatmentJourneyService.queryListChildTreatmentJourney(studyId, cohortIds, curLevel, max, patientIdStr);
		logger.info("@@@In TreatmentJourneyViewCtl.getSecondData end");
		return resultList;
	}
	
}
