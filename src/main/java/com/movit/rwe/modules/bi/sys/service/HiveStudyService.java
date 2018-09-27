package com.movit.rwe.modules.bi.sys.service;

import com.movit.rwe.common.service.CrudService;
import com.movit.rwe.modules.bi.base.dao.mysql.StudyDao;
import com.movit.rwe.modules.bi.base.entity.mysql.Study;
import com.movit.rwe.modules.bi.sys.vo.StudyVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class HiveStudyService extends CrudService<StudyDao, Study> {
	
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
