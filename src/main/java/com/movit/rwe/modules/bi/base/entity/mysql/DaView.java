package com.movit.rwe.modules.bi.base.entity.mysql;

import com.movit.rwe.common.persistence.DataEntity;
//import com.movit.rwe.modules.bi.base.entity.enums.ViewTypeEnum;
import com.movit.rwe.modules.bi.base.entity.enums.ViewTypeEnum;

/**
 * 
  * @ClassName: DaView
  * @Description: data analysis 配置保存信息，用以添加到dashboard上
  * @author wade.chen@movit-tech.com
  * @date 2016年5月30日 下午1:02:05
  *
 */
public class DaView extends DataEntity<DaView> {

	/**
	 * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	 */

	private static final long serialVersionUID = 3479241282895690401L;

	String name;

	String title;
	
	String taId;
	
	String studyId;

	int viewType = ViewTypeEnum.Da_View.getTypeId();

	String confJsonStr;

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

	public int getViewType() {
		return viewType;
	}

	public void setViewType(int viewType) {
		this.viewType = viewType;
	}

	public String getConfJsonStr() {
		return confJsonStr;
	}

	public void setConfJsonStr(String confJsonStr) {
		this.confJsonStr = confJsonStr;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getTaId() {
		return taId;
	}

	public void setTaId(String taId) {
		this.taId = taId;
	}

	public String getStudyId() {
		return studyId;
	}

	public void setStudyId(String studyId) {
		this.studyId = studyId;
	}

}
