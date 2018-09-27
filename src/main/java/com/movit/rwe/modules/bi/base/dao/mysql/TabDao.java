package com.movit.rwe.modules.bi.base.dao.mysql;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.movit.rwe.common.persistence.CrudDao;
import com.movit.rwe.common.persistence.annotation.MyBatisDao;
import com.movit.rwe.modules.bi.base.entity.mysql.Tab;

@MyBatisDao
public interface TabDao extends CrudDao<Tab> {

	/**
	 * 
	 * @Title: findAll @Description: 查出所有关联tab（未删除） @param @param
	 *         dashboardId @param @return @return List<Tab> @throws
	 */
	List<Tab> findAll(@Param("dashboardId") String dashboardId);

	void sortTab(Tab tab);

	String findMaxTabSort(@Param("dashboardId") String dashboardId);

	Tab findById(@Param("tabId") String tabId);

	void update(@Param("tabId") String id, @Param("tabName") String tabName,@Param("iconId") String iconId, @Param("sort") Integer sort, @Param("userId") String userId, @Param("date") Date date);

	void updateDate(@Param("tabId")String tabId);

}
