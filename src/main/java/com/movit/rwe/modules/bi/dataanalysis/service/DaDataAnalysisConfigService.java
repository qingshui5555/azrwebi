package com.movit.rwe.modules.bi.dataanalysis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movit.rwe.common.service.CrudService;
import com.movit.rwe.modules.bi.base.dao.mysql.DaDataAnalysisConfigDao;
import com.movit.rwe.modules.bi.base.entity.mysql.DaDataAnalysisConfig;

@Service
@Transactional
public class DaDataAnalysisConfigService extends CrudService<DaDataAnalysisConfigDao, DaDataAnalysisConfig> {
	
	@Autowired
	private DaDataAnalysisConfigDao daDataAnalysisConfigDao;
	
}
