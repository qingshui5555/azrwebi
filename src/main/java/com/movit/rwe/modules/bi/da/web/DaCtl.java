package com.movit.rwe.modules.bi.da.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

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

import com.google.gson.Gson;
import com.movit.rwe.common.web.BaseController;
import com.movit.rwe.modules.bi.base.dao.mysql.PatientGroupDao;
import com.movit.rwe.modules.bi.base.entity.mysql.BmdModelColumnMeta;
import com.movit.rwe.modules.bi.base.entity.mysql.BmdModelMeta;
import com.movit.rwe.modules.bi.base.entity.mysql.DaView;
import com.movit.rwe.modules.bi.base.entity.mysql.PatientGroup;
import com.movit.rwe.modules.bi.base.entity.mysql.Study;
import com.movit.rwe.modules.bi.base.entity.mysql.Ta;
import com.movit.rwe.modules.bi.da.enums.DaTestEnum;
import com.movit.rwe.modules.bi.da.enums.ParamSetTypeEnum;
import com.movit.rwe.modules.bi.da.service.DaService;
import com.movit.rwe.modules.bi.da.test.TestAbs;
import com.movit.rwe.modules.bi.da.test.TestFactory;
import com.movit.rwe.modules.bi.da.test.TestHelper;
import com.movit.rwe.modules.bi.da.test.dataframe.DataFrameInvoke;
import com.movit.rwe.modules.bi.da.test.dataframe.DataFrameResults;
import com.movit.rwe.modules.bi.da.test.rresult.DaException;
import com.movit.rwe.modules.bi.da.test.rresult.RResult;
import com.movit.rwe.modules.bi.da.test.set.TestExtendAtt;
import com.movit.rwe.modules.bi.dataanalysis.service.BmdModelColumnMetaService;
import com.movit.rwe.modules.bi.dataanalysis.service.BmdModelMetaService;
import com.movit.rwe.modules.bi.sys.service.StudyService;
import com.movit.rwe.modules.bi.sys.service.TaService;
import com.movit.rwe.modules.bi.sys.service.UserStudyService;
import com.movit.rwe.modules.sys.utils.UserUtils;

