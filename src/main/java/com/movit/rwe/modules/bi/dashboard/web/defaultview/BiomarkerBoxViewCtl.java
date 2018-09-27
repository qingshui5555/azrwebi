package com.movit.rwe.modules.bi.dashboard.web.defaultview;

import com.movit.rwe.common.config.GlobalConstant;
import com.movit.rwe.common.utils.CookieUtils;
import com.movit.rwe.common.web.BaseController;
import com.movit.rwe.modules.bi.base.entity.mysql.PatientGroup;
import com.movit.rwe.modules.bi.base.entity.mysql.TabView;
import com.movit.rwe.modules.bi.dashboard.common.ViewInvokeParams;
import com.movit.rwe.modules.bi.dashboard.service.BiomarkerTestService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author henry created on 2018-07-11
 */
@Controller
@RequestMapping(value = "${biPath}/dfView/biomarkerBox")
public class BiomarkerBoxViewCtl extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(BiomarkerBoxViewCtl.class);

	@Autowired
	private BiomarkerTestService biomarkerTestService;

	@RequestMapping("config")
	public ModelAndView config(ViewInvokeParams viewInvokeParams) {
		String studyId = viewInvokeParams.getStudyId();
		String tabViewId = viewInvokeParams.getTabViewId();
		logger.info("@@@In BiomarkerBoxViewCtl.config begin: studyId=" + studyId + ", tabViewId=" + tabViewId);

		TabView tabView = biomarkerTestService.queryTabViewByTabViewId(tabViewId);
		String configJson = "";
		if(tabView != null){
			configJson = tabView.getConfigJson();
		}

		String json = "";
		try {
			if (StringUtils.isEmpty(configJson)) {
				json = "{}";
			} else {
				json = JSONObject.fromObject(configJson).toString();
			}
		} catch (Exception e) {

		}
		List<PatientGroup> patientGroupList = biomarkerTestService.findAllPatientGroup(studyId);
		Map<String, Set<String>> biomarkerTestMap = biomarkerTestService.findBiomarkerAndResultTypeGroup(studyId);
		List<String> indicatorList = biomarkerTestService.findAllIndicator(studyId);

		ModelAndView mav = new ModelAndView("modules/bi/dashboard/dfView/biomarkerBoxConfig");
		mav.addObject(GlobalConstant.STR_ATTR_BIOMARKER_BOX_CONFIG_JSON, json);
		mav.addObject("indicatorList", indicatorList);
		mav.addObject("patientGroupList", patientGroupList);
		mav.addObject("biomarkerTestMap", biomarkerTestMap);

		logger.info("@@@In BiomarkerBoxViewCtl.config end");
		return mav;
	}

	@RequestMapping("index")
	public ModelAndView showChartById(ViewInvokeParams viewInvokeParams, HttpServletRequest request) {
		String studyId = viewInvokeParams.getStudyId();
		String tabViewId = viewInvokeParams.getTabViewId();
		logger.info("@@@In BiomarkerBoxViewCtl.showChartById begin: studyId=" + studyId + ", tabViewId=" + tabViewId);

		TabView tabView = biomarkerTestService.queryTabViewByTabViewId(tabViewId);
		String viewChartHeight = "400";
		if(tabView != null && !StringUtils.isBlank(tabView.getViewChartHeight())){
			viewChartHeight = tabView.getViewChartHeight();
		}
		String configJson = "{}";
		if(tabView != null){
			configJson = tabView.getConfigJson();
		}
		String patientGroupIdArr = "";
		List<Map<String, String>> geneObjArr = new ArrayList<Map<String, String>>();
		List<String> labTestArr = new ArrayList<String>();
		Map<String, String> patientGroupNameMap = new HashMap<String, String>();
		try {
			JSONObject json = JSONObject.fromObject(configJson);

			if (!ViewInvokeParams.ALL_PATIENTS.equals(viewInvokeParams.getPatientGroupArrStr())) {
				patientGroupIdArr = viewInvokeParams.getPatientGroupArrStr();
				patientGroupNameMap = biomarkerTestService.getPatientGroupNameArr(studyId, viewInvokeParams.getPatientGroupArrList());
			}  else {
				patientGroupIdArr = json.getString("patientGroupIdArr");
				patientGroupNameMap = biomarkerTestService.getPatientGroupNameArr(studyId, json.getJSONArray("patientGroupIdArr"));
			}

			geneObjArr = json.getJSONArray("geneObjArr");
			labTestArr = json.getJSONArray("labTestArr");

		} catch (Exception e) {
		}

		if ("".equals(patientGroupIdArr)) {
			patientGroupIdArr = "[]";
		}

		String style = CookieUtils.getCookie(request, "_change_style");
		if(StringUtils.isBlank(style)){
			style = "white";
		}

		ModelAndView mav = new ModelAndView("modules/bi/dashboard/dfView/biomarkerBoxView");
		mav.addObject(GlobalConstant.STR_ATTR_STUDY_ID, studyId);
		mav.addObject(GlobalConstant.STR_ATTR_VIEW_CHART_HEIGHT, viewChartHeight);
		mav.addObject(GlobalConstant.STR_ATTR_STYLE, style);
		mav.addObject(GlobalConstant.STR_ATTR_IS_PAD, viewInvokeParams.getIsPad());
		mav.addObject("patientGroupNameMap", patientGroupNameMap);
		mav.addObject("patientGroupIdArr", patientGroupIdArr);
		mav.addObject("geneObjArr", geneObjArr);
		mav.addObject("labTestArr", labTestArr);

		logger.info("@@@In BiomarkerBoxViewCtl.showChartById end");
		return mav;
	}

	@RequestMapping("getData")
	@ResponseBody
	public Map<String, Object> getBoxPlotDate(ViewInvokeParams viewInvokeParams, HttpServletRequest request) {
		String studyId = viewInvokeParams.getStudyId();
		String[] patientGroupIdArr = request.getParameterValues("patientGroupIdArr[]");
		String type = request.getParameter("type");
		String value = request.getParameter("value");
		return biomarkerTestService.getBoxPlotDate(studyId, patientGroupIdArr, type, value);
	}
}
