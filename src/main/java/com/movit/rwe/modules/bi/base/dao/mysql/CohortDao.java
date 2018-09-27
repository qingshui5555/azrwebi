package com.movit.rwe.modules.bi.base.dao.mysql;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.movit.rwe.common.persistence.CrudDao;
import com.movit.rwe.common.persistence.annotation.MyBatisDao;
import com.movit.rwe.modules.bi.base.entity.mysql.Cohort;

@MyBatisDao
public interface CohortDao extends CrudDao<Cohort> {
	
	public List<Map> getAllCohortUnionGroup();
	
	String getCohortName(@Param("id")String id);
	
}
