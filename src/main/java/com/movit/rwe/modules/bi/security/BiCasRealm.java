package com.movit.rwe.modules.bi.security;




import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.movit.rwe.modules.sys.entity.Office;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cas.CasAuthenticationException;
import org.apache.shiro.cas.CasRealm;
import org.apache.shiro.cas.CasToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.TicketValidationException;
import org.jasig.cas.client.validation.TicketValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.movit.rwe.common.config.Global;
import com.movit.rwe.common.security.Principal;
import com.movit.rwe.common.utils.SpringContextHolder;
import com.movit.rwe.common.web.Servlets;
import com.movit.rwe.modules.sys.entity.Menu;
import com.movit.rwe.modules.sys.entity.Role;
import com.movit.rwe.modules.sys.entity.User;
import com.movit.rwe.modules.sys.service.SystemService;
import com.movit.rwe.modules.sys.utils.LogUtils;
import com.movit.rwe.modules.sys.utils.UserUtils;

public class BiCasRealm extends CasRealm {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private SystemService systemService;

	public BiCasRealm() {
		super();
	}
	
	/**
	 * 获取系统业务对象
	 */
	public SystemService getSystemService() {
		if (systemService == null){
			systemService = SpringContextHolder.getBean(SystemService.class);
		}
		return systemService;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		
        CasToken casToken = (CasToken) token;
        if (token == null) {
            return null;
        }
        
        String ticket = (String)casToken.getCredentials();
        if (!org.apache.shiro.util.StringUtils.hasText(ticket)) {
            return null;
        }
        
        TicketValidator ticketValidator = ensureTicketValidator();

        try {
            // contact CAS server to validate service ticket
            Assertion casAssertion = ticketValidator.validate(ticket, getCasService());
            // get principal, user id and attributes
            AttributePrincipal casPrincipal = casAssertion.getPrincipal();
            String userId = casPrincipal.getName();
            logger.debug("Validate ticket : {} in CAS server : {} to retrieve user : {}", new Object[]{
                    ticket, getCasServerUrlPrefix(), userId
            });
            
            //根据userid查询是否存在该用户
            User user = getSystemService().getUserByLoginName(userId);
            if(user == null) {
				String defaultRoleId = Global.getDefaultRole();
				User newUser = new User();
				newUser.setCompany(new Office(Global.getDefaultCompany()));
				newUser.setOffice(new Office(Global.getDefaultOffice()));
				newUser.setLoginName(userId);
				newUser.setName(userId);
				//TODO: adding studies
				List<Role> roleList = Lists.newArrayList();
				roleList.add(new Role(defaultRoleId));
				newUser.setRoleList(roleList);
				getSystemService().saveUser(newUser);
			}

            Map<String, Object> attributes = casPrincipal.getAttributes();
            // refresh authentication token (user id + remember me)
            casToken.setUserId(userId);
            String rememberMeAttributeName = getRememberMeAttributeName();
            String rememberMeStringValue = (String)attributes.get(rememberMeAttributeName);
            boolean isRemembered = rememberMeStringValue != null && Boolean.parseBoolean(rememberMeStringValue);
            if (isRemembered) {
                casToken.setRememberMe(true);
            }
            // create simple authentication info
//            List<Object> principals = CollectionUtils.asList(userId, attributes);
            
            Principal principal = new Principal(user, false);
            
            PrincipalCollection principalCollection = new SimplePrincipalCollection(principal, principal.getName());
            return new SimpleAuthenticationInfo(principalCollection, ticket);
        } catch (TicketValidationException e) { 
        	throw new CasAuthenticationException("Unable to validate ticket [" + ticket + "]", e);
        }
		
		
//		AuthenticationInfo auth = super.doGetAuthenticationInfo(token);
		
		//如果执行到这 就说明认证成功了 根据用户ID去初始化本地的session
		
			// 根据用户名查询本地用户是否存在
//		User user = getSystemService().getUserByLoginName(auth.getPrincipals().asList().get(0).toString());
//		
//		if (user != null) {
//			if (Global.NO.equals(user.getLoginFlag())){
//				throw new AuthenticationException("msg:该已帐号禁止登录.");
//			}
//			byte[] salt = Encodes.decodeHex(user.getPassword().substring(0,16));
//			return new SimpleAuthenticationInfo(new Principal(user, false), 
//					user.getPassword().substring(16), ByteSource.Util.bytes(salt), getName());
//		} else {
//			return null;
//		}
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Principal principal = (Principal) getAvailablePrincipal(principals);
		// 获取当前已登录的用户
		if (!Global.TRUE.equals(Global.getConfig("user.multiAccountLogin"))){
			Collection<Session> sessions = getSystemService().getSessionDao().getActiveSessions(true, principal, UserUtils.getSession());
			if (sessions.size() > 0){
				// 如果是登录进来的，则踢出已在线用户
				if (UserUtils.getSubject().isAuthenticated()){
					for (Session session : sessions){
						getSystemService().getSessionDao().delete(session);
					}
				}
				// 记住我进来的，并且当前用户已登录，则退出当前用户提示信息。
				else{
					UserUtils.getSubject().logout();
					throw new AuthenticationException("msg:账号已在其它地方登录，请重新登录。");
				}
			}
		}
		User user = getSystemService().getUserByLoginName(principal.getLoginName());
		if (user != null) {
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			List<Menu> list = UserUtils.getMenuList();
			for (Menu menu : list){
				if (StringUtils.isNotBlank(menu.getPermission())){
					// 添加基于Permission的权限信息
					for (String permission : StringUtils.split(menu.getPermission(),",")){
						info.addStringPermission(permission);
					}
				}
			}
			// 添加用户权限
			info.addStringPermission("user");
			// 添加用户角色信息
			for (Role role : user.getRoleList()){
				info.addRole(role.getEnname());
			}
			// 更新登录IP和时间
			getSystemService().updateUserLoginInfo(user);
			// 记录登录日志
			LogUtils.saveLog(Servlets.getRequest(), "系统登录");
			return info;
		} else {
			return null;
		}
	}
	

}
