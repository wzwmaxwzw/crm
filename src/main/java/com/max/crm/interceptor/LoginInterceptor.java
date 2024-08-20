package com.max.crm.interceptor;

import com.max.crm.exceptions.NoLoginException;
import com.max.crm.service.UserService;
import com.max.crm.utils.LoginUserUtil;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Resource
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        if(userId==null ||userService.selectByPrimaryKey(userId)==null){
            throw new NoLoginException();
        }
        return true;
    }
}
