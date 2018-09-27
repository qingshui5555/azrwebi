package com.movit.rwe.modules.bi.base.dao.hive;

import com.movit.rwe.common.persistence.annotation.MyBatisImpalaDao;
import com.movit.rwe.modules.bi.base.entity.hive.HivePatientAttr;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisImpalaDao
public interface HivePatientAttrDao {

    List<HivePatientAttr> queryListAll(@Param("studyIds") String[] studyIds);
}
