package com.movit.rwe.modules.bi.dashboard.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.movit.rwe.common.config.GlobalConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.movit.rwe.common.web.BaseController;
import com.movit.rwe.modules.bi.base.entity.mysql.DaView;
import com.movit.rwe.modules.bi.base.entity.mysql.TabView;
import com.movit.rwe.modules.bi.base.entity.mysql.View;
import com.movit.rwe.modules.bi.da.test.TestAbs;
import com.movit.rwe.modules.bi.da.test.TestFactory;
import com.movit.rwe.modules.bi.da.test.dataframe.DataFrameInvoke;
import com.movit.rwe.modules.bi.da.test.rresult.DaException;
import com.movit.rwe.modules.bi.da.test.rresult.RResult;
import com.movit.rwe.modules.bi.dashboard.common.ViewInvokeParams;
import com.movit.rwe.modules.bi.dashboard.service.TabViewService;
import com.movit.rwe.modules.bi.dashboard.service.ViewService;

@Controller
@RequestMapping(value = "${biPath}/view/")
public class ViewCtl extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(ViewCtl.class);

	@Autowired
	ViewService viewService;
	@Autowired
	private TabViewService tabViewService;
	
	Gson gson = new Gson();

	private static final String DA_TEST_INVOKE_EXCEPTION_DEFAULT = "da.test.invoke.exception.default";

	@RequestMapping("{viewId}")
	public ModelAndView showChartById(@PathVariable("viewId") String viewId, ViewInvokeParams viewInvokeParams, RedirectAttributes redirectAttributes){
		logger.info("@@@In ViewCtl.showChartById begin, viewId: " + viewId);
		View view = viewService.get(viewId);
		viewInvokeParams.setViewId(viewId);
		String mappingUrl = view.getMappingUrl();
		
		ModelAndView model = new ModelAndView(mappingUrl);

		if(mappingUrl.startsWith(GlobalConstant.STR_REDIRECT_PREFIX)){
			redirectAttributes.addAttribute(GlobalConstant.STR_ATTR_COHORTS, viewInvokeParams.getCohorts());
			redirectAttributes.addAttribute(GlobalConstant.STR_ATTR_GROUPS, viewInvokeParams.getGroups());
			redirectAttributes.addAttribute(GlobalConstant.STR_ATTR_STUDY_ID, viewInvokeParams.getStudyId());
			redirectAttributes.addAttribute(GlobalConstant.STR_ATTR_TAB_ID, viewInvokeParams.getTabId());
			redirectAttributes.addAttribute(GlobalConstant.STR_ATTR_VIEW_ID, viewId);
			redirectAttributes.addAttribute(GlobalConstant.STR_ATTR_PATIENT_COUNT, viewInvokeParams.getPatientCount());
			redirectAttributes.addAttribute(GlobalConstant.STR_ATTR_ALL_PATIENTS_FLAG, viewInvokeParams.getAllPatientsFlag());
			redirectAttributes.addAttribute(GlobalConstant.STR_ATTR_IS_PAD, viewInvokeParams.getIsPad());
			redirectAttributes.addAttribute(GlobalConstant.STR_ATTR_TAB_VIEW_ID, viewInvokeParams.getTabViewId());
		} else {
			model.addObject("vip", viewInvokeParams);
		}
		logger.info("@@@In ViewCtl.showChartById end");
		return model;
	}

	@RequestMapping("da/{daViewId}")
	public ModelAndView showDaViewById(@PathVariable("daViewId") String daViewId,ViewInvokeParams vip,ModelAndView model){
		try {
			DaView daView = viewService.findDaViewById(daViewId);
			DataFrameInvoke dfi = viewService.getDataFrameInvoke(daView, vip);
			
			TestAbs test = TestFactory.getTestByTestName(dfi.getTestName());
			RResult rResult = test.testInvoke(dfi);
			model.addObject("rResult", rResult);
			model.addObject("dfi", dfi);
			model.addObject("daView", daView);
			model.addObject("isPad", vip.getIsPad());
			model.setViewName(test.getTestResultViewModelUrl());
		} catch (DaException daEx) {
			model.setViewName("modules/bi/da/result/error");
			model.addObject("daEx", daEx);
			logger.error(daEx.toString());
			daEx.printStackTrace();
		} catch (Exception ex) {
			model.setViewName("modules/bi/da/result/error");
			model.addObject("daEx", new DaException(ex.getMessage(), DA_TEST_INVOKE_EXCEPTION_DEFAULT, ex));
			logger.error(ex.toString());
			ex.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping("da/{daViewId}/edit")
	public ModelAndView exportPdf(@PathVariable("daViewId") String daViewId,String tabViewId, ModelAndView model) throws Exception{
		DaView dv = viewService.findDaViewById(daViewId);
		DataFrameInvoke dfi = gson.fromJson(dv.getConfJsonStr(), DataFrameInvoke.class);
		TestAbs test = TestFactory.getTestByTestName(dfi.getTestName());
		model.addObject("daView", dv);
		model.addObject("dfi", dfi);
		model.addObject("test", test);
		model.addObject("tabViewId", tabViewId);
		model.setViewName("modules/bi/dashboard/daViewEdit");
		return model;
	}
	
	@RequestMapping("showDaViewByViewId")
	@ResponseBody
	public Object showDaViewByViewId(ModelAndView model,String daViewId) {
		logger.info("@@@In ViewCtl.showDaViewByViewId begin, viewId: " + daViewId);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("isSuccess", false);
		try {
			TabView tabView = new TabView();
			tabView.setViewId(daViewId);
			List <TabView>tabViewList = tabViewService.findList(tabView);
			result.put("isSuccess", true);
			result.put("tabViewList", tabViewList);
		} catch (Exception e) {
			result.put("message", e.getMessage());
			logger.error(e.toString());
			e.printStackTrace();
		}
		logger.info("@@@In ViewCtl.showDaViewByViewId end");
		return result;
	}
}
