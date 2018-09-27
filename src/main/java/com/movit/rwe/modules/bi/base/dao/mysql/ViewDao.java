package com.movit.rwe.modules.bi.base.dao.mysql;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.movit.rwe.common.persistence.CrudDao;
import com.movit.rwe.common.persistence.annotation.MyBatisDao;
import com.movit.rwe.modules.bi.base.entity.mysql.View;
import com.movit.rwe.modules.bi.dashboard.vo.StudyDashBoardTabViewVo;
import com.movit.rwe.modules.bi.sys.vo.ViewVo;

@MyBatisDao
public interface ViewDao extends CrudDao<View> {
	
	List<View> findListByType(@Param("viewType")int viewType);
	
	List<View> findListByDashBoardId(@Param("dashBoardId")String dashBoardId);

	List<ViewVo> findAllByTabId(String tabId);
	
	List<StudyDashBoardTabViewVo> findShowAllViewVoByTabId(@Param("tabId")String tabId);
	
	List<View> findList();

}
