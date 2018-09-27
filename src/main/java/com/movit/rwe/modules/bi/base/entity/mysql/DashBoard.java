package com.movit.rwe.modules.bi.base.entity.mysql;

import com.movit.rwe.common.persistence.DataEntity;

/**
 * 
 * @ClassName: DashBoard
 * @Description: DashBoard实体类
 * @author wade.chen@movit-tech.com
 * @date 2016年3月22日 下午3:40:17
 *
 */
public class DashBoard extends DataEntity<DashBoard> {

	/**
	 * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	 */

	private static final long serialVersionUID = 4828746135564877045L;

	String studyId;

	/**
	 * 标题
	 */
	String title;
	
	String name;

	public String getStudyId() {
		return studyId;
	}

	public void setStudyId(String studyId) {
		this.studyId = studyId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
	

}
