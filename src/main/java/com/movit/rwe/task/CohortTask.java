package com.movit.rwe.task;

import com.movit.rwe.common.config.GlobalConstant;
import com.movit.rwe.common.utils.EhCacheUtils;
import com.movit.rwe.modules.bi.base.dao.hive.HiveCohortDao;
import com.movit.rwe.modules.bi.base.entity.hive.HiveCohort;
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
public class CohortTask implements ApplicationListener<ContextRefreshedEvent> {
    private static Logger logger = LoggerFactory.getLogger(StudyTask.class);

    @Autowired
    private HiveCohortDao hiveCohortDao;

    @Scheduled(cron = "0 0 * * * ?")
    public void synchronize(){
        logger.info("cohort synchronize begin");
        List<HiveCohort> hiveCohortList = hiveCohortDao.queryListAll(null);
        for(HiveCohort hiveCohort : hiveCohortList){
            String key = hiveCohort.getStudyId() + GlobalConstant.STR_CONNECTOR + hiveCohort.getGuid();
            EhCacheUtils.put(GlobalConstant.STR_CACHE_NAME_COHORT, key, hiveCohort);
        }
        logger.info("cohort synchronize end");
    }

    /**
     * spring容器所有bean加载完后执行加载cohort数据到缓存
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        synchronize();
        logger.info("程序启动加载cohort数据到缓存中");
    }
}
