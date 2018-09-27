package com.movit.rwe.modules.bi.base.dao.mysql;

import java.util.*;
import com.movit.rwe.modules.bi.base.entity.mysql.EtlResultConfig;
import org.apache.ibatis.annotations.Param;
import com.movit.rwe.common.persistence.annotation.MyBatisDao;

/**
 * @author kwqb535
 */
@MyBatisDao
public interface EtlResultConfigDao {

	/**
	 * 根据userId获取最大的版本号
	 * @param userId
	 * @return
	 */
	Long getMaxVersion(@Param("userId") Long userId);

	List<EtlResultConfig> queryListEtlResultConfig(@Param("userId") Long userId);

	EtlResultConfig queryEtlResultConfig(@Param("studyId") String studyId, @Param("indicator") String indicator);

	int removeConfigRow(@Param("id") Long id,@Param("userId") Long userId);

	int updateConfigRow(@Param("id") Long id, @Param("userId") Long userId, @Param("dataMap") Map<String, String> dataMap);

	int importConfig(@Param("columnList") List<Object> columnList, @Param("dataList") List<Object> dataList);
}
