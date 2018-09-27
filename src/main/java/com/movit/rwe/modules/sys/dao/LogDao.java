/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.movit.rwe.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.movit.rwe.common.persistence.CrudDao;
import com.movit.rwe.common.persistence.annotation.MyBatisDao;
import com.movit.rwe.modules.bi.sys.vo.LogRequestVo;
import com.movit.rwe.modules.bi.sys.vo.LogVo;
import com.movit.rwe.modules.sys.entity.Log;

/**
 * 日志DAO接口
 * 
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface LogDao extends CrudDao<Log> {

	List<LogVo> getLogList(LogRequestVo logRequestVo);

	int getLogListCount(LogRequestVo logRequestVo);

	String getLogException(String id);

}
