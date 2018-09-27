package com.movit.rwe.modules.bi.dashboard.service;

import com.movit.rwe.common.config.GlobalConstant;
import com.movit.rwe.common.config.GlobalGroupConfig;
import com.movit.rwe.common.utils.EhCacheUtils;
import com.movit.rwe.common.utils.StringUtils;
import com.movit.rwe.modules.bi.base.entity.mysql.GlobalConfig;
import com.movit.rwe.modules.bi.base.entity.mysql.GlobalGroup;
import com.movit.rwe.modules.bi.base.entity.mysql.TabView;
import com.movit.rwe.modules.bi.dashboard.vo.ConfigVo;
import com.movit.rwe.modules.bi.base.entity.vo.HiveResultVo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author kwqb535
 * @date 2018.08.14
 */
@Service
@Transactional
public class DemographicService {

	private static Logger logger = LoggerFactory.getLogger(DemographicService.class);

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
	 * 获取属性数据配置列表
	 * @param studyId
	 * @param begin
	 * @return
	 */
	public List<HiveResultVo> queryListDemographicConfig(String studyId, Integer begin, List<ConfigVo> configVoList){
		logger.info("queryListDemographicConfig begin");
		List<HiveResultVo> resultVoList = hiveResultService.queryListDemographicConfig(studyId, GlobalConstant.STR_ETL_TYPE_DEMOGRAPHIC, begin, configVoList);

		Collections.sort(resultVoList, new Comparator<HiveResultVo>() {
            @Override
            public int compare(HiveResultVo o1, HiveResultVo o2) {
                return o1.getOrder().compareTo(o2.getOrder());
            }
        });

		logger.info("queryListDemographicConfig end");
		return resultVoList;
	}

	/**
	 * 获取抽取数据配置列表
	 * @param studyId
	 * @param begin
	 * @return
	 */
	public List<HiveResultVo> queryListVitalSignConfig(String studyId, Integer begin, List<ConfigVo> configVoList){
		logger.info("queryListVitalSignConfig begin");
		List<HiveResultVo> resultVoList = hiveResultService.queryListResultConfig(studyId, GlobalConstant.STR_ETL_TYPE_VITAL_SIGN, true, null, begin, configVoList);
		Boolean stageFlag = false;
		for(HiveResultVo vo : resultVoList){
			if(vo.getKeyName().equalsIgnoreCase("Stage")){
				stageFlag = true;
				break;
			}
		}
		int size = resultVoList.size();
		if(stageFlag){
			for(String stage : GlobalConstant.vitalStageGroup) {
				HiveResultVo vo = new HiveResultVo();
				Boolean resultFlag = true;
				Integer order = begin + size;
				if(configVoList != null){
					for(ConfigVo configVo : configVoList){
						if(stage.equals(configVo.getIndicator())){
							order = configVo.getOrder();
							resultFlag = false;
							break;
						}
					}
				}
				vo.setConfigId(order + "");
				vo.setOrder(order);
				vo.setKeyName(stage);
				vo.setDescription(stage);
				vo.setStudyId(studyId);
				vo.setSubject(GlobalConstant.STR_ETL_TYPE_VITAL_SIGN);
				resultVoList.add(vo);
				if(resultFlag){
					begin++;
				}
			}
		}

		Collections.sort(resultVoList, new Comparator<HiveResultVo>() {
			@Override
			public int compare(HiveResultVo o1, HiveResultVo o2) {
				return o1.getOrder().compareTo(o2.getOrder());
			}
		});

		logger.info("queryListVitalSignConfig end");
		return resultVoList;
	}

	/**
	 * 统计cohort组里的患者数量
	 * @param studyId
	 * @param cohortIds
	 * @param allPatientsFlag
	 * @return
	 */
	public Integer countPatient(String studyId, String[] cohortIds, boolean allPatientsFlag){
		logger.info("countPatient begin");
		Integer result = 0;
		if(allPatientsFlag){
			result += hiveResultService.countPatient(studyId, GlobalConstant.STR_ETL_TYPE_DEMOGRAPHIC,null);
		} else {
			result += hiveResultService.countPatient(studyId, GlobalConstant.STR_ETL_TYPE_DEMOGRAPHIC, cohortIds);
		}
		logger.info("countPatient end");
		return result;
	}

