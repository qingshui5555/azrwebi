package com.movit.rwe.modules.bi.da.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.movit.rwe.modules.bi.base.dao.mysql.DaViewDao;
import com.movit.rwe.modules.bi.base.entity.enums.DelFlagEnum;
import com.movit.rwe.modules.bi.base.entity.mysql.DaView;
import com.movit.rwe.modules.bi.da.test.dataframe.DataFrameInvoke;

@Service
public class DaService {
	
	Gson gson = new Gson();

	@Autowired
	DaViewDao daViewDao;
	
	public DaView get(String daViewId){
		return daViewDao.get(daViewId);
	}
	
	public boolean daViewSaveToWorkSpace(DataFrameInvoke dfi,String title,String remarks){
		
		DaView dv = new DaView();
		dv.setTaId(dfi.getTaId());
		dv.setStudyId(dfi.getStudyId());
		dv.setRemarks(remarks);
		dv.setTitle(title);
		dv.setName(title);
		dv.setDelFlag(DelFlagEnum.normal.getCode()+"");
		
		String dfiJsonStr = gson.toJson(dfi);
		dv.setConfJsonStr(dfiJsonStr);
		
		dv.preInsert();
		
		int resultCount = daViewDao.insert(dv);
		
		return resultCount>0;
	}
	
	public boolean daViewUpdateToWorkSpace(DataFrameInvoke dfi,String title,String remarks,String daViewId){
		
		DaView dv = new DaView();
		dv.setId(daViewId);
		dv.setTaId(dfi.getTaId());
		dv.setStudyId(dfi.getStudyId());
		dv.setRemarks(remarks);
		dv.setTitle(title);
		dv.setName(title);
		dv.setDelFlag(DelFlagEnum.normal.getCode()+"");
		
		String dfiJsonStr = gson.toJson(dfi);
		dv.setConfJsonStr(dfiJsonStr);
		
		dv.preUpdate();
		
		int resultCount = daViewDao.update(dv);
		
		return resultCount>0;
	}
}
