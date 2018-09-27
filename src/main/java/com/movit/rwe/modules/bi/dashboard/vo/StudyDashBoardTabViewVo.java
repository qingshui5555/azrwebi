package com.movit.rwe.modules.bi.dashboard.vo;

/**
 * 
 * @ClassName: StudyDashBoardTabViewVo
 * @Description: dashboard 下tab配置信息
 * @author wade.chen@movit-tech.com
 * @date 2016年4月11日 下午2:09:14
 *
 */
public class StudyDashBoardTabViewVo {

	String tabId;

	String viewName;

	String viewId;

	int viewType;

	int sort;

	int size;

	String alias;

	public String getTabId() {
		return tabId;
	}

	public void setTabId(String tabId) {
		this.tabId = tabId;
	}

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public String getViewId() {
		return viewId;
	}

	public void setViewId(String viewId) {
		this.viewId = viewId;
	}

	public int getViewType() {
		return viewType;
	}

	public void setViewType(int viewType) {
		this.viewType = viewType;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

}
