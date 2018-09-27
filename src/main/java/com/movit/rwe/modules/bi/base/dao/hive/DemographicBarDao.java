package com.movit.rwe.modules.bi.base.dao.hive;

import com.movit.rwe.common.persistence.CrudDao;
import com.movit.rwe.common.persistence.annotation.MyBatisImpalaDao;
import com.movit.rwe.modules.bi.base.entity.hive.DemographicBar;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Mike on 2016/4/25.
 */
@MyBatisImpalaDao
public interface DemographicBarDao extends CrudDao<DemographicBar> {

	List<DemographicBar> countByGender(@Param("studyId") String studyId, @Param("groupids") String[] groupids, @Param("locale") String locale);

	List<DemographicBar> countCancerType(@Param("studyId") String studyId, @Param("groupids") String[] groupids);

	List<DemographicBar> countCancerStageType(@Param("studyId") String studyId);

	List<DemographicBar> countByAge(@Param("studyId") String studyId, @Param("s1") String s1, @Param("e1") String e1, @Param("s2") String s2, @Param("e2") String e2, @Param("s3") String s3,
			@Param("e3") String e3, @Param("s4") String s4, @Param("e4") String e4, @Param("s5") String s5, @Param("e5") String e5, @Param("s6") String s6, @Param("e6") String e6,
			@Param("s7") String s7, @Param("e7") String e7, @Param("e8") String e8, @Param("groupids") String[] groupids, @Param("locale") String locale);

	List<DemographicBar> countSmoking(@Param("studyId") String studyId,@Param("groupids") String[] groupids);

	List<DemographicBar> countAlcohol(@Param("studyId") String studyId);

	List<DemographicBar> countBmi(@Param("studyId") String studyId, @Param("groupids") String[] groupids);

	List<Double> getTotalIgeList(@Param("studyId") String studyId, @Param("groupids") String[] groupids);

	List<Double> getLabTestViewConfigDate(@Param("viewName") String viewName, @Param("studyId") String studyId, @Param("groupids") String[] groupids);

	List<DemographicBar> countPathologicalStage(@Param("studyId")String studyId, @Param("groupids")String[] groupids);
	
	List<DemographicBar> getCountByGender(@Param("studyId") String studyId, @Param("groupids") String[] groupids);
}
