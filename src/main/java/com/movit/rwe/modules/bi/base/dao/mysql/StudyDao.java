package com.movit.rwe.modules.bi.base.dao.mysql;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.movit.rwe.common.persistence.CrudDao;
import com.movit.rwe.common.persistence.annotation.MyBatisDao;
import com.movit.rwe.modules.bi.base.entity.mysql.Study;
import com.movit.rwe.modules.bi.sys.vo.StudyVo;

@MyBatisDao
public interface StudyDao extends CrudDao<Study> {

	List<Study> findListByTaId(@Param("taId") String taId);

	List<StudyVo> findVoListByTaId(@Param("taId") String taId);

	/*
	 * @Title: findList
	 * 
	 * @Description: 查询所有study（未删除）
	 * 
	 * @param @return
	 * 
	 * @return List<Study>
	 * 
	 * @throws
	 */
	List<StudyVo> findVoList();
	
	List<Study> findAllStudy();

	void updateTaName(@Param("studyId") String studyId, @Param("taId") String taId, @Param("userId") String userId, @Param("updateDate") Date updateDate);

	void updateStudyTime(@Param("taId") String taId);
} 
