package com.movit.rwe.modules.bi.base.dao.hive;

import java.util.List;

import com.movit.rwe.common.persistence.CrudDao;
import com.movit.rwe.common.persistence.annotation.MyBatisImpalaDao;
import com.movit.rwe.common.persistence.annotation.MyBatisKylinDao;
import com.movit.rwe.modules.bi.base.entity.hive.EffectionDict;

@MyBatisImpalaDao
public interface EffectionDictDao extends CrudDao<EffectionDict> {

	List<EffectionDict> getAll();

}

