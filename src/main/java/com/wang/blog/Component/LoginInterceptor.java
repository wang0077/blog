package com.wang.blog.component;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 设置登录的拦截器
 * @author wangsiyuan
 */
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    private static final String USER = "user";

    @Override
    public boolean preHandle(HttpServletRequest request,
                             @NotNull HttpServletResponse response,
                             @NotNull Object handler) throws Exception {
        if(request.getSession().getAttribute(USER) == null){
            response.sendRedirect("/admin");
            return false;
        }
        return true;
    }
}
