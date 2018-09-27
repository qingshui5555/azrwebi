package com.movit.rwe.modules.bi.base.dao.hive;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.movit.rwe.common.persistence.CrudDao;
import com.movit.rwe.common.persistence.annotation.MyBatisImpalaDao;
import com.movit.rwe.modules.bi.base.entity.hive.PatientGroupInfo;

@MyBatisImpalaDao
public interface PatientGroupInfoDao extends CrudDao<PatientGroupInfo> {

	List<String> getPatientIdsLikeGroupIds(@Param("groupIds")String[] groupIds);

}
