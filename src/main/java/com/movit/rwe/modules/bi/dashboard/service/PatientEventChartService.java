package com.movit.rwe.modules.bi.dashboard.service;

import com.movit.rwe.modules.bi.base.dao.hive.PatientEventChartDao;
import com.movit.rwe.modules.bi.base.dao.hive.PatientGroupInfoDao;
import com.movit.rwe.modules.bi.base.entity.hive.PatientEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PatientEventChartService {

	@Autowired
	private PatientEventChartDao patientEventChartDao;
	
	@Autowired
	private PatientGroupInfoDao patientGroupInfoDao;
    
	/**
	 * 根据studyId和cohorts查询patientEvent
	 * @param studyId
	 * @param cohorts
	 * @return
	 */
	
	public List<PatientEvent> getPatientEventByStudyIdAndCohorts(String studyId,String[] cohorts,List<String> referenceCodeList,List<String>assessmentResponseList,List<String> eventTypeList,Integer pageNo) {
		
		return patientEventChartDao.getPatientEventByStudyIdAndCohorts(studyId,cohorts,referenceCodeList,assessmentResponseList,eventTypeList,pageNo);
	}

	public List<String> getPatientIdsLikeGroupIds(String[] groupIds) {
		// TODO Auto-generated method stub
		return patientGroupInfoDao.getPatientIdsLikeGroupIds(groupIds);
	}

	public List<PatientEvent> getAllAssessmentOfResponse() {
		// TODO Auto-generated method stub
		return patientEventChartDao.getAllAssessmentOfResponse();
	}

	public List<PatientEvent> getAllEvent() {
		// TODO Auto-generated method stub
		return patientEventChartDao.getAllEvent();
	}

}