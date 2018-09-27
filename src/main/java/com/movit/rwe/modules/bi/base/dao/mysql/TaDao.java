package com.movit.rwe.modules.bi.base.dao.mysql;

import java.util.List;

import com.movit.rwe.common.persistence.CrudDao;
import com.movit.rwe.common.persistence.annotation.MyBatisDao;
import com.movit.rwe.modules.bi.base.entity.mysql.Ta;

@MyBatisDao
public interface TaDao extends CrudDao<Ta> {
	
	List<Ta> findList();

	Ta getByStudyId(String studyId);
}
