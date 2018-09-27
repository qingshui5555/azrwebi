package com.movit.rwe.modules.bi.dashboard.web.defaultview;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import com.movit.rwe.common.config.GlobalConstant;
import com.movit.rwe.modules.bi.base.entity.hive.Treatment;
import com.movit.rwe.modules.bi.base.entity.mysql.TabView;
import com.movit.rwe.modules.bi.base.entity.vo.HiveTreatmentVo;
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
import com.movit.rwe.modules.bi.dashboard.service.TreatmentService;

@Controller
@RequestMapping("${biPath}/dfView/treatment")
public class TreatmentViewCtl {

	private static Logger logger = LoggerFactory.getLogger(TreatmentViewCtl.class);

	@Autowired
	TreatmentService treatmentService;

	@RequestMapping("/index")
	public ModelAndView showChartById(ViewInvokeParams viewInvokeParams, HttpServletRequest request) {
		String studyId = viewInvokeParams.getStudyId();
		String tabViewId = viewInvokeParams.getTabViewId();
		Boolean needChoosePatient = viewInvokeParams.getNeedChoosePatient();
		logger.info("@@@In TreatmentViewCtl.showChartById begin: studyId=" + studyId + ", tabViewId=" + tabViewId);
		TabView tabView = treatmentService.queryTabViewByTabViewId(tabViewId);
		String viewChartHeight = "400";
		if(tabView != null && !StringUtils.isBlank(tabView.getViewChartHeight())){
			viewChartHeight = tabView.getViewChartHeight();
		}

		if (needChoosePatient == null) {
			needChoosePatient = false;
		}

		String style = CookieUtils.getCookie(request, "_change_style");
		if(StringUtils.isEmpty(style)){
			style = "white";
		}

		ModelAndView mav = new ModelAndView("modules/bi/dashboard/dfView/treatmentView");
		mav.addObject(GlobalConstant.STR_ATTR_STUDY_ID, studyId);
		mav.addObject(GlobalConstant.STR_ATTR_NEED_CHOOSE_PATIENT, needChoosePatient);
		mav.addObject(GlobalConstant.STR_ATTR_VIEW_CHART_HEIGHT, viewChartHeight);
		mav.addObject(GlobalConstant.STR_ATTR_STYLE, style);
		mav.addObject(GlobalConstant.STR_ATTR_IS_PAD, viewInvokeParams.getIsPad());

		logger.info("@@@In TreatmentViewCtl.showChartById end");
		return mav;
	}

	@RequestMapping("/matchPatientCode")
	@ResponseBody
	public Map<String, Object> matchPatientCode(ViewInvokeParams viewInvokeParams) {
		logger.info("@@@In TreatmentViewCtl.matchPatientCode begin: studyId=" + viewInvokeParams.getStudyId() + ", patientCode=" + viewInvokeParams.getPatientCode());
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put(GlobalConstant.STR_ATTR_SUCCESS, true);
		resultMap.put(GlobalConstant.STR_ATTR_PATIENT_CODE_LIST, treatmentService.matchPatientCode(viewInvokeParams.getStudyId(), viewInvokeParams.getPatientCode()));
		logger.info("@@@In TreatmentViewCtl.matchPatientCode end");
		return resultMap;
	}

	@RequestMapping("/getTreatmentPath")
	@ResponseBody
	public Map<String, Object> getTreatmentPath(ViewInvokeParams viewInvokeParams) {
		String studyId = viewInvokeParams.getStudyId();
		String patientCode = viewInvokeParams.getPatientCode();
		logger.info("@@@In TreatmentViewCtl.matchPatientCode begin: studyId=" + studyId + ", patientCode=" + patientCode);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<HiveTreatmentVo> treatmentVoList = treatmentService.queryListTreatmentVo(studyId, patientCode);
		if (treatmentVoList.isEmpty()) {
			resultMap.put(GlobalConstant.STR_ATTR_SUCCESS, false);
			resultMap.put("info", "can not find treatment");
			return resultMap;
		}

		resultMap.put(GlobalConstant.STR_ATTR_SUCCESS, true);
		resultMap.put("treatmentList", treatmentVoList);
		resultMap.put("treatmentGuidSet", getTreatmentGuidSet(treatmentVoList));
		resultMap.put("drugSet", getDrugSet(treatmentVoList));
		resultMap.put("dosageSet", getDosingSet(treatmentVoList));

		return resultMap;
	}

	/**
	 * 根据药品集合获取所有计量(去重)
	 * 
	 * @param treatmentList
	 * @return
	 */
	private Set<Double> getDosingSet(List<HiveTreatmentVo> treatmentList) {
		Set<Double> dosingSet = new TreeSet<Double>();

		for (HiveTreatmentVo treatment : treatmentList) {
			if (treatment.getDailyDose() == null) {
				dosingSet.add(0.0);
			} else {
				dosingSet.add(treatment.getDailyDose().doubleValue());
			}
		}

		return dosingSet;
	}

	/**
	 * 获取所有治疗主键（去重）
	 * 
	 * @param treatmentList
	 * @return
	 */
	private Set<String> getTreatmentGuidSet(List<HiveTreatmentVo> treatmentList) {
		Set<String> treatmentGuidSet = new HashSet<String>();

		for (HiveTreatmentVo treatment : treatmentList) {
			treatmentGuidSet.add(treatment.getVisitId());
		}

		return treatmentGuidSet;
	}

	/**
	 * 根据药品集合获取所有药名(去重)
	 * 
	 * @param treatmentList
	 * @return
	 */
	private Set<String> getDrugSet(List<HiveTreatmentVo> treatmentList) {
		Set<String> drugSet = new TreeSet<String>();

		drugSet.add("");

		for (HiveTreatmentVo treatment : treatmentList) {
			drugSet.add(treatment.getDrugName());
		}

		return drugSet;
	}

}
