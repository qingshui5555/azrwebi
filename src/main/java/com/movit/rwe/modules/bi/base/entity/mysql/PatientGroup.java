package com.movit.rwe.modules.bi.base.entity.mysql;

/**
 * 
  * @ClassName: PatientGroup
  * @Description: studyGroup & cohort 联合视图封装的模型对象，该实体类对应两张表
  * @author wade.chen@movit-tech.com
  * @date 2016年4月14日 下午1:32:14
  *
 */
public class PatientGroup {
	
	String id;
	
	int groupType;
	
	String groupName;
	
	int patinetCount;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getGroupType() {
		return groupType;
	}

	public void setGroupType(int groupType) {
		this.groupType = groupType;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public int getPatinetCount() {
		return patinetCount;
	}

	public void setPatinetCount(int patinetCount) {
		this.patinetCount = patinetCount;
	}
	
	

}
