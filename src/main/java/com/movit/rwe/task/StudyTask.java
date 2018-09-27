package com.movit.rwe.task;

import com.movit.rwe.common.config.GlobalConstant;
import com.movit.rwe.common.utils.EhCacheUtils;
import com.movit.rwe.modules.bi.base.dao.hive.HiveStudyDao;
import com.movit.rwe.modules.bi.base.entity.hive.HiveStudy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author kwqb535
 * @date 2018.08.14
 */
@Service
@Lazy(false)
@Transactional
public class StudyTask implements ApplicationListener<ContextRefreshedEvent> {

	private static Logger logger = LoggerFactory.getLogger(CohortTask.class);

	@Autowired
	private HiveStudyDao hiveStudyDao;

	@Scheduled(cron = "0 0 * * * ?")
	public void synchronize() {
		logger.info("study synchronize begin");
		List<HiveStudy> hiveStudyList = hiveStudyDao.queryListAll(null);
		for (HiveStudy hiveStudy : hiveStudyList) {
			EhCacheUtils.put(GlobalConstant.STR_CACHE_NAME_STUDY, hiveStudy.getGuid(), hiveStudy);
		}
		logger.info("study synchronize end");
	}

	/**
	 * spring容器所有bean加载完后执行加载study数据到缓存
	 */
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		synchronize();
		logger.info("程序启动加载study数据到缓存中");
	}
}
