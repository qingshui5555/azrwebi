package com.movit.rwe.modules.bi.base.dao.hive;

import com.movit.rwe.common.persistence.CrudDao;
import com.movit.rwe.common.persistence.annotation.MyBatisImpalaDao;
import com.movit.rwe.modules.bi.base.entity.hive.PatientEvent;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisImpalaDao
public interface PatientEventChartDao extends CrudDao<PatientEvent> {

	List<PatientEvent> getPatientEventByStudyIdAndCohorts(@Param("studyId")String studyId,@Param("cohorts")String[] cohorts,
			@Param("referenceCodeList")List<String> referenceCodeList, @Param("assessmentResponseList")List<String>assessmentResponseList,
			@Param("eventTypeList")List<String> eventTypeList,@Param("pageNo")Integer pageNo);

	List<PatientEvent> getAllAssessmentOfResponse();

	List<PatientEvent> getAllEvent();

}
