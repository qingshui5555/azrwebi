package com.movit.rwe.modules.bi.dashboard.web;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.movit.rwe.modules.bi.base.entity.hive.HiveStudy;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.movit.rwe.common.utils.I18NUtils;
import com.movit.rwe.common.web.BaseController;
import com.movit.rwe.modules.bi.base.entity.mysql.Study;
import com.movit.rwe.modules.bi.dashboard.common.ViewInvokeParams;
import com.movit.rwe.modules.bi.dashboard.service.DashBoardService;
import com.movit.rwe.modules.bi.dashboard.vo.StudyDashBoardShowVo;
import com.movit.rwe.modules.bi.sys.service.UserStudyService;
import com.movit.rwe.modules.bi.sys.vo.ViewVo;
import com.movit.rwe.modules.sys.utils.UserUtils;

@Controller
@RequestMapping(value = "${biPath}/dashBoard/")
public class DashBoardCtl extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(DashBoardCtl.class);

	@Autowired
	DashBoardService dashBoardService;

	@Autowired
    UserStudyService userStudyService;

	@Autowired
	I18NUtils i18NUtils;

	@RequestMapping("{studyId}")
	public ModelAndView showChartById(@PathVariable("studyId") String studyId, ModelAndView model, String lang){
		logger.info("@@@In DashBoardCtl.showChartById begin, studyId: " + studyId);
		String redirectStudyId = studyId + "";
		List<HiveStudy> studyList = userStudyService.queryListStudyByUserId(UserUtils.getPrincipal().getId());
        StudyDashBoardShowVo dashBoardShow = null;
        if(null != studyList && studyList.size() > 0){
            for(HiveStudy study: studyList){
                if(study.getGuid().equals(studyId)){
					dashBoardShow =  dashBoardService.getStudyDashBoardShowVoByStudy(study);
                }
            }
        }
		model.addObject("dashBoardShow", dashBoardShow);
		if(StringUtils.isNotBlank(lang)){
			if(Locale.CHINESE.toString().equals(lang)||Locale.CHINA.toString().equals(lang)){
	        	model.addObject("lang", Locale.CHINESE.toString());
			}else{
				model.addObject("lang", Locale.ENGLISH.toString());
			}
			model.addObject("redirectStudyId", redirectStudyId);
		}

		model.setViewName("modules/bi/dashboard/dashBoard");
		logger.info("@@@In DashBoardCtl.showChartById end");
		return model;

	}

	@RequestMapping("tab/{tabId}")
	public ModelAndView showTabById(ViewInvokeParams vip,ModelAndView model,@PathVariable("tabId") String tabId){
		vip.setTabId(tabId);
		List<ViewVo> viewList = dashBoardService.findShowViewListBuTabId(tabId);
		model.addObject("viewList", viewList);
		model.addObject("vip", vip);
		model.setViewName("modules/bi/dashboard/tab");
		return model;
	}

	/**
	 *
	  * patientsCount
	  * TODO(这里描述这个方法适用条件 – 可选)
	  * TODO(这里描述这个方法的执行流程 – 可选)
	  * TODO(这里描述这个方法的使用方法 – 可选)
	  * TODO(这里描述这个方法的注意事项 – 可选)
	  *
	  * @Title: patientsCount
	  * @Description: 统计下选择的cohort&group下一共有多少用户（去重）
	  * @param @param vip
	  * @param @param studyId
	  * @param @return
	  * @return Object    返回类型
	  * @throws
	 */
	@RequestMapping("{studyId}/patientsCount")
	@ResponseBody
	public Map<String, Integer> patientsCount(ViewInvokeParams vip,@PathVariable("studyId") String studyId){
		Integer patientsCount = dashBoardService.countPatientByGroups(vip.getCohorts(), vip.getGroups());
		Map<String, Integer> result = new HashMap<String, Integer>();
		result.put("patientsCount", patientsCount);
		return result;
	}

	@RequestMapping("export/pdf/review")
	public ModelAndView exportPdf(ModelAndView model,ViewInvokeParams vip, String studyId, HttpServletRequest request) throws Exception{
		List<HiveStudy> studyList = userStudyService.queryListStudyByUserId(UserUtils.getPrincipal().getId());
		StudyDashBoardShowVo dashBoardShow = null;
		if(null != studyList && studyList.size() > 0){
			for(HiveStudy study: studyList){
				if(study.getGuid().equals(studyId)){
					dashBoardShow =  dashBoardService.getStudyDashBoardShowVoByStudy(study);
				}
			}
		}
		model.addObject("dashBoardShow", dashBoardShow);
		model.setViewName("modules/bi/dashboard/pdfReview");
		return model;
	}

}
