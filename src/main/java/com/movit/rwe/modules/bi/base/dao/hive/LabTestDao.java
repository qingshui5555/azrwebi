package com.movit.rwe.modules.bi.base.dao.hive;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.movit.rwe.common.persistence.annotation.MyBatisImpalaDao;
import com.movit.rwe.modules.bi.base.entity.hive.LabTest;

@MyBatisImpalaDao
public interface LabTestDao {

	List<LabTest> getAllLabTest(String studyId);

	List<String> findAllIndicator(@Param("studyId") String studyId);

	List<Map<String, Object>> getLabTestResult(@Param("studyId") String studyId, @Param("patientGroupIdArr") String[] patientGroupIdArr, @Param("indicator") String indicator);

	List<LabTest> getLabTestByIndicator(@Param("studyId") String studyId, @Param("indicator") String indicator, @Param("resultType") String resultType, @Param("cohorts") String[] cohorts);

}
