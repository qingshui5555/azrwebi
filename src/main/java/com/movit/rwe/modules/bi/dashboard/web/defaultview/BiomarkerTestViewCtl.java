package com.movit.rwe.modules.bi.dashboard.web.defaultview;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.movit.rwe.common.config.GlobalConstant;
import com.movit.rwe.modules.bi.base.entity.mysql.TabView;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.movit.rwe.common.utils.CookieUtils;
import com.movit.rwe.common.web.BaseController;
import com.movit.rwe.modules.bi.base.entity.hive.BiomarkerTest;
import com.movit.rwe.modules.bi.base.entity.mysql.PatientGroup;
import com.movit.rwe.modules.bi.dashboard.common.ViewInvokeParams;
import com.movit.rwe.modules.bi.dashboard.service.BiomarkerTestService;
import com.movit.rwe.modules.bi.dashboard.service.TabViewService;
import com.movit.rwe.modules.bi.dashboard.vo.BiomarkerTestVo;

import net.sf.json.JSONObject;

/**
 * @author henry created on 2018-07-11
 */
@Controller
@RequestMapping(value = "${biPath}/dfView/biomarkerTest")
public class BiomarkerTestViewCtl extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(BiomarkerTestViewCtl.class);

	@Autowired
	private BiomarkerTestService biomarkerTestService;

	@Autowired
	private TabViewService tabViewService;

	@RequestMapping("config")
	public ModelAndView config(ViewInvokeParams viewInvokeParams) {
		String studyId = viewInvokeParams.getStudyId();
		String tabViewId = viewInvokeParams.getTabViewId();
		logger.info("@@@In BiomarkerTestViewCtl.config begin: studyId=" + studyId + ", tabViewId=" + tabViewId);

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

		Map<String, Set<String>> biomarkerMap = biomarkerTestService.queryListBiomarkerTestConfig(studyId);

		ModelAndView mav = new ModelAndView("modules/bi/dashboard/dfView/biomarkerTestConfig");
		mav.addObject(GlobalConstant.STR_ATTR_BIOMARKER_TEST_CONFIG_JSON, json);
		mav.addObject(GlobalConstant.STR_ATTR_BIOMARKER_TEST_CONFIG_MAP, biomarkerMap);

		logger.info("@@@In BiomarkerTestViewCtl.config end");
		return mav;
	}

	@RequestMapping("index")
	public ModelAndView showChartById(ViewInvokeParams viewInvokeParams, HttpServletRequest request) {
		String studyId = viewInvokeParams.getStudyId();
		String tabViewId = viewInvokeParams.getTabViewId();
		String [] cohortIds = viewInvokeParams.getCohorts();
		logger.info("@@@In BiomarkerTestViewCtl.showChartById begin: studyId=" + studyId + ", tabViewId=" + tabViewId);

		TabView tabView = biomarkerTestService.queryTabViewByTabViewId(tabViewId);
		String viewChartHeight = "575";
		if(tabView != null && !StringUtils.isBlank(tabView.getViewChartHeight())){
			viewChartHeight = tabView.getViewChartHeight();
		}
		String configJson = "{}";
		if(tabView != null){
			configJson = tabView.getConfigJson();
		}
		String measureType = null;
		List<String> biomarkerList = new ArrayList<String>();
		if (configJson != null) {
			JSONObject json = JSONObject.fromObject(configJson);
			measureType = json.getString("measureType");
			biomarkerList = json.getJSONArray("measureData");
		}
		Collections.sort(biomarkerList);
		List<String> evaluationList = biomarkerTestService.getAllEvaluation(studyId, measureType);

		// 获取每个病人对应基因的次数
		List<Integer> timesList = null;
		if (viewInvokeParams.getAllPatientsFlag()) {
			timesList = biomarkerTestService.getBiomarkerTimes(studyId, measureType, null);
		} else {
			timesList = biomarkerTestService.getBiomarkerTimes(studyId, measureType, cohortIds);
		}

		String style = CookieUtils.getCookie(request, "_change_style");
		if(StringUtils.isBlank(style)){
			style = "white";
		}

		ModelAndView mav = new ModelAndView("modules/bi/dashboard/dfView/biomarkerTestView");
		mav.addObject(GlobalConstant.STR_ATTR_STUDY_ID, studyId);
		mav.addObject(GlobalConstant.STR_ATTR_VIEW_CHART_HEIGHT, viewChartHeight);
		mav.addObject(GlobalConstant.STR_ATTR_STYLE, style);
		mav.addObject(GlobalConstant.STR_ATTR_IS_PAD, viewInvokeParams.getIsPad());
		mav.addObject(GlobalConstant.STR_ATTR_COHORTS, cohortIds);

		mav.addObject("evaluationList", evaluationList);
		mav.addObject("biomarkerList", biomarkerList);
		mav.addObject("measureType", measureType);
		mav.addObject("timesList", timesList);

		logger.info("@@@In BiomarkerTestViewCtl.showChartById end");
		return mav;
	}

	@RequestMapping("getData")
	@ResponseBody
	public Map<String, Object> getData(ViewInvokeParams viewInvokeParams, HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String studyId = viewInvokeParams.getStudyId();
			String cohorts = viewInvokeParams.getCohortIds();
			String evaluation = request.getParameter("evaluation");
			String biomarkerArrStr = request.getParameter("biomarkers");
			String measureType = request.getParameter("measureType");
			String timesStr = request.getParameter("times");
			Integer times = null;
			if(StringUtils.isNotBlank(timesStr)){
				times = Integer.parseInt(timesStr);
			}
			String[] cohortIds = null;
			if(StringUtils.isNotBlank(cohorts)){
				cohortIds = cohorts.split(";");
			}
			String[] biomarkers = null;
			if(StringUtils.isNotBlank(biomarkerArrStr)){
				biomarkers = biomarkerArrStr.split(GlobalConstant.STR_SPLIT);
			}
			List<BiomarkerTestVo> voList = biomarkerTestService.queryListBiomarkerTestData(studyId, cohortIds, biomarkers, measureType, times);
			List<BiomarkerTestVo> biomarkerTestList = new ArrayList<BiomarkerTestVo>();
			List<BiomarkerTestVo> sumBiomarkerTestList = new ArrayList<BiomarkerTestVo>();
			List<BiomarkerTestVo> unionBiomarkerTestList = new ArrayList<BiomarkerTestVo>();
			Map<String, Integer> sumBiomarkerTestMap = new HashMap<String, Integer>();
			for(BiomarkerTestVo vo : voList){
				if(StringUtils.isBlank(evaluation) || evaluation.equals(vo.getEvaluation())){
					biomarkerTestList.add(vo);
				}

				Integer subSum = sumBiomarkerTestMap.get(vo.getBiomarker());
				if(subSum == null){
					sumBiomarkerTestMap.put(vo.getBiomarker(), vo.getAmount());
				} else {
					sumBiomarkerTestMap.put(vo.getBiomarker(), subSum + vo.getAmount());
				}
			}
			for(Map.Entry<String, Integer> entry : sumBiomarkerTestMap.entrySet()){
				BiomarkerTestVo vo = new BiomarkerTestVo();
				vo.setBiomarker(entry.getKey());
				vo.setAmount(entry.getValue());
				sumBiomarkerTestList.add(vo);
				if(entry.getKey().contains(" ")){
					unionBiomarkerTestList.add(vo);
				}
			}

			List<String> xList = new ArrayList<String>();
			List<Double> yList = new ArrayList<Double>();
			if (biomarkerTestList != null && biomarkerTestList.size() > 0) {
				Collections.sort(sumBiomarkerTestList, new Comparator<BiomarkerTestVo> (){
					@Override
					public int compare(BiomarkerTestVo o1, BiomarkerTestVo o2) {
						return o1.getBiomarker().compareTo(o2.getBiomarker());
					}
				});
				Map<String, Integer> map = new TreeMap<String, Integer>();
				Map<String, Integer> unionBiomarkerMap = new TreeMap<String, Integer>();
				for (int j = 0; j < sumBiomarkerTestList.size(); j++) {
					BiomarkerTestVo sumBiomarkerTestVo = sumBiomarkerTestList.get(j);
					map.put(sumBiomarkerTestVo.getBiomarker(), sumBiomarkerTestVo.getAmount());
				}

				for (int j = 0; j < unionBiomarkerTestList.size(); j++) {
					BiomarkerTestVo sumBiomarkerTestVo = unionBiomarkerTestList.get(j);
					unionBiomarkerMap.put(sumBiomarkerTestVo.getBiomarker(), sumBiomarkerTestVo.getAmount());
				}

				for (int i = 0; i < sumBiomarkerTestList.size(); i++) {
					BiomarkerTestVo biomarkerTest = sumBiomarkerTestList.get(i);
					String biomarker = biomarkerTest.getBiomarker();

					BiomarkerTestVo biomarkerTestVo = null;
					//获取子类型或者基因的数据
					for (BiomarkerTestVo vo : biomarkerTestList) {
						if (vo.getBiomarker().equals(biomarker)) {
							biomarkerTestVo = vo;
							break;
						}
					}

					Integer amount = biomarkerTestVo == null ? 0 : biomarkerTestVo.getAmount();
					BigDecimal b = null;
					//如果包含空格说明是联合类型（EGFR EXON18），则总数量取该子类型的总数，否则取该基因的总数
//					if(biomarker.contains(" ")){
						b = new BigDecimal(((double) amount / biomarkerTest.getAmount()) * 100);
						if(amount > 0) {
							xList.add(biomarker + "," + amount + "," + biomarkerTest.getAmount());
							yList.add(b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
						}
//					}else{
//						b = new BigDecimal(((double) amount / map.get(biomarker.split(" ")[0])) * 100);
//						xList.add(biomarker + "," + amount + "," + map.get(biomarker.split(" ")[0]));
//					}
				}
			}
			resultMap.put("x", xList);
			resultMap.put("y", yList);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return resultMap;
	}

	@RequestMapping("getSubData")
	@ResponseBody
	public String getSubBiomarkers(ViewInvokeParams viewInvokeParams, HttpServletRequest request) {
		String studyId = viewInvokeParams.getStudyId();
		String biomarker = request.getParameter("biomarker");
		String measureType = request.getParameter("measureType");
		String biomarkers = "";
		List<String> subBiomarkerList = biomarkerTestService.queryListBiomarkerTestSubData(studyId, measureType, biomarker);
		int count = 0;
		for (String subBiomarker: subBiomarkerList) {
			if(!subBiomarker.trim().equalsIgnoreCase(biomarker)){
				if(count==0){
					biomarkers += subBiomarker;
				}else{
					biomarkers += "," + subBiomarker;
				}
			}
			count ++;
		}
		return biomarkers;
	}

	/**
	 * 展示 biomarker及biomarker所对应的人数
	 * 
	 * @param vip
	 * @param studyId
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("showBiomarkerTestBar")
	public ModelAndView showBiomarkerTestBar(ViewInvokeParams vip, String studyId,HttpServletRequest request) throws UnsupportedEncodingException {
		ModelAndView model = new ModelAndView("modules/bi/dashboard/dfView/biomarkerTestView");
		String viewChartHeight = tabViewService.findViewChartHeightByTabAndViewId(vip.getTabViewId());

		String configJson = vip.getConfigJson();

		String measureType = null;
		List<String> biomarkerList = new ArrayList<String>();

		if (configJson != null) {
			JSONObject json = JSONObject.fromObject(configJson);
			measureType = json.getString("measureType");
			biomarkerList = json.getJSONArray("measureData");
		}

		Collections.sort(biomarkerList);

		if (viewChartHeight == null || "".equals(viewChartHeight)) {
			viewChartHeight = "575";
		}
		String cohorts = vip.getPatientGroupArrLikeStr();

		// 获取Biomarker evaluation 列表
		List<String> evaluationList = biomarkerTestService.getAllEvaluation(studyId, measureType);

		// 获取每个病人对应基因的次数
		List<Integer> timesList = null;
		if (vip.getAllPatientsFlag()) {
			timesList = biomarkerTestService.getBiomarkerTimes(studyId, measureType, null);
		} else {
			String[] cohortsAr = vip.getCohorts();
			String[] groupsAr = vip.getGroups();
			String[] cohortsArray = new String[cohortsAr.length + groupsAr.length];
			for (int m = 0; m < cohortsArray.length; m++) {
				if (m < cohortsAr.length) {
					cohortsArray[m] = "%C_" + cohortsAr[m] + ",%";
				} else {
					cohortsArray[m] = "%G_" + groupsAr[m - cohortsAr.length] + ",%";
				}
			}
			timesList = biomarkerTestService.getBiomarkerTimes(studyId, measureType, cohortsArray);
		}

		model.addObject("evaluationList", evaluationList);
		model.addObject("biomarkerList", biomarkerList);
		model.addObject("cohorts", cohorts);
		model.addObject("viewChartHeight", viewChartHeight);
		model.addObject("studyId", studyId);
		model.addObject("measureType", measureType);
		model.addObject("timesList", timesList);
		String style = CookieUtils.getCookie(request, "_change_style");
		if(StringUtils.isBlank(style)){
			style = "white";
		}
		model.addObject("style", style);
		return model;
	}



}
