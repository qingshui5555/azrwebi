package com.movit.rwe.modules.bi.dashboard.web.defaultview;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.movit.rwe.common.utils.CookieUtils;
import com.movit.rwe.common.web.BaseController;
import com.movit.rwe.modules.bi.base.entity.hive.Questionnaire;
import com.movit.rwe.modules.bi.base.entity.mysql.PatientGroup;
import com.movit.rwe.modules.bi.dashboard.common.ViewInvokeParams;
import com.movit.rwe.modules.bi.dashboard.service.BiomarkerTestService;
import com.movit.rwe.modules.bi.dashboard.service.QuestionnaireService;
import com.movit.rwe.modules.bi.dashboard.service.TabViewService;

import net.sf.json.JSONObject;


@Controller
@RequestMapping(value = "${biPath}/dfView/questionnaire")
public class QuestionnaireViewCtl extends BaseController {

	@Autowired
	private BiomarkerTestService biomarkerTestService;
	
	@Autowired
	private QuestionnaireService questionnaireService;
	
	@Autowired
	private TabViewService tabViewService;

	@RequestMapping("index")
	public ModelAndView showChartById(ViewInvokeParams vip,String studyId,HttpServletRequest request) {
		ModelAndView model = new ModelAndView("modules/bi/dashboard/dfView/questionnaireView");
		String viewChartHeight=tabViewService.findViewChartHeightByTabAndViewId(vip.getTabViewId());
        if(viewChartHeight==null || "".equals(viewChartHeight)) {
        	viewChartHeight = "575";
        }
		//文件类型
		List<String> typeList = questionnaireService.getAllQuestionnaireType(studyId);
		// 配置内容
		String configJson = vip.getConfigJson();
		Map<String, String> patientGroupNameMap = null;
		if(configJson!=null && !"".equals(configJson)) {
			List<String> patientGroupIdList = getPatientGroupIdList(vip.getSqlQueryStr(),configJson);
			// key patientGroupIdList value  cohort名称
			patientGroupNameMap = new HashMap<String,String>();
			if(patientGroupIdList!=null && patientGroupIdList.size()>0) {
				patientGroupNameMap = biomarkerTestService.getPatientGroupNameArr(vip.getStudyId(), patientGroupIdList);
			}
		}
		//将Map按照值排序
		List <Map.Entry<String,String>>mappingList 
			= new ArrayList<Map.Entry<String,String>>(patientGroupNameMap.entrySet());
		Collections.sort(mappingList, new Comparator<Map.Entry<String,String>>(){
			public int compare(Map.Entry<String,String> mapping1,Map.Entry<String,String> mapping2){
				return mapping1.getValue().compareTo(mapping2.getValue());
			}
		});
		
		model.addObject("configJson",configJson);
		model.addObject("studyId", studyId);
		model.addObject("viewChartHeight", viewChartHeight);
		model.addObject("typeList", typeList);
		//model.addObject("patientGroupNameMap", patientGroupNameMap);
		model.addObject("patientGroupNameMap", mappingList);
		String style = CookieUtils.getCookie(request, "_change_style");
		if(StringUtils.isEmpty(style)){
			style = "white";
		}
		model.addObject("style", style);
		model.addObject("isPad", vip.getIsPad());
		return model;
	}
    /**
     * 获得
     * @param vip
     * @param configJson
     * @param patientGroupIdList
     * @return
     */
	private List<String> getPatientGroupIdList(String cohort,String configJson) {
		 List<String> patientGroupIdList = new ArrayList<String>();
		 if("%%".equals(cohort)) {
			JSONObject json = JSONObject.fromObject(configJson);
			patientGroupIdList = json.getJSONArray("patientGroupIdArr");
		 }else {
			 if(StringUtils.isEmpty(cohort)) {
				 patientGroupIdList.add("-1");
			 }else {
				String[] cohortArr = cohort.split(",");
				//%C_44,%C_45,%C_54,%C_136,%';
				for(String groupId:cohortArr) {
					if(!"%".equals(groupId)) {
						patientGroupIdList.add(groupId);
					}
				}
			 }
			
		 }
		return patientGroupIdList;
	}
	
	/**
	 * 配置页面
	 * @param studyId
	 * @param tabViewId
	 * @return
	 */
	@RequestMapping("questionnaireConfig")
	public ModelAndView questionnaireConfig(String studyId, String tabViewId) {
		ModelAndView mav = new ModelAndView("modules/bi/dashboard/dfView/questionnaireConfig");

		// 获取配置的json对象
//		String configJson = biomarkerTestService.findTabViewJsonByTabViewId(tabViewId);
		String configJson = "";
		List<String> patientListSelected = null;
		List<String> nameListSelected = null;
        if(StringUtils.isNotEmpty(configJson)) {
        	JSONObject json = JSONObject.fromObject(configJson);
    		patientListSelected = (List<String>)json.get("patientGroupIdArr");
    		nameListSelected = (List<String>)json.get("questionnaireArr");
        }
		
		List<PatientGroup> patientGroupList = biomarkerTestService.findAllPatientGroup(studyId);
		List<Questionnaire> questionnaireList = questionnaireService.findAllQuestionnaire(studyId);
		mav.addObject("patientGroupList", patientGroupList);
		mav.addObject("questionnaireList", questionnaireList);
		mav.addObject("patientListSelected", patientListSelected);
		mav.addObject("nameListSelected", nameListSelected);
		return mav;
	}
	
