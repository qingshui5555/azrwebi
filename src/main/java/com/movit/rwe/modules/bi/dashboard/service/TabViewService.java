package com.movit.rwe.modules.bi.dashboard.service;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movit.rwe.common.service.CrudService;
import com.movit.rwe.modules.bi.base.dao.mysql.TabViewDao;
import com.movit.rwe.modules.bi.base.dao.mysql.UserStudyDao;
import com.movit.rwe.modules.bi.base.entity.mysql.TabView;
import com.movit.rwe.modules.bi.base.entity.mysql.UserStudy;

@Service
@Transactional
public class TabViewService extends CrudService<TabViewDao, TabView>{
	@Autowired
	private TabViewDao tabViewDao;

	public String findViewChartHeightByTabAndViewId(String tabViewId){
		return tabViewDao.findViewChartHeightByTabAndViewId(tabViewId);
	}

	public String findTabViewJsonByTabViewId(String tabViewId){
		return tabViewDao.findTabViewJsonByTabViewId(tabViewId);
	}

	public TabView findTabViewByTabViewId(String tabViewId){
		return tabViewDao.selectByTabViewId(tabViewId);
	}
}