package com.movit.rwe.modules.bi.login.web;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.movit.rwe.common.web.BaseController;

/**
 * 
  * @ClassName: UserLoginCtl
  * @Description: BI系统的登录控制器
  * @author wade.chen@movit-tech.com
  * @date 2016年3月28日 下午2:52:27
  *
 */
@Controller
@RequestMapping("${biPath}/login/")
public class UserLoginCtl extends BaseController {
	
	@RequestMapping("cas")
	public ModelAndView loginByCas(ModelAndView mav){
		mav.setViewName("redirect:"+biPath+"/layout/home");
		return mav;
	}
	
	@RequestMapping("checkStatus")
	@ResponseBody
	public ResponseEntity<Object> checkLoginStatus(){
		Map<String, Boolean> result = new HashMap<String, Boolean>();
		Subject sub = SecurityUtils.getSubject();
		if(sub==null || sub.getPrincipal()==null){
			result.put("isLogin", false);
		}else{
			result.put("isLogin", true);
		}
		
		return new ResponseEntity<Object>(result, HttpStatus.OK);
	}

}
