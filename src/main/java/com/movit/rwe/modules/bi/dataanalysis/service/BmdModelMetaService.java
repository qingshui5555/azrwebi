package com.movit.rwe.modules.bi.dataanalysis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movit.rwe.common.service.CrudService;
import com.movit.rwe.modules.bi.base.dao.mysql.BmdModelColumnMetaDao;
import com.movit.rwe.modules.bi.base.dao.mysql.BmdModelMetaDao;
import com.movit.rwe.modules.bi.base.entity.mysql.BmdModelMeta;

@Service
@Transactional
public class BmdModelMetaService extends CrudService<BmdModelMetaDao, BmdModelMeta> {
	
	@Autowired
	private BmdModelMetaDao bmdModelMetaDao;
	@Autowired
	private BmdModelColumnMetaDao bmdModelColumnMetaDao;
	
	public List<BmdModelMeta> findAllList(String studyId){
		BmdModelMeta bmdModelMeta = new BmdModelMeta();
		bmdModelMeta.setStudyId(studyId);
		return bmdModelMetaDao.findAllList(bmdModelMeta);
	}
	
	
}
