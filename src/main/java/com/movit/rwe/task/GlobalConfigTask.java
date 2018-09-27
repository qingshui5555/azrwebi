package com.movit.rwe.task;

import com.movit.rwe.common.config.GlobalConstant;
import com.movit.rwe.common.utils.EhCacheUtils;
import com.movit.rwe.modules.bi.base.dao.mysql.GlobalConfigDao;
import com.movit.rwe.modules.bi.base.entity.mysql.GlobalConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author kwqb535
 * @date 2018.08.14
 */
@Service
@Lazy(false)
@Transactional(rollbackFor = Exception.class)
public class GlobalConfigTask implements ApplicationListener<ContextRefreshedEvent> {
    private static Logger logger = LoggerFactory.getLogger(GlobalConfigTask.class);

    @Autowired
    private GlobalConfigDao globalConfigDao;

    @Scheduled(cron = "0 0/5 * * * ?")
    public void synchronize(){
        logger.info("GlobalConfig synchronize begin");
        List<GlobalConfig> globalConfigList = globalConfigDao.queryListAll(null);
        for(GlobalConfig globalConfig : globalConfigList){
            String cacheKey = globalConfig.getStudyId() + GlobalConstant.STR_CONNECTOR + globalConfig.getSubject() + GlobalConstant.STR_CONNECTOR + globalConfig.getId();
            EhCacheUtils.put(GlobalConstant.STR_CACHE_NAME_GLOBAL_CONFIG, cacheKey, globalConfig);
        }
        logger.info("GlobalConfig synchronize end");
    }

    /**
     * spring容器所有bean加载完后执行加载GlobalConfig数据到缓存
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        synchronize();
        logger.info("程序启动加载GlobalConfig数据到缓存中");
    }
}
