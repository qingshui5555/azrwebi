package com.movit.rwe.modules.bi.base.dao.mysql;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.movit.rwe.common.persistence.CrudDao;
import com.movit.rwe.common.persistence.annotation.MyBatisDao;
import com.movit.rwe.modules.bi.base.entity.mysql.DemographicView;

@MyBatisDao
public interface DemographicViewDao extends CrudDao<DemographicView> {

	public List<DemographicView> findAll();

	public DemographicView findById(@Param("id") String id);
}
