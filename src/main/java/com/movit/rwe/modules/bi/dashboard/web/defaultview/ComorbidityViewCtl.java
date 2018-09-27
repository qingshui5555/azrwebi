package com.movit.rwe.modules.bi.dashboard.web.defaultview;

import java.util.*;

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
import com.movit.rwe.modules.bi.dashboard.service.ComorbidityService;

@Controller
@RequestMapping(value = "${biPath}/dfView/comorbidity")
public class ComorbidityViewCtl {

	private static Logger logger = LoggerFactory.getLogger(ComorbidityViewCtl.class);

	@Autowired
	private ComorbidityService comorbidityService;

	@RequestMapping("index")
	public ModelAndView showChartById(ViewInvokeParams viewInvokeParams, HttpServletRequest request) {
		String studyId = viewInvokeParams.getStudyId();
		String tabViewId = viewInvokeParams.getTabViewId();
		String isPad = viewInvokeParams.getIsPad();
		logger.info("@@@In ComorbidityViewCtl.showChartById begin: studyId=" + studyId + ", tabViewId=" + tabViewId);

		TabView tabView = comorbidityService.queryTabViewByTabViewId(tabViewId);
		String viewChartHeight = "400";
		if(tabView != null && !StringUtils.isBlank(tabView.getViewChartHeight())){
			viewChartHeight = tabView.getViewChartHeight();
		}

		String style = CookieUtils.getCookie(request, "_change_style");
		if(StringUtils.isEmpty(style)){
			style = "white";
		}

		ModelAndView mav = new ModelAndView("modules/bi/dashboard/dfView/comorbidityView");
		mav.addObject(GlobalConstant.STR_ATTR_VIEW_CHART_HEIGHT, viewChartHeight);
		mav.addObject(GlobalConstant.STR_ATTR_STYLE, style);
		mav.addObject(GlobalConstant.STR_ATTR_IS_PAD, isPad);

		logger.info("@@@In ComorbidityViewCtl.showChartById end");
		return mav;
	}

	@RequestMapping("/getData")
	@ResponseBody
	public Map<String, Object> getData(ViewInvokeParams viewInvokeParams) {
		String studyId = viewInvokeParams.getStudyId();
		String [] cohortIds = viewInvokeParams.getCohorts();
		String startDate = viewInvokeParams.getStartDate();
		String endDate = viewInvokeParams.getEndDate();
		String comorbidity = viewInvokeParams.getComorbidity();
		logger.info("@@@In ComorbidityViewCtl.getData begin: studyId=" + studyId + ", comorbidity=" + comorbidity);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> comorbidityDataList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> comorbiditySubDataList = new ArrayList<Map<String, Object>>();
		Integer sumCount = comorbidityService.queryListComorbidityData(studyId, cohortIds, startDate, endDate, comorbidity, comorbidityDataList, comorbiditySubDataList);
		if (comorbidityDataList == null) {
			resultMap.put(GlobalConstant.STR_ATTR_SUCCESS, false);
			resultMap.put("info", "can not get data");
			return resultMap;
		}

		resultMap.put(GlobalConstant.STR_ATTR_COMORBIDITY_DATA_LIST, comorbidityDataList);
		resultMap.put(GlobalConstant.STR_ATTR_COMORBIDITY_SUB_DATA_LIST, comorbiditySubDataList);
		resultMap.put(GlobalConstant.STR_ATTR_SUCCESS, true);
		resultMap.put(GlobalConstant.STR_ATTR_SUM_COUNT, sumCount);

		logger.info("@@@In ComorbidityViewCtl.getData end");
		return resultMap;
	}
}
