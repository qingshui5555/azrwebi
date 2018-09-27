package com.movit.rwe.modules.bi.base.dao.mysql;

import java.util.List;

import com.movit.rwe.modules.bi.base.entity.mysql.Study;
import org.apache.ibatis.annotations.Param;

import com.movit.rwe.common.persistence.CrudDao;
import com.movit.rwe.common.persistence.annotation.MyBatisDao;
import com.movit.rwe.modules.bi.base.entity.mysql.UserStudy;
import com.movit.rwe.modules.sys.entity.Menu;

/**
 * 
  * @ClassName: UserStudyDao
  * @Description: 用户和study的关联表
  * @author wade.chen@movit-tech.com
  * @date 2016年3月29日 下午6:41:34
  *
 */
@MyBatisDao
public interface UserStudyDao extends CrudDao<UserStudy> {
	
	/**
	 * 
	  * findDashBoardMenuListByUserId
	  * TODO(这里描述这个方法适用条件 – 可选)
	  * TODO(这里描述这个方法的执行流程 – 可选)
	  * TODO(这里描述这个方法的使用方法 – 可选)
	  * TODO(这里描述这个方法的注意事项 – 可选)
	  *
	  * @Title: findByUserId
	  * @Description: 根据用户ID查询其所拥有的study权限
	  * @param @param userId
	  * @param @return    
	  * @return List<UserStudy>    返回类型
	  * @throws
	 */
	List<Menu> findDashBoardMenuListByUserId(@Param("userId")String userId);

	List<Study> findStudiesByUserId(@Param("userId")String userId);

	/**
	 * 查询用户下的研究列表
	 * @param userId
	 * @return
	 */
	List<UserStudy> queryListByUserId(@Param("userId") String userId);

	void deleteUserStudyByUser(@Param("userId")String userId);
}