	/**
	 * 获取属性数据
	 * @param studyId
	 * @param cohortIds
	 * @param patientCount
	 * @param demographicIndicatorMap
	 * @param dataMap
	 * @param dataSizeMap
	 * @param dataUnitMap
	 */
	public void queryListDemographicData(String studyId, String[] cohortIds, Integer patientCount, Map<String,HiveResultVo> demographicIndicatorMap, Map<String,Map<String,Integer>> dataMap, Map<String,Integer> dataSizeMap, Map<String,String> dataUnitMap) {
		logger.info("queryListDemographicData begin");

		for(String indicator : demographicIndicatorMap.keySet()){
			if(indicator.equalsIgnoreCase(GlobalConstant.STR_INDICATOR_AGE_GROUP)){
				Map<String, Object> groupAge = new LinkedHashMap<String, Object>();
				List<Object> ehCacheList = EhCacheUtils.getValuesWithKeys(GlobalConstant.STR_CACHE_NAME_GLOBAL_GROUP, indicator);
				for(Object object : ehCacheList){
					GlobalGroup globalGroup = (GlobalGroup) object;
					groupAge.put(globalGroup.getKey(), globalGroup.getValue());
				}
				List<Map<String, Object>> groupList = hiveResultService.queryListGroupCountData(studyId, GlobalConstant.STR_ETL_TYPE_DEMOGRAPHIC, GlobalConstant.STR_INDICATOR_AGE_GROUP, groupAge, cohortIds, null, null);
				Map<String, Integer> viewDateMap = new LinkedHashMap<String, Integer>();
				Integer sum = fillViewDataMap(viewDateMap, groupList.get(0), groupAge);
				viewDateMap.put("Missing", patientCount - sum);
				dataMap.put(indicator, viewDateMap);
				dataSizeMap.put(indicator, patientCount);
				dataUnitMap.put(indicator, GlobalConstant.STR_INDICATOR_AGE_GROUP);
			} else if(indicator.equalsIgnoreCase(GlobalConstant.STR_INDICATOR_GENDER)){
				List<Object> objList = EhCacheUtils.getValuesWithKeys(GlobalConstant.STR_CACHE_NAME_GLOBAL_CONFIG, studyId, GlobalConstant.STR_ETL_TYPE_DEMOGRAPHIC);
				List<HiveResultVo> demographicDataList = hiveResultService.queryListDemographicCountData(studyId, GlobalConstant.STR_ETL_TYPE_DEMOGRAPHIC, new String[]{indicator}, cohortIds, null, null);
				Map<String, Integer> viewDateMap = new LinkedHashMap<String, Integer>();
				Integer sum = 0;
				for(HiveResultVo result : demographicDataList){
					String genderKey = result.getKeyValue();
					for(Object obj : objList){
						GlobalConfig config = (GlobalConfig) obj;
						if(config.getKey().equals(result.getKeyValue())){
							genderKey = config.getValue();
							break;
						}
					}
					viewDateMap.put(genderKey, result.getPatientCount());
					sum += result.getPatientCount();
				}
				viewDateMap.put("Missing", patientCount - sum);
				dataMap.put(indicator, viewDateMap);
				dataSizeMap.put(indicator, patientCount);
				dataUnitMap.put(indicator, indicator);
			} else {
				List<HiveResultVo> demographicDataList = hiveResultService.queryListDemographicCountData(studyId, GlobalConstant.STR_ETL_TYPE_DEMOGRAPHIC, new String[]{indicator}, cohortIds, null, null);
				Map<String, Integer> viewDateMap = new LinkedHashMap<String, Integer>();
				Integer sum = 0;
				for(HiveResultVo result : demographicDataList){
					viewDateMap.put(result.getKeyValue(), result.getPatientCount());
					sum += result.getPatientCount();
				}
				viewDateMap.put("Missing", patientCount - sum);
				dataMap.put(indicator, viewDateMap);
				dataSizeMap.put(indicator, patientCount);
				dataUnitMap.put(indicator, indicator);
			}
		}

		logger.info("queryListDemographicData end");
	}

