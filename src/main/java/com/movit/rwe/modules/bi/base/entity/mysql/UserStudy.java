package com.movit.rwe.modules.bi.base.entity.mysql;

import com.movit.rwe.common.persistence.DataEntity;

/**
 * 
  * @ClassName: UserStudy
  * @Description: 用户和study的关系表
  * @author wade.chen@movit-tech.com
  * @date 2016年3月28日 下午8:21:16
  *
 */
public class UserStudy extends DataEntity<UserStudy> {

	private static final long serialVersionUID = -3464722487878596581L;
	
	String studyId;
	
	String userId;

	public String getStudyId() {
		return studyId;
	}

	public void setStudyId(String studyId) {
		this.studyId = studyId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	

}
