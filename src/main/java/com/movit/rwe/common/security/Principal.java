package com.movit.rwe.common.security;

import java.io.Serializable;

import com.movit.rwe.modules.sys.entity.User;
import com.movit.rwe.modules.sys.utils.UserUtils;

/**
 * 用户授权对象
  * @ClassName: Principal
  * @Description: TODO
  * @author wade.chen@movit-tech.com
  * @date 2016年3月29日 下午3:41:38
  *
 */
public class Principal implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id; // 编号
	private String loginName; // 登录名
	private String name; // 姓名
	private boolean mobileLogin; // 是否手机登录

	public Principal(User user, boolean mobileLogin) {
		this.id = user.getId();
		this.loginName = user.getLoginName();
		this.name = user.getName();
		this.mobileLogin = mobileLogin;
	}

	public String getId() {
		return id;
	}

	public String getLoginName() {
		return loginName;
	}

	public String getName() {
		return name;
	}

	public boolean isMobileLogin() {
		return mobileLogin;
	}

	/**
	 * 获取SESSIONID
	 */
	public String getSessionid() {
		try{
			return (String) UserUtils.getSession().getId();
		}catch (Exception e) {
			return "";
		}
	}
	
	@Override
	public String toString() {
		return id;
	}

}
