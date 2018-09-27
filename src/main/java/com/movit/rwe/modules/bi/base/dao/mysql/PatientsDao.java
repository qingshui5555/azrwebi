package com.movit.rwe.modules.bi.base.dao.mysql;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.movit.rwe.common.persistence.CrudDao;
import com.movit.rwe.common.persistence.annotation.MyBatisDao;
import com.movit.rwe.modules.bi.base.entity.mysql.Patients;

@MyBatisDao
public interface PatientsDao extends CrudDao<Patients> {

	List<String> fuzzyQueryPatientCode(@Param("code") String code);

	String getPatientId(@Param("code") String patientCode);
	
	List<String> fuzzyQueryPatientCodeByStudy(@Param("code") String code,@Param("studyId") String studyId);
}
