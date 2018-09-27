package com.movit.rwe.modules.bi.base.dao.hive;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

import com.movit.rwe.common.persistence.CrudDao;
import com.movit.rwe.common.persistence.annotation.MyBatisImpalaDao;
import com.movit.rwe.modules.bi.base.entity.hive.MedDosingSchedule;

/**
 * 
 * @Project : az-rwe-bi
 * @Title : MedDosingScheduleDao.java
 * @Package com.movit.rwe.modules.bi.treatment.dao
 * @Description : 用药表相关操作
 * @author Yuri.Zheng
 * @email Yuri.Zheng@movit-tech.com
 * @date 2016年3月8日 下午1:56:36
 *
 */
@MyBatisImpalaDao
public interface MedDosingScheduleDao extends CrudDao<MedDosingSchedule> {

	List<Map<String,String>> getTreatmentDrug(@Param("studyId") String studyId,@Param("cohorts")String[] cohorts);
}
