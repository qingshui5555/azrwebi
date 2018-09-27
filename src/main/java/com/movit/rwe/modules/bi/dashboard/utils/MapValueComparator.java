package com.movit.rwe.modules.bi.dashboard.utils;

import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
/**
 * 比较器类
 * @author Administrator
 *
 */
public class MapValueComparator implements Comparator<Map.Entry<String, Integer>> {

	@Override
	public int compare(Entry<String, Integer> me1, Entry<String, Integer> me2) {

		return me1.getValue().compareTo(me2.getValue());
	}

}