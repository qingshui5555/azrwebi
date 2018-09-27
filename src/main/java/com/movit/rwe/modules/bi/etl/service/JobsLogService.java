package com.movit.rwe.modules.bi.etl.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movit.rwe.common.service.CrudService;
import com.movit.rwe.modules.bi.base.dao.mysql.JobsLogDao;
import com.movit.rwe.modules.bi.base.entity.mysql.JobsLog;

@Service
@Transactional(readOnly = true)
public class JobsLogService extends CrudService<JobsLogDao, JobsLog> {
	
}
