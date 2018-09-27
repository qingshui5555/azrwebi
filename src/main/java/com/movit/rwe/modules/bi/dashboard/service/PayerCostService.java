package com.movit.rwe.modules.bi.dashboard.service;

import java.util.*;

import com.movit.rwe.common.config.GlobalConstant;
import com.movit.rwe.modules.bi.base.entity.mysql.TabView;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PayerCostService {

	private static Logger logger = LoggerFactory.getLogger(PayerCostService.class);

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
	 *
 	 * @param studyId
	 * @param cohortIds
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Map<String, Object>> queryListDetailPayerCost(String studyId, String[] cohortIds, String startDate, String endDate) {
		logger.info("queryListDetailPayerCost begin");
		List<Map<String, Object>> resultList = hiveResultService.queryListDetailPayerCost(studyId, GlobalConstant.STR_ETL_TYPE_PAYER_COST, cohortIds, startDate, endDate);
		Map<String, Object> otherMap = null;
		Iterator<Map<String, Object>> it = resultList.iterator();
		while(it.hasNext()){
			Map<String, Object> map = it.next();
			String name = (String) map.get("item");
			if(name.equalsIgnoreCase("Other")){
				otherMap = map;
				it.remove();
			}
		}
		Collections.sort(resultList,new Comparator<Map<String, Object>>() {
			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				String name1 = (String) o1.get("item");
				String name2 = (String) o2.get("item");
				if(StringUtils.isBlank(name1) || StringUtils.isBlank(name2)){
					return 0;
				} else {
					return name1.compareTo(name2);
				}

			}
		});
		resultList.add(otherMap);
		logger.info("queryListDetailPayerCost end");
		return resultList;
	}

	/**
	 *
	 * @param studyId
	 * @param cohortIds
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Map<String, Object>> queryListTotalPayerCost(String studyId, String[] cohortIds, String startDate, String endDate) {
		logger.info("queryListTotalPayerCost begin");
		List<Map<String, Object>> resultDetailList = hiveResultService.queryListTotalPayerCost(studyId, GlobalConstant.STR_ETL_TYPE_PAYER_COST, cohortIds, startDate, endDate);
		logger.info("queryListTotalPayerCost end");
		return resultDetailList;
	}
}