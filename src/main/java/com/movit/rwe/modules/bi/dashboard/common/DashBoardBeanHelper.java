package com.movit.rwe.modules.bi.dashboard.common;

import java.util.List;
import com.movit.rwe.modules.bi.base.entity.hive.HivePatientGroup;
import com.movit.rwe.modules.bi.base.entity.hive.HiveStudy;
import com.movit.rwe.modules.bi.base.entity.mysql.DashBoard;
import com.movit.rwe.modules.bi.base.entity.mysql.Ta;
import com.movit.rwe.modules.bi.dashboard.vo.StudyDashBoardShowVo;
import com.movit.rwe.modules.bi.dashboard.vo.StudyDashBoardTabVo;

public class DashBoardBeanHelper {

	public static StudyDashBoardShowVo getStudyDashBoardShowVo(HiveStudy study, Ta ta, DashBoard dashBoard, List<StudyDashBoardTabVo> tabs, List<HivePatientGroup> patientGroupList){
		StudyDashBoardShowVo sd = new StudyDashBoardShowVo();
		sd.setStudy(study);
		sd.setDashBoard(dashBoard);
		sd.setShowTabList(tabs);
		sd.setPatientGroupList(patientGroupList);
		sd.setTa(ta);
		return sd;
	}
	
}
