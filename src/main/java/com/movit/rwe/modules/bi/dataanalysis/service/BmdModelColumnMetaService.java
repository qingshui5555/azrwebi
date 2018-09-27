package com.movit.rwe.modules.bi.dataanalysis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movit.rwe.common.service.CrudService;
import com.movit.rwe.modules.bi.base.dao.mysql.BmdModelColumnMetaDao;
import com.movit.rwe.modules.bi.base.entity.mysql.BmdModelColumnMeta;

@Service
@Transactional
public class BmdModelColumnMetaService extends CrudService<BmdModelColumnMetaDao, BmdModelColumnMeta> {
	
	@Autowired
	private BmdModelColumnMetaDao bmdModelColumnMetaDao;
	
	public List<BmdModelColumnMeta> loadByModelMetaId(String modelMetaId){
		return bmdModelColumnMetaDao.loadByModelMetaId(modelMetaId);
	}
	
	
}
