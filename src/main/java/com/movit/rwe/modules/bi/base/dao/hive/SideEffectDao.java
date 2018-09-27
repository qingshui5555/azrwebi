package com.movit.rwe.modules.bi.base.dao.hive;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.ibatis.annotations.Param;

import com.movit.rwe.common.persistence.CrudDao;
import com.movit.rwe.common.persistence.annotation.MyBatisImpalaDao;
import com.movit.rwe.common.persistence.annotation.MyBatisKylinDao;
import com.movit.rwe.modules.bi.base.entity.hive.SideEffect;
import com.movit.rwe.modules.bi.dashboard.vo.SideEffectVo;

@MyBatisImpalaDao
public interface SideEffectDao extends CrudDao<SideEffect> {

	List<SideEffectVo> getSideEffectVo(@Param("studyId") String studyId, @Param("drug") String drug, @Param("solution") String solution, @Param("unionDrugTreatmentIdArr") String[] unionDrugTreatmentIdArr,
			@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("query") String[] query);

	TreeSet<String> getDrug(@Param("studyId") String studyId, @Param("cohorts") String[] cohorts);

}
