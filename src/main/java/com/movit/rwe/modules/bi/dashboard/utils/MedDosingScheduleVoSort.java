package com.movit.rwe.modules.bi.dashboard.utils;

import java.util.Comparator;

import com.movit.rwe.modules.bi.dashboard.vo.MedDosingScheduleVo;

/**
 * 
 * @Project : az-rwe-bi
 * @Title : MedDosingScheduleSort.java
 * @Package com.movit.rwe.modules.bi.treatment.utils
 * @Description : 药物比较排序
 * @author Yuri.Zheng
 * @email Yuri.Zheng@movit-tech.com
 * @date 2016年3月8日 下午4:21:40
 *
 */
public class MedDosingScheduleVoSort implements Comparator<MedDosingScheduleVo> {

	/**
	 * 先根据时间排序 在根据治疗路径的治疗方案排序
	 */
	@Override
	public int compare(MedDosingScheduleVo m1, MedDosingScheduleVo m2) {
		long m1Time = m1.getDosingDate().getTime();
		long m2Time = m2.getDosingDate().getTime();
		String m1Solution = m1.getTreatmentSolution();
		String m2Solution = m2.getTreatmentSolution();

		if (m1Time > m2Time) {
			return 1;
		} else if (m1Time < m2Time) {
			return -1;
		} else {
			return m1Solution.compareTo(m2Solution);
		}
	}

}
