package com.movit.rwe.modules.bi.base.dao.hive;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.movit.rwe.common.persistence.CrudDao;
import com.movit.rwe.common.persistence.annotation.MyBatisImpalaDao;
import com.movit.rwe.modules.bi.base.entity.hive.BiomarkerTest;
import com.movit.rwe.modules.bi.dashboard.vo.BiomarkerTestVo;

@MyBatisImpalaDao
public interface BiomarkerTestDao extends CrudDao<BiomarkerTest> {

	List<BiomarkerTest> getByReferenceCode(@Param("studyId") String studyId, @Param("referenceCode") String referenceCode);

	List<String> getSubBiomarkers(@Param("studyId") String studyId, @Param("biomarker") String biomarker);

	List<BiomarkerTestVo> getAmountAndBiomarkerByCohortAndStudyId(@Param("cohorts") String[] cohorts, @Param("studyId") String studyId, @Param("evaluation") String evaluation,
			@Param("biomarkerListSelected") Set<String> biomarkerListSelected, @Param("measureType") String measureType,@Param("times") Integer Times);
	
	List<BiomarkerTestVo> getAmountAndUnionBiomarkerByCohortAndStudyId(@Param("cohorts") String[] cohorts, @Param("studyId") String studyId, @Param("evaluation") String evaluation,
			@Param("unionBiomarkerListSelected") Set<String> unionBiomarkerListSelected, @Param("measureType") String measureType,@Param("times") Integer Times);


	List<BiomarkerTestVo> getAllEvaluation(@Param("measureType") String measureType);

	List<BiomarkerTestVo> getAllBiomarker(@Param("studyId") String studyId, @Param("cohorts") String[] cohorts);

	List<BiomarkerTest> findBiomarkerAndResultTypeGroup(String studyId);

	List<BiomarkerTest> getAllResultType();

	List<Map<String, Object>> getBiomarkerTestScore(@Param("studyId") String studyId, @Param("patientGroupIdArr") String[] patientGroupIdArr, @Param("gene") String gene,
			@Param("resultType") String resultType);

	List<BiomarkerTest> getBiomarkerByIndicator(@Param("studyId") String studyId, @Param("indicator") String indicator, @Param("resultType") String resultType, @Param("cohorts") String[] cohorts);

	List<Map<String, String>> findBiomarkerTestFilter(@Param("studyId") String studyId);

	List<Integer> getBiomarkerTimes(@Param("studyId") String studyId, @Param("measureType") String measureType,@Param("cohorts") String[] cohorts);
	
}
