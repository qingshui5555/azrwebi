package com.movit.rwe.modules.bi.dashboard.service;

import com.movit.rwe.common.config.GlobalConstant;
import com.movit.rwe.common.utils.StringUtils;
import com.movit.rwe.modules.bi.base.dao.hive.HiveResultDao;
import com.movit.rwe.modules.bi.base.entity.hive.BiomarkerTest;
import com.movit.rwe.modules.bi.base.entity.hive.HiveResult;
import com.movit.rwe.modules.bi.base.entity.vo.*;
import com.movit.rwe.modules.bi.dashboard.vo.BiomarkerTestVo;
import com.movit.rwe.modules.bi.dashboard.vo.ConfigVo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class HiveResultService {
    private static Logger logger = LoggerFactory.getLogger(HiveResultService.class);

    @Autowired
    HiveResultDao hiveResultDao;

    /**
     * 获取属性配置列表
     * @param studyId
     * @param subject
     * @param begin
     * @return
     */
    public List<HiveResultVo> queryListDemographicConfig(String studyId, String subject, Integer begin, List<ConfigVo> configVoList){
        logger.info("queryListDemographicConfig begin, studyId:" + studyId + ", subject:" + subject + ", begin:" + begin);

        List<HiveResultVo> resultVoList = new ArrayList<HiveResultVo>();
        List<HiveResult> demographicConfigList = hiveResultDao.queryListDemographicConfig(studyId, subject);
        for(HiveResult result : demographicConfigList){
            if(convertResultVo(resultVoList, result, begin, configVoList)) {
                begin++;
            }
        }

        logger.info("queryListDemographicConfig end");
        return resultVoList;
    }

    /**
     * 获取抽取数据配置列表
     * @param studyId
     * @param begin
     * @return
     */
    public List<HiveResultVo> queryListResultConfig(String studyId, String subject, Boolean withUnit, String[] cohortIds, Integer begin, List<ConfigVo> configVoList){
        logger.info("queryListResultConfig begin");

        List<HiveResultVo> resultVoList = new ArrayList<HiveResultVo>();
        List<HiveResult> resultConfigList = hiveResultDao.queryListResultConfig(studyId, subject, withUnit, cohortIds);
        Map<String, Set<String>> unitMap = new LinkedHashMap<String, Set<String>>();
        for(HiveResult result : resultConfigList){
            if(result.getKeyName().contains("Unit")){
                Set<String> unitSet = unitMap.get(result.getKeyName());
                if(unitSet == null && !StringUtils.isBlank(result.getKeyValue())){
                    unitSet = new HashSet<String>();
                    unitSet.add(result.getKeyValue());
                    unitMap.put(result.getKeyName(), unitSet);
                } else if(!StringUtils.isBlank(result.getKeyValue())){
                    unitSet.add(result.getKeyValue());
                }
            } else {
                if(convertResultVo(resultVoList, result, begin, configVoList)) {
                    begin++;
                }
            }
        }
        if(withUnit) {
            for (HiveResultVo resultVo : resultVoList) {
                Set<String> unitSet = unitMap.get(resultVo.getKeyName() + " Unit");
                if (unitSet != null) {
                    resultVo.setUnitSet(unitSet);
                }
            }
        }

        logger.info("queryListResultConfig end");
        return resultVoList;
    }

    /**
     * 获取biomarker test数据配置
     * @param studyId
     * @param subject
     * @return
     */
    public List<Map<String, String>> queryListBiomarkerTestConfig(String studyId, String subject) {
        logger.info("queryListBiomarkerTestConfig begin");
        List<Map<String, String>> dataList = hiveResultDao.queryListBiomarkerTestConfig(studyId, subject);
        logger.info("queryListBiomarkerTestConfig end");
        return dataList;
    }

    /**
     * 获取属性统计数据
     * @param studyId
     * @param subject
     * @param indicators
     * @param cohortIds
     */
    public List<HiveResultVo> queryListDemographicCountData(String studyId, String subject, String[] indicators, String[] cohortIds, String startDate, String endDate) {
        logger.info("queryListDemographicCountData begin");
        List<HiveResultVo> demographicDataList = hiveResultDao.queryListDemographicCountData(studyId, subject, cohortIds, indicators, startDate, endDate);
        logger.info("queryListDemographicCountData end");
        return demographicDataList;
    }

    public List<Map<String, Object>> queryListGroupCountData(String studyId, String subject, String indicator, Map<String, Object> groups, String[] cohortIds, String startDate, String endDate) {
        logger.info("queryListAgeGroupCountData begin");
        List<Map<String, Object>> ageGroupDataList = hiveResultDao.queryListGroupCountData(studyId, subject, cohortIds, indicator, groups, startDate, endDate);
        logger.info("queryListAgeGroupCountData end");
        return ageGroupDataList;
    }

    /**
     * 按固定分组统计
     * @param studyId
     * @param subject
     * @param indicator
     * @param groups
     * @param cohortIds
     * @return
     */
    public List<Map<String, Object>> queryListVitalGroupCountData(String studyId, String subject, String indicator, Map<String, Object> groups, String[] cohortIds) {
        logger.info("queryListAgeGroupCountData begin");
        List<Map<String, Object>> ageGroupDataList = hiveResultDao.queryListVitalGroupCountData(studyId, subject, cohortIds, indicator, groups);
        logger.info("queryListAgeGroupCountData end");
        return ageGroupDataList;
    }

    /**
     * 模糊匹配属性数据
     * @param studyId
     * @param subject
     * @param indicator
     * @param searchText
     */
    public List<String> queryListDemographicMatchData(String studyId, String subject, String indicator, String searchText) {
        logger.info("queryListDemographicMatchData begin");
        List<String> demographicDataList = hiveResultDao.queryListDemographicMatchData(studyId, subject, indicator, searchText);
        logger.info("queryListDemographicMatchData end");
        return demographicDataList;
    }

    /**
     * 获取抽取原始数据
     * @param studyId
     * @param subject
     * @param indicators
     * @param indicatorUnits
     * @param cohortIds
     */
    public List<HiveResult> queryListResultData(String studyId, String subject, String[] indicators, String[] indicatorUnits, String[] cohortIds, String startDate, String endDate) {
        logger.info("queryListResultData begin");
        List<HiveResult> resultDataList = hiveResultDao.queryListResultData(studyId, subject, cohortIds, indicators, indicatorUnits, startDate, endDate);
        logger.info("queryListResultData end");
        return resultDataList;
    }

    /**
     * 获取抽取原始数据
     * @param studyId
     * @param subject
     * @param indicators
     * @param cohortIds
     */
    public List<HiveResultVo> queryListVitalAttrData(String studyId, String subject, String[] indicators, String[] cohortIds, String startDate, String endDate) {
        logger.info("queryListVitalAttrData begin");
        List<HiveResultVo> resultDataList = hiveResultDao.queryListVitalAttrData(studyId, subject, indicators, cohortIds, startDate, endDate);
        logger.info("queryListVitalAttrData end");
        return resultDataList;
    }

    public List<HiveResultVo> queryListVitalStageData(String studyId, String subject, String[] indicators, String[] cohortIds, String startDate, String endDate) {
        logger.info("queryListVitalStageData begin");
        List<HiveResultVo> resultDataList = hiveResultDao.queryListVitalStageData(studyId, subject, indicators, cohortIds, startDate, endDate);
        logger.info("queryListVitalStageData end");
        return resultDataList;
    }

    /**
     * 获取LabTest原始数据
     * @param studyId
     * @param subject
     * @param indicator
     * @param indicatorUnit
     * @param cohortIds
     */
    public List<HiveResultVo> queryListLabTestData(String studyId, String subject, String indicator, String indicatorUnit, String[] cohortIds, String startDate, String endDate) {
        logger.info("queryListResultData begin");
        List<HiveResultVo> resultDataList = hiveResultDao.queryListLabTestData(studyId, subject, cohortIds, indicator, indicatorUnit, startDate, endDate);
        logger.info("queryListResultData end");
        return resultDataList;
    }

    /**
     * 获取LabTest Table 原始数据
     * @param studyId
     * @param subject
     * @param cohortIds
     * @param indicators
     * @param indicatorUnits
     */
    public List<HiveLabTestCountVo> queryListLabTestTableData(String studyId, String subject, String[] cohortIds, String[] indicators, String[] indicatorUnits) {
        logger.info("queryListLabTestTableData begin");
        List<HiveLabTestCountVo> resultDataList = hiveResultDao.queryListLabTestTableData(studyId, subject, cohortIds, indicators, indicatorUnits);
        logger.info("queryListLabTestTableData end");
        return resultDataList;
    }

    /**
     * 获取Comorbidity统计数据
     * @param studyId
     * @param subject
     * @param indicator
     * @param cohortIds
     */
    public List<HiveResultVo> queryListComorbidityCountData(String studyId, String subject, String indicator, String[] cohortIds, String startDate, String endDate) {
        logger.info("queryListResultData begin");
        List<HiveResultVo> resultDataList = hiveResultDao.queryListComorbidityCountData(studyId, subject, cohortIds, indicator, startDate, endDate);
        logger.info("queryListResultData end");
        return resultDataList;
    }

    /**
     * 获取Treatment所有统计数据
     * @param studyId
     * @param subject
     * @param cohortIds
     * @param types
     * @return
     */
    public List<HiveTreatmentCountVo> queryListAllTreatmentCountData(String studyId, String subject, String[] cohortIds, String[] types) {
        logger.info("queryListTreatmentData begin");
        List<HiveTreatmentCountVo> treatmentDataList = hiveResultDao.queryListAllTreatmentCountData(studyId, subject, cohortIds, types);
        logger.info("queryListTreatmentData end");
        return treatmentDataList;
    }

    /**
     * 获取Treatment First Level统计数据
     * @param studyId
     * @param subject
     * @param cohortIds
     * @param firstLine
     * @param secondLine
     * @param types
     * @return
     */
    public List<HiveTreatmentCountVo> queryListFirstLevelTreatmentJourney(String studyId, String subject, String[] cohortIds, String[] types, String firstLine, String secondLine) {
        logger.info("queryListTreatmentData begin");
        List<HiveTreatmentCountVo> treatmentDataList = hiveResultDao.queryListFirstLevelTreatmentJourney(studyId, subject, cohortIds, types, firstLine, secondLine);
        logger.info("queryListTreatmentData end");
        return treatmentDataList;
    }

    /**
     * 获取treatment 子项统计数据
     * @param studyId
     * @param subject
     * @param cohortIds
     * @param curLevel
     * @param nextLevel
     * @param patientIdStr
     * @param types
     * @return
     */
    public List<HiveTreatmentCountVo> queryListTreatmentChildCountData(String studyId, String subject, String[] cohortIds, String[] types, String curLevel, String nextLevel, String patientIdStr) {
        logger.info("queryListTreatmentData begin");
        List<HiveTreatmentCountVo> treatmentDataList = hiveResultDao.queryListTreatmentChildCountData(studyId, subject, cohortIds, types, curLevel, nextLevel, patientIdStr);
        logger.info("queryListTreatmentData end");
        return treatmentDataList;
    }

    /**
     * 获取患者所有治疗
     * @param studyId
     * @param subject
     * @param demographicName
     * @param demographicValue
     * @return
     */
    public List<HiveTreatmentVo> queryListTreatmentByPatientCode(String studyId, String subject, String demographicName, String demographicValue) {
        logger.info("queryListTreatmentByPatientCode begin");
        List<HiveTreatmentVo> treatmentVoList = hiveResultDao.queryListTreatmentByPatient(studyId, subject, demographicName, demographicValue);
        logger.info("queryListTreatmentByPatientCode end");
        return treatmentVoList;
    }

    /**
     * 获取所有花销统计
     * @param studyId
     * @param subject
     * @param cohortIds
     * @param startDate
     * @param endDate
     * @return
     */
    public List<Map<String, Object>> queryListDetailPayerCost(String studyId, String subject, String[] cohortIds, String startDate, String endDate) {
        logger.info("queryListDetailPayerCost begin");
        List<Map<String, Object>> dataList = hiveResultDao.queryListDetailPayerCost(studyId, subject, cohortIds, startDate, endDate);
        logger.info("queryListDetailPayerCost end");
        return dataList;
    }

    /**
     * 获取所有支付详细信息
     * @param studyId
     * @param subject
     * @param cohortIds
     * @param startDate
     * @param endDate
     * @return
     */
    public List<Map<String, Object>> queryListTotalPayerCost(String studyId, String subject, String[] cohortIds, String startDate, String endDate) {
        logger.info("queryListTotalPayerCost begin");
        List<Map<String, Object>> detailDataList = hiveResultDao.queryListTotalPayerCost(studyId, subject, cohortIds, startDate, endDate);
        logger.info("queryListTotalPayerCost end");
        return detailDataList;
    }

    public List<Map<String, Object>> queryListWorldMap(String studyId, String subject, String countType, String indicator, String[] cohortIds, String startDate, String endDate) {
        logger.info("queryListWorldMap begin");
        List<Map<String, Object>> dataList = hiveResultDao.queryListWorldMap(studyId, subject, countType, indicator, cohortIds, startDate, endDate);
        logger.info("queryListWorldMap end");
        return dataList;
    }

    /**
     *
     * @param studyId
     * @param subject
     * @param cohortId
     * @param timeType
     * @return
     */
    public List<Map<String,Object>> queryListSurvivalCurve(String studyId, String subject, String cohortId, String timeType) {
        logger.info("queryListSurvivalCurve begin");
        List<Map<String, Object>> dataList = hiveResultDao.queryListSurvivalCurve(studyId, subject, cohortId, timeType);
        logger.info("queryListSurvivalCurve end");
        return dataList;
    }

    /**
     *
     * @param studyId
     * @param subject
     * @param cohortId
     * @param timeType
     * @return
     */
    public List<Map<String,Object>> queryListFirstPDCurve(String studyId, String subject, String cohortId, String timeType) {
        logger.info("queryListFirstPDCurve begin");
        List<Map<String, Object>> dataList = hiveResultDao.queryListFirstPDCurve(studyId, subject, cohortId, timeType);
        logger.info("queryListFirstPDCurve end");
        return dataList;
    }


    /**
     * 获取患者所有治疗
     * @param studyId
     * @param subject
     * @param demographicName
     * @param demographicValue
     * @return
     */
    public List<BiomarkerTest> queryListBiomarkerTestByPatientCode(String studyId, String subject, String demographicName, String demographicValue) {
        logger.info("queryListBiomarkerTestByPatientCode begin");
        List<BiomarkerTest> biomarkerTestList = hiveResultDao.queryListBiomarkerTest(studyId, subject, demographicName, demographicValue);
        logger.info("queryListBiomarkerTestByPatientCode end");
        return biomarkerTestList;
    }

    public List<Integer> queryBiomarkerTimes(String studyId, String subject, String measureType, String[] cohortIds) {
        logger.info("queryBiomarkerTimes begin");
        List<Integer> resultList = hiveResultDao.queryBiomarkerTimes(studyId, subject, measureType, cohortIds);
        logger.info("queryBiomarkerTimes end");
        return resultList;
    }

    public List<String> queryListEvaluation(String studyId, String subject, String measureType) {
        logger.info("queryListEvaluation begin");
        List<String> resultList = hiveResultDao.queryListEvaluation(studyId, subject, measureType);
        logger.info("queryListEvaluation end");
        return resultList;
    }

    public List<BiomarkerTestVo> queryListBiomarkerTestData(String studyId, String subject, String[] cohortIds, String[] biomarkers, String measureType, Integer times) {
        logger.info("queryListBiomarkerTestData begin");
        List<BiomarkerTestVo> resultList = hiveResultDao.queryListBiomarkerTestData(studyId, subject, cohortIds, biomarkers, measureType, times);
        logger.info("queryListBiomarkerTestData end");
        return resultList;
    }

    public List<String> queryListBiomarkerTestSubData(String studyId, String subject, String measureType, String biomarker) {
        logger.info("queryListBiomarkerTestSubData begin");
        List<String> resultList = hiveResultDao.queryListBiomarkerTestSubData(studyId, subject, measureType, biomarker);
        logger.info("queryListBiomarkerTestSubData end");
        return resultList;
    }

    public Integer countPatient(String studyId, String subject, String[] cohortIds) {
        logger.info("countPatient begin");
        Integer result = hiveResultDao.countPatient(studyId, subject, cohortIds);
        logger.info("countPatient end");
        return result;
    }

    public List<ConfigVo> queryListConfigVo(JSONArray configJson, String configListAttr){
        List<ConfigVo> list = new ArrayList<ConfigVo>();
        for(int i=0; i<configJson.size(); i++) {
            JSONArray json = JSONArray.fromObject(configJson.getJSONObject(i).get(configListAttr));
            for(int j=0; j<json.size(); j++) {
                JSONObject labJSON = json.getJSONObject(j);
                String id = labJSON.get(GlobalConstant.STR_ATTR_ID).toString();
                String order = labJSON.get(GlobalConstant.STR_ATTR_ORDER).toString();
                String indicator = labJSON.get(GlobalConstant.STR_ATTR_INDICATOR).toString();
                String resultUnit = labJSON.get(GlobalConstant.STR_ATTR_RESULT_UNIT).toString();
                String lowerRef = labJSON.get(GlobalConstant.STR_ATTR_LOWER_REF).toString();
                if(StringUtils.isBlank(lowerRef)){
                    lowerRef = GlobalConstant.STR_ATTR_LOWER_REF_DEFAULT;
                }
                String upperRef = labJSON.get(GlobalConstant.STR_ATTR_UPPER_REF).toString();
                if(StringUtils.isBlank(upperRef)){
                    upperRef = GlobalConstant.STR_ATTR_UPPER_REF_DEFAULT;
                }

                ConfigVo vo = new ConfigVo(id, resultUnit, Double.parseDouble(lowerRef), Double.parseDouble(upperRef));
                vo.setOrder(Integer.parseInt(order));
                vo.setIndicator(indicator);
                list.add(vo);
            }
        }

        Collections.sort(list, new Comparator<ConfigVo>() {
            @Override
            public int compare(ConfigVo o1, ConfigVo o2) {
                return o1.getOrder().compareTo(o2.getOrder());
            }
        });

        return list;
    }

    private Boolean convertResultVo(List<HiveResultVo> resultList, HiveResult result, Integer begin, List<ConfigVo> configVoList){
        Boolean resultFlag = true;
        HiveResultVo resultVo = new HiveResultVo();
        BeanUtils.copyProperties(result, resultVo);
        Integer order = begin;
        if(configVoList != null){
            for(ConfigVo configVo : configVoList){
                if(resultVo.getKeyName().equals(configVo.getIndicator())){
                    order = configVo.getOrder();
                    resultFlag = false;
                    break;
                }
            }
        }
        resultVo.setConfigId(order + "");
        resultVo.setOrder(order);
        resultVo.setLowerRef("0.0");
        resultVo.setUpperRef("0.0");
        resultList.add(resultVo);

        return resultFlag;
    }
}
