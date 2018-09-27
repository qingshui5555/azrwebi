package com.movit.rwe.modules.bi.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movit.rwe.common.service.CrudService;
import com.movit.rwe.modules.bi.base.dao.mysql.TaDao;
import com.movit.rwe.modules.bi.base.entity.mysql.Ta;
@Service
@Transactional
public class TaService extends CrudService<TaDao, Ta> {
	
	public List<Ta> findList(){
		return dao.findList();
	}

	public Ta getByStudyId(String studyId){
		return dao.getByStudyId(studyId);
	}
}
