package com.movit.rwe.modules.bi.dashboard.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

import com.movit.rwe.common.config.GlobalConstant;
import com.movit.rwe.common.utils.EhCacheUtils;
import com.movit.rwe.modules.bi.base.entity.hive.HiveCohort;
import com.movit.rwe.modules.bi.base.entity.mysql.TabView;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movit.rwe.modules.bi.base.dao.hive.SurvivalCurveDao;

@Service
@Transactional
public class SurvivalCurveService {

	private static Logger logger = LoggerFactory.getLogger(SurvivalCurveService.class);

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
	 * 获取生存曲线数据
	 * @param studyId
	 * @param cohortIds
	 * @param countType
	 * @param timeType
	 * @param allPatientsCount
	 * @return
	 */
	public List<Map<String, Object>> queryListSurvivalCurve(String studyId, String[] cohortIds, String countType, String timeType, Boolean allPatientsCount) {
		logger.info("queryListSurvivalCurve begin");
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        if(allPatientsCount) {
            Map<String, Object> resultMap = createSurvivalCurve(studyId, null, countType, timeType);
            resultMap.put("name", "All Patients");
            resultList.add(resultMap);

        } else {
            if(cohortIds != null){
                for(String cohortId : cohortIds){
                    resultList.add(createSurvivalCurve(studyId, cohortId, countType, timeType));
                }
            } else {
                resultList.add(createSurvivalCurve(studyId, null, countType, timeType));
            }
        }

		logger.info("queryListSurvivalCurve end");
		return resultList;
	}

	private Map<String, Object> createSurvivalCurve(String studyId, String cohortId, String countType, String timeType){
        Map<String, Object> resultMap = new TreeMap<String, Object>();
        Integer number = 0;
        List<Map<String, Object>> dataList = null;
        if (StringUtils.isBlank(countType)) {
            number = 5;
            dataList = hiveResultService.queryListSurvivalCurve(studyId, GlobalConstant.STR_ETL_TYPE_SURVIVAL_CURVE, cohortId, timeType);
        } else {
            number = 15;
            dataList = hiveResultService.queryListFirstPDCurve(studyId, GlobalConstant.STR_ETL_TYPE_SURVIVAL_CURVE, cohortId, timeType);
        }

        Integer sum = 0;
        for (Map<String, Object> map : dataList) {
            if (map.get("counter") == null || "0".equals(map.get("counter").toString())) {
                continue;
            } else {
                sum += Integer.parseInt(map.get("counter").toString());
            }
            Integer survival = Integer.parseInt(map.get("survival").toString());
            if (survival > number) {
                continue;
            }

            resultMap.put("death_" + survival, survival);
            resultMap.put("survivalPer_" + survival, sum);
        }

        if(StringUtils.isBlank(cohortId)){
            resultMap.put("name", "");
        } else {
            String key = studyId + GlobalConstant.STR_CONNECTOR + cohortId;
            HiveCohort hiveCohort = (HiveCohort) EhCacheUtils.get(GlobalConstant.STR_CACHE_NAME_COHORT, key);
            if(hiveCohort != null) {
                resultMap.put("name", hiveCohort.getCohortName());
            } else {
                resultMap.put("name", "");
            }
        }

        if(sum.equals(0)){
            sum += 1;
        }

        Integer counter = sum;
        for (int i = 1; i < number + 1; i++) {
            Object object = resultMap.get("survivalPer_" + i);
            if (object == null) {
                resultMap.put("death_" + i, i);
                resultMap.put("survivalPer_" + i, new BigDecimal(counter * 100.0 / sum).setScale(2, BigDecimal.ROUND_HALF_UP));
            } else {
                counter = Integer.parseInt(object.toString());
                resultMap.put("survivalPer_" + i, new BigDecimal(counter * 100.0 / sum).setScale(2, BigDecimal.ROUND_HALF_UP));
            }
        }

        resultMap.put("survivalSum", sum);
        resultMap.put("number", number);
        return resultMap;
    }
}