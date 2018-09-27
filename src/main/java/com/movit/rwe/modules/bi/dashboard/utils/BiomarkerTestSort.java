package com.movit.rwe.modules.bi.dashboard.utils;

import java.util.Comparator;

import com.movit.rwe.modules.bi.base.entity.hive.BiomarkerTest;

public class BiomarkerTestSort implements Comparator<BiomarkerTest> {

	@Override
	public int compare(BiomarkerTest o1, BiomarkerTest o2) {
		long m1Time = o1.getTestDate().getTime();
		long m2Time = o2.getTestDate().getTime();

		if (m1Time > m2Time) {
			return 1;
		} else if (m1Time < m2Time) {
			return -1;
		}
		return 0;
	}

}
