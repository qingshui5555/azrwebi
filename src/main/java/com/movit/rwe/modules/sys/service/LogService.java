/**
 * Copyright &copy; 2012-2013 <a href="httparamMap://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.movit.rwe.modules.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movit.rwe.common.persistence.Page;
import com.movit.rwe.common.service.CrudService;
import com.movit.rwe.common.utils.DateUtils;
import com.movit.rwe.modules.bi.sys.vo.LogRequestVo;
import com.movit.rwe.modules.bi.sys.vo.LogVo;
import com.movit.rwe.modules.sys.dao.LogDao;
import com.movit.rwe.modules.sys.entity.Log;

/**
 * 日志Service
 * @author ThinkGem
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class LogService extends CrudService<LogDao, Log> {
	
	@Autowired
	LogDao logDao;

	public Page<Log> findPage(Page<Log> page, Log log) {
		
		// 设置默认时间范围，默认当前月
		if (log.getBeginDate() == null){
			log.setBeginDate(DateUtils.setDays(DateUtils.parseDate(DateUtils.getDate()), 1));
		}
		if (log.getEndDate() == null){
			log.setEndDate(DateUtils.addMonths(log.getBeginDate(), 1));
		}
		
		return super.findPage(page, log);
		
	}

	public List<LogVo> getLogList(LogRequestVo logRequestVo) {
		return logDao.getLogList(logRequestVo);
	}

	public int getLogListCount(LogRequestVo logRequestVo) {
		return logDao.getLogListCount(logRequestVo);
	}

	public String getLogException(String id) {
		return logDao.getLogException(id);
	}
	
}
