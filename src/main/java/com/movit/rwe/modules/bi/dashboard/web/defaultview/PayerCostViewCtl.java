package com.movit.rwe.modules.bi.dashboard.web.defaultview;

import java.util.Date;
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
import com.movit.rwe.modules.bi.dashboard.service.PayerCostService;

@Controller
@RequestMapping(value = "${biPath}/dfView/payerCost")
public class PayerCostViewCtl extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(PayerCostViewCtl.class);

	@Autowired
	private PayerCostService payerCostService;

	@RequestMapping("index")
	public ModelAndView showChartById(ViewInvokeParams viewInvokeParams, HttpServletRequest request) {
		String studyId = viewInvokeParams.getStudyId();
		String tabViewId = viewInvokeParams.getTabViewId();
		Boolean allPatientsFlag = viewInvokeParams.getAllPatientsFlag();
		String cohortIdStr = StringUtils.join(viewInvokeParams.getCohorts(), GlobalConstant.STR_SPLIT);
		logger.info("@@@In PayerCostViewCtl.showChartById begin: studyId=" + studyId + ", tabViewId=" + tabViewId);

		TabView tabView = payerCostService.queryTabViewByTabViewId(tabViewId);
		String viewChartHeight = "400";
		if(tabView != null && !StringUtils.isBlank(tabView.getViewChartHeight())){
			viewChartHeight = tabView.getViewChartHeight();
		}

		String style = CookieUtils.getCookie(request, "_change_style");
		if(StringUtils.isEmpty(style)){
			style = "white";
		}

		ModelAndView mav = new ModelAndView("modules/bi/dashboard/dfView/payerCostView");
		mav.addObject(GlobalConstant.STR_ATTR_COHORT_IDS, cohortIdStr);
		mav.addObject(GlobalConstant.STR_ATTR_ALL_PATIENTS_FLAG, allPatientsFlag);
		mav.addObject(GlobalConstant.STR_ATTR_VIEW_CHART_HEIGHT, viewChartHeight);
		mav.addObject(GlobalConstant.STR_ATTR_STUDY_ID, studyId);
		mav.addObject(GlobalConstant.STR_ATTR_STYLE, style);

		logger.info("@@@In PayerCostViewCtl.showChartById end");
		return mav;
	}

	@RequestMapping("/getDetailData")
	@ResponseBody
	public Map<String, Object> getData(ViewInvokeParams viewInvokeParams) {
		String studyId = viewInvokeParams.getStudyId();
		String startDate = viewInvokeParams.getStartDate();
		String endDate = viewInvokeParams.getEndDate();
		String[] cohortIds = null;
		if(!StringUtils.isBlank(viewInvokeParams.getCohortIds())){
			cohortIds = viewInvokeParams.getCohortIds().split(GlobalConstant.STR_SPLIT);
		}
		logger.info("@@@In PayerCostViewCtl.getDetailData begin: studyId=" + studyId + ", cohort=" + cohortIds);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> dataList = payerCostService.queryListDetailPayerCost(studyId, cohortIds, startDate, endDate);
		if (dataList == null) {
			resultMap.put(GlobalConstant.STR_ATTR_SUCCESS, false);
			resultMap.put("info", "can not get data");
			return resultMap;
		}
		resultMap.put(GlobalConstant.STR_ATTR_SUCCESS, true);
		resultMap.put("children", dataList);

		logger.info("@@@In PayerCostViewCtl.getDetailData end");
		return resultMap;
	}
	
	@RequestMapping("/getTotalData")
	@ResponseBody
	public Map<String, Object> getDetailData(ViewInvokeParams viewInvokeParams) {
		String studyId = viewInvokeParams.getStudyId();
		String startDate = viewInvokeParams.getStartDate();
		String endDate = viewInvokeParams.getEndDate();
		String[] cohortIds = null;
		if(!StringUtils.isBlank(viewInvokeParams.getCohortIds())){
			cohortIds = viewInvokeParams.getCohortIds().split(GlobalConstant.STR_SPLIT);
		}
		logger.info("@@@In PayerCostViewCtl.getTotalData begin: studyId=" + studyId + ", cohort=" + cohortIds);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> detailDataList = payerCostService.queryListTotalPayerCost(studyId, cohortIds, startDate, endDate);
		if (detailDataList == null) {
			resultMap.put(GlobalConstant.STR_ATTR_SUCCESS, false);
			resultMap.put("info", "can not get data");
			return resultMap;
		}

		resultMap.put(GlobalConstant.STR_ATTR_SUCCESS, true);
		resultMap.put("children", detailDataList);

		logger.info("@@@In PayerCostViewCtl.getTotalData end");
		return resultMap;
	}
}
