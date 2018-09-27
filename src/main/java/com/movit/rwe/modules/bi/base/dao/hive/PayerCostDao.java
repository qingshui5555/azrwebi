package com.movit.rwe.modules.bi.base.dao.hive;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.movit.rwe.common.persistence.CrudDao;
import com.movit.rwe.common.persistence.annotation.MyBatisImpalaDao;
import com.movit.rwe.common.persistence.annotation.MyBatisKylinDao;
import com.movit.rwe.modules.bi.base.entity.hive.PayerCost;

@MyBatisImpalaDao
public interface PayerCostDao extends CrudDao<PayerCost> {

	/**
	 * 根据患者主键和项目主键查找花费
	 * 
	 * @param studyId
	 * @return
	 */
	List<Map<String, Object>> getPayerCost(@Param("studyId") String studyId, @Param("cohortQuery") String[] cohortQuery, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

	/**
	 * 
	* @Title: 获取详细花费
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param studyId
	* @param @param cohortQuery
	* @param @param startDate
	* @param @param endDate
	* @param @return
	* @return List<Map<String,Object>>
	* @throws
	 */
	List<Map<String, Object>> getDetailPayerCost(@Param("studyId") String studyId, @Param("cohortQuery") String[] cohortQuery, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
