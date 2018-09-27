package com.movit.rwe.modules.bi.dashboard.service;

import java.util.*;

import com.movit.rwe.common.config.GlobalConstant;
import com.movit.rwe.common.utils.EhCacheUtils;
import com.movit.rwe.modules.bi.base.entity.mysql.GlobalConfig;
import com.movit.rwe.modules.bi.base.entity.mysql.TabView;
import com.movit.rwe.modules.bi.base.entity.vo.HiveResultVo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author kwqb535
 * @date 2018.08.14
 */
@Service
@Transactional
public class ComorbidityService {

	private static Logger logger = LoggerFactory.getLogger(ComorbidityService.class);

	@Autowired
	private TabViewService tabViewService;

	@Autowired
	HiveResultService hiveResultService;

	/**
	 * 获取tabView
	 * @param tabViewId
	 * @return
	 */
	public TabView queryTabViewByTabViewId(String tabViewId) {
		return tabViewService.findTabViewByTabViewId(tabViewId);
	}

	/**
	 * 查询药品列表
	 * @param studyId
	 * @param cohortIds
	 * @return
	 */
	public Set<String> queryListDrugConfig(String studyId, String []cohortIds){
		logger.info("queryListDrugConfig begin");
//		List<String> keyList = new ArrayList<String>();
//		if(cohortIds != null && cohortIds.length > 0){
//			List<String> cohortKeyList = hiveCohortDao.queryListPatientId(cohortIds);
//			for(String patientId : cohortKeyList){
//				keyList.add(studyId + GlobalConstant.STR_CONNECTOR + patientId);
//			}
//		} else {
//			List<String> ehList = EhCacheUtils.getKeys(GlobalConstant.STR_CACHE_NAME_PATIENT);
//			for(String patientIdAndStudyId : ehList){
//				if(patientIdAndStudyId.startsWith(studyId)){
//					keyList.add(patientIdAndStudyId);
//				}
//			}
//		}
//		List<Object> ehResultList = EhCacheUtils.getValuesByKeys(GlobalConstant.STR_CACHE_NAME_TREATMENT, keyList);
		Set<String> drugSet = new HashSet<String>();
//		for(Object object : ehResultList) {
//			Map<String, Treatment> ehMap = (Map<String, Treatment>) object;
//			for (Map.Entry<String, Treatment> entry : ehMap.entrySet()) {
//				Treatment treatment = entry.getValue();
//				if (StringUtils.isBlank(treatment.getDrugName()) || treatment.getLine() == null) {
//					continue;
//				}
//				drugSet.add(treatment.getDrugName());
//			}
//		}
		logger.info("queryListDrugConfig end");
		return drugSet;
	}

