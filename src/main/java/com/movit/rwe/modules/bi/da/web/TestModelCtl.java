package com.movit.rwe.modules.bi.da.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.movit.rwe.common.web.BaseController;
import com.movit.rwe.modules.bi.base.entity.mysql.DaTestModel;
import com.movit.rwe.modules.bi.da.service.TestModelService;
import com.movit.rwe.modules.bi.da.test.TestAbs;
import com.movit.rwe.modules.bi.da.test.TestFactory;
import com.movit.rwe.modules.bi.da.test.TestHelper;
import com.movit.rwe.modules.bi.da.test.dataframe.DataFrameInvoke;
import com.movit.rwe.modules.bi.da.test.dataframe.DataFrameResults;
import com.movit.rwe.modules.bi.da.test.model.TestModel;
import com.movit.rwe.modules.bi.da.test.model.TestModelInvoke;
import com.movit.rwe.modules.bi.da.test.rresult.DaException;

@Controller
@RequestMapping(value = "${biPath}/da/model/")
public class TestModelCtl extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(TestModelCtl.class);

	Gson gson = new Gson();

	@Autowired
	TestModelService testModelService;
	@Autowired
	private TestHelper testHelper;

	@RequestMapping("choose/{testName}")
	public ModelAndView list(ModelAndView model, @PathVariable("testName") String testName) {

		TestAbs test = TestFactory.getTestByTestName(testName);

		if (test.isNeedTestModel()) {
			List<TestModel> modelList = testModelService.findListByTestName(testName);
			model.addObject("modelList", modelList);
		}

		model.addObject("test", test);
		model.setViewName("modules/bi/da/testModel/testModels");
		return model;
	}

	@RequestMapping("extendAtt/{testName}")
	public ModelAndView extendAtt(ModelAndView model, @PathVariable("testName") String testName) {
		TestAbs test = TestFactory.getTestByTestName(testName);
		model.addObject("test", test);
		model.setViewName(test.getTestModelExtendAttViewUrl());
		return model;
	}

	@RequestMapping("excute")
	public ModelAndView excute(ModelAndView model, HttpServletRequest request) {
		try{
			TestModel testModel = this.excute2TestModel(request);
	
			model.addObject("testModel", testModel);
			model.setViewName(testModel.getTestInstance().getTestModelResultViewUrl());
		
		} catch (Exception ex) {
			model.setViewName("modules/bi/da/testModel/result/error");
			model.addObject("daEx", new DaException(ex.getMessage(), "da.test.invoke.exception.default", ex));
			logger.error(ex.toString());
			ex.printStackTrace();
		}
		return model;
	}

	@RequestMapping("save")
	@ResponseBody
	public Object save(ModelAndView model, HttpServletRequest request) {

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("isSuccess", false);
		try {
			TestModel testModel = this.excute2TestModel(request);
			if (testModelService.insert(testModel)) {
				result.put("isSuccess", true);
			} else {
				result.put("message", "保存失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("message", e.getMessage());
			logger.error(e.toString());
			e.printStackTrace();
		}
		return result;
	}

	private TestModel excute2TestModel(HttpServletRequest request) {

		TestModelInvoke tmi = testHelper.toTestModelInvoke(request);

		TestAbs test = TestFactory.getTestByTestName(tmi.getDataFtameInvoke().getTestName());

		if (test.isNeedTestModelExtendAtt()) {
			tmi.setTestModelTestExtendAtt(test.getTestModelExtendAtt(request));
		}

		DataFrameResults results = test.loadDataFrameResult(tmi.getDataFtameInvoke());

		TestModel testModel = test.invokeTestModel(results, tmi);

		testModel.setName(tmi.getName());
		testModel.setRemarks(tmi.getRemarks());
		testModel.setDaTestName(test.getTestName());

		return testModel;

	}

}
