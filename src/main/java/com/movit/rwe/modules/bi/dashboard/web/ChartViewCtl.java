package com.movit.rwe.modules.bi.dashboard.web;

import com.movit.rwe.modules.bi.base.entity.mysql.DemographicView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.movit.rwe.common.web.BaseController;
import com.movit.rwe.modules.bi.base.entity.mysql.Chart;
import com.movit.rwe.modules.bi.dashboard.common.ChartInvokeParams;
import com.movit.rwe.modules.bi.dashboard.service.ChartService;

import java.util.List;

@Controller
@RequestMapping(value = "${biPath}/chart/")
public class ChartViewCtl extends BaseController {
	
	@Autowired
	ChartService chartService;
	
	@RequestMapping("show")
	public ModelAndView showChartById(ChartInvokeParams cip){
		
		Chart chart =  chartService.get(cip.getChartId());
		ModelAndView model = new ModelAndView("modules/bi/dashboard/chart");
		model.addObject("cip", cip);
		model.addObject("chart", chart);
		
		return model;
	}
}