	/**
	 *
	 * @param studyId
	 * @param cohortIds
	 * @param startDate
	 * @param endDate
	 * @param comorbidity
	 * @param dataList
	 * @param subDataList
	 */
	public Integer queryListComorbidityData(String studyId, String[] cohortIds, String startDate, String endDate, String comorbidity, List<Map<String, Object>> dataList, List<Map<String, Object>> subDataList){
		logger.info("queryListComorbidityData begin");

		String indicator = null;
		if(!StringUtils.isBlank(comorbidity) && !comorbidity.equalsIgnoreCase("ALL")){
			indicator = comorbidity;
		}

		List<HiveResultVo> resultVoList = hiveResultService.queryListComorbidityCountData(studyId, GlobalConstant.STR_ETL_TYPE_COMORBIDITY, indicator, cohortIds, startDate, endDate);
		Integer sumCount = 0;
		Map<String, Map<String, Integer>> resultMap = new HashMap<String, Map<String, Integer>>();
		for(HiveResultVo resultVo: resultVoList){
			String drugName = resultVo.getKeyName();
			if(StringUtils.isBlank(drugName)){
				continue;
			}
			if(drugName.equals(GlobalConstant.STR_ETL_TYPE_COMORBIDITY)){
				sumCount = resultVo.getPatientCount();
				continue;
			}
			String drugType = resultVo.getKeyValue();
			Integer count = resultVo.getPatientCount();
			Map<String, Integer> map = resultMap.get(drugName);
			if(map == null){
				map = new  TreeMap<String, Integer>();
			}
			map.put(drugType, count);
			resultMap.put(drugName, map);
		}

        List<Map.Entry<String, Map<String, Integer>>> resultList = new ArrayList<Map.Entry<String, Map<String, Integer>>>(resultMap.entrySet());
        Collections.sort(resultList, new Comparator<Map.Entry<String, Map<String, Integer>>>() {

        	@Override
            public int compare(Map.Entry<String, Map<String, Integer>> o1, Map.Entry<String, Map<String, Integer>> o2) {
                Integer i1 = 0;
                for(Map.Entry<String, Integer> entry1 : o1.getValue().entrySet()){
                    i1 += entry1.getValue();
                }
                Integer i2 = 0;
                for(Map.Entry<String, Integer> entry2 : o2.getValue().entrySet()){
                    i2 += entry2.getValue();
                }
                return i1 - i2;
            }
        });

		List<Object> objList = EhCacheUtils.getValuesWithKeys(GlobalConstant.STR_CACHE_NAME_GLOBAL_CONFIG, studyId, GlobalConstant.STR_ETL_TYPE_COMORBIDITY);
		Map<String, List<Map<String, Object>>> rootMap = new HashMap<String,  List<Map<String, Object>>>();
		Map<String, Integer> sumMap = new HashMap<String, Integer>();
		for(Map.Entry<String, Map<String, Integer>> entry : resultList){
			GlobalConfig globalConfig = null;
			for(Object obj : objList){
				GlobalConfig tmpGlobalConfig = (GlobalConfig) obj;
				if(tmpGlobalConfig.getKey().equals(entry.getKey())){
					globalConfig = tmpGlobalConfig;
					break;
				}
			}

			if(globalConfig != null) {
				String cacheKey = studyId + GlobalConstant.STR_CONNECTOR + GlobalConstant.STR_ETL_TYPE_COMORBIDITY + GlobalConstant.STR_CONNECTOR + globalConfig.getParentId();
				GlobalConfig parentGlobalConfig = (GlobalConfig)EhCacheUtils.get(GlobalConstant.STR_CACHE_NAME_GLOBAL_CONFIG, cacheKey);
				if(parentGlobalConfig == null){
					continue;
				}
				String key = parentGlobalConfig.getValue();
				Map<String, Object> map = new TreeMap<String, Object>();
				Integer subSum = 0;
				Integer subSumCount = 0;
				Integer num = 1;
				List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(entry.getValue().entrySet());
				Collections.sort(list,new Comparator<Map.Entry<String, Integer>>() {
					@Override
					public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
						return o2.getValue().compareTo(o1.getValue());
					}
				});

				for (Map.Entry<String, Integer> subEntry : list) {
					if(subEntry.getKey().equals("Patient Count")){
						subSum = subEntry.getValue();
						continue;
					}
					Map<String, Object> subMap = new TreeMap<String, Object>();
					subMap.put("type", key);
					subMap.put("name", globalConfig.getValue());
					subMap.put("childName", subEntry.getKey());
					subMap.put("value", subEntry.getValue());
					subMap.put("number", num++);
					subSumCount += subEntry.getValue();
					subDataList.add(subMap);
				}

				map.put("sumCount", subSumCount);
				map.put("name", globalConfig.getValue());
				map.put("value", subSum);

				List<Map<String, Object>> rootList = rootMap.get(key);
				if(rootList == null){
					rootList = new ArrayList<Map<String, Object>>();
					rootList.add(map);
					rootMap.put(key, rootList);
					sumMap.put(key, subSum);
				} else{
					rootList.add(map);
					sumMap.put(key, subSum + sumMap.get(key));
				}
			}
		}

		for(Map.Entry<String, List<Map<String, Object>>> entry : rootMap.entrySet()) {
			Map<String, Object> map = new TreeMap<String, Object>();
			map.put("name", entry.getKey());
			map.put("value", sumMap.get(entry.getKey()));
			map.put("children", entry.getValue());
			dataList.add(map);
		}

		logger.info("queryListComorbidityData end");

		return sumCount;
	}

}
