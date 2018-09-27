package com.movit.rwe.modules.bi.dashboard.service;

import java.util.*;

import com.movit.rwe.common.config.GlobalConstant;
import com.movit.rwe.common.utils.EhCacheUtils;
import com.movit.rwe.modules.bi.base.entity.hive.HiveCohort;
import com.movit.rwe.modules.bi.base.entity.hive.HiveResult;
import com.movit.rwe.modules.bi.base.entity.mysql.TabView;
import com.movit.rwe.modules.bi.base.entity.vo.HiveBiomarkerTestConfigVo;
import com.movit.rwe.modules.bi.base.entity.vo.HiveResultVo;
import com.movit.rwe.modules.bi.base.entity.vo.HiveVitalSignVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.movit.rwe.modules.bi.base.dao.hive.BiomarkerTestDao;
import com.movit.rwe.modules.bi.base.dao.hive.LabTestDao;
import com.movit.rwe.modules.bi.base.dao.mysql.CohortDao;
import com.movit.rwe.modules.bi.base.dao.mysql.PatientGroupDao;
import com.movit.rwe.modules.bi.base.dao.mysql.PatientsDao;
import com.movit.rwe.modules.bi.base.dao.mysql.TabViewDao;
import com.movit.rwe.modules.bi.base.entity.hive.BiomarkerTest;
import com.movit.rwe.modules.bi.base.entity.mysql.PatientGroup;
import com.movit.rwe.modules.bi.dashboard.utils.BiomarkerTestSort;
import com.movit.rwe.modules.bi.dashboard.utils.PatientGroupInfoUtil;
import com.movit.rwe.modules.bi.dashboard.vo.BiomarkerTestVo;

import javax.xml.bind.annotation.XmlElementDecl;

@Service
@Transactional
public class BiomarkerTestService {

	private static Logger logger = LoggerFactory.getLogger(BiomarkerTestService.class);

	@Autowired
	private TabViewService tabViewService;

	@Autowired
	HiveResultService hiveResultService;

	@Autowired
	private BiomarkerTestDao biomarkerTestDao;

	@Autowired
	private LabTestDao labTestDao;

	/**
	 * 获取tabView
	 * @param tabViewId
	 * @return
	 */
	public TabView queryTabViewByTabViewId(String tabViewId) {
		return tabViewService.findTabViewByTabViewId(tabViewId);
	}

	/**
	 * 获取biomarker配置
	 * @param studyId
	 * @return
	 */
	public Map<String, Set<String>> queryListBiomarkerTestConfig(String studyId) {
		List<Map<String, String>> voList = hiveResultService.queryListBiomarkerTestConfig(studyId, GlobalConstant.STR_ETL_TYPE_BIOMARKER_TEST);

		Map<String, Set<String>> resultMap = new HashMap<String, Set<String>>();

		for (Map<String, String> map : voList) {
			String measure = map.get("measure");
			String gene = map.get("gene");
			Set<String> resultSet = resultMap.get(measure);
			if (resultSet == null) {
				resultSet = new TreeSet<String>();
				resultMap.put(measure, resultSet);
			}

			resultSet.add(gene);
		}

		return resultMap;
	}

	public List<PatientGroup> findAllPatientGroup(String studyId) {
		List<PatientGroup> resultList = new ArrayList<PatientGroup>();
		List<Object> objectList = EhCacheUtils.getValuesWithKeys(GlobalConstant.STR_CACHE_NAME_COHORT, studyId);
		for(Object obj : objectList){
			HiveCohort hiveCohort = (HiveCohort) obj;
			PatientGroup patientGroup = new PatientGroup();
			patientGroup.setGroupType(1);
			patientGroup.setGroupName(hiveCohort.getCohortName());
			patientGroup.setPatinetCount(hiveCohort.getPatientCount().intValue());
			patientGroup.setId(hiveCohort.getGuid());
			resultList.add(patientGroup);
		}
		return resultList;
	}