	/**
	 * 获取抽取数据
	 * @param studyId
	 * @param cohortIds
	 * @param patientCount
	 * @param indicatorMap
	 * @param dataMap
	 * @param dataSizeMap
	 * @param dataUnitMap
	 */
	public void queryListVitalSignData(String studyId, String[] cohortIds, Integer patientCount, Map<String,HiveResultVo> indicatorMap, Map<String,Map<String,Integer>> dataMap, Map<String,Integer> dataSizeMap, Map<String,String> dataUnitMap) {
		logger.info("queryListVitalSignData begin");
		for(String indicator : indicatorMap.keySet()){
			Map<String, Integer> viewDateMap = new LinkedHashMap<String, Integer>();
			Integer sum = 0;
			if(indicator.equals(GlobalConstant.STR_INDICATOR_BMI)){
				Map<String, Object> groupBmi = new LinkedHashMap<String, Object>();
				List<Object> ehCacheList = EhCacheUtils.getValuesWithKeys(GlobalConstant.STR_CACHE_NAME_GLOBAL_GROUP, indicator);
				for(Object object : ehCacheList){
					GlobalGroup globalGroup = (GlobalGroup) object;
					groupBmi.put(globalGroup.getKey(), globalGroup.getValue());
				}
				List<Map<String, Object>> groupList = hiveResultService.queryListVitalGroupCountData(studyId, GlobalConstant.STR_ETL_TYPE_VITAL_SIGN, GlobalConstant.STR_INDICATOR_BMI, groupBmi, cohortIds);
				sum = fillViewDataMap(viewDateMap, groupList.get(0), groupBmi);
			} else if(indicator.equals(GlobalConstant.STR_INDICATOR_DBP)){
				Map<String, Object> groupDbp = new LinkedHashMap<String, Object>();
				List<Object> ehCacheList = EhCacheUtils.getValuesWithKeys(GlobalConstant.STR_CACHE_NAME_GLOBAL_GROUP, indicator);
				for(Object object : ehCacheList){
					GlobalGroup globalGroup = (GlobalGroup) object;
					groupDbp.put(globalGroup.getKey(), globalGroup.getValue());
				}
				List<Map<String, Object>> groupList = hiveResultService.queryListVitalGroupCountData(studyId, GlobalConstant.STR_ETL_TYPE_VITAL_SIGN, GlobalConstant.STR_INDICATOR_DBP, groupDbp, cohortIds);
				sum = fillViewDataMap(viewDateMap, groupList.get(0), groupDbp);
			} else if(indicator.equals(GlobalConstant.STR_INDICATOR_SBP)){
				Map<String, Object> groupSbp = new LinkedHashMap<String, Object>();
				List<Object> ehCacheList = EhCacheUtils.getValuesWithKeys(GlobalConstant.STR_CACHE_NAME_GLOBAL_GROUP, indicator);
				for(Object object : ehCacheList){
					GlobalGroup globalGroup = (GlobalGroup) object;
					groupSbp.put(globalGroup.getKey(), globalGroup.getValue());
				}
				List<Map<String, Object>> groupList = hiveResultService.queryListVitalGroupCountData(studyId, GlobalConstant.STR_ETL_TYPE_VITAL_SIGN, GlobalConstant.STR_INDICATOR_SBP, groupSbp, cohortIds);
				sum = fillViewDataMap(viewDateMap, groupList.get(0), groupSbp);
			} else if(indicator.equals(GlobalConstant.STR_INDICATOR_WAIST)){
				Map<String, Object> groupWaist = new LinkedHashMap<String, Object>();
				List<Object> ehCacheList = EhCacheUtils.getValuesWithKeys(GlobalConstant.STR_CACHE_NAME_GLOBAL_GROUP, indicator);
				for(Object object : ehCacheList){
					GlobalGroup globalGroup = (GlobalGroup) object;
					groupWaist.put(globalGroup.getKey(), globalGroup.getValue());
				}
				List<Map<String, Object>> groupList = hiveResultService.queryListVitalGroupCountData(studyId, GlobalConstant.STR_ETL_TYPE_VITAL_SIGN, GlobalConstant.STR_INDICATOR_WAIST, groupWaist, cohortIds);
				sum = fillViewDataMap(viewDateMap, groupList.get(0), groupWaist);
			} else if(indicator.equals(GlobalConstant.STR_INDICATOR_CSTAGE) || indicator.equals(GlobalConstant.STR_INDICATOR_PSTAGE)){
				List<HiveResultVo> dataList = hiveResultService.queryListVitalStageData(studyId, GlobalConstant.STR_ETL_TYPE_VITAL_SIGN, new String[]{indicator}, cohortIds, null, null);
				for(HiveResultVo result : dataList){
					viewDateMap.put(result.getKeyValue(), result.getPatientCount());
					sum += result.getPatientCount();
				}
			} else if(indicator.equals(GlobalConstant.STR_INDICATOR_CANCER_TYPE)){
				List<Object> objList = EhCacheUtils.getValuesWithKeys(GlobalConstant.STR_CACHE_NAME_GLOBAL_CONFIG, studyId, GlobalConstant.STR_ETL_TYPE_VITAL_SIGN);
				List<HiveResultVo> dataList = hiveResultService.queryListVitalAttrData(studyId, GlobalConstant.STR_ETL_TYPE_VITAL_SIGN, new String[]{indicator}, cohortIds, null, null);
				for(HiveResultVo result : dataList){
					String key = result.getKeyValue();
					GlobalConfig globalConfig = null;
					for(Object obj : objList){
						GlobalConfig tmpGlobalConfig = (GlobalConfig) obj;
						if(tmpGlobalConfig.getKey().equals(key)){
							globalConfig = tmpGlobalConfig;
							break;
						}
					}
					if(globalConfig != null){
						String cacheKey = studyId + GlobalConstant.STR_CONNECTOR + GlobalConstant.STR_ETL_TYPE_VITAL_SIGN + GlobalConstant.STR_CONNECTOR + globalConfig.getParentId();
						GlobalConfig parentGlobalConfig = (GlobalConfig) EhCacheUtils.get(GlobalConstant.STR_CACHE_NAME_GLOBAL_CONFIG, cacheKey);
						if(parentGlobalConfig != null && parentGlobalConfig.getParentId() == 0L){
							key = globalConfig.getValue();
						} else if(parentGlobalConfig != null){
							key = parentGlobalConfig.getValue();
						}
					}

					Integer typeSum = viewDateMap.get(key);
					if(typeSum == null){
						viewDateMap.put(key, result.getPatientCount());
					} else {
						viewDateMap.put(key, typeSum + result.getPatientCount());
					}
					sum += result.getPatientCount();
				}
			} else {
				List<HiveResultVo> dataList = hiveResultService.queryListVitalAttrData(studyId, GlobalConstant.STR_ETL_TYPE_VITAL_SIGN, new String[]{indicator}, cohortIds, null, null);
				for(HiveResultVo result : dataList){
					viewDateMap.put(result.getKeyValue(), result.getPatientCount());
					sum += result.getPatientCount();
				}
			}

			viewDateMap.put("Missing", patientCount - sum);
			dataMap.put(indicator, viewDateMap);
			dataSizeMap.put(indicator, patientCount);
			String resultUnit = indicatorMap.get(indicator).getKeyUnit();
			if(StringUtils.isBlank(resultUnit) || resultUnit.contains("null")) {
				dataUnitMap.put(indicator, indicator);
			} else {
				dataUnitMap.put(indicator, indicator + " (" + resultUnit + ")");
			}
		}

		logger.info("queryListVitalSignData end");
	}

