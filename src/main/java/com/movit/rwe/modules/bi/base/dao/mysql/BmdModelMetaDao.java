package com.movit.rwe.modules.bi.base.dao.mysql;

import com.movit.rwe.common.persistence.CrudDao;
import com.movit.rwe.common.persistence.annotation.MyBatisDao;
import com.movit.rwe.modules.bi.base.entity.mysql.BmdModelMeta;

@MyBatisDao
public interface BmdModelMetaDao extends CrudDao<BmdModelMeta> {
	
}
