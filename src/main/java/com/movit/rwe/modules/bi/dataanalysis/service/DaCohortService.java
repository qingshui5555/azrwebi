package com.movit.rwe.modules.bi.dataanalysis.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movit.rwe.common.service.CrudService;
import com.movit.rwe.modules.bi.base.dao.mysql.CohortDao;
import com.movit.rwe.modules.bi.base.entity.mysql.Cohort;

@Service
@Transactional
public class DaCohortService extends CrudService<CohortDao, Cohort> {
	
	@Autowired
	private CohortDao cohortDao;
	
	/**
	 * 查询cohort和group表union的总数据
	 * @return
	 */
	public List<Map> getAllConhortUnionGroup(){
		return cohortDao.getAllCohortUnionGroup();
	}
}
