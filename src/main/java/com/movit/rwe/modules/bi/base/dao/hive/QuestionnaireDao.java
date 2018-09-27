package com.movit.rwe.modules.bi.base.dao.hive;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.movit.rwe.common.persistence.CrudDao;
import com.movit.rwe.common.persistence.annotation.MyBatisImpalaDao;
import com.movit.rwe.modules.bi.base.entity.hive.Questionnaire;

@MyBatisImpalaDao
public interface QuestionnaireDao extends CrudDao<Questionnaire> {

	List<Questionnaire> findAllQuestionnaire(@Param("studyId") String studyId);

	List<Double> getQuestionnaireByGroupIdAndType(@Param("studyId")String studyId,@Param("groupId")String groupId, @Param("type")String type);

	List<String> getAllQuestionnaireType(@Param("studyId")String studyId);

}
