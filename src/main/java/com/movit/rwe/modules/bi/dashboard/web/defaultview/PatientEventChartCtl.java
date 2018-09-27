package com.movit.rwe.modules.bi.dashboard.web.defaultview;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.movit.rwe.common.utils.CookieUtils;
import com.movit.rwe.common.utils.I18NUtils;
import com.movit.rwe.modules.bi.base.entity.hive.PatientEvent;
import com.movit.rwe.modules.bi.dashboard.common.ViewInvokeParams;
import com.movit.rwe.modules.bi.dashboard.service.PatientEventChartService;
import com.movit.rwe.modules.bi.dashboard.utils.MapValueComparator;
import com.movit.rwe.modules.bi.dashboard.vo.ItemStyle;
import com.movit.rwe.modules.bi.dashboard.vo.LineStyle;
import com.movit.rwe.modules.bi.dashboard.vo.MarkLine;
import com.movit.rwe.modules.bi.dashboard.vo.Normal;
import com.movit.rwe.modules.bi.dashboard.vo.SeriesVo;
import com.movit.rwe.modules.bi.dashboard.vo.Tooltip;
import com.movit.rwe.modules.bi.sys.service.StudyService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "${biPath}/dfView/patientEventChart")
public class PatientEventChartCtl {

	@Autowired
	private PatientEventChartService patientEventChartService;

	@Autowired
	private StudyService studyService;
	// 事件类型图标
	private static Map<String,String> symbolMap = new HashMap<String,String>();
	// 点和当前点画线的颜色
	private  Map<String,String> lineColorMap = new HashMap<String,String>();
	
	@Autowired
	private I18NUtils i18NUtils;
	
