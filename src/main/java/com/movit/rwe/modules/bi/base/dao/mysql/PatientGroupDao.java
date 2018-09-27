package com.movit.rwe.modules.bi.base.dao.mysql;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.movit.rwe.common.persistence.annotation.MyBatisDao;
import com.movit.rwe.modules.bi.base.entity.mysql.PatientGroup;

@MyBatisDao
public interface PatientGroupDao {
	
	
	List<PatientGroup> findAll(@Param("studyId")String studyId);
	
	Integer countPatientByGroups(@Param("cohorts")String[] cohorts,@Param("groups")String[] groups);
	
	String getGroupName(@Param("id")String id);
	
	/**
	 * 
	  * loadByTypeAndId
	  * @Title: loadByTypeAndId
	  * @Description: 根据ID和类型获取分组对象
	  * @param @param id
	  * @param @param type 0：group 1：cohort
	  * @param @return    
	  * @return PatientGroup    返回类型
	  * @throws
	 */
	PatientGroup loadByTypeAndId(@Param("id")String id,@Param("type")int type);
	
}
