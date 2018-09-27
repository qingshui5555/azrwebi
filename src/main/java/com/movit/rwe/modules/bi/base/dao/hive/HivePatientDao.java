package com.movit.rwe.modules.bi.base.dao.hive;

import com.movit.rwe.common.persistence.annotation.MyBatisImpalaDao;
import com.movit.rwe.modules.bi.base.entity.hive.HivePatient;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@MyBatisImpalaDao
public interface HivePatientDao {

    List<HivePatient> queryListAll(@Param("studyIds") String[] studyIds);
}
