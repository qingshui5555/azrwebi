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
import com.movit.rwe.common.web.BaseController;
import com.movit.rwe.modules.bi.dashboard.common.ViewInvokeParams;
import com.movit.rwe.modules.bi.dashboard.service.SurvivalCurveService;

@Controller
@RequestMapping(value = "${biPath}/dfView/survivalCurve")
public class SurvivalCurveViewCtl extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(SurvivalCurveViewCtl.class);

	@Autowired
	private SurvivalCurveService survivalCurveService;

	@RequestMapping("index")
	public ModelAndView showChartById(ViewInvokeParams viewInvokeParams, HttpServletRequest request) {
		String studyId = viewInvokeParams.getStudyId();
		String tabViewId = viewInvokeParams.getTabViewId();
		String isPad = viewInvokeParams.getIsPad();
		String cohortIdStr = StringUtils.join(viewInvokeParams.getCohorts(), GlobalConstant.STR_SPLIT);
        Boolean allPatientsFlag = viewInvokeParams.getAllPatientsFlag();
		logger.info("@@@In SurvivalCurveViewCtl.showChartById begin: studyId=" + studyId + ", tabViewId=" + tabViewId);

		TabView tabView = survivalCurveService.queryTabViewByTabViewId(tabViewId);
		String viewChartHeight = "400";
		if(tabView != null && !StringUtils.isBlank(tabView.getViewChartHeight())){
			viewChartHeight = tabView.getViewChartHeight();
		}

		String style = CookieUtils.getCookie(request, "_change_style");
		if(StringUtils.isEmpty(style)){
			style = "white";
		}

		ModelAndView mav = new ModelAndView("modules/bi/dashboard/dfView/survivalCurveView");
		mav.addObject(GlobalConstant.STR_ATTR_COHORT_IDS, cohortIdStr);
		mav.addObject(GlobalConstant.STR_ATTR_VIEW_CHART_HEIGHT, viewChartHeight);
        mav.addObject(GlobalConstant.STR_ATTR_ALL_PATIENTS_FLAG, allPatientsFlag);
		mav.addObject(GlobalConstant.STR_ATTR_STUDY_ID, studyId);
		mav.addObject(GlobalConstant.STR_ATTR_STYLE, style);
		mav.addObject(GlobalConstant.STR_ATTR_IS_PAD, isPad);

		logger.info("@@@In SurvivalCurveViewCtl.showChartById end");
		return mav;
	}
	
	@RequestMapping("/getData")
	@ResponseBody
	public Map<String, Object> getData(ViewInvokeParams viewInvokeParams){
		String studyId = viewInvokeParams.getStudyId();
		String [] cohortIds = viewInvokeParams.getCohorts();
		String countType = viewInvokeParams.getCountType();
		String timeType = viewInvokeParams.getTimeType();
		Boolean allPatientsFlag = viewInvokeParams.getAllPatientsFlag();
		logger.info("@@@In SurvivalCurveViewCtl.getData begin: studyId=" + studyId + ", countType=" + countType + ", timeType=" + timeType);
		List<Map<String, Object>> dataList = survivalCurveService.queryListSurvivalCurve(studyId, cohortIds, countType, timeType, allPatientsFlag);

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put(GlobalConstant.STR_ATTR_SURVIVAL_CURVE_DATA_LIST, dataList);
        resultMap.put(GlobalConstant.STR_ATTR_SUCCESS, true);

		logger.info("@@@In SurvivalCurveViewCtl.getData end");
		return resultMap;
	}
}
