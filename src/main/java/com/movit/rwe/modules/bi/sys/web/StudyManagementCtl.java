package com.movit.rwe.modules.bi.sys.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.movit.rwe.modules.bi.base.entity.mysql.*;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.movit.rwe.common.web.BaseController;
import com.movit.rwe.modules.bi.sys.service.StudyManagementService;
import com.movit.rwe.modules.bi.sys.service.TabIconService;
import com.movit.rwe.modules.bi.sys.vo.StudyVo;
import com.movit.rwe.modules.bi.sys.vo.ViewVo;
import com.movit.rwe.modules.sys.entity.User;
import com.movit.rwe.modules.sys.utils.UserUtils;

/**
 * 
 * @Title: StudyManagementCtl.java
 * @Package com.movit.rwe.modules.bi.dashboard.web
 * @Description: study管理
 * @author Yuri.Zheng
 * @date 2016年4月7日 下午1:55:10
 */
@Controller
@RequestMapping(value = "${biPath}/sys/studyManagment")
public class StudyManagementCtl extends BaseController {

	@Autowired
	private StudyManagementService studyManagementService;
	@Autowired
	private TabIconService tabIconService;
	/**
	 * 
	 * @Title: index @Description: study管理页面 @param @param
	 *         taId @param @return @return ModelAndView @throws
	 */
	@RequestMapping("/index")
	public ModelAndView index(String taId) {
		ModelAndView mav = new ModelAndView("modules/bi/sys/studyManagment");

		List<EtlTaConfig> taList = studyManagementService.getAllTaConfig();

		List<StudyVo> studyVoList = studyManagementService.getStudyByTaId(taId);

		mav.addObject("taId", taId);
		mav.addObject("taList", taList);
		mav.addObject("studyVoList", studyVoList);
		return mav;
	}

	/**
	 * 
	 * @Title: tabList @Description: study下的tab页面 @param @param
	 *         dashboardId @param @return @return ModelAndView @throws
	 */
	@RequestMapping("/tabList")
	public ModelAndView tabList(String dashboardId, String studyName) {
		ModelAndView mav = new ModelAndView("modules/bi/sys/tabList");
		List<Tab> tabList = studyManagementService.findAllTabByDashboardId(dashboardId);

		mav.addObject("tabList", tabList);
		mav.addObject("dashboardId", dashboardId);
		mav.addObject("studyName", studyName);
		return mav;
	}

	/**
	 * 
	 * @Title: createTabIndex @Description: study新增tab页面 @param @param
	 *         dashboardId @param @return @return ModelAndView @throws
	 */
	@RequestMapping("/createTabIndex")
	public ModelAndView createTabIndex(String dashboardId) {
		ModelAndView mav = new ModelAndView("modules/bi/sys/createTab");

		Integer sort = studyManagementService.findMaxTabSort(dashboardId);
		List <TabIcon> tabIconList = tabIconService.findList(new TabIcon());
		mav.addObject("maxSort", ++sort);
		mav.addObject("dashboardId", dashboardId);
		mav.addObject("tabIconList", tabIconList);
		return mav;
	}

	/**
	 * 
	 * @Title: createTab @Description: 新增tab @param @param
	 *         dashboardId @param @param tabName @param @param
	 *         sort @param @return @return ModelAndView @throws
	 */
	@RequestMapping("/createTab")
	public ModelAndView createTab(String dashboardId, String tabName, String iconId,@RequestParam(defaultValue = "0") Integer sort) {
		ModelAndView mav = new ModelAndView(new RedirectView("tabList"));
		User user = UserUtils.getUser();

		studyManagementService.createTab(dashboardId, user, tabName, iconId, sort);

		mav.addObject("dashboardId", dashboardId);
		return mav;
	}

