package com.movit.rwe.modules.bi.dashboard.web.defaultview;

import com.movit.rwe.common.config.GlobalConstant;
import com.movit.rwe.common.utils.CookieUtils;
import com.movit.rwe.modules.bi.base.entity.mysql.TabView;
import com.movit.rwe.modules.bi.dashboard.common.ViewInvokeParams;
import com.movit.rwe.modules.bi.dashboard.service.WorldMapService;
import com.movit.rwe.modules.bi.dashboard.vo.ConfigVo;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "${biPath}/dfView/worldMap")
public class WorldMapViewCtl {

	private static Logger logger = LoggerFactory.getLogger(WorldMapViewCtl.class);

	@Autowired
	private WorldMapService worldMapService;

	@RequestMapping("config")
	public ModelAndView config(ViewInvokeParams viewInvokeParams) {
		String studyId = viewInvokeParams.getStudyId();
		String tabViewId = viewInvokeParams.getTabViewId();
		logger.info("@@@In LabTestViewCtl.config begin: studyId=" + studyId + ", tabViewId=" + tabViewId);

		TabView tabView = worldMapService.queryTabViewByTabViewId(tabViewId);
		String configJson = "";
		String viewChartHeight = "400";
		if(tabView != null && !StringUtils.isBlank(tabView.getViewChartHeight())){
			configJson = tabView.getConfigJson();
			viewChartHeight = tabView.getViewChartHeight();
		}

		Integer begin = 1;
		List<ConfigVo> configVoList = null;
		if(!StringUtils.isEmpty(configJson)) {
			JSONArray jsonArr = JSONArray.fromObject(configJson);
			configVoList = worldMapService.queryListConfigVo(JSONArray.fromObject(jsonArr.getJSONObject(1)));
			ConfigVo vo = configVoList.get(configVoList.size() - 1);
			begin = vo.getOrder() + 1;
		}

		ModelAndView mav = new ModelAndView("modules/bi/dashboard/dfView/worldMapConfig");
		mav.addObject(GlobalConstant.STR_ATTR_WORLD_MAP_CONFIG_LIST, worldMapService.queryListWorldMapConfig(studyId, begin, configVoList));
		mav.addObject(GlobalConstant.STR_ATTR_CONFIG_VO_LIST, configVoList);
		mav.addObject(GlobalConstant.STR_ATTR_VIEW_CHART_HEIGHT, viewChartHeight);

		logger.info("@@@In LabTestViewCtl.config end");
		return mav;
	}

	@RequestMapping("index")
	public ModelAndView showChartById(ViewInvokeParams viewInvokeParams, HttpServletRequest request) {
		String studyId = viewInvokeParams.getStudyId();
		String tabViewId = viewInvokeParams.getTabViewId();
		logger.info("@@@In WorldMapViewCtl.showChartById begin: studyId=" + studyId + ", tabViewId=" + tabViewId);

		TabView tabView = worldMapService.queryTabViewByTabViewId(tabViewId);
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

		ModelAndView mav = new ModelAndView("modules/bi/dashboard/dfView/worldMapView");
		mav.addObject(GlobalConstant.STR_ATTR_VIEW_CHART_HEIGHT, viewChartHeight);
		mav.addObject(GlobalConstant.STR_ATTR_STYLE, style);
		mav.addObject(GlobalConstant.STR_ATTR_WORLD_MAP_INDICATOR_MAP, worldMapService.queryIndicatorMap(configJson));

		logger.info("@@@In WorldMapViewCtl.showChartById end");
		return mav;
	}

	@RequestMapping("/getData")
	@ResponseBody
	public Map<String, Object> getWorldMapData(ViewInvokeParams viewInvokeParams) {
		String studyId = viewInvokeParams.getStudyId();
		String [] cohortIds = viewInvokeParams.getCohorts();
		String startDate = viewInvokeParams.getStartDate();
		String endDate = viewInvokeParams.getEndDate();
		String indicator = viewInvokeParams.getIndicator();
		String countType = viewInvokeParams.getCountType();
		logger.info("@@@In WorldMapViewCtl.getComorbidityData begin: studyId=" + studyId);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> worldMapDataList = worldMapService.queryListWorldMapData(studyId, countType, indicator, cohortIds, startDate, endDate);
		if (worldMapDataList == null) {
			resultMap.put(GlobalConstant.STR_ATTR_SUCCESS, false);
			resultMap.put("info", "can not get data");
			return resultMap;
		}

		resultMap.put(GlobalConstant.STR_ATTR_WORLD_MAP_DATA_LIST, worldMapDataList);
		resultMap.put(GlobalConstant.STR_ATTR_SUCCESS, true);

		logger.info("@@@In WorldMapViewCtl.getComorbidityData end");
		return resultMap;
	}
}
