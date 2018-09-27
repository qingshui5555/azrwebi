package com.movit.rwe.modules.bi.base.dao.mysql;

import com.movit.rwe.common.persistence.CrudDao;
import com.movit.rwe.common.persistence.annotation.MyBatisEtlLogDao;
import com.movit.rwe.modules.bi.base.entity.mysql.JobsLog;

@MyBatisEtlLogDao
public interface JobsLogDao extends CrudDao<JobsLog> {
	
	//List<Map<String,Object>> getAllJobsLog();
}
