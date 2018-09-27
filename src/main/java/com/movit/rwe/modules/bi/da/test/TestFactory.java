package com.movit.rwe.modules.bi.da.test;

import com.movit.rwe.common.utils.SpringContextHolder;
import com.movit.rwe.modules.bi.da.enums.DaTestEnum;

public class TestFactory {

	public static TestAbs getTestByBeanName(String beanName){
		return SpringContextHolder.getBean(beanName);
	}
	
	public static TestAbs getTestByTestName(String testName){
		DaTestEnum de = DaTestEnum.loadByName(testName);
		return TestFactory.getTestByBeanName(de.getBeanName());
	}
}
