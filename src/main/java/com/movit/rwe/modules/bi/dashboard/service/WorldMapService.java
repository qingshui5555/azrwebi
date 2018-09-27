package com.movit.rwe.modules.bi.dashboard.service;

import com.movit.rwe.common.config.GlobalConstant;
import com.movit.rwe.common.config.GlobalGroupConfig;
import com.movit.rwe.common.utils.EhCacheUtils;
import com.movit.rwe.modules.bi.base.dao.mysql.EtlResultConfigDao;
import com.movit.rwe.modules.bi.base.entity.mysql.EtlResultConfig;
import com.movit.rwe.modules.bi.base.entity.mysql.GlobalGroup;
import com.movit.rwe.modules.bi.base.entity.mysql.TabView;
import com.movit.rwe.modules.bi.base.entity.vo.HiveResultVo;
import com.movit.rwe.modules.bi.dashboard.vo.ConfigVo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class WorldMapService {

	private static Logger logger = LoggerFactory.getLogger(WorldMapService.class);

	@Autowired
	private TabViewService tabViewService;

	@Autowired
	HiveResultService hiveResultService;

	@Autowired
	EtlResultConfigDao etlResultConfigDao;

	/**
	 * 获取tabView
	 * @param tabViewId
	 * @return
	 */
	public TabView queryTabViewByTabViewId(String tabViewId) {
		return tabViewService.findTabViewByTabViewId(tabViewId);
	}

	/**
	 * 获得配置信息的id
	 * @param configJson
	 * @return
	 */
	public List<ConfigVo> queryListConfigVo(JSONArray configJson) {
		logger.info("queryListConfigVo begin");
		List<ConfigVo> resultVoList = hiveResultService.queryListConfigVo(configJson, GlobalConstant.STR_ATTR_WORLD_MAP_CONFIG_LIST);
		logger.info("queryListConfigVo begin");
		return resultVoList;
	}

	/**
	 * 获取抽取数据配置列表
	 * @param studyId
	 * @param begin
	 * @param configVoList
	 * @return
	 */
	public List<HiveResultVo> queryListWorldMapConfig(String studyId, Integer begin, List<ConfigVo> configVoList) {
		logger.info("queryListWorldMapConfig begin");
		List<HiveResultVo> resultVoList = hiveResultService.queryListResultConfig(studyId, GlobalConstant.STR_ETL_TYPE_LAB_TEST, true, null, begin, configVoList);
		List<HiveResultVo> vitalList = hiveResultService.queryListResultConfig(studyId, GlobalConstant.STR_ETL_TYPE_VITAL_SIGN, true, null, 500, configVoList);
		resultVoList.addAll(vitalList);

		Collections.sort(resultVoList, new Comparator<HiveResultVo>() {
			@Override
			public int compare(HiveResultVo o1, HiveResultVo o2) {
				return o1.getOrder().compareTo(o2.getOrder());
			}
		});

		logger.info("queryListWorldMapConfig end");
		return resultVoList;
	}

	/**
	 * 获取指标过滤条件列表
	 * @param configJson
	 * @return
	 */
	public Map<String, String> queryIndicatorMap(String configJson) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		JSONArray jsonArr = JSONArray.fromObject(configJson);
		JSONArray labTestJson = JSONArray.fromObject(jsonArr.getJSONObject(1));
		for(int i=0; i<labTestJson.size(); i++) {
			JSONArray indicatorJson = JSONArray.fromObject(labTestJson.getJSONObject(i).get(GlobalConstant.STR_ATTR_WORLD_MAP_CONFIG_LIST));
			for(int j=0; j<indicatorJson.size(); j++) {
				JSONObject json = indicatorJson.getJSONObject(j);
				map.put(json.get(GlobalConstant.STR_ATTR_INDICATOR).toString(), json.get(GlobalConstant.STR_ATTR_DESCRIPTION).toString());
			}
		}

		return map;
	}

	public List<Map<String, Object>> queryListWorldMapData(String studyId, String countType, String indicator, String [] cohortIds, String startDate, String endDate){
		logger.info("queryListWorldMapData begin");
		EtlResultConfig etlResultConfig = etlResultConfigDao.queryEtlResultConfig(studyId, indicator);
		if(etlResultConfig == null){
			return null;
		}

		List<Map<String, Object>> resultList = hiveResultService.queryListWorldMap(studyId, etlResultConfig.getSubject(), countType, indicator, cohortIds, startDate, endDate);
		logger.info("queryListWorldMapData end");

		return resultList;
	}

}