@Controller
@RequestMapping(value = "${biPath}/da/")
public class DaCtl extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(DaCtl.class);
	
	Gson gson = new Gson();

	private static final String DA_TEST_INVOKE_EXCEPTION_DEFAULT = "da.test.invoke.exception.default";

	@Autowired
	private TaService taService;
	@Autowired
	private StudyService studyService;
	@Autowired
	private BmdModelMetaService bmdModelMetaService;
	@Autowired
	private BmdModelColumnMetaService bmdModelColumnMetaService;
	@Autowired
	private PatientGroupDao patientGroupDao;
	@Autowired
	private TestHelper testHelper;
	@Autowired
	private DaService daService;
	@Autowired
	private UserStudyService userStudyService;

	@RequestMapping("index")
	public ModelAndView list(ModelAndView model) {
		List<Ta> taAllList = taService.findList();
		//List<Study> studyList = studyService.findAllStudy();
		//获取当前用户拥有权限的study
		List<HiveStudy> studyList = userStudyService.queryListStudyByUserId(UserUtils.getUser().getId());
		//根据study来过滤对应ta
		Set<String> set=new HashSet<String>();
		for(HiveStudy study : studyList){
			Ta ta = taService.getByStudyId(study.getGuid());
			if(ta != null){
				study.setTaId(ta.getId());
				set.add(ta.getId() + "");
			}
		}
		List<Ta> taList = new ArrayList<Ta>();
		for(String taId:set){
			for(Ta ta:taAllList){
				if(taId.equals(ta.getId())){
					taList.add(ta);
				}
			}
		}
		
		DaTestEnum[] testArray = DaTestEnum.values();
		// List modelList = bmdModelMetaService.findAllList();

		model.addObject("taList", taList);
		model.addObject("studyList", studyList);
		model.addObject("testArray", testArray);
		// model.addObject("modelList",modelList);

		model.setViewName("modules/bi/da/index");
		return model;
	}

	@RequestMapping("cohorts")
	public ModelAndView conhots(ModelAndView model, String studyId) {
		List<PatientGroup> cohortList = patientGroupDao.findAll(studyId);
		model.setViewName("modules/bi/da/cohorts");
		model.addObject("cohortList", cohortList);
		return model;
	}

	@RequestMapping("paramset")
	public ModelAndView paramSet(ModelAndView model, String testName, String studyId) {
		DaTestEnum de = DaTestEnum.loadByName(testName);
		TestAbs test = TestFactory.getTestByBeanName(de.getBeanName());
		ParamSetTypeEnum pstu = test.getParamSetType();
		model.setViewName("redirect:" + pstu.getSetCtlUrl() + "?testName=" + testName + "&studyId=" + studyId);
		return model;
	}

	@RequestMapping("paramset/loadModelColumn")
	@ResponseBody
	public List<BmdModelColumnMeta> getModelColumnListByModelId(String modelMetaId) {
		if(StringUtils.isNotBlank(modelMetaId)){
			return bmdModelColumnMetaService.loadByModelMetaId(modelMetaId);
		}else{
			return null;
		}
	}

	@RequestMapping("paramset/default")
	public ModelAndView paramSetForDefault(ModelAndView model, String testName, String studyId) {
		model.setViewName("modules/bi/da/paramSet/default");

		List<BmdModelMeta> modelList = bmdModelMetaService.findAllList(studyId);
		DaTestEnum de = DaTestEnum.loadByName(testName);
		TestAbs test = TestFactory.getTestByBeanName(de.getBeanName());
		ParamSetTypeEnum pstu = test.getParamSetType();

		model.addObject("modelList", modelList);
		model.addObject("test", test);
		model.addObject("testEnum", de);
		model.addObject("pstu", pstu);
		return model;
	}

	@RequestMapping("paramset/survival")
	public ModelAndView paramSetForSurvival(ModelAndView model, String testName, String studyId) {
		model.setViewName("modules/bi/da/paramSet/survival");

		List<BmdModelMeta> modelList = bmdModelMetaService.findAllList(studyId);
		DaTestEnum de = DaTestEnum.loadByName(testName);
		TestAbs test = TestFactory.getTestByBeanName(de.getBeanName());
		ParamSetTypeEnum pstu = test.getParamSetType();

		model.addObject("modelList", modelList);
		model.addObject("test", test);
		model.addObject("testEnum", de);
		model.addObject("pstu", pstu);
		return model;
	}
	
	@RequestMapping("paramset/anova")
	public ModelAndView paramSetForAnova(ModelAndView model, String testName, String studyId) {
		model.setViewName("modules/bi/da/paramSet/anova");

		List<BmdModelMeta> modelList = bmdModelMetaService.findAllList(studyId);
		DaTestEnum de = DaTestEnum.loadByName(testName);
		TestAbs test = TestFactory.getTestByBeanName(de.getBeanName());
		ParamSetTypeEnum pstu = test.getParamSetType();

		model.addObject("modelList", modelList);
		model.addObject("test", test);
		model.addObject("testEnum", de);
		model.addObject("pstu", pstu);
		return model;
	}

	@RequestMapping("dataframe")
	public ModelAndView loadDataFrame(ModelAndView model, HttpServletRequest request) {

		model.setViewName("modules/bi/da/dataFrame");

		DataFrameInvoke invoke = testHelper.toDataFrameInvoke(request);

		TestAbs test = TestFactory.getTestByTestName(invoke.getTestName());
		DataFrameResults results = test.loadDataFrameResult(invoke);
		model.addObject("results", results);

		return model;
	}

	@RequestMapping("extendAtt")
	public ModelAndView loadExtendAtt(HttpServletRequest request, ModelAndView model, String testName,String daViewId,String reRunTestName) {

		DaTestEnum de = DaTestEnum.loadByName(testName);
		TestAbs test = TestFactory.getTestByBeanName(de.getBeanName());
		
		if(!StringUtils.isEmpty(daViewId)){
			DaView daView = daService.get(daViewId);
			model.addObject("daView", daView);
			DataFrameInvoke dfi = this.DaView2DataFrameInvoke(daView);
			//如果dashboard中上次执行的方法与本次执行的相同则加载上次执行的扩展参数到执行页面
			if(testName.equals(reRunTestName)){
				TestExtendAtt tea = test.getTestExtendAtt(request);
				dfi.setExtAtt(tea);
			}
			model.addObject("dfi", dfi);
		}

		model.addObject("test", test);
		model.addObject("testEnum", de);
		model.setViewName(test.getTestExtendAttViewModelUrl());

		return model;
	}

	@RequestMapping("execute")
	public ModelAndView executeTest(ModelAndView model, HttpServletRequest request) {

		try {
			DataFrameInvoke invoke = testHelper.toDataFrameInvoke(request);
			TestAbs test = TestFactory.getTestByTestName(invoke.getTestName());
			invoke.setExtAtt(test.getTestExtendAtt(request));

			DataFrameResults results = test.loadDataFrameResult(invoke);
			RResult rResult = test.testInvoke(invoke,results);

			model.addObject("rResult", rResult);
			model.addObject("dfi", invoke);
			model.addObject("dataFrameResults", results);
			model.setViewName(test.getTestResultViewModelUrl());
		} catch (DaException daEx) {
			model.setViewName("modules/bi/da/result/error");
			model.addObject("daEx", daEx);
			model.addObject("daExMessage", daEx.getMessage());
			logger.error(daEx.toString());
			daEx.printStackTrace();
		} catch (Exception ex) {
			model.setViewName("modules/bi/da/result/error");
			model.addObject("daEx", new DaException(ex.getMessage(), DA_TEST_INVOKE_EXCEPTION_DEFAULT, ex));
			model.addObject("daExMessage", ex.getMessage());
			logger.error(ex.toString());
			ex.printStackTrace();
		}
		return model;
	}
	
	/**
	 * 
	  * executeTest
	  *
	  * @Title: executeTest
	  * @Description: 基于传入的test扩展属性进行二次计算
	  * @param @param model
	  * @param @param daViewId
	  * @param @param request
	  * @param @return    
	  * @return ModelAndView    返回类型
	  * @throws
	 */
	@RequestMapping("execute/{daViewId}/extendAtt")
	public ModelAndView executeTest(ModelAndView model,@PathVariable("daViewId")String daViewId,String testName, HttpServletRequest request) {

		try {
			
			DaTestEnum de = DaTestEnum.loadByName(testName);
			TestAbs test = TestFactory.getTestByBeanName(de.getBeanName());
			DaView daView = daService.get(daViewId);
			DataFrameInvoke invoke = this.DaView2DataFrameInvoke(daView);
			invoke.setExtAtt(test.getTestExtendAtt(request));

			RResult rResult = test.testInvoke(invoke);

			model.addObject("rResult", rResult);
			model.addObject("dfi", invoke);
			model.addObject("daView", daView);
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

	@RequestMapping("save")
	@ResponseBody
	public Object save(ModelAndView model, HttpServletRequest request,String title,String remarks) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("isSuccess", false);
		try {
			DataFrameInvoke invoke = testHelper.toDataFrameInvoke(request);
			TestAbs test = TestFactory.getTestByTestName(invoke.getTestName());
			invoke.setExtAtt(test.getTestExtendAtt(request));
			
			boolean resultSuccess = daService.daViewSaveToWorkSpace(invoke, title, remarks);
			result.put("isSuccess", resultSuccess);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("message", e.getMessage());
		}
		return result;
	}
	
	
	private DataFrameInvoke DaView2DataFrameInvoke(DaView daView){
		return gson.fromJson(daView.getConfJsonStr(),DataFrameInvoke.class);
	}


	@RequestMapping("paramset/linearRegression")
	public ModelAndView paramSetForLinearRegression(ModelAndView model, String testName, String studyId) {
		model.setViewName("modules/bi/da/paramSet/linearRegression");

		List<BmdModelMeta> modelList = bmdModelMetaService.findAllList(studyId);
		DaTestEnum de = DaTestEnum.loadByName(testName);
		TestAbs test = TestFactory.getTestByBeanName(de.getBeanName());
		ParamSetTypeEnum pstu = test.getParamSetType();

		model.addObject("modelList", modelList);
		model.addObject("test", test);
		model.addObject("testEnum", de);
		model.addObject("pstu", pstu);
		return model;
	}
	
	@RequestMapping("paramset/logisticRegression")
	public ModelAndView paramSetForLogisticRegression(ModelAndView model, String testName, String studyId) {
		model.setViewName("modules/bi/da/paramSet/logisticRegression");

		List<BmdModelMeta> modelList = bmdModelMetaService.findAllList(studyId);
		DaTestEnum de = DaTestEnum.loadByName(testName);
		TestAbs test = TestFactory.getTestByBeanName(de.getBeanName());
		ParamSetTypeEnum pstu = test.getParamSetType();

		model.addObject("modelList", modelList);
		model.addObject("test", test);
		model.addObject("testEnum", de);
		model.addObject("pstu", pstu);
		return model;
	}
	
	@RequestMapping("paramset/ksvm")
	public ModelAndView paramSetForKsvm(ModelAndView model, String testName, String studyId) {
		model.setViewName("modules/bi/da/paramSet/ksvm");

		List<BmdModelMeta> modelList = bmdModelMetaService.findAllList(studyId);
		DaTestEnum de = DaTestEnum.loadByName(testName);
		TestAbs test = TestFactory.getTestByBeanName(de.getBeanName());
		ParamSetTypeEnum pstu = test.getParamSetType();

		model.addObject("modelList", modelList);
		model.addObject("test", test);
		model.addObject("testEnum", de);
		model.addObject("pstu", pstu);
		return model;
	}
}
