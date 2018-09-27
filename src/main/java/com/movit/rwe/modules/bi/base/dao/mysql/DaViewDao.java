package com.movit.rwe.modules.bi.base.dao.mysql;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.movit.rwe.common.persistence.CrudDao;
import com.movit.rwe.common.persistence.annotation.MyBatisDao;
import com.movit.rwe.modules.bi.base.entity.mysql.DaView;
import com.movit.rwe.modules.bi.da.vo.WorkSpaceDaViewShowVo;

@MyBatisDao
public interface DaViewDao extends CrudDao<DaView> {

	List<DaView> findListByStudyId(@Param("studyId")String studyId);
	
	List<DaView> findAllList();
	
	List<WorkSpaceDaViewShowVo> findAllWsShowList();
	
	List<WorkSpaceDaViewShowVo> findAllWsShowListByUserId(String userId);
}