	/**
	 * 获取指标过滤条件列表
	 * @param configJson
	 * @return
	 */
	public Map<String, HiveResultVo> queryIndicatorMap(String configJson) {
		logger.info("queryIndicatorMap begin");

		Map<String, HiveResultVo> map = new LinkedHashMap<String, HiveResultVo>();
		JSONArray jsonArr = JSONArray.fromObject(configJson);
		JSONArray demographicJson = JSONArray.fromObject(jsonArr.getJSONObject(1));
		for(int i=0; i<demographicJson.size(); i++) {
			JSONArray indicatorJson = JSONArray.fromObject(demographicJson.getJSONObject(i).get(GlobalConstant.STR_ATTR_DEMOGRAPHIC_CONFIG_LIST));
			for(int j=0; j<indicatorJson.size(); j++) {
				JSONObject json = indicatorJson.getJSONObject(j);
				HiveResultVo result = new HiveResultVo();
				String indicator = json.get(GlobalConstant.STR_ATTR_INDICATOR).toString();
				result.setKeyName(indicator);
				result.setDescription(json.get(GlobalConstant.STR_ATTR_DESCRIPTION).toString());
				result.setKeyUnit(json.get(GlobalConstant.STR_ATTR_RESULT_UNIT).toString());
				map.put(indicator, result);
			}
		}

		logger.info("queryIndicatorMap end");
		return map;
	}

	/**
	 * 获取配置列表
	 * @param configJsonStr
	 * @return
	 */
	public List<ConfigVo> queryListConfigVo(String configJsonStr) {
		logger.info("queryListConfigVo begin");
		JSONArray jsonArr = JSONArray.fromObject(configJsonStr);
		JSONArray configJson = JSONArray.fromObject(jsonArr.getJSONObject(1));
		List<ConfigVo> resultVoList = hiveResultService.queryListConfigVo(configJson, GlobalConstant.STR_ATTR_DEMOGRAPHIC_CONFIG_LIST);
		logger.info("queryListConfigVo end");
		return resultVoList;
	}

	private Integer fillViewDataMap(Map<String, Integer> viewDateMap, Map<String, Object> map, Map<String, Object> groupMap){
		Integer sum = 0;
		for(Map.Entry<String, Object> entry : groupMap.entrySet()){
			String key = entry.getKey();
			String value = "";
			if(key.equals("less")){
				value = "[0, " + entry.getValue() + ")";
			} else if(key.equals("more")){
				value = "[" + entry.getValue() + ", ∞)";
			} else {
				value = "[" + key.replaceAll("!", ".") + ", " + entry.getValue() + ")";
			}
			Integer count = 0;
			Object obj = map.get(key);
			if(obj != null){
				count = ((Long) obj).intValue();
			}
			viewDateMap.put(value, count);
			sum += count;
		}

		return sum;
	}

}
