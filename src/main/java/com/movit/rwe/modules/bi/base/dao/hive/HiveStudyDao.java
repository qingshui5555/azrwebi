package com.movit.rwe.modules.bi.base.dao.hive;

import com.movit.rwe.common.persistence.annotation.MyBatisImpalaDao;
import com.movit.rwe.modules.bi.base.entity.hive.HiveStudy;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisImpalaDao
public interface HiveStudyDao {

    List<HiveStudy> queryListAll(@Param("studyIds") String[] studyIds);
}
