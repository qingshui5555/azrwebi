package com.movit.rwe.modules.bi.base.entity.mysql;

import com.movit.rwe.common.persistence.DataEntity;

public class TabView extends DataEntity<TabView> {

	/**
	 * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	 */

	private static final long serialVersionUID = 3479241282895690401L;

	private String dashboardId;

	private String tabId;

	private int viewType;

	private String viewId;

	private int sort;

	private String alias;

	private String colType;
	private String customTitleName;
	private String configJson;
	private String viewChartHeight;

	private int size;

	public String getDashboardId() {
		return dashboardId;
	}

	public void setDashboardId(String dashboardId) {
		this.dashboardId = dashboardId;
	}

	public String getTabId() {
		return tabId;
	}

	public void setTabId(String tabId) {
		this.tabId = tabId;
	}

	public String getViewId() {
		return viewId;
	}

	public void setViewId(String viewId) {
		this.viewId = viewId;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public int getViewType() {
		return viewType;
	}

	public void setViewType(int viewType) {
		this.viewType = viewType;
	}

	public String getColType() {
		return colType;
	}

	public void setColType(String colType) {
		this.colType = colType;
	}

	public String getCustomTitleName() {
		return customTitleName;
	}

	public void setCustomTitleName(String customTitleName) {
		this.customTitleName = customTitleName;
	}

	public String getConfigJson() {
		return configJson;
	}

	public void setConfigJson(String configJson) {
		this.configJson = configJson;
	}

	public String getViewChartHeight() {
		return viewChartHeight;
	}

	public void setViewChartHeight(String viewChartHeight) {
		this.viewChartHeight = viewChartHeight;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

}
