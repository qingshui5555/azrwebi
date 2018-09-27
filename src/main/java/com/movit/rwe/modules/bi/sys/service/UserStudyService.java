package com.movit.rwe.modules.bi.sys.service;

import java.util.*;

import com.movit.rwe.common.config.GlobalConstant;
import com.movit.rwe.common.utils.EhCacheUtils;
import com.movit.rwe.modules.bi.base.entity.hive.HiveStudy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.movit.rwe.common.service.CrudService;
import com.movit.rwe.modules.bi.base.dao.mysql.UserStudyDao;
import com.movit.rwe.modules.bi.base.entity.mysql.UserStudy;

@Service
public class UserStudyService extends CrudService<UserStudyDao, UserStudy> {

	private static Logger logger = LoggerFactory.getLogger(UserStudyService.class);

	public List<HiveStudy> queryListStudyByUserId(String userId) {
		logger.info("@@@IN queryListStudyByUserId begin ");
		List<UserStudy> userStudyList = dao.queryListByUserId(userId);
		List<String> studyIdList = new ArrayList<String>();
		for (UserStudy userStudy : userStudyList) {
			studyIdList.add(userStudy.getStudyId());
		}

		List<HiveStudy> resultList = new ArrayList<HiveStudy>();
		List<Object> ehList = EhCacheUtils.getValuesByKeys(GlobalConstant.STR_CACHE_NAME_STUDY, studyIdList);
		if (null != ehList && ehList.size() > 0) {
			for (Object object : ehList) {
				if (!"".equals(object)) {
					resultList.add((HiveStudy) object);
				}
			}
		}

		logger.info("@@@IN queryListStudyByUserId end");
		return resultList;
	}

	public List<HiveStudy> queryListAllStudy() {
		logger.info("@@@IN queryListAllStudy begin ");
		List<HiveStudy> resultList = new ArrayList<HiveStudy>();
		List<Object> ehList = EhCacheUtils.getValues(GlobalConstant.STR_CACHE_NAME_STUDY);
		for (Object object : ehList) {
			resultList.add((HiveStudy) object);
		}

		logger.info("@@@IN queryListAllStudy end");
		return resultList;
	}
}
