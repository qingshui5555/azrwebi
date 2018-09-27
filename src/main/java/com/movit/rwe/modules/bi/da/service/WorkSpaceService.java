package com.movit.rwe.modules.bi.da.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movit.rwe.modules.bi.base.dao.mysql.DaViewDao;
import com.movit.rwe.modules.bi.base.dao.mysql.TabViewDao;
import com.movit.rwe.modules.bi.base.entity.mysql.DaView;
import com.movit.rwe.modules.bi.base.entity.mysql.TabView;
import com.movit.rwe.modules.bi.da.vo.WorkSpaceDaViewShowVo;

@Service
public class WorkSpaceService {

	@Autowired
	DaViewDao daViewDao;
	@Autowired
	TabViewDao tabViewDao;
	
	public List<DaView> findAllDaView(){
		return daViewDao.findAllList();
	}
	
	public  List<WorkSpaceDaViewShowVo> findAllWsShowList(){
		return daViewDao.findAllWsShowList();
	}
	
	public  List<WorkSpaceDaViewShowVo> findAllWsShowListByUserId(String userId){
		return daViewDao.findAllWsShowListByUserId(userId);
	}
	
	/**
	 * 删除daview及关联表tabView的数据
	 * @param daViewId
	 * @return
	 */
	@Transactional(readOnly = false)
	public boolean deleteDaView(String daViewId){
		DaView daView = new DaView();
		daView.setId(daViewId);
		daViewDao.delete(daView);
		tabViewDao.deleteByViewId(daViewId);
		return true;
	}
	
	public WorkSpaceDaViewShowVo getDaView(String daViewId) throws Exception{
		DaView daView = daViewDao.get(daViewId);
		WorkSpaceDaViewShowVo workSpaceDaViewShowVo = new WorkSpaceDaViewShowVo();
		BeanUtils.copyProperties(workSpaceDaViewShowVo, daView);
		return workSpaceDaViewShowVo;
	}
	
}
