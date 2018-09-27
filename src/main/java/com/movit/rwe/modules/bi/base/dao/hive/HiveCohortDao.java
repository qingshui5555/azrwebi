package com.movit.rwe.modules.bi.base.dao.hive;

import com.movit.rwe.common.persistence.CrudDao;
import com.movit.rwe.common.persistence.annotation.MyBatisImpalaDao;
import com.movit.rwe.modules.bi.base.entity.hive.HiveCohort;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisImpalaDao
public interface HiveCohortDao extends CrudDao<HiveCohort> {

	List<HiveCohort> queryListAll(@Param("studyIds") String[] studyIds);

	Integer countByCohortIds(@Param("cohortIds") String[] cohortIds);

	List<String> queryListPatientId(@Param("cohortIds") String[] cohortIds);
}
