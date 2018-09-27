package com.movit.rwe.modules.bi.base.dao.mysql;

import com.movit.rwe.common.persistence.annotation.MyBatisOpenmrsDao;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@MyBatisOpenmrsDao
public interface OpenmrsUserDao {

	List<String> getForcePassword(@Param("username") String username);

}
