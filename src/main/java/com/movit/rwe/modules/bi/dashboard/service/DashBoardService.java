package com.movit.rwe.modules.bi.dashboard.service;

import java.util.*;

import com.movit.rwe.common.config.GlobalConstant;
import com.movit.rwe.common.utils.EhCacheUtils;
import com.movit.rwe.modules.bi.base.dao.hive.HiveCohortDao;
import com.movit.rwe.modules.bi.base.entity.hive.HiveCohort;
import com.movit.rwe.modules.bi.base.entity.hive.HivePatientGroup;
import com.movit.rwe.modules.bi.base.entity.hive.HiveStudy;
import com.movit.rwe.modules.sys.utils.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.movit.rwe.modules.bi.base.dao.mysql.DashBoardDao;
import com.movit.rwe.modules.bi.base.dao.mysql.PatientGroupDao;
import com.movit.rwe.modules.bi.base.dao.mysql.TaDao;
import com.movit.rwe.modules.bi.base.dao.mysql.TabDao;
import com.movit.rwe.modules.bi.base.dao.mysql.ViewDao;
import com.movit.rwe.modules.bi.base.entity.mysql.DashBoard;
import com.movit.rwe.modules.bi.base.entity.mysql.Ta;
import com.movit.rwe.modules.bi.base.entity.mysql.Tab;
import com.movit.rwe.modules.bi.dashboard.common.DashBoardBeanHelper;
import com.movit.rwe.modules.bi.dashboard.vo.StudyDashBoardShowVo;
import com.movit.rwe.modules.bi.dashboard.vo.StudyDashBoardTabViewVo;
import com.movit.rwe.modules.bi.dashboard.vo.StudyDashBoardTabVo;
import com.movit.rwe.modules.bi.sys.vo.ViewVo;

@Service
@Transactional
public class DashBoardService {

	private static Logger logger = LoggerFactory.getLogger(DashBoardService.class);

	@Autowired
	DashBoardDao dashBoardDao;
	
	@Autowired
	PatientGroupDao patientGroupDao;

	@Autowired
	HiveCohortDao hiveCohortDao;

	@Autowired
	TabDao tabDao;
		
	@Autowired
	ViewDao viewDao;
	
	@Autowired
	TaDao taDao;
	
	/**
	 * 
	  * getStudyDashBoardShowVoByStudyId
	  * TODO(这里描述这个方法适用条件 – 可选)
	  * TODO(这里描述这个方法的执行流程 – 可选)
	  * TODO(这里描述这个方法的使用方法 – 可选)
	  * TODO(这里描述这个方法的注意事项 – 可选)
	  *
	  * @Title: getStudyDashBoardShowVoByStudyId
	  * @Description: 根据studyID获取其对应的dashboard
	  * @param @param studyId
	  * @param @return    
	  * @return StudyDashBoardShowVo    返回类型
	  * @throws
	 */
	public StudyDashBoardShowVo getStudyDashBoardShowVoByStudy(HiveStudy study){
		Ta ta = taDao.getByStudyId(study.getGuid());
		if(ta == null){
			return null;
		}

		DashBoard dashBoard = dashBoardDao.findByStudyId(study.getGuid());
		if(dashBoard==null) {
			return null;
		}

		List<Tab> tabs = tabDao.findAll(dashBoard.getId());
		List<StudyDashBoardTabVo> tabVos = new ArrayList<StudyDashBoardTabVo>();
		if(!CollectionUtils.isEmpty(tabs)){
			for(Tab tab : tabs){
				String tabId = tab.getId();
				StudyDashBoardTabVo tabVo = new StudyDashBoardTabVo();
				tabVo.setTab(tab);
				tabVo.setViewList(this.findShowViewVoListByTabId(tabId));
				logger.info("tabId " + tabId);
				tabVos.add(tabVo);
			}
		}

		List<HivePatientGroup> patientGroupList = new ArrayList<HivePatientGroup>();
		List<Object> hiveCohortList = EhCacheUtils.getValuesWithKeys(GlobalConstant.STR_CACHE_NAME_COHORT, study.getGuid());
		for(Object object : hiveCohortList){
			HiveCohort cohort = (HiveCohort) object;
			if(UserUtils.getPrincipal().getId().equals(cohort.getCreateBy()+"")){
				HivePatientGroup group = new HivePatientGroup();
				group.setGuid(cohort.getGuid());
				group.setGroupName(cohort.getCohortName());
				group.setGroupType("1");
				group.setGroupCount(cohort.getPatientCount());
				patientGroupList.add(group);
			}
		}

		return DashBoardBeanHelper.getStudyDashBoardShowVo(study, ta, dashBoard, tabVos, patientGroupList);
		
	}
	
	/**
	 * 
	  * findShowViewListBuTabId
	  * TODO(这里描述这个方法适用条件 – 可选)
	  * TODO(这里描述这个方法的执行流程 – 可选)
	  * TODO(这里描述这个方法的使用方法 – 可选)
	  * TODO(这里描述这个方法的注意事项 – 可选)
	  *
	  * @Title: findShowViewListBuTabId
	  * @Description: 根据tabid 查询其中包含的view清单
	  * @param @param tabId
	  * @param @return    
	  * @return List<View>    返回类型
	  * @throws
	 */
	public List<ViewVo> findShowViewListBuTabId(String tabId){
		return viewDao.findAllByTabId(tabId);
	}
	
	public List<StudyDashBoardTabViewVo> findShowViewVoListByTabId(String tabId){
		return viewDao.findShowAllViewVoByTabId(tabId);
	}
	
	public Integer countPatientByGroups(String[] cohorts, String[] groups){
		Integer result = 0;
		if(cohorts != null && cohorts.length > 0){
			result += hiveCohortDao.countByCohortIds(cohorts);
		}
		return result;
	}

}
