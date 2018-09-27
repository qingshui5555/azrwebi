package com.movit.rwe.modules.bi.etl.ctl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.movit.rwe.common.web.BaseController;
import com.movit.rwe.modules.bi.base.entity.mysql.JobsLog;
import com.movit.rwe.modules.bi.etl.service.JobsLogService;

@Controller
@RequestMapping(value = "${biPath}/jobsLog")
public class JobsLogCtl extends BaseController{

	@Autowired
	private JobsLogService jobsLogService;
	
	@RequestMapping("/list")
	public ModelAndView list(ModelAndView mv){
		List <JobsLog> jobsLogList = jobsLogService.findList(new JobsLog());
		mv.addObject("jobsLogList", jobsLogList);
		mv.setViewName("modules/bi/etl/jobsLogList");
		return mv;
	}
	
}
