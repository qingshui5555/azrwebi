package com.movit.rwe.modules.bi.dashboard.service;

import java.util.*;

import com.movit.rwe.common.config.GlobalConstant;
import com.movit.rwe.common.config.GlobalGroupConfig;
import com.movit.rwe.modules.bi.base.entity.enums.LineEnum;
import com.movit.rwe.modules.bi.base.entity.mysql.TabView;
import com.movit.rwe.modules.bi.base.entity.vo.HiveTreatmentCountVo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TreatmentJourneyService {

	private static Logger logger = LoggerFactory.getLogger(TreatmentJourneyService.class);

	@Autowired
	private TabViewService tabViewService;

	@Autowired
	private HiveResultService hiveResultService;

	/**
	 * 获取tabView
	 * @param tabViewId
	 * @return
	 */
	public TabView queryTabViewByTabViewId(String tabViewId) {
		return tabViewService.findTabViewByTabViewId(tabViewId);
	}

	/**
	 * 获取treatment 所有统计数据
	 * @param studyId
	 * @param cohortIds
	 * @return
	 */
	public List<Map<String, Object>> queryListAllTreatmentJourney(String studyId, String[] cohortIds) {
		logger.info("@@@In TreatmentJourneyService.queryListAllTreatmentJourney begin: studyId=" + studyId + ", cohortIds=" + StringUtils.join(cohortIds, GlobalConstant.STR_SPLIT));
		List<HiveTreatmentCountVo> treatmentCountVoList = hiveResultService.queryListAllTreatmentCountData(studyId, GlobalConstant.STR_ETL_TYPE_TREATMENT, cohortIds, getTypesByStudyId(studyId));
		Map<String, Integer> map = new TreeMap<String, Integer>();
		Map<String, Integer> durationMap = new TreeMap<String, Integer>();
		for(HiveTreatmentCountVo vo : treatmentCountVoList){
			Integer lineI = LineEnum.getLineWithDefault(vo.getLineStr());
			if(lineI == null){
				continue;
			}
			if(vo.getDrugName().equalsIgnoreCase("ANTIHYPERTENSIVES")){
				continue;
			}
			String key = vo.getDrugName() + GlobalConstant.STR_CONNECTOR + lineI;

			Integer value = map.get(key);
			if(value == null){
				map.put(key, vo.getPatientCount());
			} else {
				map.put(key, value + vo.getPatientCount());
			}

			Integer timesDay = vo.getTimesDay();
			if(timesDay == null || timesDay < 1){
				timesDay = 1;
			}

			Integer duration = durationMap.get(key);
			if(duration == null){
				durationMap.put(key, timesDay);
			} else {
				durationMap.put(key, duration +  timesDay);
			}
		}

		List<Map.Entry<String,Integer>> list = new ArrayList<Map.Entry<String,Integer>>(map.entrySet());
		Collections.sort(list,new Comparator<Map.Entry<String,Integer>>() {
			@Override
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				return (o2.getValue() - o1.getValue());
			}
		});

		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < list.size(); i++) {
			Map.Entry<String, Integer> entry = list.get(i);
			Map<String, Object> result = new HashMap<String, Object>();
			String[] keyStr = entry.getKey().split(GlobalConstant.STR_CONNECTOR);
			if(keyStr == null || keyStr.length != 2){
				continue;
			}
			result.put("curLevel", Integer.parseInt(keyStr[1]));
			result.put("name", keyStr[0]);
			result.put("value", entry.getValue());
			result.put("timeDays", durationMap.get(entry.getKey()));
			result.put("line", GlobalConstant.LINE_COLOR);
			result.put("style", GlobalConstant.STYLE_WHITE);
			result.put("level", GlobalConstant.colorGroup[Integer.parseInt(keyStr[1])]);
			resultList.add(result);
		}

		logger.info("@@@In TreatmentJourneyService.queryListTreatmentJourney end ");
		return resultList;
	}

	/**
	 * 获取treatment 第一级统计数据
	 * @param studyId
	 * @param cohortIds
	 * @param max
	 * @return
	 */
	public List<Map<String, Object>> queryListFirstLevelTreatmentJourney(String studyId, String[] cohortIds, Integer max) {
		logger.info("@@@In TreatmentJourneyService.queryListFirstLevelTreatmentJourney begin: studyId=" + studyId + ", cohortIds=" + StringUtils.join(cohortIds, GlobalConstant.STR_SPLIT));
		List<HiveTreatmentCountVo> treatmentCountVoList = hiveResultService.queryListFirstLevelTreatmentJourney(studyId, GlobalConstant.STR_ETL_TYPE_TREATMENT, cohortIds, getTypesByStudyId(studyId), LineEnum.FIRST_LINE.getName(), LineEnum.SECOND_LINE.getName());
		Map<String, Integer> map = new TreeMap<String, Integer>();
		Set<String> patientIdSet = new HashSet<String>();
		Map<String, String> childMap = new HashMap<String, String>();
		Map<String, Integer> durationMap = new TreeMap<String, Integer>();
		for(HiveTreatmentCountVo vo : treatmentCountVoList){
			Integer lineI = LineEnum.getLineWithDefault(vo.getLineStr());
			if(vo.getDrugName().equalsIgnoreCase("ANTIHYPERTENSIVES")){
				continue;
			}
			String key = vo.getDrugName() + GlobalConstant.STR_CONNECTOR + lineI;
			if(!LineEnum.FIRST_LINE.getLine().equals(lineI)){
				CollectionUtils.addAll(patientIdSet, vo.getPatientIdStr().split(GlobalConstant.STR_SPLIT));
				continue;
			} else {
				childMap.put(key, vo.getPatientIdStr());
			}

			Integer value = map.get(key);
			if(value == null){
				map.put(key, vo.getPatientCount());
			} else {
				map.put(key, value + vo.getPatientCount());
			}

			Integer timesDay = vo.getTimesDay();
			if(timesDay == null || timesDay < 1){
				timesDay = 1;
			}

			Integer duration = durationMap.get(key);
			if(duration == null){
				durationMap.put(key, timesDay);
			} else {
				durationMap.put(key, duration +  timesDay);
			}
		}

		List<Map.Entry<String,Integer>> list = new ArrayList<Map.Entry<String,Integer>>(map.entrySet());
		Collections.sort(list,new Comparator<Map.Entry<String,Integer>>() {
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				return (o2.getValue() - o1.getValue());
			}
		});

		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < list.size(); i++) {
			Map.Entry<String, Integer> entry = list.get(i);
			Map<String, Object> result = new HashMap<String, Object>();
			String[] keyStr = entry.getKey().split(GlobalConstant.STR_CONNECTOR);
			if(keyStr == null || keyStr.length != 2){
				continue;
			}
			result.put("curLevel", Integer.parseInt(keyStr[1]));
			result.put("name", keyStr[0]);
			result.put("value", entry.getValue());
			result.put("timeDays", durationMap.get(entry.getKey()));
			result.put("line", GlobalConstant.LINE_STYLE);
			result.put("style", GlobalConstant.STYLE_WHITE);
			result.put("level", GlobalConstant.colorGroup[Integer.parseInt(keyStr[1])]);
			result.put("patientIdStr", childMap.get(entry.getKey()));
			if(containsWithSplit(patientIdSet, childMap.get(entry.getKey()), GlobalConstant.STR_SPLIT)){
				result.put("hasChildren", true);
			} else {
				result.put("hasChildren", false);
			}
			resultList.add(result);
			if(!max.equals(GlobalConstant.COUNT_ALL) && max <= resultList.size()){
				break;
			}
		}

		logger.info("@@@In TreatmentJourneyService.queryListFirstLevelTreatmentJourney end ");
		return resultList;
	}

	/**
	 * 获取treatment 子项数据
	 * @param studyId
	 * @param cohortIds
	 * @param level
	 * @param max
	 * @param patientIdStr
	 * @return
	 */
	public List<Map<String, Object>> queryListChildTreatmentJourney(String studyId, String[] cohortIds, Integer level, Integer max, String patientIdStr) {
		logger.info("@@@In TreatmentJourneyService.queryListChildTreatmentJourney begin: studyId=" + studyId + ", cohortIds=" + StringUtils.join(cohortIds, GlobalConstant.STR_SPLIT));
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		String curLevelStr = "";
		String nextLevelStr = "";
		if(level != null && level == 1){
			curLevelStr = LineEnum.SECOND_LINE.getName();
			nextLevelStr = LineEnum.THIRD_LINE.getName();
		} else if(level != null && level == 2){
			curLevelStr = LineEnum.THIRD_LINE.getName();
			nextLevelStr = LineEnum.MULTI_LINE.getName();
		} else if(level != null && level == 3){
			curLevelStr = LineEnum.MULTI_LINE.getName();
			nextLevelStr = LineEnum.MULTI_LINE.getName();
		} else {
			return resultList;
		}
		List<HiveTreatmentCountVo> treatmentCountVoList = hiveResultService.queryListTreatmentChildCountData(studyId, GlobalConstant.STR_ETL_TYPE_TREATMENT, cohortIds, getTypesByStudyId(studyId), curLevelStr, nextLevelStr, patientIdStr);
		Map<String, Integer> map = new TreeMap<String, Integer>();
		Set<String> patientIdSet = new HashSet<String>();
		Map<String, String> childMap = new TreeMap<String, String>();
		Map<String, Integer> durationMap = new TreeMap<String, Integer>();
		for(HiveTreatmentCountVo vo : treatmentCountVoList){
			Integer lineI = LineEnum.getLineWithDefault(vo.getLineStr());
			if(vo.getDrugName().equalsIgnoreCase("ANTIHYPERTENSIVES")){
				continue;
			}
			String key = vo.getDrugName() + GlobalConstant.STR_CONNECTOR + lineI;
			if(lineI == level + 2){
				CollectionUtils.addAll(patientIdSet, vo.getPatientIdStr().split(GlobalConstant.STR_SPLIT));
				continue;
			} else {
				childMap.put(key, vo.getPatientIdStr());
			}

			Integer value = map.get(key);
			if(value == null){
				map.put(key, vo.getPatientCount());
			} else {
				map.put(key, value + vo.getPatientCount());
			}

			Integer timesDay = vo.getTimesDay();
			if(timesDay == null || timesDay < 1){
				timesDay = 1;
			}

			Integer duration = durationMap.get(key);
			if(duration == null){
				durationMap.put(key, timesDay);
			} else {
				durationMap.put(key, duration +  timesDay);
			}
		}

		List<Map.Entry<String,Integer>> list = new ArrayList<Map.Entry<String,Integer>>(map.entrySet());
		Collections.sort(list,new Comparator<Map.Entry<String,Integer>>() {
			@Override
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				return (o2.getValue() - o1.getValue());
			}
		});


		for (int i = 0; i < list.size(); i++) {
			Map.Entry<String, Integer> entry = list.get(i);
			Map<String, Object> result = new HashMap<String, Object>();
			String[] keyStr = entry.getKey().split(GlobalConstant.STR_CONNECTOR);
			if(keyStr == null || keyStr.length != 2){
				continue;
			}
			result.put("curLevel", Integer.parseInt(keyStr[1]));
			result.put("name", keyStr[0]);
			result.put("value", entry.getValue());
			result.put("timeDays", durationMap.get(entry.getKey()));
			result.put("line", GlobalConstant.LINE_STYLE);
			result.put("style", GlobalConstant.STYLE_BLACK);
			result.put("level", GlobalConstant.colorGroup[Integer.parseInt(keyStr[1])]);
			result.put("patientIdStr", childMap.get(entry.getKey()));
			if(containsWithSplit(patientIdSet, childMap.get(entry.getKey()), GlobalConstant.STR_SPLIT)){
				result.put("hasChildren", true);
			} else {
				result.put("hasChildren", false);
			}
			resultList.add(result);
			if(!max.equals(GlobalConstant.COUNT_ALL) && max <= resultList.size()){
				break;
			}
		}

		logger.info("@@@In TreatmentJourneyService.queryListChildTreatmentJourney end ");
		return resultList;
	}

	private boolean containsWithSplit(Set<String> target, String source, String split){
		boolean result = false;
		if(StringUtils.isBlank(source) || target == null || target.size() == 0){
			return result;
		}
		Set<String> sourceSet = new HashSet<String>();
		CollectionUtils.addAll(sourceSet, source.split(split));
		for(String str : sourceSet){
			if(target.contains(str)){
				result = true;
				break;
			}
		}

		return result;
	}

	private String[] getTypesByStudyId(String studyId){
		String[] types = null;
		if(studyId.equalsIgnoreCase("22")){
			types = new String[]{"Antidiabetic drugs"};
		}
		return types;
	}
}
