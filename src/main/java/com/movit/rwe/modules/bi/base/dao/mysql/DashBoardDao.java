package com.movit.rwe.modules.bi.base.dao.mysql;

import org.apache.ibatis.annotations.Param;

import com.movit.rwe.common.persistence.CrudDao;
import com.movit.rwe.common.persistence.annotation.MyBatisDao;
import com.movit.rwe.modules.bi.base.entity.mysql.DashBoard;

@MyBatisDao
public interface DashBoardDao extends CrudDao<DashBoard> {
	
	/**
	 * 
	  * findByStudyId
	  * TODO(这里描述这个方法适用条件 – 可选)
	  * TODO(这里描述这个方法的执行流程 – 可选)
	  * TODO(这里描述这个方法的使用方法 – 可选)
	  * TODO(这里描述这个方法的注意事项 – 可选)
	  *
	  * @Title: findByStudyId
	  * @Description: 假设一个study 有多个dashboard 只取第一个  管理的时候需要维护好 之能有1个
	  * @param @param studyId
	  * @param @return    
	  * @return DashBoard    返回类型
	  * @throws
	 */
	DashBoard findByStudyId(@Param("studyId")String studyId);

}
