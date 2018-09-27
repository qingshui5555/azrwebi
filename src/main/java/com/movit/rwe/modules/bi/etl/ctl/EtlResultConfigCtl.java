package com.movit.rwe.modules.bi.etl.ctl;

import java.util.*;
import com.movit.rwe.common.config.GlobalConstant;
import com.movit.rwe.modules.bi.dashboard.common.ViewInvokeParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.movit.rwe.modules.bi.etl.service.EtlResultConfigService;


@Controller
@RequestMapping(value = "${biPath}/etl/config")
public class EtlResultConfigCtl {
	private static Logger logger = LoggerFactory.getLogger(EtlResultConfigCtl.class);
	@Autowired
	private EtlResultConfigService etlResultConfigService;

	@RequestMapping("index")
	private ModelAndView showChartById(ViewInvokeParams viewInvokeParams) {
		logger.info("@@@In EtlResultConfigCtl.showChartById begin");
		ModelAndView mav = new ModelAndView("modules/bi/etl/etlResultConfig");
		mav.addObject(GlobalConstant.STR_ATTR_ETL_RESULT_CONFIG_DATA_LIST, etlResultConfigService.queryListEtlResultConfig());

		logger.info("@@@In EtlResultConfigCtl.showChartById end");
		return mav;
	}


	/**
	 * 删除单条配置
	 */
	@RequestMapping("row/remove")
	@ResponseBody
	private Map<String, Object> removeSingleRow(Long id) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int result = etlResultConfigService.removeConfigRow(id);
		resultMap.put("success", result > 0);
		return resultMap;
	}

	/**
	 * 修改单条配置
	 */
	@RequestMapping("row/update")
	@ResponseBody
	private Map<String, Object> updateSingleRow(String jsonData) {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		int result = etlResultConfigService.updateConfigRow(jsonData);
		resultMap.put("success", result > 0);
		return resultMap;
	}

	@RequestMapping("/import")
	public ModelAndView importExcel(@RequestParam("excel_file") MultipartFile file) {
		ModelAndView mav = new ModelAndView(new RedirectView("index"));
		int result = etlResultConfigService.importByExcel(file);
		mav.addObject("success", result > 0);
		return mav;
	}

}
