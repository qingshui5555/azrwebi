package com.movit.rwe.modules.bi.etl.service;

import java.util.List;

import com.movit.rwe.modules.bi.base.dao.mysql.OpenmrsUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movit.rwe.modules.bi.base.dao.mysql.RweUserStudyDao;

@Service
@Transactional
public class RweUserService {

	@Autowired
	private RweUserStudyDao rweUserStudyDao;

	@Autowired
	private OpenmrsUserDao openmrsUserDao;

	public String getRweUserStudyIds(String loginName) {
		return rweUserStudyDao.getRweUserStudyIds(loginName);
	}

	public Integer getRweUserByLoginName(String loginName) {
		// TODO Auto-generated method stub
		return rweUserStudyDao.getRweUserByLoginName(loginName);
	}

	public String getForcePassword(String username){
		String forcePassword = null;
		List<String> list = openmrsUserDao.getForcePassword(username);
		if(null != list && list.size() > 0){
			forcePassword = list.get(0);
		}
		return forcePassword;
	}
}
