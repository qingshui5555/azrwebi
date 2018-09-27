package com.movit.rwe.modules.bi.dashboard.vo;

import java.util.List;

import com.movit.rwe.modules.bi.base.entity.hive.HivePatientGroup;
import com.movit.rwe.modules.bi.base.entity.hive.HiveStudy;
import com.movit.rwe.modules.bi.base.entity.mysql.DashBoard;
import com.movit.rwe.modules.bi.base.entity.mysql.Ta;

/**
 * 
  * @ClassName: StudyDashBoardShowVo
  * @Description: studydashboard做前台展现时封装的属性对象
  * @author wade.chen@movit-tech.com
  * @date 2016年3月22日 下午4:08:25
  *
 */
public class StudyDashBoardShowVo {
	
	HiveStudy study;
	
	Ta ta;
	
	DashBoard dashBoard;
	
	List<StudyDashBoardTabVo> showTabList;

	List<HivePatientGroup> patientGroupList;

	public HiveStudy getStudy() {
		return study;
	}

	public void setStudy(HiveStudy study) {
		this.study = study;
	}

	public DashBoard getDashBoard() {
		return dashBoard;
	}

	public void setDashBoard(DashBoard dashBoard) {
		this.dashBoard = dashBoard;
	}

	public List<StudyDashBoardTabVo> getShowTabList() {
		return showTabList;
	}

	public void setShowTabList(List<StudyDashBoardTabVo> showTabList) {
		this.showTabList = showTabList;
	}

	public List<HivePatientGroup> getPatientGroupList() {
		return patientGroupList;
	}

	public void setPatientGroupList(List<HivePatientGroup> patientGroupList) {
		this.patientGroupList = patientGroupList;
	}

	public Ta getTa() {
		return ta;
	}

	public void setTa(Ta ta) {
		this.ta = ta;
	}
	

}
