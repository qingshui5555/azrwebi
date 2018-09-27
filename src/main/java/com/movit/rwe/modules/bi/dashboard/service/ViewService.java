package com.movit.rwe.modules.bi.dashboard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.movit.rwe.common.service.CrudService;
import com.movit.rwe.modules.bi.base.dao.mysql.DaViewDao;
import com.movit.rwe.modules.bi.base.dao.mysql.PatientGroupDao;
import com.movit.rwe.modules.bi.base.dao.mysql.TabViewDao;
import com.movit.rwe.modules.bi.base.dao.mysql.ViewDao;
import com.movit.rwe.modules.bi.base.entity.enums.DelFlagEnum;
import com.movit.rwe.modules.bi.base.entity.enums.ViewTypeEnum;
import com.movit.rwe.modules.bi.base.entity.mysql.DaView;
import com.movit.rwe.modules.bi.base.entity.mysql.PatientGroup;
//import com.movit.rwe.modules.bi.base.entity.enums.DelFlagEnum;
//import com.movit.rwe.modules.bi.base.entity.enums.ViewTypeEnum;
import com.movit.rwe.modules.bi.base.entity.mysql.View;
import com.movit.rwe.modules.bi.da.test.dataframe.DataFrameInvoke;
import com.movit.rwe.modules.bi.dashboard.common.ViewInvokeParams;

@Service
@Transactional
public class ViewService extends CrudService<ViewDao, View> {
	
	@Autowired
	private ViewDao viewDao;
	
	@Autowired
	private TabViewDao tabViewDao;
	
	@Autowired
	DaViewDao daViewDao;
	
	@Autowired
	private PatientGroupDao patientGroupDao;
	/**
	 * 
	  * findDefaultViewList 
	  * TODO(这里描述这个方法适用条件 – 可选)
	  * TODO(这里描述这个方法的执行流程 – 可选)
	  * TODO(这里描述这个方法的使用方法 – 可选)
	  * TODO(这里描述这个方法的注意事项 – 可选)
	  *
	  * @Title: findDefaultViewList
	  * @Description: 获取默认view清单
	  * @param @return
	  * @return List<View>    返回类型
	  * @throws
	 */
	List<View> findDefaultViewList(){
		return viewDao.findListByType(ViewTypeEnum.Default_View.getTypeId());
	}

	/**
	 * 
	  * findCustomViewList
	  * TODO(这里描述这个方法适用条件 – 可选)
	  * TODO(这里描述这个方法的执行流程 – 可选)
	  * TODO(这里描述这个方法的使用方法 – 可选)
	  * TODO(这里描述这个方法的注意事项 – 可选)
	  *
	  * @Title: findCustomViewList
	  * @Description: 获取自定义view清单
	  * @param @return
	  * @return List<View>    返回类型
	  * @throws
	 */
	List<View> findCustomViewList(){
		return viewDao.findListByType(ViewTypeEnum.Custom_View.getTypeId());
	}
	
	/**
	 * 
	  * deleteByEntity
	  * TODO(这里描述这个方法适用条件 – 可选)
	  * TODO(这里描述这个方法的执行流程 – 可选)
	  * TODO(这里描述这个方法的使用方法 – 可选)
	  * TODO(这里描述这个方法的注意事项 – 可选)
	  *
	  * @Title: deleteByEntity
	  * @Description: 逻辑删除谋对象，删除的同时还能update相关属性(逻辑删除)
	  * @param @param view
	  * @param @return    
	  * @return boolean    返回类型
	  * @throws
	 */
	public boolean deleteByEntity(View view){
		boolean isSuccess = false;
		
		if(view != null){
			view.setDelFlag(DelFlagEnum.deled.getCode()+"");
			isSuccess = viewDao.update(view)>0;
		}
		
		return isSuccess;
	}
	
	/**
	 * 
	  * deleteById
	  * TODO(这里描述这个方法适用条件 – 可选)
	  * TODO(这里描述这个方法的执行流程 – 可选)
	  * TODO(这里描述这个方法的使用方法 – 可选)
	  * TODO(这里描述这个方法的注意事项 – 可选)
	  *
	  * @Title: deleteById
	  * @Description: 直接根据ID删除对象 （逻辑删除）
	  * @param @param id
	  * @param @return    
	  * @return boolean    返回类型
	  * @throws
	 */
	public boolean deleteById(String id){
		View view = viewDao.get(id);
		return this.deleteByEntity(view);
	}
	
	
	public String findTabViewConfigJson(String tabViewId) {
		return tabViewDao.findConfigJson(tabViewId);
	}
	
	public DaView findDaViewById(String daViewId){
		return daViewDao.get(daViewId);
	}
	
	public DataFrameInvoke getDataFrameInvoke(DaView daView,ViewInvokeParams vip){
		Gson gson = new Gson();
		DataFrameInvoke dfi = gson.fromJson(daView.getConfJsonStr(), DataFrameInvoke.class);
		//根据dashboard选择的组进行计算
		Long[] cohortIds = new Long[vip.getCohorts().length];
		for(int i=0; i<cohortIds.length; i++){
			cohortIds[i] = Long.parseLong(vip.getCohorts()[i]);
		}
		String groups[] = vip.getGroups();
		if(cohortIds!=null&&cohortIds.length>0){
			dfi.getConhortChosen().setCohortIds(cohortIds);
			PatientGroup[] cohortsObj = new PatientGroup[cohortIds.length];
			for(int i=0;i<cohortIds.length;i++){
				cohortsObj[i] = patientGroupDao.loadByTypeAndId(cohortIds[i]+"", 1);
			}
			dfi.getConhortChosen().setCohortsObj(cohortsObj);
		}
		if(groups!=null&&groups.length>0){
			dfi.getConhortChosen().setGroups(groups);
			PatientGroup[] groupsObj = new PatientGroup[groups.length];
			for(int i=0;i<groups.length;i++){
				groupsObj[i] = patientGroupDao.loadByTypeAndId(groups[i], 0);
			}
			dfi.getConhortChosen().setGroupsObj(groupsObj);
		}
		
		return dfi;
	}
}
