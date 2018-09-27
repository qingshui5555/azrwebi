package com.movit.rwe.modules.bi.base.dao.mysql;

import org.apache.ibatis.annotations.Param;

import com.movit.rwe.common.persistence.annotation.MyBatisOpenmrsDao;

/**
 * 
  * @ClassName: UserStudyDao
  * @Description: 用户和study的关联表
  * @author wade.chen@movit-tech.com
  * @date 2016年3月29日 下午6:41:34
  *
 */
@MyBatisOpenmrsDao
public interface RweUserStudyDao {

	public String getRweUserStudyIds(@Param("loginName")String loginName);

	public Integer getRweUserByLoginName(@Param("loginName")String loginName);
}
