package com.movit.rwe.modules.bi.dashboard.vo;

import java.util.List;

import com.movit.rwe.modules.bi.base.entity.mysql.Tab;
import com.movit.rwe.modules.bi.base.entity.mysql.View;

/**
 * 
  * @ClassName: StudyDashBoardTabVo
  * @Description: dashboard 下tab配置信息
  * @author wade.chen@movit-tech.com
  * @date 2016年4月11日 下午2:09:14
  *
 */
public class StudyDashBoardTabVo {
	
	Tab tab;
	
	List<StudyDashBoardTabViewVo> viewList;

	public Tab getTab() {
		return tab;
	}

	public void setTab(Tab tab) {
		this.tab = tab;
	}

	public List<StudyDashBoardTabViewVo> getViewList() {
		return viewList;
	}

	public void setViewList(List<StudyDashBoardTabViewVo> viewList) {
		this.viewList = viewList;
	}

}
