package com.movit.rwe.modules.bi.base.dao.hive;

import java.util.List;
import java.util.Map;

import com.movit.rwe.common.persistence.CrudDao;
import com.movit.rwe.common.persistence.annotation.MyBatisImpalaDao;

@MyBatisImpalaDao
public interface DataAnalysisDao extends CrudDao<Object> {

	List<Map> executeSql(String sql);

}
