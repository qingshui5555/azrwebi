package com.movit.rwe.modules.sys.interceptor;

import com.movit.rwe.common.service.BaseService;
import com.movit.rwe.common.utils.I18NUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Mike on 2016/4/14.
 */
public class LocaleInterceptor extends BaseService implements HandlerInterceptor {

    @Autowired
    private I18NUtils i18NUtils;

    @Override
    @Transactional(readOnly = false)
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        i18NUtils.settingLocale(request, response);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {

    }
}