	public Map<String, Set<String>> findBiomarkerAndResultTypeGroup(String studyId) {
		Map<String, Set<String>> resultMap = new LinkedHashMap<String, Set<String>>();
		Set<String> resultSet = new TreeSet<String>();
		resultSet.add("mutation");
		resultMap.put("Mutation", resultSet);
		return resultMap;
	}

	public List<String> findAllIndicator(String studyId) {
		Set<String> resultSet = new HashSet<String>();
		List<HiveResultVo> hiveResultVoList = hiveResultService.queryListResultConfig(studyId, GlobalConstant.STR_ETL_TYPE_LAB_TEST, true, null, 1, null);
		for(HiveResultVo vo : hiveResultVoList){
			resultSet.add(vo.getKeyName());
		}
		return new ArrayList<String>(resultSet);
	}

	public Map<String, String> getPatientGroupNameArr(String studyId, List<String> patientGroupIdList) {
		Map<String, String> map = new HashMap<String, String>();

		for (String patientGroupId : patientGroupIdList) {
			String groupName = getGroupName(studyId, patientGroupId);
			map.put(patientGroupId, groupName);
		}
		return map;
	}

	private String getGroupName(String studyId, String groupId) {
		String groupName = "";
		if (groupId.indexOf("C") != -1) {
			HiveCohort hiveCohort = (HiveCohort) EhCacheUtils.get(GlobalConstant.STR_CACHE_NAME_COHORT, studyId + GlobalConstant.STR_CONNECTOR + groupId.split("_")[1]);
			groupName = hiveCohort.getCohortName();
		}
		return groupName;
	}

	public Map<String, Object> getBoxPlotDate(String studyId, String[] patientGroupIdArr, String type, String value) {
		if ("labTest".equals(type)) {
			return getLabTestBoxPlotDate(studyId, patientGroupIdArr, value);
		}
		if ("genObj".equals(type)) {
			return getBiomarkerBoxPlotDate(studyId, patientGroupIdArr, value);
		}
		return null;
	}

	private Map<String, Object> getBiomarkerBoxPlotDate(String studyId, String[] patientGroupIdArr, String value) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		String[] valueArr = value.split("_");
		String gene = valueArr[0];
		String resultType = valueArr.length > 1 ? valueArr[1] : "";
		// String patientGroupId =
		// PatientGroupInfoUtil.sortGroupids(Arrays.asList(patientGroupIdArr));

		List<Map<String, Object>> biomarkerTestScoreList = biomarkerTestDao.getBiomarkerTestScore(studyId, patientGroupIdArr, gene, resultType);

		resultMap.put("success", true);
		resultMap.put("scoreList", biomarkerTestScoreList);
		return resultMap;
	}

	private Map<String, Object> getLabTestBoxPlotDate(String studyId, String[] patientGroupIdArr, String indicator) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> labTestResultList = labTestDao.getLabTestResult(studyId, patientGroupIdArr, indicator);

		resultMap.put("success", true);
		resultMap.put("scoreList", labTestResultList);
		return resultMap;
	}

	public List<String> getAllEvaluation(String studyId, String measureType) {
		return hiveResultService.queryListEvaluation(studyId, GlobalConstant.STR_ETL_TYPE_BIOMARKER_TEST, measureType);
	}

	public List<Integer> getBiomarkerTimes(String studyId, String measureType, String[] cohortIds) {
		return hiveResultService.queryBiomarkerTimes(studyId, GlobalConstant.STR_ETL_TYPE_BIOMARKER_TEST, measureType, cohortIds);
	}

	public List<BiomarkerTestVo> queryListBiomarkerTestData(String studyId, String[] cohortIds, String[] biomarkers, String measureType, Integer times) {
		return hiveResultService.queryListBiomarkerTestData(studyId, GlobalConstant.STR_ETL_TYPE_BIOMARKER_TEST, cohortIds, biomarkers, measureType, times);
	}

	public List<String> queryListBiomarkerTestSubData(String studyId, String measureType, String biomarker) {
		List<String> list = hiveResultService.queryListBiomarkerTestSubData(studyId, GlobalConstant.STR_ETL_TYPE_BIOMARKER_TEST, measureType, biomarker);
		return list;
	}
}