package com.movit.rwe.modules.bi.base.dao.hive;

import com.movit.rwe.common.persistence.annotation.MyBatisImpalaDao;
import com.movit.rwe.modules.bi.base.entity.hive.BiomarkerTest;
import com.movit.rwe.modules.bi.base.entity.hive.HiveResult;
import com.movit.rwe.modules.bi.base.entity.vo.*;
import com.movit.rwe.modules.bi.dashboard.vo.BiomarkerTestVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@MyBatisImpalaDao
public interface HiveResultDao {

    /**
     * 获取患者属性配置数据
     * @param studyId
     * @param subject
     * @return
     */
    List<HiveResult> queryListDemographicConfig(@Param("studyId") String studyId, @Param("subject") String subject);

    /**
     * 获取抽取配置数据
     * @param studyId
     * @param subject
     * @param withUnit
     * @return
     */
    List<HiveResult> queryListResultConfig(@Param("studyId") String studyId, @Param("subject") String subject, @Param("withUnit") Boolean withUnit, @Param("cohortIds") String[] cohortIds);

    /**
     * 获取biomarker test配置数据
     * @param studyId
     * @param subject
     * @return
     */
    List<Map<String, String>> queryListBiomarkerTestConfig(@Param("studyId") String studyId, @Param("subject") String subject);

