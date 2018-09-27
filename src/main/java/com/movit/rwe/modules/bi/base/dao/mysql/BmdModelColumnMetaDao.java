package com.movit.rwe.modules.bi.base.dao.mysql;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.movit.rwe.common.persistence.CrudDao;
import com.movit.rwe.common.persistence.annotation.MyBatisDao;
import com.movit.rwe.modules.bi.base.entity.mysql.BmdModelColumnMeta;

@MyBatisDao
public interface BmdModelColumnMetaDao extends CrudDao<BmdModelColumnMeta> {
	
	/**
	 * 
	  * loadByModelId
	  * @Title: loadByModelId
	  * @Description: 根据模型对象的ID查找列对象
	  * @param @param modelId
	  * @param @return    
	  * @return List<BmdModelColumnMeta>    返回类型
	  * @throws
	 */
	public List<BmdModelColumnMeta> loadByModelMetaId(@Param("modelMetaId")String modelMetaId);
	
}
