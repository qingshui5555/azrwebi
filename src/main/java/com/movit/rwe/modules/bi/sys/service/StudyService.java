package com.movit.rwe.modules.bi.sys.service;

import java.util.List;

import com.movit.rwe.modules.bi.sys.vo.StudyVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movit.rwe.common.service.CrudService;
import com.movit.rwe.modules.bi.base.dao.mysql.StudyDao;
import com.movit.rwe.modules.bi.base.entity.mysql.Study;
@Service
@Transactional
public class StudyService extends CrudService<StudyDao, Study> {
	
	public List<Study> findListByTaId(String taId){
		return dao.findListByTaId(taId);
	}

	public List<StudyVo> findAllStudies() {
		return dao.findVoList();
	}
	
	public List<Study> findAllStudy() {
		return dao.findAllStudy();
	}

}