    /**
     * 获取属性统计数据
     * @param studyId
     * @param subject
     * @param cohortIds
     * @param indicators
     * @param startDate
     * @param endDate
     * @return
     */
    List<HiveResultVo> queryListDemographicCountData(@Param("studyId") String studyId, @Param("subject") String subject, @Param("cohortIds") String[] cohortIds, @Param("indicators") String[] indicators, @Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 获取固定分组统计信息
     * @param studyId
     * @param subject
     * @param cohortIds
     * @param indicator
     * @param groups
     * @param startDate
     * @param endDate
     * @return
     */
    List<Map<String, Object>> queryListGroupCountData(@Param("studyId") String studyId, @Param("subject") String subject, @Param("cohortIds") String[] cohortIds, @Param("indicator") String indicator, @Param("groups") Map<String, Object> groups, @Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 获取生命体征固定分组统计信息
     * @param studyId
     * @param subject
     * @param cohortIds
     * @param indicator
     * @param groups
     * @return
     */
    List<Map<String, Object>> queryListVitalGroupCountData(@Param("studyId") String studyId, @Param("subject") String subject, @Param("cohortIds") String[] cohortIds, @Param("indicator") String indicator, @Param("groups") Map<String, Object> groups);

    /**
     * 获取属性统计数据
     * @param studyId
     * @param subject
     * @param indicator
     * @param searchText
     * @return
     */
    List<String> queryListDemographicMatchData(@Param("studyId") String studyId, @Param("subject") String subject, @Param("indicator") String indicator, @Param("searchText") String searchText);

    /**
     * 获取抽取原始数据
     * @param studyId
     * @param subject
     * @param cohortIds
     * @param indicators
     * @param indicatorUnits
     * @return
     */
    List<HiveResult> queryListResultData(@Param("studyId") String studyId, @Param("subject") String subject, @Param("cohortIds") String[] cohortIds, @Param("indicators") String[] indicators, @Param("indicatorUnits") String[] indicatorUnits, @Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 获取抽取原始数据
     * @param studyId
     * @param subject
     * @param cohortIds
     * @param indicators
     * @return
     */
    List<HiveResultVo> queryListVitalAttrData(@Param("studyId") String studyId, @Param("subject") String subject, @Param("indicators") String[] indicators, @Param("cohortIds") String[] cohortIds, @Param("startDate") String startDate, @Param("endDate") String endDate);

    List<HiveResultVo> queryListVitalStageData(@Param("studyId") String studyId, @Param("subject") String subject, @Param("indicators") String[] indicators, @Param("cohortIds") String[] cohortIds, @Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 获取LabTest原始数据
     * @param studyId
     * @param subject
     * @param cohortIds
     * @param indicator
     * @param indicatorUnit
     * @return
     */
    List<HiveResultVo> queryListLabTestData(@Param("studyId") String studyId, @Param("subject") String subject, @Param("cohortIds") String[] cohortIds, @Param("indicator") String indicator, @Param("indicatorUnit") String indicatorUnit, @Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 获取LabTest原始数据
     * @param studyId
     * @param subject
     * @param cohortIds
     * @param indicators
     * @param indicatorUnits
     * @return
     */
    List<HiveLabTestCountVo> queryListLabTestTableData(@Param("studyId") String studyId, @Param("subject") String subject, @Param("cohortIds") String[] cohortIds, @Param("indicators") String[] indicators, @Param("indicatorUnits") String[] indicatorUnits);

    /**
     * 获取Comorbidity统计数据
     * @param studyId
     * @param subject
     * @param cohortIds
     * @param indicator
     * @param startDate
     * @param endDate
     * @return
     */
    List<HiveResultVo> queryListComorbidityCountData(@Param("studyId") String studyId, @Param("subject") String subject, @Param("cohortIds") String[] cohortIds, @Param("indicator") String indicator, @Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 获取Treatment统计数据
     * @param studyId
     * @param subject
     * @param cohortIds
     * @return
     */
    List<HiveTreatmentCountVo> queryListAllTreatmentCountData(@Param("studyId") String studyId, @Param("subject") String subject, @Param("cohortIds") String[] cohortIds, @Param("types") String[] types);

    /**
     * 获取Treatment First Level统计数据
     * @param studyId
     * @param subject
     * @param cohortIds
     * @param firstLine
     * @param secondLine
     * @return
     */
    List<HiveTreatmentCountVo> queryListFirstLevelTreatmentJourney(@Param("studyId") String studyId, @Param("subject") String subject, @Param("cohortIds") String[] cohortIds, @Param("types") String[] types, @Param("firstLine") String firstLine, @Param("secondLine") String secondLine);

    /**
     * 获取Treatment 子项统计数据
     * @param studyId
     * @param subject
     * @param cohortIds
     * @param firstLine
     * @param secondLine
     * @param patientIdStr
     * @return
     */
    List<HiveTreatmentCountVo> queryListTreatmentChildCountData(@Param("studyId") String studyId, @Param("subject") String subject, @Param("cohortIds") String[] cohortIds, @Param("types") String[] types, @Param("firstLine") String firstLine, @Param("secondLine") String secondLine, @Param("patientIdStr") String patientIdStr);

    /**
     * 根据患者查询所有治疗
     * @param studyId
     * @param subject
     * @param demographicName
     * @param demographicValue
     * @return
     */
    List<HiveTreatmentVo> queryListTreatmentByPatient(@Param("studyId") String studyId, @Param("subject") String subject, @Param("demographicName") String demographicName, @Param("demographicValue") String demographicValue);

    /**
     * 开销分类统计
     * @param studyId
     * @param subject
     * @param cohortIds
     * @param startDate
     * @param endDate
     * @return
     */
    List<Map<String, Object>> queryListDetailPayerCost(@Param("studyId") String studyId, @Param("subject") String subject, @Param("cohortIds") String[] cohortIds, @Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 获取所有支付详细信息
     * @param studyId
     * @param subject
     * @param cohortIds
     * @param startDate
     * @param endDate
     * @return
     */
    List<Map<String, Object>> queryListTotalPayerCost(@Param("studyId") String studyId, @Param("subject") String subject, @Param("cohortIds") String[] cohortIds, @Param("startDate") String startDate, @Param("endDate") String endDate);

    List<Map<String, Object>> queryListWorldMap(@Param("studyId") String studyId, @Param("subject") String subject, @Param("countType") String countType, @Param("indicator") String indicator, @Param("cohortIds") String[] cohortIds, @Param("startDate") String startDate, @Param("endDate") String endDate);

    List<Map<String,Object>> queryListSurvivalCurve(@Param("studyId") String studyId, @Param("subject") String subject, @Param("cohortId") String cohortId, @Param("timeType") String timeType);

    List<Map<String,Object>> queryListFirstPDCurve(@Param("studyId") String studyId, @Param("subject") String subject, @Param("cohortId") String cohortId, @Param("timeType") String timeType);

    List<BiomarkerTest> queryListBiomarkerTest(@Param("studyId") String studyId, @Param("subject") String subject, @Param("demographicName") String demographicName, @Param("demographicValue") String demographicValue);

    List<Integer> queryBiomarkerTimes(@Param("studyId") String studyId, @Param("subject") String subject, @Param("measureType") String measureType, @Param("cohortIds") String[] cohortIds);

    List<String> queryListEvaluation(@Param("studyId") String studyId, @Param("subject") String subject, @Param("measureType") String measureType);

    List<BiomarkerTestVo> queryListBiomarkerTestData(@Param("studyId") String studyId, @Param("subject") String subject, @Param("cohortIds") String[] cohortIds, @Param("biomarkers") String[] biomarkers, @Param("measureType") String measureType, @Param("times") Integer times);

    List<String> queryListBiomarkerTestSubData(@Param("studyId") String studyId, @Param("subject") String subject, @Param("measureType") String measureType, @Param("biomarker") String biomarker);

    Integer countPatient(@Param("studyId") String studyId, @Param("subject") String subject, @Param("cohortIds") String[] cohortIds);
}
