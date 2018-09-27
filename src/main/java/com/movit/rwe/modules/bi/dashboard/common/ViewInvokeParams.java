package com.movit.rwe.modules.bi.dashboard.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.ArrayUtils;

/**
 * chart请求参数对象
 * 
 * @author wade
 *
 */
public class ViewInvokeParams {

	public static final String ALL_PATIENTS = "[\"all\"]";

	String studyId;

	String[] cohorts;

	String cohortIds;

	String[] groups;

	String tabId;

	String viewId;

	String patientCount;

	String configJson;

	Boolean allPatientsFlag;

	String tabViewId;

	String isPad;

	String indicator;

	Integer max;

	Integer level;

	String patientIdStr;

	Boolean needChoosePatient;

	String patientCode;

	String startDate;

	String endDate;

	String comorbidity;

	String countType;

	String timeType;

	public String getPatientCode() {
		return patientCode;
	}

	public void setPatientCode(String patientCode) {
		this.patientCode = patientCode;
	}

	public Integer getMax() {
		return max;
	}

	public void setMax(Integer max) {
		this.max = max;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getPatientIdStr() {
		return patientIdStr;
	}

	public void setPatientIdStr(String patientIdStr) {
		this.patientIdStr = patientIdStr;
	}

	public String getPatientCount() {
		return patientCount;
	}

	public void setPatientCount(String patientCount) {
		this.patientCount = patientCount;
	}

	public String getStudyId() {
		return studyId;
	}

	public void setStudyId(String studyId) {
		this.studyId = studyId;
	}

	public String getCohortIds() {
		return cohortIds;
	}

	public void setCohortIds(String cohortIds) {
		this.cohortIds = cohortIds;
	}

	public String[] getCohorts() {
		return cohorts;
	}

	public void setCohorts(String[] cohorts) {
		this.cohorts = cohorts;
	}

	public String[] getGroups() {
		return groups;
	}

	public void setGroups(String[] groups) {
		this.groups = groups;
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

	public String getConfigJson() {
		return configJson;
	}

	public void setConfigJson(String configJson) {
		this.configJson = configJson;
	}

	public Boolean getAllPatientsFlag() {
		return allPatientsFlag;
	}

	public void setAllPatientsFlag(Boolean allPatientsFlag) {
		this.allPatientsFlag = allPatientsFlag;
	}

	public String getTabViewId() {
		return tabViewId;
	}

	public void setTabViewId(String tabViewId) {
		this.tabViewId = tabViewId;
	}

	public String getIsPad() {
		return isPad;
	}

	public void setIsPad(String isPad) {
		this.isPad = isPad;
	}

	public String getIndicator() {
		return indicator;
	}

	public void setIndicator(String indicator) {
		this.indicator = indicator;
	}

	public Boolean getNeedChoosePatient() {
		return needChoosePatient;
	}

	public void setNeedChoosePatient(Boolean needChoosePatient) {
		this.needChoosePatient = needChoosePatient;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getComorbidity() {
		return comorbidity;
	}

	public void setComorbidity(String comorbidity) {
		this.comorbidity = comorbidity;
	}

	public String getCountType() {
		return countType;
	}

	public void setCountType(String countType) {
		this.countType = countType;
	}

	public String getTimeType() {
		return timeType;
	}

	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}

	public String getSqlQueryStr() {
		if (allPatientsFlag) {
			return "%%";
		}

		StringBuffer sb = new StringBuffer("");
		if (!ArrayUtils.isEmpty(cohorts)) {
			for (String cohortId : cohorts) {
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

	/**
	 * 
	 * @Title: getPatientGroupArrStr @Description: 以字符串数组形式返回组
	 *         例:["C_166","C_167","C_193","C_205","G_1",
	 *         "G_8"] @param @return @return String @throws
	 */
	public String getPatientGroupArrStr() {
		if (allPatientsFlag) {
			return ALL_PATIENTS;
		}

		StringBuffer sb = new StringBuffer("[");
		if (!ArrayUtils.isEmpty(cohorts)) {
			for (String cohortId : cohorts) {
				sb.append("\"C_" + cohortId + "\",");
			}
		}

		if (!ArrayUtils.isEmpty(groups)) {
			Set<Integer> groupList = new TreeSet<Integer>();
			for (String group : groups) {
				int groupInt = Integer.parseInt(group);
				groupList.add(groupInt);
			}
			for (Integer group : groupList) {
				sb.append("\"G_" + group + "\",");
			}
		}
		if (sb.length() > 1) {
			sb.deleteCharAt(sb.length() - 1);
		}
		sb.append("]");
		return sb.toString();
	}

	public String getPatientGroupArrLikeStr() {
		if (allPatientsFlag) {
			return "%%";
		}

		StringBuffer sb = new StringBuffer("");
		if (!ArrayUtils.isEmpty(cohorts)) {
			for (String cohortId : cohorts) {
				sb.append("%C_" + cohortId + ",%;");
			}
		}

		if (!ArrayUtils.isEmpty(groups)) {
			Set<Integer> groupList = new TreeSet<Integer>();
			for (String group : groups) {
				int groupInt = Integer.parseInt(group);
				groupList.add(groupInt);
			}
			for (Integer group : groupList) {
				sb.append("%G_" + group + ",%;");
			}
		}
		if (sb.length() > 1) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	/**
	 * 
	 * @Title: getPatientGroupArrList @Description:
	 *         以集合方式返回所有组 @param @return @return List<String> @throws
	 */
	public List<String> getPatientGroupArrList() {
		List<String> list = new ArrayList();

		if (allPatientsFlag) {
			list.add("all");
			return list;
		}

		if (!ArrayUtils.isEmpty(cohorts)) {
			for (String cohortId : cohorts) {
				list.add("C_" + cohortId);
			}
		}

		if (!ArrayUtils.isEmpty(groups)) {
			Set<Integer> groupList = new TreeSet<Integer>();
			for (String group : groups) {
				int groupInt = Integer.parseInt(group);
				groupList.add(groupInt);
			}
			for (Integer group : groupList) {
				list.add("G_" + group);
			}
		}
		return list;
	}
}
