package com.movit.rwe.modules.bi.da.test.set;

import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.ArrayUtils;

import com.movit.rwe.modules.bi.base.entity.mysql.PatientGroup;

public class ConhortChosen {

	Long[] cohortIds;
	
	PatientGroup[] cohortsObj;

	String[] groups;
	
	PatientGroup[] groupsObj;

	public Long[] getCohortIds() {
		return cohortIds;
	}

	public void setCohortIds(Long[] cohortIds) {
		this.cohortIds = cohortIds;
	}

	public String[] getGroups() {
		return groups;
	}

	public void setGroups(String[] groups) {
		this.groups = groups;
	}

	public PatientGroup[] getCohortsObj() {
		return cohortsObj;
	}

	public void setCohortsObj(PatientGroup[] cohortsObj) {
		this.cohortsObj = cohortsObj;
	}

	public PatientGroup[] getGroupsObj() {
		return groupsObj;
	}

	public void setGroupsObj(PatientGroup[] groupsObj) {
		this.groupsObj = groupsObj;
	}

	public String getSqlQueryStr() {
		StringBuffer sb = new StringBuffer("");
		if (!ArrayUtils.isEmpty(cohortIds)) {
			for (Long cohortId : cohortIds) {
				sb.append("C_" + cohortId + ",%");
			}
		}

		if (!ArrayUtils.isEmpty(groups)) {
			Set<Integer> groupList = new TreeSet<Integer>();
			for (String group : groups) {
				int groupInt = Integer.parseInt(group);
				groupList.add(groupInt);
			}
			for (Integer group : groupList) {
				sb.append("G_" + group + ",%");
			}
		}
		return sb.length() == 0 ? "" : sb.insert(0, "%").toString();
	}

}
