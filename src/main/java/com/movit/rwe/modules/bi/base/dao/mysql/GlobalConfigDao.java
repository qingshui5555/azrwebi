package com.movit.rwe.modules.bi.base.dao.mysql;

import com.movit.rwe.common.persistence.CrudDao;
import com.movit.rwe.common.persistence.annotation.MyBatisDao;
import com.movit.rwe.modules.bi.base.entity.mysql.GlobalConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author kwqb535
 * @date 2018.08.14
 */
@MyBatisDao
public interface GlobalConfigDao extends CrudDao<GlobalConfig> {
	List<GlobalConfig> queryListAll(@Param("studyIds") String[] studyIds);
}
