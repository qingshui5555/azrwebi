package com.movit.rwe.modules.bi.dashboard.web.defaultview;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.movit.rwe.common.config.GlobalConstant;
import com.movit.rwe.modules.bi.base.entity.mysql.TabView;
import com.movit.rwe.modules.bi.dashboard.vo.ConfigVo;
import com.movit.rwe.modules.bi.base.entity.vo.HiveResultVo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.movit.rwe.common.utils.CookieUtils;
import com.movit.rwe.modules.bi.dashboard.common.ViewInvokeParams;
import com.movit.rwe.modules.bi.dashboard.service.DemographicService;

/**
 * Created by Mike on 2016/4/25.
 */

@Controller
@RequestMapping(value = "${biPath}/dfView/demographic")
public class DemographicViewCtl {

	private static Logger logger = LoggerFactory.getLogger(DemographicViewCtl.class);

	@Autowired
	DemographicService demographicService;

	@RequestMapping("config")
	public ModelAndView config(ViewInvokeParams viewInvokeParams) {
		String studyId = viewInvokeParams.getStudyId();
		String tabViewId = viewInvokeParams.getTabViewId();
		logger.info("@@@In DemographicViewCtl.config begin: studyId=" + studyId + ", tabViewId=" + tabViewId);

		TabView tabView = demographicService.queryTabViewByTabViewId(tabViewId);
		String configJson = "";
		String viewChartHeight = "400";
		String alias = "";
		if(tabView != null && !StringUtils.isBlank(tabView.getViewChartHeight())){
			configJson = tabView.getConfigJson();
			viewChartHeight = tabView.getViewChartHeight();
			alias = tabView.getAlias();
		}

		Integer begin = 1;
		List<ConfigVo> configVoList = null;
		if(!StringUtils.isEmpty(configJson)&&!"{}".equals(configJson)) {
			configVoList = demographicService.queryListConfigVo(configJson);
			ConfigVo vo = configVoList.get(configVoList.size() - 1);
			begin = vo.getOrder() + 1;
		}

		List<HiveResultVo> demographicConfigList = new ArrayList<HiveResultVo>();
        if(alias.equalsIgnoreCase(GlobalConstant.STR_ETL_TYPE_DEMOGRAPHIC)){
			demographicConfigList.addAll(demographicService.queryListDemographicConfig(studyId, begin, configVoList));
		} else if(alias.replace(" ", "").equalsIgnoreCase(GlobalConstant.STR_ETL_TYPE_VITAL_SIGN.replace(" ", ""))){
			demographicConfigList.addAll(demographicService.queryListVitalSignConfig(studyId, begin + 1000, configVoList));
		} else {
			demographicConfigList.addAll(demographicService.queryListDemographicConfig(studyId, begin, configVoList));
			demographicConfigList.addAll(demographicService.queryListVitalSignConfig(studyId, begin + 1000, configVoList));
		}

		ModelAndView mav = new ModelAndView("modules/bi/dashboard/dfView/demographicConfig");
		mav.addObject(GlobalConstant.STR_ATTR_DEMOGRAPHIC_CONFIG_LIST, demographicConfigList);
		mav.addObject(GlobalConstant.STR_ATTR_CONFIG_VO_LIST, configVoList);
		mav.addObject(GlobalConstant.STR_ATTR_VIEW_CHART_HEIGHT, viewChartHeight);

		logger.info("@@@In DemographicViewCtl.config end");
		return mav;
	}

	@RequestMapping("index")
	public ModelAndView showChartById(ViewInvokeParams viewInvokeParams, HttpServletRequest request) {
		String studyId = viewInvokeParams.getStudyId();
		String tabViewId = viewInvokeParams.getTabViewId();
		String [] cohortIds = viewInvokeParams.getCohorts();
		String isPad = viewInvokeParams.getIsPad();
		Boolean allPatientsFlag = viewInvokeParams.getAllPatientsFlag();
		logger.info("@@@In DemographicViewCtl.showChartById begin: studyId=" + studyId + ", tabViewId=" + tabViewId);

		TabView tabView = demographicService.queryTabViewByTabViewId(viewInvokeParams.getTabViewId());
		String viewChartHeight= "400";
		if(tabView != null && !StringUtils.isBlank(tabView.getViewChartHeight())){
			viewChartHeight = tabView.getViewChartHeight();
		}

		String alias = GlobalConstant.STR_ETL_TYPE_DEMOGRAPHIC;
		String configJson = "{}";
		if(tabView != null){
			alias = tabView.getAlias().replaceAll(" ", "");
			configJson = tabView.getConfigJson();
		}

        String style = CookieUtils.getCookie(request, "_change_style");
        if(StringUtils.isBlank(style)){
            style = "white";
        }
		String local = CookieUtils.getCookie(request, "_change_locale");

		Map<String, HiveResultVo> demographicIndicatorMap = demographicService.queryIndicatorMap(configJson);
		Map<String, Map<String, Integer>> dataMap = new LinkedHashMap<String, Map<String, Integer>>();
		Map<String, Integer> dataSizeMap = new LinkedHashMap<String, Integer>();
        Map<String, String> dataUnitMap = new LinkedHashMap<String, String>();
		Integer patientCount = demographicService.countPatient(studyId, cohortIds, allPatientsFlag);
		if(alias.equalsIgnoreCase(GlobalConstant.STR_ETL_TYPE_DEMOGRAPHIC)){
			demographicService.queryListDemographicData(studyId, cohortIds, patientCount, demographicIndicatorMap, dataMap, dataSizeMap, dataUnitMap);
		} else if(alias.replace(" ", "").equalsIgnoreCase(GlobalConstant.STR_ETL_TYPE_VITAL_SIGN.replace(" ", ""))){
			demographicService.queryListVitalSignData(studyId, cohortIds, patientCount, demographicIndicatorMap, dataMap, dataSizeMap, dataUnitMap);
		} else {
			demographicService.queryListDemographicData(studyId, cohortIds, patientCount, demographicIndicatorMap, dataMap, dataSizeMap, dataUnitMap);
			demographicService.queryListVitalSignData(studyId, cohortIds, patientCount, demographicIndicatorMap, dataMap, dataSizeMap, dataUnitMap);
		}

		ModelAndView mav = new ModelAndView("modules/bi/dashboard/dfView/demographicView");
        mav.addObject(GlobalConstant.STR_ATTR_DEMOGRAPHIC_DATA_MAP, dataMap);
        mav.addObject(GlobalConstant.STR_ATTR_DEMOGRAPHIC_DATA_SIZE_MAP, dataSizeMap);
        mav.addObject(GlobalConstant.STR_ATTR_DEMOGRAPHIC_DATA_UNIT_MAP, dataUnitMap);
        mav.addObject(GlobalConstant.STR_ATTR_DEMOGRAPHIC_INDICATOR_MAP, demographicIndicatorMap);
        mav.addObject(GlobalConstant.STR_ATTR_ALIAS, alias);
        mav.addObject(GlobalConstant.STR_ATTR_COHORTS, cohortIds);
        mav.addObject(GlobalConstant.STR_ATTR_CONFIG_JSON, configJson);
        mav.addObject(GlobalConstant.STR_ATTR_VIEW_CHART_HEIGHT, viewChartHeight);
        mav.addObject(GlobalConstant.STR_ATTR_STUDY_ID, studyId);
        mav.addObject(GlobalConstant.STR_ATTR_STYLE, style);
        mav.addObject(GlobalConstant.STR_ATTR_IS_PAD, isPad);

		logger.info("@@@In DemographicViewCtl.showChartById end");
		return mav;
	}
}
