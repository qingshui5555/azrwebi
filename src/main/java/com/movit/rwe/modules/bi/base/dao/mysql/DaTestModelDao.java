package com.movit.rwe.modules.bi.base.dao.mysql;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.movit.rwe.common.persistence.CrudDao;
import com.movit.rwe.common.persistence.annotation.MyBatisDao;
import com.movit.rwe.modules.bi.base.entity.mysql.DaTestModel;

@MyBatisDao
public interface DaTestModelDao extends CrudDao<DaTestModel> {

	List<DaTestModel> findListByTestName(@Param("testName")String testName);

}
