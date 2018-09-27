package com.movit.rwe.modules.bi.dashboard.service;

import java.util.*;

import com.movit.rwe.common.config.GlobalConstant;
import com.movit.rwe.common.utils.EhCacheUtils;
import com.movit.rwe.modules.bi.base.entity.hive.Treatment;
import com.movit.rwe.modules.bi.base.entity.mysql.TabView;
import com.movit.rwe.modules.bi.base.entity.vo.HiveTreatmentVo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Yuri.Zheng 治疗路径相关服务
 *
 */
@Service
public class TreatmentService {

	private static Logger logger = LoggerFactory.getLogger(TreatmentService.class);

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
	 * 智能搜索病历号并返回匹配列表
	 * @param studyId
	 * @param patientCode
	 * @return
	 */
	public List<String> matchPatientCode(String studyId, String patientCode) {
		logger.info("@@@In TreatmentService.matchPatientCode begin: studyId=" + studyId + ", patientCode=" + patientCode);
		List<String> patientCodeList = hiveResultService.queryListDemographicMatchData(studyId, GlobalConstant.STR_ETL_TYPE_DEMOGRAPHIC, GlobalConstant.STR_PATIENT_ATTR_REFERENCE_CODE, patientCode);
		logger.info("@@@In TreatmentService.matchPatientCode end");

		return patientCodeList;
	}

	/**
	 * 获取治疗集合
	 * @param patientCode
	 * @return
	 */
	public List<HiveTreatmentVo> queryListTreatmentVo(String studyId, String patientCode) {
		logger.info("@@@In TreatmentService.matchPatientCode begin: studyId=" + studyId + ", patientCode=" + patientCode);
		List<HiveTreatmentVo> resultList = hiveResultService.queryListTreatmentByPatientCode(studyId, GlobalConstant.STR_ETL_TYPE_TREATMENT, GlobalConstant.STR_PATIENT_ATTR_REFERENCE_CODE, patientCode);
		logger.info("@@@In TreatmentService.matchPatientCode begin: studyId=" + studyId + ", patientCode=" + patientCode);
		return resultList;
	}
}