	/**
	 * 
	 * @Title: dropTab @Description: ajax删除tab @param @param
	 *         tabId @param @return @return Map<String,Object> @throws
	 */
	@RequestMapping("/dropTab")
	@ResponseBody
	public Map<String, Object> dropTab(String tabId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		User user = UserUtils.getUser();

		studyManagementService.dropTab(user, tabId);
		return resultMap;
	}

	@RequestMapping("/sortTab")
	@ResponseBody
	public Map<String, Object> sortTab(String tabId, Integer tabSort, String prevTabId, Integer prevTabSort) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		User user = UserUtils.getUser();

		studyManagementService.sortTab(user, tabId, tabSort, prevTabId, prevTabSort);
		return resultMap;
	}

	@RequestMapping("/viewList")
	public ModelAndView viewList(String tabId, String tabName, String dashboardId, String studyName) {
		ModelAndView mav = new ModelAndView("modules/bi/sys/viewList");

		List<ViewVo> tabViewList = studyManagementService.findAllViewByTabId(tabId);
		List<View> viewList = studyManagementService.findAllView();
		List<DaView> daViewList = studyManagementService.findDaViewByStudyId(dashboardId);

		mav.addObject("tabId", tabId);
		mav.addObject("dashboardId", dashboardId);
		mav.addObject("tabViewList", tabViewList);
		mav.addObject("viewList", viewList);
		mav.addObject("tabName", tabName);
		mav.addObject("studyName", studyName);
		mav.addObject("daViewList", daViewList);
		return mav;
	}

	@RequestMapping("/addView")
	@ResponseBody
	public Map<String, Object> addView(String tabId, String viewId, String dashboardId,int viewType,int size) {
		User user = UserUtils.getUser();

		return studyManagementService.addView(user, tabId, viewId, dashboardId,viewType,size);
	}
	
	@RequestMapping("/sortView")
	@ResponseBody
	public Map<String,Object> sortView(String[] tabViewIdArr,String tabId){
		studyManagementService.sortView(tabViewIdArr,tabId);
		return null;
	}

	@RequestMapping("/dropView")
	@ResponseBody
	public Map<String, Object> dropView(String tabViewId) {
		User user = UserUtils.getUser();

		return studyManagementService.dropView(user, tabViewId);
	}

	@RequestMapping("/configTabView")
	@ResponseBody
	public Map<String, Object> configTabView(String tabViewId, String configJson, String alias, String viewChartHeight) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			configJson = StringEscapeUtils.unescapeHtml(configJson);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		studyManagementService.configTabView(tabViewId, configJson, alias,viewChartHeight);
		resultMap.put("success", true);
		return resultMap;
	}

	@RequestMapping("/modifyTab")
	public ModelAndView modifyTab(String tabId, String tabName, String studyName, String dashboardId) {
		ModelAndView mav = new ModelAndView("modules/bi/sys/modifyTab");

		Tab tab = studyManagementService.findTab(tabId);
		
		List <TabIcon> tabIconList = tabIconService.findList(new TabIcon());
		
		mav.addObject("tab", tab);
		mav.addObject("studyName", studyName);
		mav.addObject("dashboardId", dashboardId);
		mav.addObject("tabIconList", tabIconList);
		return mav;
	}

	@RequestMapping("/updateTab")
	public ModelAndView updateTab(String id, String tabName, String dashboardId, String iconId,@RequestParam(defaultValue = "0") Integer sort) {
		ModelAndView mav = new ModelAndView(new RedirectView("tabList"));
		User user = UserUtils.getUser();
        
		studyManagementService.updateTab(id, user, tabName, iconId,sort);

		mav.addObject("dashboardId", dashboardId);
		return mav;
	}

	@RequestMapping("/updateStudyTaName")
	@ResponseBody
	public Map<String, Object> updateStudyTaName(String studyId, String taId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(StringUtils.isBlank(taId)){
			taId = null;
		}
		studyManagementService.updateStudyTaName(studyId, taId, UserUtils.getUser().getId(), new Date());
		resultMap.put("success", true);
		return resultMap;
	}
}
