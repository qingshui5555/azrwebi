package com.movit.rwe.modules.bi.base.dao.hive;

import java.util.List;

import com.movit.rwe.modules.bi.base.entity.hive.Treatment;
import org.apache.ibatis.annotations.Param;

import com.movit.rwe.common.persistence.CrudDao;
import com.movit.rwe.common.persistence.annotation.MyBatisImpalaDao;

/**
 * 
 * @Project : az-rwe-bi
 * @Title : TreatmentDao.java
 * @Package com.movit.rwe.modules.bi.treatment.dao
 * @Description : 治疗路径
 * @author Yuri.Zheng
 * @email Yuri.Zheng@movit-tech.com
 * @date 2016年3月7日 下午5:34:15
 *
 */
@MyBatisImpalaDao
public interface TreatmentDao extends CrudDao<Treatment> {

	/**
	 * 
	 * @Title: getTreatment @Description: 根据患者主键和研究主键查询治疗信息 @author
	 * Yuri.Zheng @param patientId @param studyId @return @throws
	 */
	public List<Treatment> getTreatment(@Param("patientId") String patientId, @Param("studyId") String studyId);

	/**
	 * 
	* @Title: getSolutionByStudyId 
	* @Description: 根据studyid查询Solution（去重）
	* @param @param studyId
	* @param @return
	* @return List<String>
	* @throws
	 */
	public List<String> getSolutionByStudyId(@Param("studyId") String studyId,@Param("cohorts")String[] cohorts);

}
