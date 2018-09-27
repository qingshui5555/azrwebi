package com.movit.rwe.modules.bi.sys.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.movit.rwe.modules.bi.base.dao.mysql.*;
import com.movit.rwe.modules.bi.base.entity.mysql.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movit.rwe.modules.bi.sys.vo.StudyVo;
import com.movit.rwe.modules.bi.sys.vo.ViewVo;
import com.movit.rwe.modules.sys.entity.User;

/**
 * 
 * @Title: StudyManagementService.java
 * @Package com.movit.rwe.modules.bi.dashboard.service
 * @Description: study管理相关服务
 * @author Yuri.Zheng
 * @date 2016年4月7日 下午2:00:50
 */
@Service
@Transactional
public class StudyManagementService {

	@Autowired
	private TaDao taDao;

	@Autowired
	private EtlTaConfigDao taConfigDao;

	@Autowired
	private StudyDao studyDao;

	@Autowired
	private TabDao tabDao;

	@Autowired
	private ViewDao viewDao;

	@Autowired
	private TabViewDao tabViewDao;

	@Autowired
	private DaViewDao daViewDao;

	/**
	 * 获取所有治疗领域配置
	 * @return
	 */
	public List<EtlTaConfig> getAllTaConfig() {
		return taConfigDao.queryList();
	}

	/**
	 * @Title: getStudyByTaId @Description: 根据ta主键查询study @param @param
	 *         taId @param @return @return List<Study> @throws
	 */
	public List<StudyVo> getStudyByTaId(String taId) {
		// 查询所有study
		if ("-1".equals(taId)) {
			return studyDao.findVoList();
		}

		// 根据条件查询study
		if ("0".equals(taId)) {
			taId = null;
		}
		return studyDao.findVoListByTaId(taId);
	}

	public List<Tab> findAllTabByDashboardId(String dashboardId) {
		return tabDao.findAll(dashboardId);
	}

	public void createTab(String dashboardId, User user, String tabName, String iconId, Integer sort) {
		// 封装tab对象
		Tab tab = new Tab();
		tab.preInsert();
		tab.setCreateBy(user);
		tab.setCreateDate(new Date());
		tab.setDashboardId(dashboardId);
		tab.setDelFlag(Tab.DEL_FLAG_NORMAL);
		tab.setName(tabName);
		tab.setIcon(iconId);
		tab.setSort(sort);
		tab.setUpdateBy(user);
		tab.setUpdateDate(new Date());

		tabDao.insert(tab);
		
		studyDao.updateStudyTime(dashboardId);
	}

	public void dropTab(User updateUser, String tabId) {
		Tab tab = new Tab();
		tab.setId(tabId);
		tab.setUpdateBy(updateUser);
		tab.setUpdateDate(new Date());

		tabDao.delete(tab);
	}

	public void sortTab(User updateUser, String tabId, Integer tabSort, String prevTabId, Integer prevTabSort) {
		Tab currentTab = new Tab();
		currentTab.setId(tabId);
		currentTab.setUpdateBy(updateUser);
		currentTab.setUpdateDate(new Date());
		currentTab.setSort(prevTabSort);
		tabDao.sortTab(currentTab);

		Tab prevTab = new Tab();
		prevTab.setId(prevTabId);
		prevTab.setUpdateBy(updateUser);
		prevTab.setUpdateDate(new Date());
		prevTab.setSort(tabSort);
		tabDao.sortTab(prevTab);
	}

	public List<ViewVo> findAllViewByTabId(String tabId) {
		return viewDao.findAllByTabId(tabId);
	}

	public List<View> findAllView() {
		return viewDao.findList();
	}

	public Map<String, Object> addView(User updateUser, String tabId, String viewId, String dashboardId,int viewType,int size) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

//		if (checkTabViewExists(tabId, viewId)) {
//			resultMap.put("success", false);
//			resultMap.put("info", "View exists already!");
//			return resultMap;
//		}

		insertTabView(updateUser, tabId, viewId, dashboardId,viewType,size);
		studyDao.updateStudyTime(dashboardId);
		
		updateTabDate(tabId);
		
		resultMap.put("success", true);
		return resultMap;
	}

	private Boolean checkTabViewExists(String tabId, String viewId) {
		int count = tabViewDao.checkTabViewExists(tabId, viewId);
		if (count > 0)
			return true;
		return false;
	}

	private void insertTabView(User updateUser, String tabId, String viewId, String dashboardId,int viewType,int size) {
		TabView tabView = new TabView();
		tabView.preInsert();
		tabView.setCreateBy(updateUser);
		tabView.setCreateDate(new Date());
		tabView.setDashboardId(dashboardId);
		tabView.setDelFlag(TabView.DEL_FLAG_NORMAL);
		tabView.setTabId(tabId);
		tabView.setUpdateBy(updateUser);
		tabView.setUpdateDate(new Date());
		tabView.setViewId(viewId);
		tabView.setViewType(viewType);
		tabView.setSize(size);

		tabViewDao.insert(tabView);
		updateTabDate(tabId);
		
		studyDao.updateStudyTime(dashboardId);
	}

	public Map<String, Object> dropView(User user, String tabViewId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		TabView tabView = new TabView();
		tabView.setId(tabViewId);
		tabView.setUpdateBy(user);
		tabView.setUpdateDate(new Date());
		tabViewDao.delete(tabView);
		resultMap.put("success", true);
		return resultMap;
	}

	public void configTabView(String tabViewId, String configJson, String alias, String viewChartHeight) {
		tabViewDao.updateConfig(tabViewId, configJson, alias, viewChartHeight);
	}

	public Integer findMaxTabSort(String dashboardId) {
		String sort = tabDao.findMaxTabSort(dashboardId);
		if(sort==null){
			return 0;
		}else{
			return Integer.parseInt(sort);
		}
	}

	public Tab findTab(String tabId) {
		return tabDao.findById(tabId);
	}

	public void updateTab(String id, User user, String tabName, String iconId, Integer sort) {
		tabDao.update(id, tabName, iconId, sort, user.getId(), new Date());
	}

	public void updateStudyTaName(String studyId, String taId, String userId, Date updateTime) {
		studyDao.updateTaName(studyId, taId, userId, updateTime);
	}

	public List<DaView> findDaViewByStudyId(String dashboardId) {
		return daViewDao.findListByStudyId(dashboardId);
	}

	public void sortView(String[] tabViewIdArr,String tabId) {
		for(int i=0;i<tabViewIdArr.length;i++){
			int num=i+1;
			tabViewDao.updateSort(tabViewIdArr[i],num);
		}
		updateTabDate(tabId);
	}
	
	private void updateTabDate(String tabId){
		tabDao.updateDate(tabId);
	}
}
