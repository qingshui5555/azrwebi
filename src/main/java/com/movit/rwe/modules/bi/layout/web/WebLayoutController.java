package com.movit.rwe.modules.bi.layout.web;

import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.movit.rwe.modules.bi.dashboard.web.DashBoardCtl;
import com.movit.rwe.modules.bi.etl.service.RweUserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.movit.rwe.common.web.BaseController;
import com.movit.rwe.modules.sys.utils.UserUtils;

@Controller
@Scope("prototype")
@RequestMapping(value = "${biPath}/layout/")
public class WebLayoutController extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(WebLayoutController.class);

	@Value("${cas.server.logout.url}?service=${cas.project.url}")
	String casLogOutUrl;

	@Value("${rwe.project.change.password.url}")
	String changePasswordUrl;

	@Autowired
	private RweUserService rweUserService;
	
	@RequestMapping("head")
	public ModelAndView goHead(ModelAndView mv){
		logger.info("@@@In WebLayoutController.goHead begin");
		mv.setViewName("modules/bi/layout/head");
		logger.info("@@@In WebLayoutController.goHead end");
		return mv;
	}
	
	@RequestMapping("resource")
	public ModelAndView goResource(ModelAndView mv,HttpServletRequest request){
		logger.info("@@@In WebLayoutController.goResource begin");
		String style="";
		Cookie[] cookies = request.getCookies();
		if(cookies!=null){
	        for (Cookie cookie : cookies) {
	            if("_change_style".equalsIgnoreCase(cookie.getName())) {
	            	style = cookie.getValue();
	                break;
	            }
	        }
        }
		if(StringUtils.isBlank(style)){
			style = "white";
		}
		mv.addObject("style", style);
		mv.setViewName("modules/bi/layout/resource");
		logger.info("@@@In WebLayoutController.goResource end");
		return mv;
	}
	
	@RequestMapping("sidebar")
	public ModelAndView goSideBar(ModelAndView mv){
		logger.info("@@@In WebLayoutController.goSideBar begin");
		mv.setViewName("modules/bi/layout/sidebar");
		logger.info("@@@In WebLayoutController.goSideBar end");
		return mv;
	}
	
	@RequestMapping("settings")
	public ModelAndView goSettings(ModelAndView mv){
		mv.setViewName("modules/bi/layout/settings");
		return mv;
	}
	
	@RequestMapping("foot")
	public ModelAndView goFoot(ModelAndView mv){
		mv.setViewName("modules/bi/layout/foot");
		return mv;
	}
	
	@RequestMapping("home")
	public ModelAndView goHome(ModelAndView mv){
		logger.info("@@@In WebLayoutController.goHome begin");
		mv.setViewName("modules/bi/layout/home");
		String forcePassword = rweUserService.getForcePassword(UserUtils.getUser().getCurrentUser().getLoginName());
		mv.addObject("forcePassword", forcePassword);
		mv.addObject("changePasswordUrl", changePasswordUrl);
		logger.info("@@@In WebLayoutController.goHome end");
		return mv;
	}
	
	@RequestMapping("logout")
	public ModelAndView logout(ModelAndView mv){
		UserUtils.getSubject().logout();
		mv.setViewName("redirect:"+casLogOutUrl);
		return mv;
	}

}
