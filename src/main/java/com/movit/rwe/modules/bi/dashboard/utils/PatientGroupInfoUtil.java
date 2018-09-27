package com.movit.rwe.modules.bi.dashboard.utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PatientGroupInfoUtil {

	/**
	 * 
	 * @Title: sortGroupids @Description: 重新排序 @param @param
	 * groupIds @param @return @return String @throws
	 */
	public static String sortGroupids(List<String> groupIds) {
		Collections.sort(groupIds, new Comparator<String>() {
			@Override
			public int compare(String obj1, String obj2) {
				String type1 = obj1.split("_")[0];
				Integer id1 = Integer.parseInt(obj1.split("_")[1]);
				String type2 = obj2.split("_")[0];
				Integer id2 = Integer.parseInt(obj2.split("_")[1]);
				
				if (type1.compareTo(type2) == 0) {
					return id1-id2;
				} else {
					return type1.compareTo(type2);
				}
			}
		});
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<groupIds.size();i++){
			if(i==0){
				sb.append("%");
			}
			sb.append(groupIds.get(i) + ",%");
		}
		return sb.toString();
	}
}
