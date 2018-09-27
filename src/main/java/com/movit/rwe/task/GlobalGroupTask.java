package com.movit.rwe.task;

import com.movit.rwe.common.config.GlobalConstant;
import com.movit.rwe.common.utils.EhCacheUtils;
import com.movit.rwe.modules.bi.base.dao.mysql.GlobalGroupDao;
import com.movit.rwe.modules.bi.base.entity.mysql.GlobalGroup;
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
public class GlobalGroupTask implements ApplicationListener<ContextRefreshedEvent> {
    private static Logger logger = LoggerFactory.getLogger(GlobalGroupTask.class);

    @Autowired
    private GlobalGroupDao globalGroupDao;

    @Scheduled(cron = "0 0/5 * * * ?")
    public void synchronize(){
        logger.info("GlobalGroup synchronize begin");
        List<GlobalGroup> globalGroupList = globalGroupDao.queryListAll();
        for(GlobalGroup globalGroup : globalGroupList){
            String cacheKey = globalGroup.getId() + GlobalConstant.STR_CONNECTOR + globalGroup.getName();
            EhCacheUtils.put(GlobalConstant.STR_CACHE_NAME_GLOBAL_GROUP, cacheKey, globalGroup);
        }
        logger.info("GlobalGroup synchronize end");
    }

    /**
     * spring容器所有bean加载完后执行加载GlobalGroup数据到缓存
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        synchronize();
        logger.info("程序启动加载GlobalGroup数据到缓存中");
    }
}
