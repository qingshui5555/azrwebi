package com.movit.rwe.modules.bi.dashboard.web;

import com.movit.rwe.common.web.BaseController;
import com.movit.rwe.modules.bi.base.entity.hive.HiveStudy;
import com.movit.rwe.modules.bi.base.entity.mysql.Study;
import com.movit.rwe.modules.bi.dashboard.common.ViewInvokeParams;
import com.movit.rwe.modules.bi.dashboard.service.DashBoardService;
import com.movit.rwe.modules.bi.dashboard.vo.StudyDashBoardShowVo;
import com.movit.rwe.modules.bi.sys.service.UserStudyService;
import com.movit.rwe.modules.bi.sys.vo.ViewVo;
import com.movit.rwe.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "${biPath}/dashBoardForPad/")
public class DashBoardForPadCtl extends BaseController {
	
	@Autowired
	DashBoardService dashBoardService;

	@Autowired
    UserStudyService userStudyService;
	
	@RequestMapping("{studyId}/{userId}/{locale}")
	public ModelAndView showChartById(ModelAndView model,@PathVariable("studyId") String studyId, @PathVariable("userId") String userId, @PathVariable("locale") String locale){
		List<HiveStudy> studyList = userStudyService.queryListStudyByUserId(userId);
        StudyDashBoardShowVo dashBoardShow = null;
        if(null != studyList && studyList.size() > 0){
            for(HiveStudy study: studyList){
                if(study.getGuid().equals(studyId)){
					dashBoardShow =  dashBoardService.getStudyDashBoardShowVoByStudy(study);
                }
            }

        }
		model.addObject("dashBoardShow", dashBoardShow);
        model.addObject("locale", locale);
        model.addObject("isPad", "1");
		model.setViewName("modules/bi/dashboard/dashBoardForPad");
		return model;
		
	}
	
	@RequestMapping("tab/{tabId}")
	public ModelAndView showTabById(ViewInvokeParams vip,ModelAndView model,@PathVariable("tabId") String tabId){
		vip.setTabId(tabId);
		List<ViewVo> viewList = dashBoardService.findShowViewListBuTabId(tabId);
		model.addObject("viewList", viewList);
		model.addObject("vip", vip);
		model.setViewName("modules/bi/dashboard/tabForPad");
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
	public Map<String, Integer> patientsCount(ViewInvokeParams vip, @PathVariable("studyId") String studyId){
		Integer patientsCount = dashBoardService.countPatientByGroups(vip.getCohorts(), vip.getGroups());
		Map<String, Integer> result = new HashMap<String, Integer>();
		result.put("patientsCount", patientsCount);
		return result;
	}

}
