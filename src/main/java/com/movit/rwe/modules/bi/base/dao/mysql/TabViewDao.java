package com.movit.rwe.modules.bi.base.dao.mysql;

import org.apache.ibatis.annotations.Param;

import com.movit.rwe.common.persistence.CrudDao;
import com.movit.rwe.common.persistence.annotation.MyBatisDao;
import com.movit.rwe.modules.bi.base.entity.mysql.TabView;

@MyBatisDao
public interface TabViewDao extends CrudDao<TabView> {

	int checkTabViewExists(@Param("tabId") String tabId, @Param("viewId") String viewId);

	void updateConfig(@Param("tabViewId") String tabViewId, @Param("configJson") String configJson, @Param("alias") String alias, @Param("viewChartHeight") String viewChartHeight);

	String findConfigJson(@Param("tabViewId") String tabViewId);

	String findTabViewJsonByTabViewId(@Param("tabViewId") String tabViewId);

	String findHeightByTabViewId(@Param("tabViewId") String tabViewId);

	int deleteByViewId(@Param("viewId") String viewId);

	String findViewChartHeightByTabAndViewId(@Param("tabViewId") String tabViewId);

	void updateSort(@Param("tabViewId") String tabViewId, @Param("sort") int sort);

	TabView selectByTabViewId(@Param("tabViewId") String tabViewId);

}
