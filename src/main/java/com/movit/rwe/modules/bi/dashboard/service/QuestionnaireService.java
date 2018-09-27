package com.movit.rwe.modules.bi.dashboard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movit.rwe.modules.bi.base.dao.hive.QuestionnaireDao;
import com.movit.rwe.modules.bi.base.entity.hive.Questionnaire;

@Service
@Transactional
public class QuestionnaireService {

	@Autowired
	private QuestionnaireDao questionnaireDao;

	public List<Questionnaire> findAllQuestionnaire(String studyId) {
		// TODO Auto-generated method stub
		return questionnaireDao.findAllQuestionnaire(studyId);
	}

	public List<Double> getQuestionnaireByGroupIdAndType(String studyId, String groupId,String type) {
		// TODO Auto-generated method stub
		return questionnaireDao.getQuestionnaireByGroupIdAndType(studyId,groupId,type);
	}

	public List<String> getAllQuestionnaireType(String studyId) {
		// TODO Auto-generated method stub
		return questionnaireDao.getAllQuestionnaireType(studyId);
	}
}