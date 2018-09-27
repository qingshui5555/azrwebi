package com.movit.rwe.common.utils;

import com.movit.rwe.modules.sys.entity.User;
import com.movit.rwe.modules.sys.service.SystemService;
import com.movit.rwe.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.util.*;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * Created by Mike on 2016/4/14.
 */
@Service
public class I18NUtils {

    @Autowired
    @Qualifier("messageSource")
    private ResourceBundleMessageSource messageSource;

    @Autowired
    @Qualifier("localeResolver")
    private CookieLocaleResolver localeResolver;

    @Autowired
    private SystemService systemService;

    /**
     * 获取国际化字符串
     * @param mKey message key
     * @param params params in message
     * @return
     */
    public String get(String mKey, String...params) {
        String localeStr = UserUtils.getUser().getLocale();

        Locale locale = Locale.CHINESE; // default locale

        if("en".equalsIgnoreCase(localeStr)) {
            locale = Locale.ENGLISH;
        }
        return getByLocal(locale, mKey, params);
    }

    /**
     * 获取国际化字符串
     * @param mKey message key
     * @param params params in message
     * @return
     */
    public String getByLocal(Locale locale, String mKey, String...params) {
        return messageSource.getMessage(mKey, params, locale);
    }

    /**
     * 中英文切换. 默认为中文
     * @param request
     * @param response
     * @return
     */
    public void settingLocale(HttpServletRequest request, HttpServletResponse response) {
        User user = UserUtils.getUser();
        Locale toLocale = null;

        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
	        for (Cookie cookie : cookies) {
	            if("_change_locale".equalsIgnoreCase(cookie.getName())) {
	                toLocale = new Locale(cookie.getValue());
	                break;
	            }
	        }
        }

        if(toLocale != null) {
        	if(user!=null&&user.getLocale()!=null&&!user.getLocale().equals(toLocale.toString())){
        		systemService.updateLocaleById(user.getId(), toLocale.toString());
        	}
            localeResolver.setLocale(request, response, toLocale);
        } else {
        	//当cookie中_change_locale为空则取用户数据库中保存的locale值
        	if(user.getLocale()!=null){
        		Locale locale = new Locale(user.getLocale());
        		localeResolver.setLocale(request, response, locale);
        	}else{
        		localeResolver.setLocale(request, response, Locale.CHINESE);
        	}
        }
    }
    
    public void settingLocale(HttpServletRequest request, HttpServletResponse response,String lang) {
        User user = UserUtils.getUser();

        if(Locale.CHINESE.toString().equals(lang)||Locale.CHINA.toString().equals(lang)){
        	localeResolver.setLocale(request, response, Locale.CHINESE);
        	systemService.updateLocaleById(user.getId(), Locale.CHINESE.toString());
		}else{
			localeResolver.setLocale(request, response, Locale.ENGLISH);
			systemService.updateLocaleById(user.getId(), Locale.ENGLISH.toString());
		}
    }
    
}
