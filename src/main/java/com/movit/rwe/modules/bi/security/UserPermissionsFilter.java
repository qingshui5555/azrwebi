/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.movit.rwe.modules.bi.security;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 表单验证（包含验证码）过滤类
 * @author ThinkGem
 * @version 2014-5-19
 */
@Service
public class UserPermissionsFilter extends org.apache.shiro.web.filter.authc.UserFilter {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		boolean result =super.isAccessAllowed(request, response, mappedValue);
		if(logger.isDebugEnabled()){
			logger.debug("request:{},response:{},mappedValue:{},result:{}",new Object[]{request,response,mappedValue,result});
		}
		
		return result;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		boolean result = super.onAccessDenied(request, response);
		if(logger.isDebugEnabled()){
			logger.debug("request:{},response:{},:{}",new Object[]{request,response,});
		}
		return result;
	}

	
}