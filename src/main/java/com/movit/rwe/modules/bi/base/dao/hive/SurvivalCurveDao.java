package com.movit.rwe.modules.bi.base.dao.hive;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.movit.rwe.common.persistence.CrudDao;
import com.movit.rwe.common.persistence.annotation.MyBatisImpalaDao;
import com.movit.rwe.modules.bi.base.entity.hive.EffectionDict;

@MyBatisImpalaDao
public interface SurvivalCurveDao extends CrudDao<EffectionDict> {

	List<Map<String, Object>> get(@Param("studyId") String studyId, @Param("cohort") String cohort);

	List<Map<String, Object>> getPdSurvivalTime(@Param("studyId") String studyId, @Param("cohort") String cohort,@Param("timeType") Integer timeType);
}
