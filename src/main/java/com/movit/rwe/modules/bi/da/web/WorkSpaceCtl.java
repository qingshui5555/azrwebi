package com.movit.rwe.modules.bi.da.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.movit.rwe.common.web.BaseController;
import com.movit.rwe.modules.bi.base.entity.mysql.Study;
import com.movit.rwe.modules.bi.base.entity.mysql.Ta;
import com.movit.rwe.modules.bi.da.enums.DaTestEnum;
import com.movit.rwe.modules.bi.da.service.DaService;
import com.movit.rwe.modules.bi.da.service.WorkSpaceService;
import com.movit.rwe.modules.bi.da.test.TestAbs;
import com.movit.rwe.modules.bi.da.test.TestFactory;
import com.movit.rwe.modules.bi.da.test.TestHelper;
import com.movit.rwe.modules.bi.da.test.dataframe.DataFrameInvoke;
import com.movit.rwe.modules.bi.da.vo.WorkSpaceDaViewShowVo;
import com.movit.rwe.modules.bi.sys.service.StudyService;
import com.movit.rwe.modules.bi.sys.service.TaService;
import com.movit.rwe.modules.sys.utils.UserUtils;

@Controller
@RequestMapping(value = "${biPath}/ws/")
public class WorkSpaceCtl extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(WorkSpaceCtl.class);
	
	@Autowired
	WorkSpaceService workSpaceService;
	@Autowired
	private TaService taService;
	@Autowired
	private StudyService studyService;
	@Autowired
	private TestHelper testHelper;
	@Autowired
	private DaService daService;
	
	@RequestMapping("list")
	public ModelAndView list(ModelAndView model) {

		//List<WorkSpaceDaViewShowVo> daViewList = workSpaceService.findAllWsShowList();
		List<WorkSpaceDaViewShowVo> daViewList = 
				workSpaceService.findAllWsShowListByUserId(UserUtils.getUser().getId());
		
		model.addObject("daViewList", daViewList);

		model.setViewName("modules/bi/da/wsList");
		return model;
	}

	@RequestMapping("delete")
	@ResponseBody
	public Object delete(ModelAndView model,HttpServletRequest request,String daViewId) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("isSuccess", false);
		try {
			boolean resultSuccess = workSpaceService.deleteDaView(daViewId);
			result.put("isSuccess", resultSuccess);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("message", e.getMessage());
		}
		return result;
	}
	
	@RequestMapping("edit")
	public ModelAndView edit(ModelAndView model,String daViewId){
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("isSuccess", false);
		try {
			WorkSpaceDaViewShowVo workSpaceDaView = workSpaceService.getDaView(daViewId);
			List<Ta> taList = taService.findList();
			List<Study> studyList = studyService.findAllStudy();
			DaTestEnum[] testArray = DaTestEnum.values();

			model.addObject("taList", taList);
			model.addObject("studyList", studyList);
			model.addObject("testArray", testArray);
			model.addObject("workSpaceDaView",workSpaceDaView);
			model.setViewName("modules/bi/da/wsEdit");
			result.put("isSuccess", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("message", e.getMessage());
		}
		model.addObject("result",result);
		return model;
	}
	
	@RequestMapping("update")
	@ResponseBody
	public Object update(ModelAndView model, HttpServletRequest request,String title,String remarks,String daViewId) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("isSuccess", false);
		try {
			DataFrameInvoke invoke = testHelper.toDataFrameInvoke(request);
			TestAbs test = TestFactory.getTestByTestName(invoke.getTestName());
			invoke.setExtAtt(test.getTestExtendAtt(request));
			
			boolean resultSuccess = daService.daViewUpdateToWorkSpace(invoke, title, remarks,daViewId);
			result.put("isSuccess", resultSuccess);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("message", e.getMessage());
		}
		return result;
	}
}
