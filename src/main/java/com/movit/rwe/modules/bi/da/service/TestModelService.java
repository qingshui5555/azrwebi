package com.movit.rwe.modules.bi.da.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movit.rwe.modules.bi.base.dao.mysql.DaTestModelDao;
import com.movit.rwe.modules.bi.base.entity.mysql.DaTestModel;
import com.movit.rwe.modules.bi.da.test.model.TestModel;

@Service
public class TestModelService {
	
	@Autowired
	DaTestModelDao daTestModelDao;
	
	public List<TestModel> findListByTestName(String testName){
		
		List<DaTestModel> dtmList = daTestModelDao.findListByTestName(testName);
		List<TestModel> tmList = null;
		if(dtmList!=null && dtmList.size()>0){
			tmList = new ArrayList<TestModel>();
			for(DaTestModel dtm :dtmList){
				tmList.add(new TestModel(dtm));
			}
		}
		
		return tmList;
	}
	
	public boolean insert(TestModel testModel){
		if(testModel==null)
			throw new RuntimeException("TestModel is null!");
		
		DaTestModel dm = (DaTestModel)testModel;
		dm.preInsert();
		return daTestModelDao.insert(dm)>0;
	}

}