	/**
	 * 加载questionnaire 数据
	 * @param studyId
	 * @param tabViewId
	 * @return
	 */
	@RequestMapping("getQuestionnaireData")
	@ResponseBody
	public Map<String, Object> getQuestionnaireData(String studyId,String type,String cohorts) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Object> listY = new ArrayList<Object>();
		//cohort 名称
		List<String> listX = new ArrayList<String>();
		//error bar 工型 数据
		List<Object[]> upAndLow= new ArrayList<Object[]>();
		List<String> patientGroupIdList = new ArrayList<String>();
		try {
			// cohorts 前台勾选的 cohorts
			//if(!StringUtils.isEmpty(cohorts) & !"".equals(cohorts)) {
			if(!"-1".equals(cohorts)) {
				patientGroupIdList = Arrays.asList(cohorts.split(","));
				if(patientGroupIdList!=null && patientGroupIdList.size()>0) {
					Map<String, String> patientGroupNameMap = biomarkerTestService.getPatientGroupNameArr(studyId, patientGroupIdList);
					for(String groupId : patientGroupIdList) {
						List<Double> questionnaireList = questionnaireService.getQuestionnaireByGroupIdAndType(studyId,groupId,type);
					    if(questionnaireList!=null && questionnaireList.size()>0) {
					    	
					    	//平均值
					    	double avgScore = getAverage(questionnaireList.toArray(new Double[questionnaireList.size()]));
					    	
					    	//上限
					    	double up = avgScore+getStandardDiviation(questionnaireList.toArray(new Double[questionnaireList.size()]));
					    	//下限
					    	double low = avgScore-getStandardDiviation(questionnaireList.toArray(new Double[questionnaireList.size()]));
					    	Double[] errorBar= {setScale(low),setScale(low),setScale(low),setScale(low),setScale(up)};
					    	upAndLow.add(errorBar);
					    	listX.add(patientGroupNameMap.get(groupId));
					    	listY.add(setScale(avgScore));
					    }else {//没查到cohort的问卷调查数据
					    	Object[] errorBar= {null,null};
					    	upAndLow.add(errorBar);
					    	listX.add(patientGroupNameMap.get(groupId));
					    	listY.add(0);
					    }
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		resultMap.put("x", listX);
		resultMap.put("barData", listY);
		resultMap.put("type", type);
		resultMap.put("boxData", upAndLow);
		return resultMap;
	}
	
     private double setScale(double value){
    	 BigDecimal bd = new BigDecimal(value);
    	 return bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
     }
	 /**
	  * 求给定双精度数组中值的和
	  *
	  * @param inputData
	  *            输入数据数组
	  * @return 运算结果
	  */
	 public static double getSum(Double[] inputData) {
	  if (inputData == null || inputData.length == 0)
	   return -1;
	  int len = inputData.length;
	  double sum = 0;
	  for (int i = 0; i < len; i++) {
	   sum = sum + inputData[i];
	  }

	  return sum;

	 }

	 /**
	  * 求给定双精度数组中值的数目
	  *
	  * @param input
	  *            Data 输入数据数组
	  * @return 运算结果
	  */
	 public static int getCount(Double[] inputData) {
	  if (inputData == null)
	   return -1;

	  return inputData.length;
	 }

	 /**
	  * 求给定双精度数组中值的平均值
	  *
	  * @param inputData
	  *            输入数据数组
	  * @return 运算结果
	  */
	 public static double getAverage(Double[] inputData) {
	  if (inputData == null || inputData.length == 0)
	   return -1;
	  int len = inputData.length;
	  double result;
	  result = getSum(inputData) / len;
	  
	  return result;
	 }

	 /**
	  * 求给定双精度数组中值的平方和
	  *
	  * @param inputData
	  *            输入数据数组
	  * @return 运算结果
	  */
	 public static double getSquareSum(Double[] inputData) {
	  if(inputData==null||inputData.length==0)
	      return -1;
	     int len=inputData.length;
	  double sqrsum = 0.0;
	  for (int i = 0; i <len; i++) {
	   sqrsum = sqrsum + inputData[i] * inputData[i];
	  }

	  
	  return sqrsum;
	 }

	 /**
	  * 求给定双精度数组中值的方差
	  *
	  * @param inputData
	  *            输入数据数组
	  * @return 运算结果
	  */
	 public static double getVariance(Double[] inputData) {
	  int count = getCount(inputData);
	  double sqrsum = getSquareSum(inputData);
	  double average = getAverage(inputData);
	  double result;
	  result = (sqrsum - count * average * average) / count;

	     return result;
	 }

	 /**
	  * 求给定双精度数组中值的标准差
	  *
	  * @param inputData
	  *            输入数据数组
	  * @return 运算结果
	  */
	 public static double getStandardDiviation(Double[] inputData) {
	  double result;
	  //绝对值化很重要
	  result = Math.sqrt(Math.abs(getVariance(inputData)));
	  
	  return result;

	 }
}
