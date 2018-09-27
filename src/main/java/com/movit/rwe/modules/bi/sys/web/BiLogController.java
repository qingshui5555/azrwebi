/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.movit.rwe.modules.bi.sys.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.movit.rwe.common.utils.DatatablesViewPage;
import com.movit.rwe.common.web.BaseController;
import com.movit.rwe.modules.bi.sys.vo.LogRequestVo;
import com.movit.rwe.modules.bi.sys.vo.LogVo;
import com.movit.rwe.modules.sys.entity.Log;
import com.movit.rwe.modules.sys.service.LogService;

/**
 * 
 * @ClassName: LogController
 * @Description: 查询业务日志
 * @author wade.chen@movit-tech.com
 * @date 2016年4月1日 下午5:18:42
 *
 */
@Controller
@RequestMapping(value = "${biPath}/sys/log")
public class BiLogController extends BaseController {

	@Autowired
	private LogService logService;

	// @RequiresPermissions("sys:log:view")
	@RequestMapping(value = { "list", "" })
	public String list(Log log, HttpServletRequest request, HttpServletResponse response, Model model) {
		// Page<Log> page = logService.findPage(new Page<Log>(request,
		// response), log);
		model.addAttribute("log", log);
		return "modules/bi/sys/logList";
	}

	@RequestMapping("/getLoglist")
	@ResponseBody
	public DatatablesViewPage getLoglist(LogRequestVo logRequestVo) {

		int logVoCount = logService.getLogListCount(logRequestVo);
		List<LogVo> logVoList = logService.getLogList(logRequestVo);

		DatatablesViewPage d = new DatatablesViewPage();
		d.setAaData(logVoList);
		d.setiTotalDisplayRecords(logVoCount);
		d.setiTotalRecords(logVoCount);
		return d;
	}

	@RequestMapping("/logException")
	public ModelAndView logException(String id) {
		ModelAndView mav = new ModelAndView("modules/bi/sys/logException");
		String exception = logService.getLogException(id);
		mav.addObject("logException", exception);

		return mav;
	}
}
