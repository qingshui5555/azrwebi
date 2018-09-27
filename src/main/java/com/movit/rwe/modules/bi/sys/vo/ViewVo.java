package com.movit.rwe.modules.bi.sys.vo;

import java.util.Date;

import com.movit.rwe.modules.bi.base.entity.enums.ViewTypeEnum;
import com.movit.rwe.modules.sys.entity.User;

public class ViewVo {

	String name;

	String title;

	int viewType = ViewTypeEnum.Default_View.getTypeId();

	String mappingUrl;

	Boolean configFlag;

	String configUrl;

	String tabViewId;

	String alias;

	String viewChartHeight;

	String id;
	String remarks; // 备注
	User createBy; // 创建者
	Date createDate; // 创建日期
	User updateBy; // 更新者
	Date updateDate; // 更新日期
	String delFlag;

	private int size;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMappingUrl() {
		return mappingUrl;
	}

	public void setMappingUrl(String mappingUrl) {
		this.mappingUrl = mappingUrl;
	}

	public int getViewType() {
		return viewType;
	}

	public void setViewType(int viewType) {
		this.viewType = viewType;
	}

	public Boolean getConfigFlag() {
		return configFlag;
	}

	public void setConfigFlag(Boolean configFlag) {
		this.configFlag = configFlag;
	}

	public String getConfigUrl() {
		return configUrl;
	}

	public void setConfigUrl(String configUrl) {
		this.configUrl = configUrl;
	}

	public String getTabViewId() {
		return tabViewId;
	}

	public void setTabViewId(String tabViewId) {
		this.tabViewId = tabViewId;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getViewChartHeight() {
		return viewChartHeight;
	}

	public void setViewChartHeight(String viewChartHeight) {
		this.viewChartHeight = viewChartHeight;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public User getCreateBy() {
		return createBy;
	}

	public void setCreateBy(User createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public User getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(User updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

}
