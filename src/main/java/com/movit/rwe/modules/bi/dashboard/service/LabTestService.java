package com.movit.rwe.modules.bi.dashboard.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import com.movit.rwe.common.config.GlobalConstant;
import com.movit.rwe.common.config.GlobalGroupConfig;
import com.movit.rwe.common.utils.StringUtils;
import com.movit.rwe.modules.bi.base.entity.enums.UnitEnum;
import com.movit.rwe.modules.bi.base.entity.mysql.TabView;
import com.movit.rwe.modules.bi.base.entity.vo.HiveLabTestCountVo;
import com.movit.rwe.modules.bi.dashboard.vo.ConfigVo;
import com.movit.rwe.modules.bi.base.entity.vo.HiveResultVo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LabTestService {

	private static Logger logger = LoggerFactory.getLogger(LabTestService.class);

	@Autowired
	private TabViewService tabViewService;

    @Autowired
    HiveResultService hiveResultService;

	/**
	 * 获取tabView
	 * @param tabViewId
	 * @return
	 */
	public TabView queryTabViewByTabViewId(String tabViewId) {
		return tabViewService.findTabViewByTabViewId(tabViewId);
	}

	/**
	 * 获取抽取数据配置列表
     * @param studyId
     * @param begin
     * @param configVoList
	 * @return
	 */
	public List<HiveResultVo> queryListLabTestConfig(String studyId, Integer begin, List<ConfigVo> configVoList) {
		logger.info("queryListLabTestConfig begin");
        List<HiveResultVo> resultVoList = hiveResultService.queryListResultConfig(studyId, GlobalConstant.STR_ETL_TYPE_LAB_TEST, true, null, begin, configVoList);

        Collections.sort(resultVoList, new Comparator<HiveResultVo>() {
            @Override
            public int compare(HiveResultVo o1, HiveResultVo o2) {
                return o1.getOrder().compareTo(o2.getOrder());
            }
        });

        logger.info("queryListLabTestConfig end");
		return resultVoList;
	}

	/**
	 * 获取数据结果
	 * @param studyId
	 * @param indicator
	 * @param cohortIds
	 * @return
	 */
	public List<HiveResultVo> queryListLabTestData(String studyId, String indicator, String resultUnit, String[] cohortIds) {
		logger.info("queryListLabTestResult begin");

		List<HiveResultVo> resultList = new ArrayList<HiveResultVo>();
        List<HiveResultVo> labTestDataList = hiveResultService.queryListLabTestData(studyId, GlobalConstant.STR_ETL_TYPE_LAB_TEST, indicator, indicator + " Unit", cohortIds, "2017-06-01", null);
        for(HiveResultVo resultVo : labTestDataList){
			if(!StringUtils.isBlank(resultUnit) && !StringUtils.isBlank(resultVo.getKeyUnit()) && !resultUnit.equals(resultVo.getKeyUnit())){
				UnitEnum unitEnum = UnitEnum.load(indicator, resultVo.getKeyUnit());
				if(unitEnum != null){
					BigDecimal b = new BigDecimal(Double.parseDouble(resultVo.getKeyValue()) * unitEnum.getFactor());
                    resultVo.setKeyValue(b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + "");
					resultVo.setKeyUnit(resultUnit);
				}
			}
			resultList.add(resultVo);
		}

		logger.info("queryListLabTestResult end");
		return resultList;
	}

    public Integer countPatient(String studyId, String[]cohortIds, boolean allPatientsFlag){
        logger.info("countPatient begin");
        Integer result = 0;
        if(allPatientsFlag){
            result += hiveResultService.countPatient(studyId, GlobalConstant.STR_ETL_TYPE_DEMOGRAPHIC,null);
        } else {
            result += hiveResultService.countPatient(studyId, GlobalConstant.STR_ETL_TYPE_DEMOGRAPHIC, cohortIds);
        }
        logger.info("countPatient end");
        return result;
    }

    /**
     * 获取数据结果
     * @param studyId
     * @param indicators
     * @return
     */
    public List<HiveLabTestCountVo> queryListLabTestTableData(String studyId, String[] cohortIds, String[] indicators, Integer patientCount) {
        logger.info("queryListLabTestTableData begin");
        String[] indicatorUnits = new String[indicators.length];
        for(int i=0; i< indicators.length; i++){
            indicatorUnits[i] = indicators[i] + " Unit";
        }
        List<HiveLabTestCountVo> voList = hiveResultService.queryListLabTestTableData(studyId, GlobalConstant.STR_ETL_TYPE_LAB_TEST, cohortIds, indicators, indicatorUnits);
        List<HiveLabTestCountVo> resultList = new ArrayList<HiveLabTestCountVo>();
        for(HiveLabTestCountVo vo : voList){
            if(vo.getUnit() == null || vo.getrSum() == null){
                continue;
            }
            vo.setPopt(new BigDecimal(vo.getpCount() * 100.0/patientCount).setScale(2, BigDecimal.ROUND_HALF_UP));
            vo.setPonr(new BigDecimal(vo.getnCount() * 100.0 / vo.getvCount()).setScale(2, BigDecimal.ROUND_HALF_UP));
            vo.setAc(new BigDecimal(vo.getvCount() * 1.0/vo.getpCount()).setScale(0, BigDecimal.ROUND_HALF_UP) + " ( " + vo.getvMin() + " ~ " + vo.getvMax() + " )");
            vo.setAr(vo.getrSum().divide(new BigDecimal(vo.getvCount()),2, BigDecimal.ROUND_HALF_EVEN) + " " + vo.getUnit() + " ( " + vo.getrMin().setScale(2, BigDecimal.ROUND_HALF_EVEN) + " ~ " + vo.getrMax().setScale(2, BigDecimal.ROUND_HALF_EVEN) + " )");
            resultList.add(vo);
        }

        Collections.sort(resultList, new Comparator<HiveLabTestCountVo>() {
            @Override
            public int compare(HiveLabTestCountVo a, HiveLabTestCountVo b) {
                int at = 0, bt = 0;
                for(int i = 0; i<GlobalGroupConfig.labTestGroup.length; i++){
                    if(a.getType().equals(GlobalGroupConfig.labTestGroup[i])){
                        at = i;
                    }
                    if(b.getType().equals(GlobalGroupConfig.labTestGroup[i])){
                        bt = i;
                    }
                }

                if(bt == at){
                    return a.getIndicator().toUpperCase().compareTo(b.getIndicator().toUpperCase());
                } else {
                    return at - bt;
                }
            }
        });

        logger.info("queryListLabTestTableData end");
        return resultList;
    }

    /**
     * 获得配置信息的id
     * @param configJson
     * @return
     */
    public List<ConfigVo> queryListConfigVo(JSONArray configJson) {
        logger.info("queryListConfigVo begin");
        List<ConfigVo> resultVoList = hiveResultService.queryListConfigVo(configJson, GlobalConstant.STR_ATTR_LAB_TEST_CONFIG_LIST);
        logger.info("queryListConfigVo begin");
        return resultVoList;
    }

    /**
     * 获取指标过滤条件列表
     * @param configJson
     * @return
     */
    public Map<String, String> queryIndicatorMap(String configJson) {
        Map<String, String> map = new LinkedHashMap<String, String>();
        JSONArray jsonArr = JSONArray.fromObject(configJson);
        JSONArray labTestJson = JSONArray.fromObject(jsonArr.getJSONObject(1));
        for(int i=0; i<labTestJson.size(); i++) {
            JSONArray indicatorJson = JSONArray.fromObject(labTestJson.getJSONObject(i).get(GlobalConstant.STR_ATTR_LAB_TEST_CONFIG_LIST));
            for(int j=0; j<indicatorJson.size(); j++) {
                JSONObject json = indicatorJson.getJSONObject(j);
                String indicator = json.get(GlobalConstant.STR_ATTR_INDICATOR).toString();
                String description = json.get(GlobalConstant.STR_ATTR_DESCRIPTION).toString();
                map.put(indicator, description);
            }
        }

        return map;
    }

    /**
     * 找到当前的 LabTest的上限和下限
     * @param configJson
     * @param indicator
     * @return
     */
    public Map<String,String> queryMapUpperAndLower(String configJson,String indicator) {
        Map<String, String> map = new HashMap<String,String>();
        JSONArray jsonArr = JSONArray.fromObject(configJson);
        String unit = jsonArr.getJSONObject(0).get("unit").toString();
        map.put("unit", unit);
        JSONArray labTestJson = JSONArray.fromObject(jsonArr.getJSONObject(1));
        for(int i=0;i<labTestJson.size();i++) {
            JSONArray json = JSONArray.fromObject(labTestJson.getJSONObject(i).get(GlobalConstant.STR_ATTR_LAB_TEST_CONFIG_LIST));
            for(int j=0;j<json.size();j++) {
                JSONObject labJSON = json.getJSONObject(j);
                if(indicator.equals(labJSON.get("indicator"))) {
                    String lowerRef = labJSON.get("lowerRef").toString();
                    String upperRef = labJSON.get("upperRef").toString();
                    String resultUnit = labJSON.get("resultUnit").toString();
                    map.put("lowerRef", lowerRef);
                    map.put("upperRef", upperRef);
                    map.put("resultUnit", resultUnit);
                    return map;
                }
            }
        }
        return map;
    }

    /**
     * 按照天统计测试结果
     * @param dataList
     * @param resultMap
     */
    public void loadLabTestDataByDay(List<HiveResultVo> dataList, Map<String, Object> resultMap) {
        String tempTime = "";
        int x = -1;
        List<Object> listPoint = new ArrayList<Object>();
        List<String> listX = new ArrayList<String>();
        List<String> tooltipList = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy.M.d");
        for(int i=0;i<dataList.size();i++) {
            try {
                HiveResultVo data = dataList.get(i);
                Double result = 0d;
                if(!StringUtils.isEmpty(data.getKeyValue().trim())) {
                    result = Double.parseDouble(data.getKeyValue().trim());
                }
                String assayDate = sdf.format(data.getVisitOn());
                String referenceCode = data.getReferenceCode();
                if(tempTime.equals(assayDate)) {
                    Object[] point = {x,result};
                    listPoint.add(point);
                }else {
                    Object[] point = {++x,result};
                    listPoint.add(point);
                    tempTime = assayDate;
                    listX.add(assayDate);
                }
                tooltipList.add(x+","+result+","+referenceCode);
            } catch (Exception e) {

            }
        }
        resultMap.put("x", listX);
        resultMap.put("y", listPoint);
        resultMap.put("tooltipList", tooltipList);
        resultMap.put("maxX", x);
    }

    /**
     * 按周统计测试结果
     * @param dataList
     * @param resultMap
     */
    public void loadLabTestDataByWeek(List<HiveResultVo> dataList, Map<String, Object> resultMap) {
        String tempTime = "";//临时记住测试时间
        int x = -1;//x轴横坐标
        int index = -1;//索引
        List<Object> listPoint = new ArrayList<Object>();
        List<String> listX = new ArrayList<String>();
        List<String> tooltipList = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy.M.d");
        for(int i=0;i<dataList.size();i++) {
            HiveResultVo data = dataList.get(i);
            Double result = 0d;
            if(!StringUtils.isEmpty(data.getKeyValue().trim())) {
                try {
                    result = Double.parseDouble(data.getKeyValue().trim());
                } catch (Exception e) {
                    e.printStackTrace();
                    result = 0d;
                }
            }
            String assayDate = sdf.format(data.getCreateOn());
            String referenceCode = data.getReferenceCode();
            if(tempTime.equals(assayDate)) {
                Object[] point = {x,result};
                listPoint.add(point);
            }else {
                if(++index % 7 == 0) {
                    ++x;
                    listX.add("week"+(x+1));
                }
                Object[] point = {x,result};
                listPoint.add(point);
                tempTime = assayDate;
            }
            tooltipList.add(x+","+result+","+referenceCode);
        }
        resultMap.put("x", listX);
        resultMap.put("y", listPoint);
        resultMap.put("tooltipList", tooltipList);
        resultMap.put("maxX", x);
    }
}
