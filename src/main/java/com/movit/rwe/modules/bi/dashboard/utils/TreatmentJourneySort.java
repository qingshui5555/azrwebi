package com.movit.rwe.modules.bi.dashboard.utils;

import java.util.Comparator;
import java.util.Map;
/**
 * TreatmentJourney中倒序排列数据
 *
 */
public class TreatmentJourneySort implements Comparator<Map<String,Object>> {

	@Override
	public int compare(Map<String,Object> o1, Map<String,Object> o2) {
		//根据map中value的数值倒序排列数据，1为向后移，-1向前移，0不动
		Integer v1 = Integer.parseInt(o1.get("value").toString());
		Integer v2 = Integer.parseInt(o2.get("value").toString());
		if(v1>v2){
			return -1;
		}else if(v1<v2){
			return 1;
		}else{
			return 0;
		}
	}

}