	private String others = "";
	private String[] colors = {"#FD026C","#6B9494","#A5D22D","#4682B8","#2292DD","#55AA99","#859966","#A3BB44","#E6941A","#996585",
			"#11EE96","#EE11EE","#BB45CC","#6F8391","#00CCFF",};
	static {
		symbolMap.put("Enroll", "path://d='M165.439448 62.597687 943.022199 511.536442 165.439448 960.473151Z'");
		symbolMap.put("Withdrawal/Complation","path://d='M472,185H287V0H185v185H0v102h185v185h102V287h185V185z'" );
		symbolMap.put("Death", "path://fill-rule='evenodd' d='M10,16 10,0 0,8z'");
		symbolMap.put("Followup", "circle");
		
	}
	@RequestMapping("/index")
	public ModelAndView index(ModelAndView model, ViewInvokeParams vip,String studyId,HttpServletRequest request) {
		this.others = "";
	
		//Assessment of response 列表
		List<PatientEvent> assessmentResponseList= patientEventChartService.getAllAssessmentOfResponse();

//		为反应评估分配颜色
		if(assessmentResponseList!=null && assessmentResponseList.size()>0) {
			for(int i=0;i<assessmentResponseList.size();i++) {
				PatientEvent assessmentResponse = assessmentResponseList.get(i);
				lineColorMap.put(assessmentResponse.getAssessmentResponse(), colors[i%14]);
			}
		}

        com.movit.rwe.modules.bi.base.entity.mysql.Study study = studyService.get(studyId);
        String studyCode = "";
        if(null != study){
            studyCode = study.getStudyCode();
        }

		model.setViewName("modules/bi/dashboard/dfView/patientEventChart");
		model.addObject("studyId", studyId);
		model.addObject("studyCode", studyCode);
		model.addObject("cohorts", vip.getPatientGroupArrLikeStr());
		String style = CookieUtils.getCookie(request, "_change_style");
		if(StringUtils.isEmpty(style)){
			style = "white";
		}
		model.addObject("style", style);
		return model;
	}
	/**
	 * 格式化反应评估列表
	 * @return
	 */
	private Set<String> formatEventType() {
		List<PatientEvent> eventList = patientEventChartService.getAllEvent();
		Set<String> set = new HashSet<String>();
		if(eventList!=null && eventList.size()>0) {
			for(int i = 0;i<eventList.size();i++) {
				PatientEvent patientEvent = eventList.get(i);
				String eventType = patientEvent.getEventType();
				//Enroll  Withdrawal/Complation  Death Ongoing  其他时间类型都成为 other
				if(!"Ongoing".equals(patientEvent.getEventType())) {
					if(symbolMap.get(eventType)==null) {
						this.others += eventType+",";
						set.add("other");
					}else {
						set.add(eventType);
					}
				}else {
					set.add(eventType);
				}
			}
		}
		return set;
	}
	/**
	 * 展示patient event chart
	 * @param response
	 * @param studyId
	 * @param cohorts
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	@RequestMapping("/getPatientEventChartData")
	@ResponseBody
	public Map<String, Object> getPatientEventChartData(HttpServletResponse response,String studyId,String cohorts,String referenceCode ,String assessmentResponse,String eventType,String other,Integer pageNo ) throws IOException, ParseException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//前台没有选的病号
		List<String> referenceCodeList = null; 
		if(!StringUtils.isEmpty(referenceCode)) {
			String[] referenceCodeAttr =  referenceCode.split(",");
			referenceCodeList = new ArrayList<String>();
			for(String temp:referenceCodeAttr) {
				referenceCodeList.add(temp);
			}
		}
		//前台没有选 反应评估
		List<String> assessmentResponseList = null; 
		if(!StringUtils.isEmpty(assessmentResponse)) {
			String[] assessmentResponseAttr =  assessmentResponse.split(",");
			assessmentResponseList = new ArrayList<String>();
			for(String temp:assessmentResponseAttr) {
				assessmentResponseList.add("-1".equals(temp)?"":temp);
			}
			if(assessmentResponse.endsWith(",")){
				assessmentResponseList.add("");
			}
		}
		//前台没有选的时间类型
		List<String> eventTypeList = null; 
		if(!StringUtils.isEmpty(eventType)) {
			String[] eventTypeAttr =  eventType.split(",");
			eventTypeList = new ArrayList<String>();
			for(String temp:eventTypeAttr) {
				if("other".equals(temp)) {//获得other所代表的事件类型集合
					for(String tempOther:other.split(",")) {
						eventTypeList.add(tempOther);
					}
				}else {
					eventTypeList.add(temp);
				}
			}
		}
		if(StringUtils.isEmpty(pageNo)) {//如果为空 默认 前二十条数据
			pageNo = 20;
		}
		
		List<String> codeList = new ArrayList<String>();//前台所需的y轴数据
		List<String> responseList = new ArrayList<String>();
		List<String> responseTempList = new ArrayList<String>();
		List<String> eventList = new ArrayList<String>();
		List<String> listY = new ArrayList<String>();//前台所需的y轴数据
		List<String> toolTipPoint = new ArrayList<String>();//点的tooltips
		listY.add("");
		
		//根据studyId和cohorts获得patientEvent数据
		List<PatientEvent> patientEventList = patientEventChartService.getPatientEventByStudyIdAndCohorts(studyId, cohorts.split(";"), referenceCodeList,assessmentResponseList,eventTypeList,pageNo);
		
		if(patientEventList==null || patientEventList.size()==0) {
			List<SeriesVo> series = new ArrayList<SeriesVo>();
		    int max = 2;
			resultMap.put("x", series);
			resultMap.put("y", listY);
			resultMap.put("toolTipPoint", toolTipPoint);
			resultMap.put("max", max);
			resultMap.put("size", listY.size());
			resultMap.put("codeList", codeList);
			resultMap.put("assessmentResponseList", responseList);
			resultMap.put("eventList", eventList);
			resultMap.put("otherEvent", this.others);
			return resultMap;
		}
		//收集点
		List<SeriesVo> series = new ArrayList<SeriesVo>();
		
	    int prePointX = 0;//记住前一个点的横坐标
	    int currPointIndex = 0;//当前点的顺序
	    int max = 2;
	    String reflistFirst = "";
	    SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
        if(eventType.indexOf("other") == -1){
            this.others = "";
        }
		for(int j=0;j<patientEventList.size();j++) {
			PatientEvent patientEvent = patientEventList.get(j);
			String eTime = sdf.format(patientEvent.getEventDatetime());
			
			String currReferenceCode = patientEvent.getReferenceCode();
			if(!codeList.contains(currReferenceCode)) {//如果listY中 ReferenceCode 不存在则加入到集合中
				codeList.add(currReferenceCode);
				listY.add(patientEvent.getReferenceCode());
			}
			String currResponseCode = patientEvent.getAssessmentResponse();
			if(!responseTempList.contains(currResponseCode)) {
                responseTempList.add(currResponseCode);
                currResponseCode += "," + lineColorMap.get(currResponseCode);
                responseList.add(currResponseCode);
			}
			String event = patientEvent.getEventType();
            if(!"Ongoing".equals(event)) {
                if(symbolMap.get(event)==null) {
                    this.others += event+",";
                    event = "other";
                    patientEvent.setEventType("Other");
                }
            }
			if(!eventList.contains(event)&&!event.equals("Followup")) {
                eventList.add(event);
			}
			SeriesVo vo = new SeriesVo();
			
			if(reflistFirst.equals(currReferenceCode)) {//如果 Y的病历号和 patientEvent里面的病历号相同
				//根据echart缩写的参数抽象出的实体 一个SeriesVo对应一个点
				vo.setName(patientEvent.getReferenceCode()+"-"+j);
				vo.setType("scatter");
				//设置事件内容对应的图标 取不到值默认是圆点
				if(symbolMap.get(patientEvent.getEventType())==null) {//其他事件
					if(!"Ongoing".equals(patientEvent.getEventType())) {
						vo.setSymbol("path://d='M512 909.994667l-61.994667-56.021333q-105.984-96-153.984-141.994667t-107.008-114.005333-80.981333-123.008-22.016-112.981333q0-98.005333 66.986667-166.016t166.997333-68.010667q116.010667 0 192 89.984 75.989333-89.984 192-89.984 100.010667 0 166.997333 68.010667t66.986667 166.016q0 77.994667-52.010667 162.005333t-112.981333 146.005333-198.997333 185.984z'");
					}
				}else {
					vo.setSymbol(symbolMap.get(patientEvent.getEventType()));
				}
				
				if(patientEvent.getEventType().equals("Followup")){
					vo.setSymbolSize(1);
				}
				
				//当前点和划线所对应的颜色
				ItemStyle itemStyle = new ItemStyle();
				JSONObject jsonNormal = new JSONObject() ;
				jsonNormal.put("color",lineColorMap.get(patientEvent.getAssessmentResponse()));
				itemStyle.setNormal(jsonNormal);
				vo.setItemStyle(itemStyle);
				
				//同一个病历号所对应的数据
				PatientEvent prePatientEvent = patientEventList.get(j-1);
				String bTime = sdf.format(prePatientEvent.getEventDatetime());
				//当前 数据事件日期 减去 前一条数据事件日期 值
				int day = diffDays(bTime,eTime);
				int lineDay = day;
				day += prePointX;
				if(day>max)
					max = day;//x州的最大值
				Object[][] point = {{day,currPointIndex,patientEvent.getEventType(),patientEvent.getAssessmentResponse()}};
				vo.setData(point);
				//点的 tips
				toolTipPoint.add(day+","+currPointIndex+","+patientEvent.getEventType()+","+patientEvent.getAssessmentResponse());
				 //画线
				MarkLine markLine = new MarkLine();
				markLine.setAnimation(false);
				//设置画线的样式
				LineStyle lineStyle = new LineStyle();
				Normal normal = new Normal();
				normal.setType("solid");
				lineStyle.setNormal(normal);
				markLine.setLineStyle(lineStyle);
				// 线上的提示信息
				Tooltip tip = new Tooltip();
				String msg = i18NUtils.get("patientEvent.bar.y");
				tip.setFormatter(msg+":"+lineDay);
				markLine.setTooltip(tip);
				//设置画线的坐标 
				List<JSONArray> data = new ArrayList<JSONArray>();
				JSONArray array = new JSONArray();
				JSONObject startPoint = new JSONObject();
			    
				startPoint.put("coord", new Integer[]{prePointX,currPointIndex});
				startPoint.put("symbol", "none");
				
				JSONObject endPoint = new JSONObject();
				endPoint.put("coord", new Integer[]{day,currPointIndex});
				endPoint.put("symbol", "none");
				
				array.add(startPoint);
				array.add(endPoint);
				data.add(array);
				markLine.setData(data);
                if(lineDay != 0){
                    vo.setMarkLine(markLine);
                }
				//记住当前横坐标，
				prePointX = day;
				series.add(vo);//添加当前的点到集合中
			}else {//相同病历号的点已经收集完了，把该病历号移除
				reflistFirst = currReferenceCode;
				prePointX = 1;//记住前一个点的横坐标
				//根据echart缩写的参数抽象出的实体 一个SeriesVo对应一个点
				vo.setName(patientEvent.getReferenceCode()+"-"+j);
				vo.setType("scatter");
				//设置事件内容对应的图标 取不到值默认是圆点
				if(symbolMap.get(patientEvent.getEventType())==null) {//其他事件
					if(!"Ongoing".equals(patientEvent.getEventType())) {
						vo.setSymbol("path://d='M512 909.994667l-61.994667-56.021333q-105.984-96-153.984-141.994667t-107.008-114.005333-80.981333-123.008-22.016-112.981333q0-98.005333 66.986667-166.016t166.997333-68.010667q116.010667 0 192 89.984 75.989333-89.984 192-89.984 100.010667 0 166.997333 68.010667t66.986667 166.016q0 77.994667-52.010667 162.005333t-112.981333 146.005333-198.997333 185.984z'");
					}
				}else {
					vo.setSymbol(symbolMap.get(patientEvent.getEventType()));
				}
				
				if(patientEvent.getEventType().equals("Followup")){
					vo.setSymbolSize(1);
				}
				
				//当前点和划线所对应的颜色
				ItemStyle itemStyle = new ItemStyle();
				JSONObject jsonNormal = new JSONObject() ;
				jsonNormal.put("color",lineColorMap.get(patientEvent.getAssessmentResponse()));
				itemStyle.setNormal(jsonNormal);
				vo.setItemStyle(itemStyle);
				
				// 如果 该点事当前病例号的第一个，则不划线，并且横坐标为 0 
				Object[][] point = {{1,++currPointIndex,patientEvent.getEventType(),patientEvent.getAssessmentResponse()}};
				vo.setData(point);
				//点的 tips
				toolTipPoint.add(1+","+currPointIndex+","+patientEvent.getEventType()+","+patientEvent.getAssessmentResponse());
				series.add(vo);//添加当前的点到集合中
			}
		}
        Collections.sort(codeList, new Comparator<String>() {
	            public int compare(String arg0, String arg1) {
	                int hits0 = Integer.parseInt(arg0);
	                int hits1 = Integer.parseInt(arg1);
	                if (hits0 > hits1) {
	                    return -1;
	                } else if (hits1 == hits0) {
	                    return 0;
	                } else {
	                    return 1;
	                }
	            }
	        });
	resultMap.put("x", series);
	resultMap.put("y", listY);
	resultMap.put("toolTipPoint", toolTipPoint);
	resultMap.put("max", max);
	resultMap.put("size", listY.size());
	resultMap.put("codeList", codeList);
    resultMap.put("assessmentResponseList", responseList);
    resultMap.put("eventList", eventList);
    resultMap.put("otherEvent", this.others);
	return resultMap;
}
		
	/**
	 * 使用 Map按value进行排序
	 * @param
	 * @return
	 */
	public  List<String> sortMapByValue(Map<String, Integer> oriMap) {
		if (oriMap == null || oriMap.isEmpty()) {
			return null;
		}
		LinkedList<String> sortedList = new LinkedList<String>();
		List<Map.Entry<String, Integer>> entryList = new ArrayList<Map.Entry<String, Integer>>(
				oriMap.entrySet());
		Collections.sort(entryList, new MapValueComparator());

		Iterator<Map.Entry<String, Integer>> iter = entryList.iterator();
		Map.Entry<String, Integer> tmpEntry = null;
		while (iter.hasNext()) {
			tmpEntry = iter.next();
			sortedList.add(tmpEntry.getKey());
		}
		return sortedList;
	}
	
	/* 
	 *  函数功能说明  
	 *  求两个日期相差天数    
	 *  @throws 
	 */
	public static int diffDays(String smdate, String edate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(smdate));
		long time1 = cal.getTimeInMillis();
		cal.setTime(sdf.parse(edate));
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);
		return Integer.parseInt(String.valueOf(between_days));
	}
	
	
}
