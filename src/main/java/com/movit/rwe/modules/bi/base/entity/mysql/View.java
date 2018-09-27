package com.movit.rwe.modules.bi.base.entity.mysql;

import com.movit.rwe.common.persistence.DataEntity;
//import com.movit.rwe.modules.bi.base.entity.enums.ViewTypeEnum;
import com.movit.rwe.modules.bi.base.entity.enums.ViewTypeEnum;

/**
 * 
 * @Project : az-rwe-bi-web
 * @Title : View.java
 * @Package com.movit.rwe.modules.bi.base.entity.mysql
 * @Description : 图表对象
 * @author Wade.chen
 * @email Wade.chen@movit-tech.com
 * @date 2016年3月1日 下午1:35:45
 *
 */
public class View extends DataEntity<View> {

	/**
	 * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	 */

	private static final long serialVersionUID = 3479241282895690401L;

	String name;

	String title;

	int viewType = ViewTypeEnum.Default_View.getTypeId();

	String mappingUrl;

	Boolean configFlag;

	String configUrl;

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


}
